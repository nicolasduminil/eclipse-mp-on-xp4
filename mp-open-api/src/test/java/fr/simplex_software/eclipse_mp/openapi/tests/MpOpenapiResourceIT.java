package fr.simplex_software.eclipse_mp.openapi.tests;

import fr.simplex_software.eclipse_mp.openapi.*;
import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.test.api.*;
import org.jboss.shrinkwrap.api.*;
import org.jboss.shrinkwrap.api.spec.*;
import org.junit.jupiter.api.*;
import org.yaml.snakeyaml.*;

import java.net.*;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

public class MpOpenapiResourceIT
{
  private final static String DEPLOYMENT_NAME =
    MpOpenapiResourceIT.class.getSimpleName();

  @Deployment(testable = false)
  public static Archive<?> deployment()
  {
    return ShrinkWrap.create(
        WebArchive.class, String.format("%s.war", DEPLOYMENT_NAME))
      .addClasses(
        Greetings.class,
        MpOpenapiApp.class,
        MpOpenApiResource.class)
      .addAsResource(MpOpenapiResourceIT.class.getClassLoader().getResource(
          "META-INF/schema-microprofile-config.properties"),
        "META-INF/microprofile-config.properties");
  }

  @Test
  public void testOpenApiDocumentForV20Annotations(@ArquillianResource
  URL baseURL) throws URISyntaxException
  {
    String responseContent = get(baseURL.toURI().resolve("/openapi")).then()
      .statusCode(200)
      .extract().asString();
    Map<String, Object> paths =
      (Map<String, Object>) ((Map<String, Object>) new Yaml().load(
        responseContent)).get("paths");
    assertThat(paths).isNotEmpty();
    assertThat(
      ((Map<String, Object>) paths.get("/districts/{code}"))).isNotEmpty();

  }
}
