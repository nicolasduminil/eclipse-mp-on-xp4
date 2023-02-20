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
@Tag("LocalTests")
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
    Map<String, Object> apiMap =
      (Map<String, Object>) ((Map<String, Object>) new Yaml().load(
        responseContent)).get("paths");
    assertThat(apiMap).isNotEmpty();
    assertThat(apiMap.size()).isEqualTo(3);
    assertThat(
      ((Map<String, Object>) apiMap.get("/test/json"))).isNotEmpty();
    assertThat(
      ((Map<String, Object>) apiMap.get("/test/xml"))).isNotEmpty();
    apiMap = (Map<String, Object>)apiMap.get("/test/text");
    assertThat(apiMap).isNotEmpty();
    apiMap.forEach((k, v) -> System.out.println("### key: " + k + " value: " + v));
    apiMap = (Map<String, Object>)apiMap.get("get");
    assertThat(apiMap).isNotEmpty();
    apiMap.forEach((k, v) -> System.out.println("### key: " + k + " value: " + v));
    apiMap = (Map<String, Object>)apiMap.get("responses");
    assertThat(apiMap).isNotEmpty();
    Map<String, Object> http200Response = (Map<String, Object>) apiMap.get("200");
    assertThat(http200Response).isNotEmpty();
  }
}
