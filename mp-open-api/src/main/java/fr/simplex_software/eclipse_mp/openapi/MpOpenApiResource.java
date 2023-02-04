package fr.simplex_software.eclipse_mp.openapi;

//import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.responses.*;

import javax.enterprise.context.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@RequestScoped
@Path("/test")
public class MpOpenApiResource
{
  @GET
  @Path("/text")
  //@Operation(description = "Getting a text formatted test string.")
  //@APIResponse(responseCode = "200", description = "Execution has succeeded")
  public String sayHelloText()
  {
    return "Hello World !";
  }

  @GET
  @Path("/json")
  @Produces(MediaType.APPLICATION_JSON)
  //@Operation(description = "Getting a JSON formatted test string.")
  @APIResponse(responseCode = "200", description = "Execution has succeeded")
  //@APIResponse(responseCode = "500", description = "JSON Error")
  public Greetings sayHelloJson()
  {
    return new Greetings("Hello World !");
  }

  @GET
  @Path("/xml")
  @Produces(MediaType.APPLICATION_XML)
  //@Operation(description = "Getting an XML formatted test string.")
  //@APIResponse(responseCode = "200", description = "Execution has succeeded")
  //@APIResponse(responseCode = "500", description = "XML Error")
  public Greetings sayHelloXml()
  {
    return new Greetings("Hello World !");
  }
}
