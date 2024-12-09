package authn_server.tests.medium.utils;

import authn_server.controller.client.ClientRequest;

public class TestDataUtils {

    public static ClientRequest createClientRequest(String username, String password) {
        return ClientRequest.builder().username(username).password(password).build();
    }
}
