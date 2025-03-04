package web6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Web6Application {

	public static void main(String[] args) {
		SpringApplication.run(Web6Application.class, args);
        try {
            openHomePage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	private static void openHomePage() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080/");
	}
}
