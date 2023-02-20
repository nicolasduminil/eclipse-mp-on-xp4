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
@Tag("LocalTests")
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
      .log()
      .body()
      .body("status", is("UP"),
        "checks", hasSize(2),
        "checks.status", hasItem("UP"),
        "checks.name", hasItem("System Uptime Health Check"),
        "checks.data.loadAverage", hasSize(2),
        "checks.data.loadAverage-per-processor", hasSize(2),
        "checks.data.loadAverage-max", hasSize(2),
        "checks.data.loadAverage-max", hasItem("0.75"));
  }
}
