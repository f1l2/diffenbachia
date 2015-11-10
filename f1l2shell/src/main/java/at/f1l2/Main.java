package at.f1l2;

import java.io.IOException;

import org.springframework.shell.Bootstrap;

public class Main {

	/**
	 * Main class that delegates to Spring Shell's Bootstrap class in order to simplify debugging inside an IDE
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Bootstrap.main(args);

	}

}
