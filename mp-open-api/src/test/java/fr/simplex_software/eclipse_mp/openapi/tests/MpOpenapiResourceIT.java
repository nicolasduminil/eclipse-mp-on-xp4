package fr.simplex_software.eclipse_mp.openapi.tests;

import fr.simplex_software.eclipse_mp.openapi.*;
import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.junit5.*;
import org.jboss.arquillian.test.api.*;
import org.jboss.shrinkwrap.api.*;
import org.jboss.shrinkwrap.api.spec.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.yaml.snakeyaml.*;

import java.net.*;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class MpOpenapiResourceIT
{
  private final static String DEPLOYMENT_NAME =
    MpOpenapiResourceIT.class.getSimpleName();
  @ArquillianResource
  private URL baseURL;

  @Deployment
  public static Archive<?> deployment()
  {
    return ShrinkWrap.create(
        WebArchive.class, String.format("%s.war", DEPLOYMENT_NAME))
      .addClasses(
        Greetings.class,
        MpOpenapiApp.class,
        MpOpenApiResource.class)
      .addAsResource("META-INF/microprofile-config.properties");
  }

  @Test
  public void testOpenApiDocumentForV20Annotations() throws URISyntaxException
  {
    String responseContent = get(baseURL.toURI().resolve("/openapi")).then()
      .statusCode(200)
      .extract().asString();
    Map<String, Object> paths =
      (Map<String, Object>) ((Map<String, Object>) new Yaml().load(
        responseContent)).get("paths");
    assertThat(paths).isNotEmpty();
    assertThat(paths.size()).isEqualTo(3);
    Map<String, Object> getAs = (Map<String, Object>)paths.get("/test/text");
    assertThat(getAs).isNotEmpty();
    Map<String, Object> responses = (Map<String, Object>)getAs.get("responses");
    assertThat(responses).isNotEmpty();
    Map<String, Object> http200Response = (Map<String, Object>) responses.get("200");
    assertThat(http200Response).isNotEmpty();
    assertThat(
      ((Map<String, Object>) paths.get("/test/json"))).isNotEmpty();
    assertThat(
      ((Map<String, Object>) paths.get("/test/xml"))).isNotEmpty();
  }
}
