package authn_server.tests.medium;

import authn_server.controller.client.ClientRequest;
import authn_server.controller.client.ClientResponse;
import authn_server.tests.medium.utils.TestDataUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientIntegrationTests extends BaseIntegrationTests {

    @Test
    void createClientSuccess_withValidRequest() {
        ClientRequest clientRequest = TestDataUtils.createClientRequest("kiran", "something");
        Response response = createClient(clientRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        ClientResponse clientResponse = response.as(ClientResponse.class);
        assertThat(clientResponse.getId()).isNotNull();
        assertThat(clientResponse.getUsername()).isEqualTo("kiran");
    }

}
