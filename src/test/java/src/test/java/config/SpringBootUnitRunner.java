package src.test.java.config;

import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.runners.model.InitializationError;

import com.bookstore.bookstoreapplication.BookStoreApplication;

public class SpringBootUnitRunner extends ZeroCodeUnitRunner {
	static {
		BookStoreApplication.start();
	}
	public SpringBootUnitRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
		// TODO Auto-generated constructor stub
	}
}
