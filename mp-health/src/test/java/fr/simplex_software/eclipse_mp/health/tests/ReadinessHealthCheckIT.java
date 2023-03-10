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
public class ReadinessHealthCheckIT extends HealthCheck
{
  private final URL ENDPOINT_URL = new URL("http://localhost:9990/health/ready");

  public ReadinessHealthCheckIT() throws MalformedURLException
  {
  }

  @Test
  public void testReadinessShouldSucceed()
  {
    given()
      .get(ENDPOINT_URL)
      .then()
      .contentType(ContentType.JSON)
      .header("Content-Type", containsString("application/json"))
      .log()
      .body()
      .body("status", is("UP"),
        "checks", hasSize(5),
        "checks.status", hasItems("UP"),
        "checks.name", hasItems("server-state", "deployments-status", "boot-errors"),
        "checks.data", hasSize(5),
        "checks.data.value", hasItems("running"));
  }
}
