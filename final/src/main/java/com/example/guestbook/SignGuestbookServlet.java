/**
 * Copyright 2014-2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//[START all]
package com.example.guestbook;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

/**
 * Form Handling Servlet
 * Most of the action for this sample is in webapp/guestbook.jsp, which displays the
 * {@link Greeting}'s. This servlet has one method
 * {@link #doPost(<#HttpServletRequest req#>, <#HttpServletResponse resp#>)} which takes the form
 * data and saves it.
 */
public class SignGuestbookServlet extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	  System.out.println("doPost");
    Greeting greeting;

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();  // Find out who the user is.

    String guestbookName = req.getParameter("guestbookName");
    String content = req.getParameter("content");
    if (user != null) {
      greeting = new Greeting(guestbookName, content, user.getUserId(), user.getEmail());
    } else {
      greeting = new Greeting(guestbookName, content);
    }
    

    // Use Objectify to save the greeting and now() is used to make the call synchronously as we
    // will immediately get a new page using redirect and we want the data to be present.
    ObjectifyService.ofy().save().entity(greeting).now();
    deleteWhenToMuch(5, greeting.theBook);

    resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
  }


public  void deleteWhenToMuch(int i, com.googlecode.objectify.Key<Guestbook> theBook){
	if(theBook == null || i < 0){
		return;
	}
	
	List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).ancestor(theBook).order("-date").list();
	 
	List<Greeting> res = new LinkedList<Greeting>(); 
	 
	if(greetings.size() > i){
		 res = greetings.subList(i, greetings.size());
		 ObjectifyService.ofy().delete().entities(res);
	}
	 
	 
	 
}

}
//[END all]
