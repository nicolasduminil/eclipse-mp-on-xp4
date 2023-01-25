package fr.simplex_software.eclipse_mp.config;

import java.util.*;

import org.eclipse.microprofile.config.inject.*;

import javax.enterprise.context.*;
import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@ApplicationScoped
public class MpConfigResource
{
  @Inject
  @ConfigProperty(name = "greeting.message")
  String message;

  @Inject
  @ConfigProperty(name = "greeting.suffix", defaultValue = "!")
  String suffix;

  @Inject
  @ConfigProperty(name = "greeting.name")
  Optional<String> name;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello()
  {
    return message + " " + name.orElse("world") + suffix;
  }
}
