package fr.simplex_software.eclipse_mp.config.tests;

import io.restassured.http.*;
import org.junit.jupiter.api.*;

import java.net.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Tag("CloudTests")
public class CloudMpConfigResourceIT
{
  private final URL ENDPOINT_URL = new URL("http://mp-config-nicolasduminil-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/config/test");

  public CloudMpConfigResourceIT() throws MalformedURLException
  {
  }

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
