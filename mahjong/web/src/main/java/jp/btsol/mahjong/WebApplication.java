package jp.btsol.mahjong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * WEB application for Mahjong
 * @author B&T Solutions Inc.
 *
 */
@SpringBootApplication
public class WebApplication {
	/**
	 * main(entry point)
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
