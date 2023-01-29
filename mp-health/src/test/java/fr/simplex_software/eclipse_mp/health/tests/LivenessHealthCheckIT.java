package fr.simplex_software.eclipse_mp.health.tests;

import fr.simplex_software.eclipse_mp.health.*;
import io.restassured.http.ContentType;
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
public class LivenessHealthCheckIT
{
  private final URL ENDPOINT_URL = new URL("http://localhost:8080/health/live");

  public LivenessHealthCheckIT() throws MalformedURLException
  {
  }

  @Deployment(testable = false)
  public static Archive<?> deployment()
  {
    return ShrinkWrap.create(WebArchive.class, LivenessHealthCheckIT.class.getSimpleName() + ".war")
      .addClasses(LivenessHealthCheck.class);
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
        "checks", hasSize(2),
        "checks.status", hasItems("UP"),
        "checks.name", contains("Liveness"),
        "checks.data", hasSize(2),
        "checks.data[0..1].key", hasItems("value"));
  }
}
