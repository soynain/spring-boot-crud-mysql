package com.crudspringdbconn.main;


import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.crudspringdbconn.main.services.FilesStorageService;

@SpringBootApplication
public class MainApplication implements CommandLineRunner{
	@Resource
	FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		/*So we start the service without deleting any of the files from
		 * the server, to do that you just need to mark as comment
		 * the deleteAll() method call line
		*/
		storageService.deleteAll();
		storageService.init();
	 
	  
	}
}
