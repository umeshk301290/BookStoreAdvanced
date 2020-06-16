package com.bookstore.bookstoreapplication.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bookstore.bookstoreapplication.entity.BookStoreInformation;
import com.bookstore.bookstoreapplication.exception.BookStoreInformationException;
import com.bookstore.bookstoreapplication.service.BookStoreInformationService;
import com.hazelcast.core.HazelcastInstance;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author umeshkumar01
 *
 */
@RestController
@Slf4j
@Api(value="onlinebookstore", description="Book store managment")
public class BookStoreInformationController {

	@Autowired
	BookStoreInformationService bookInformationService;
	
	@Autowired
	HazelcastInstance instance;
	
	@Autowired
	RestTemplate template;

	/**
	 * @param book
	 * @return
	 * @throws BookStoreInformationException
	 */
	@PostMapping(value = "/books")
    @ApiOperation(value = "Add a new book",response = BookStoreInformation.class)
	public ResponseEntity<BookStoreInformation> addBookInformation(@Valid @RequestBody BookStoreInformation book)
			throws BookStoreInformationException {
		log.info("going to add a book for isbn {} , title {} , author {}", book.getIsbn(), book.getTitle(),
				book.getAuthor());
		ResponseEntity<BookStoreInformation> bookStoreInformationResponse = bookInformationService
				.addBookInformation(book);
		Map<String,BookStoreInformation> hm = instance.getMap("my-map");
		hm.put(bookStoreInformationResponse.getBody().getIsbn(), bookStoreInformationResponse.getBody());
		return bookStoreInformationResponse;
	}

	/**
	 * @param isbn
	 * @return
	 * @throws BookStoreInformationException
	 */
	@GetMapping(value = "/books/{isbn}")
    @ApiOperation(value = "Find a new book",response = BookStoreInformation.class)
	public ResponseEntity<BookStoreInformation> fetchBookBasedOnIsbn(@PathVariable("isbn") String isbn)
			throws BookStoreInformationException {
		log.info("searching a book with isbn {} ", isbn);
		ResponseEntity<BookStoreInformation> bookStoreInformationResponse = bookInformationService
				.fetchBookInformation(isbn);
		Map<String,BookStoreInformation> hm = instance.getMap("my-map");
		hm.get(bookStoreInformationResponse.getBody().getIsbn());

		return bookStoreInformationResponse;
	}

	/**
	 * @param title
	 * @return
	 * @throws BookStoreInformationException
	 */
	@GetMapping(value = "books/titles/{title}")
    @ApiOperation(value = "Find a book based on title",response = BookStoreInformation.class)
	public ResponseEntity<List<BookStoreInformation>> fetchBookBasedOnTitle(@PathVariable("title") String title)
			throws BookStoreInformationException {
		log.info("searching  books with title {} ", title);
		ResponseEntity<List<BookStoreInformation>> bookStoreInformationResponse = bookInformationService
				.fetchBookInformationFromTitle(title);
		return bookStoreInformationResponse;
	}

	/**
	 * @param author
	 * @return
	 * @throws BookStoreInformationException
	 */
	@GetMapping(value = "books/authors/{author}")
    @ApiOperation(value = "Find a book based on author",response = BookStoreInformation.class)
	public ResponseEntity<List<BookStoreInformation>> fetchBookBasedOnAuthor(@PathVariable("author") String author)
			throws BookStoreInformationException {
		log.info("searching  books with author {} ", author);
		ResponseEntity<List<BookStoreInformation>> bookStoreInformationResponse = bookInformationService
				.fetchBookInformationFromAuthor(author);
		return bookStoreInformationResponse;

	}

	/**
	 * @param isbn
	 * @return
	 * @throws BookStoreInformationException
	 */
	@GetMapping(value = "books/mediacoverages/{isbn}")
    @ApiOperation(value = "Find media coverage of book",response = BookStoreInformation.class)
	public ResponseEntity<List<String>> searchMediaCoverage(@PathVariable("isbn") String isbn)
			throws BookStoreInformationException {
		log.info("searching  media coverages for book for isbn {} ", isbn);
		ResponseEntity<List<String>> responseList = bookInformationService.searchMediaCoverage(isbn);
		return responseList;

	}

	/**
	 * @param isbn
	 * @param quantity
	 * @return
	 * @throws BookStoreInformationException
	 */
	@PatchMapping(value = "books/{isbn}")
    @ApiOperation(value = "buy a book",response = BookStoreInformation.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request", response = BookStoreInformationException.class),
            @ApiResponse(code = 401, message = "Not authorized", response = BookStoreInformationException.class),
            @ApiResponse(code = 404, message = "Not Found", response = BookStoreInformationException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = BookStoreInformationException.class),
            @ApiResponse(code = 500, message = "Technical error", response = BookStoreInformationException.class) })
	public ResponseEntity<BookStoreInformation> buyBook(@PathVariable("isbn") String isbn,
			@RequestParam("quantity") Integer quantity) throws BookStoreInformationException {
		log.info("going to buy {} copies of book for isbn {}", quantity, isbn);
		ResponseEntity<BookStoreInformation> bookStoreInformationResponse = bookInformationService.purchaseBook(isbn,
				quantity);

		return bookStoreInformationResponse;
	}

}
