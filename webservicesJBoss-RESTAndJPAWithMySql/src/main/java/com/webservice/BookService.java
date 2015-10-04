package com.webservice;

import com.entity.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 12/05/15
 * Time: 08:51
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@Path("/bookservice")
public class BookService {

    private static final Logger mLogger = Logger.getLogger(BookService.class);

    // you can call the below method using the browser and a url like:
    //  http://127.0.0.1:8080/webservices-rest/rest/bookservice/status

    @PersistenceContext(name = "bookServicePersistenceUnit")
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("status")
    public Response getStatus()
    {
        return Response.ok("{ \"status\": \"REST webservice running ...\" }").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("books")
    public Response getBooks()
    {
        String response = null;

        try {
            Query query = entityManager.createQuery("from com.entity.Book");
            List<Book> books = query.getResultList();

            response = toJSONString(books);
        }
        catch (Exception err)
        {
            response = "{\"status\":\"401\","
                + "\"message\":\"No content found \","
                + "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";

            mLogger.info("response: " + response);

            return Response.status(401).entity(response).build();
        }

        mLogger.info("response: " + response);

        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("book/{name}")
    public Response getBook(@PathParam("name") String bookName)
    {
        String response = null;

        try {
            Book existingBook = entityManager.find(Book.class, bookName);
            if(null == existingBook)
            {
                response = "{\"status\":\"401\","
                        + "\"message\":\"No content found \","
                        + "\"developerMessage\":\"Book - "+bookName+" Not Found in Library\"" + "}";

                mLogger.info("response: " + response);

                return Response.status(401).entity(response).build();
            }

            response = toJSONString(existingBook);
        }
        catch (Exception err)
        {
            response = "{\"status\":\"401\","
                    + "\"message\":\"No content found \","
                    + "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";

            mLogger.info("response: " + response);

            return Response.status(401).entity(response).build();
        }

        mLogger.info("response: " + response);

        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("book")
    public Response createNewBook(String payload)
    {
        mLogger.info("createNewBook: " + payload);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Gson gson = gsonBuilder.create();

        // Get book Object parsed from JSON string
        Book book = gson.fromJson(payload, Book.class);

        String response = null;

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(book);
            entityManager.flush();
            entityManager.refresh(book);
            entityManager.getTransaction().commit();

            response = "{"
                + "\"href\":\"http://localhost:8080/BookWebService/rest/bookservice/book/" + book.getName() + "\","
                + "\"message\":\"New book successfully created.\""
                + "}";
        }
        catch (Exception err)
        {
            mLogger.error("Exception: " + err.getMessage());

            response = "{\"status\":\"500\","+
                "\"message\":\"Resource not created.\","+
                "\"developerMessage\":\"" + err.getMessage() + "\"" +
                "}";

            return  Response.status(500).entity(response).build();
        }

        return  Response.status(201).entity(response).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("book/{name}")
    public Response updateBook(@PathParam("name") String bookName, String payload)
    {
        mLogger.info("updateBook: " + payload);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Gson gson = gsonBuilder.create();

        // Get book Object parsed from JSON string
        Book updatedBook = gson.fromJson(payload, Book.class);

        mLogger.info("updatedBook. name: " + updatedBook.getName());
        mLogger.info("updatedBook. isbn: " + updatedBook.getIsbn());
        mLogger.info("updatedBook. price: " + updatedBook.getPrice());
        mLogger.info("updatedBook. publishedDate: " + updatedBook.getPublishedDate());

        String response = null;

        try {
            entityManager.getTransaction().begin();

            Book existingBook = entityManager.find(Book.class, bookName);

            existingBook.setName(updatedBook.getName());
            existingBook.setIsbn(updatedBook.getIsbn());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setPublishedDate(updatedBook.getPublishedDate());

            entityManager.merge(existingBook);

            entityManager.getTransaction().commit();

            response = "{"
                + "\"href\":\"http://localhost:8080/BookWebService/rest/bookservice/book/" + updatedBook.getName() + "\","
                + "\"message\":\"" + bookName + " was successfully updated.\""
                + "}";
        }
        catch (Exception err)
        {
            response = "{\"status\":\"304\","+
                "\"message\":\"Resource not modified.\","+
                "\"developerMessage\":\""+err.getMessage()+"\""+
                "}";

            return Response.status(304).entity(response).build();
        }

        return  Response.status(200).entity(response).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("book/{name}")
    public Response deleteBook(@PathParam("name") String bookName)
    {
        String response = null;

        try {
            entityManager.getTransaction().begin();

            Book existingBook = entityManager.find(Book.class, bookName);

            entityManager.remove(existingBook);

            entityManager.getTransaction().commit();

            response = "{"
                + "\"message\":\"Book succesfully deleted\""
                + "}";
        }
        catch (Exception err)
        {
            mLogger.error("Exception: " + err.getMessage());

            response = "{\"status\":\"500\","+
                "\"message\":\"Resource not deleted.\""+
                "\"developerMessage\":\""+err.getMessage()+"\""+
                "}";

            return  Response.status(500).entity(response).build();
        }

        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }

    public String toJSONString(Object object)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // ISO8601 /
        // UTC
        Gson gson = gsonBuilder.create();

        return gson.toJson(object);
    }

}
