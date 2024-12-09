package authn_server.tests.medium;

import authn_server.controller.client.ClientRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("test")
public class BaseIntegrationTests {

    @LocalServerPort
    private int localPort;

    private static final String CLIENT_BASE_PATH = "/client";


    @BeforeEach
    void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(localPort)
                .setContentType(ContentType.JSON)
                .build();
    }


    public Response createClient(ClientRequest clientRequest) {
        return given().body(clientRequest).when().post(CLIENT_BASE_PATH + "/add");
    }

}
