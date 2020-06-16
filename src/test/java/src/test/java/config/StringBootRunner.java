package src.test.java.config;

import org.jsmart.zerocode.core.runner.ZeroCodePackageRunner;
import org.junit.runners.model.InitializationError;

import com.bookstore.bookstoreapplication.BookStoreApplication;

public class StringBootRunner extends ZeroCodePackageRunner {

	static {
		BookStoreApplication.start();
	}
	public StringBootRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
		// TODO Auto-generated constructor stub
	}

}
