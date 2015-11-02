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

public class GuestbookResource extends ServerResource {

	@Get("XML")
	public Guestbook represent() {

		String name = (String) getRequest().getAttributes().get("guestbookname");

		if (name == null || name.isEmpty()) {
			System.out.println("error");
		}

		Guestbook gb = new Guestbook();
		gb.book = name;

		List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).ancestor(gb).order("-date")
				.list();

		GuestbookRepresentation gbr = new GuestbookRepresentation();
		gbr.book = name;
		gbr.greetings = greetings;

		return gbr;
	}

}
