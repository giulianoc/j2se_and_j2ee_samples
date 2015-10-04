package com.webservice;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.entity.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qualifier.Resource;

// www.mysite.com/rest/bookservice

@Path("bookservice")
public class BookService {

	@Inject
	private EntityManager em;

	// www.mysite.com/rest/bookservice/status
	@GET
	@Produces("text/html")
	@Path("status")
	public Response getStatus() {

		return Response.ok("<h1>REST service is up !!!</h1>").build();
	}

	// www.mysite.com/rest/bookservice/list
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list")
	public Response listBooks() {
		String response = null;
		try {
			em = Resource.getEntityManager();
			Query query = em.createQuery("FROM com.entity.Book");
			List<Book> list = query.getResultList();
			em.close();
			response = toJSONString(list);
		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();

		}
		return Response.ok(response).build();
	}
	
	// www.mysite.com/rest/bookservice/Book/I am that, I am
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("Book/{name}")
	public Response getBooks(@PathParam("name") String bookName) {
		String response = null;
		try{
			em = Resource.getEntityManager();
			Book existingBook = em.find(Book.class, bookName);
			em.close();
			response = toJSONString(existingBook);
		}catch(Exception err){
			response = "{\"status\":\"401\","+
						"\"message\":\"No content found \""+
						"\"developerMessage\":\""+err.getMessage()+"\""+
						"}";
			return  Response.status(401).entity(response).build(); 

		}
		return Response.ok(response).build();
	}
	
	// www.mysite.com/rest/bookservice/create
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("create")
	public Response createBook(String payload) {

		System.out.println("payload - " + payload);

		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Gson gson = gsonBuilder.create();

		// Get book Object parsed from JSON string
		Book book = gson.fromJson(payload, Book.class);
		String returnCode = "200";
		em = Resource.getEntityManager();

		// Insert Book using JTA persistance with Hibernate
		try {
			em.getTransaction().begin();
			em.persist(book);
			em.flush();
			em.refresh(book);
			em.getTransaction().commit();
			em.close();
			
			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/rest/bookservice/Book/"+book.getName()+"\","
					+ "\"message\":\"New book successfully created.\""
					+ "}";
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+
					"\"message\":\"Resource not created.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(404).entity(returnCode).build(); 

		}
		return  Response.status(201).entity(returnCode).build(); 

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("edit/{name}")
	public Response updateBook(@PathParam("name") String bookName,
			String payload) {

		System.out.println("payload - " + payload);

		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
		Gson gson = gsonBuilder.create();

		
		// Get book Object parsed from JSON string
		Book book = gson.fromJson(payload, Book.class);
		String returnCode = "200";

		System.out.println("Book Name - " + book.getName());
		System.out.println("Book ISBN - " + book.getIsbn());
		System.out.println("Book Price - " + book.getPrice());
		System.out.println("Book publishedDate - " + book.getPublishedDate());

		em = Resource.getEntityManager();

		// Update using JTA persistance with Hibernate
		try {
			em.getTransaction().begin();
			Book existingBook = em.find(Book.class, bookName);
			System.out
					.println("Existing Book Name - " + existingBook.getName());
			existingBook.setIsbn(book.getIsbn());
			existingBook.setPrice(book.getPrice());
			existingBook.setPublishedDate(book.getPublishedDate());
			em.merge(existingBook);
			em.getTransaction().commit();
			em.close();
			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/rest/bookservice/Book/"+book.getName()+"\","
					+ "\"message\":\"New book successfully updated.\""
					+ "}";
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"304\","+
					"\"message\":\"Resource not modified.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(304).entity(returnCode).build(); 
		}
		return  Response.status(200).entity(returnCode).build(); 
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("delete/{name}")
	public Response deleteBook(@PathParam("name") String bookName) {
		em = Resource.getEntityManager();
		String returnCode = "";
		// Remove a book using JTA persistance with Hibernate
		try {
			em.getTransaction().begin();
			Book existingBook = em.find(Book.class, bookName);
			em.remove(existingBook);
			em.getTransaction().commit();
			em.close();
			returnCode = "{"
					+ "\"message\":\"Book succesfully deleted\""
					+ "}";
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+
					"\"message\":\"Resource not deleted.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(500).entity(returnCode).build(); 
		}
		return Response.ok(returnCode).build();
	}
	
	public String toJSONString(Object list) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Gson gson = gsonBuilder.create();
		return gson.toJson(list);
	}
}
