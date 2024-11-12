package authn_server.tests.unit.controller;

import authn_server.controller.client.ClientController;
import authn_server.controller.client.ClientRequest;
import authn_server.controller.client.ClientResponse;
import authn_server.exception.ClientAlreadyExistException;
import authn_server.exception.ErrorResponse;
import authn_server.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static authn_server.helpers.JsonHelper.asJsonString;
import static authn_server.helpers.JsonHelper.asObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    private static final String CLIENT_REQUEST_PATH = "/client";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;


    @Test
    void testAddClient_whenRequestValidAndClientDoesNotExist() throws Exception {
        ClientRequest clientRequest = ClientRequest.builder().username("kiran").password("password").build();
        when(clientService.add(clientRequest)).thenReturn(ClientResponse.builder().id(1L).username("kiran").password("password").build());
        MvcResult mvcResult = mockMvc.perform(post(CLIENT_REQUEST_PATH + "/add").content(asJsonString(clientRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ClientResponse clientResponse = asObject(response, ClientResponse.class);
        assertThat(clientResponse.getId()).isEqualTo(1L);
        assertThat(clientResponse.getUsername()).isEqualTo("kiran");

    }

    @Test
    void testAddClient_whenRequestNotValid() throws Exception {
        ClientRequest clientRequest = ClientRequest.builder().password("password").build();
        MvcResult mvcResult = mockMvc.perform(post(CLIENT_REQUEST_PATH + "/add").content(asJsonString(clientRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = asObject(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo("AUTHN-003");

    }

    @Test
    void testAddClient_whenRequestValidAndClientAlreadyExists() throws Exception {
        ClientRequest clientRequest = ClientRequest.builder().username("kiran").password("password").build();
        when(clientService.add(clientRequest)).thenThrow(ClientAlreadyExistException.class);
        MvcResult mvcResult = mockMvc.perform(post(CLIENT_REQUEST_PATH + "/add").content(asJsonString(clientRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = asObject(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo("AUTHN-001");
    }


}
