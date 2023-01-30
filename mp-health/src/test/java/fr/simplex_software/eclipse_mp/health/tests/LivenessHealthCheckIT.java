package fr.simplex_software.eclipse_mp.health.tests;

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
public class LivenessHealthCheckIT extends HealthCheck
{
  private final URL ENDPOINT_URL = new URL("http://localhost:9990/health/live");

  public LivenessHealthCheckIT() throws MalformedURLException
  {
  }

  @Test
  public void testLivenessShouldSucceed()
  {
    given()
      .get(ENDPOINT_URL)
      .then()
      .contentType(ContentType.JSON)
      .header("Content-Type", containsString("application/json"))
      .body("status", is("UP"),
        "checks", hasSize(1),
        "checks.status", hasItem("UP"));
  }
}
