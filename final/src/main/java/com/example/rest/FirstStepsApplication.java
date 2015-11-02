package com.example.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class FirstStepsApplication extends Application {

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a
		// new instance of HelloWorldResource.
		Router router = new Router(getContext());

		// guestbook/?name

		router.attach("/greeting/{id}", GreetingResource.class);
		router.attach("/guestbook", GuestbookResourceDefault.class);
		router.attach("/guestbook/{guestbookname}", GuestbookResource.class);

		// Defines only one route
		router.attachDefault(HelloWorldResource.class);
		return router;
	}
}