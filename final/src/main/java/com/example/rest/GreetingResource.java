package com.example.rest;

import java.util.List;

import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.example.guestbook.Greeting;
import com.example.guestbook.Guestbook;
import com.google.appengine.repackaged.com.google.api.client.json.jackson2.JacksonFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class GreetingResource extends ServerResource {

	@Get("XML")
	public Greeting represent() {

		String id = (String) getRequest().getAttributes().get("id");

		if (id == null || id.isEmpty()) {
			System.out.println("error");
		}

		Long idLong = Long.parseLong(id);

		Guestbook gb = new Guestbook();
		gb.book = "default";

		Greeting greeting = ObjectifyService.ofy().load().type(Greeting.class).parent(gb).id(idLong).now();

		return greeting;
	}

}
