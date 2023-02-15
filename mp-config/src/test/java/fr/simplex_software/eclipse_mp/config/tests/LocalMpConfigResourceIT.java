package fr.simplex_software.eclipse_mp.config.tests;

import io.restassured.http.*;
import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.junit5.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import java.net.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(ArquillianExtension.class)
@RunAsClient
@Tag("LocalTests")
public class LocalMpConfigResourceIT
{
  private final URL ENDPOINT_URL = new URL ("http://localhost:8080/config/test");

  public LocalMpConfigResourceIT() throws MalformedURLException {}

  @Test
  public void testEndpointCallShouldSucceed() throws MalformedURLException
  {
    given()
      .contentType(ContentType.TEXT)
      .get(ENDPOINT_URL)
      .then()
      .assertThat().statusCode(200)
      .and()
      .body(containsString("hello Eclipse MP!"));
  }
}
