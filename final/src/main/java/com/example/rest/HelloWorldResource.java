package com.example.rest;

import org.restlet.resource.ServerResource;

public class HelloWorldResource extends ServerResource {

	public String represent() {

//		Greeting g = new Greeting("book", "content", "id", "email");
		return "hello, world (from the cloud!)";
	}

}
