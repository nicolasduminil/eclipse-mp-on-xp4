package fr.simplex_software.eclipse_mp.health.tests;

import fr.simplex_software.eclipse_mp.health.*;
import io.restassured.http.*;
import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.junit5.*;
import org.jboss.shrinkwrap.api.*;
import org.jboss.shrinkwrap.api.spec.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import java.net.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class ReadinessHealthCheckIT
{
  private final URL ENDPOINT_URL = new URL ("http://localhost:8080/health/live");

  public ReadinessHealthCheckIT() throws MalformedURLException
  {
  }

  @Deployment(testable = false)
  public static Archive<?> deployment() {
    return ShrinkWrap.create(WebArchive.class, ReadinessHealthCheckIT.class.getSimpleName() + ".war")
      .addClasses(ReadinessHealthCheck.class);
  }

  @Test
  public void testReadinessShouldSucceed()
  {
    given()
      .get(ENDPOINT_URL)
      .then()
      .contentType(ContentType.JSON)
      .header("Content-Type", containsString("application/json"))
      .body("status", is("UP"),
        "checks", hasSize(2),
        "checks.status", hasItems("UP"),
        "checks.name", contains("Readiness"),
        "checks.data", hasSize(2),
        "checks.data[0..1].key", hasItems("value"));
  }
}
