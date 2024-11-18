package authn_server.tests.unit.controller;

import authn_server.controller.client.ClientController;
import authn_server.controller.client.ClientRequest;
import authn_server.controller.client.ClientResponse;
import authn_server.exception.ClientAlreadyExistException;
import authn_server.exception.ErrorResponse;
import authn_server.exception.NoSuchClientExistException;
import authn_server.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;

import static authn_server.helpers.JsonHelper.asJsonString;
import static authn_server.helpers.JsonHelper.asObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    private static final String CLIENT_REQUEST_PATH = "/client";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    //positive test case
    //when the request is valid and client does not exist in the database
    @Test
    void testAddClient_whenRequestValidAndClientDoesNotExist() throws Exception {
        ClientRequest clientRequest = ClientRequest.builder().username("kiran").password("pP@1yhnb").build();
        when(clientService.add(clientRequest)).thenReturn(ClientResponse.builder().id(1L).username("kiran").password("pP@1yhnb").build());
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

    //negative test case
    //when the request is not valid either the pwd and username are empty or pwd doesn't validate the criteria or username is not a string
    @Test
    void testAddClient_whenRequestNotValid() throws Exception {
        ClientRequest clientRequest = ClientRequest.builder().password("pP@1yhnb").build();
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
        ClientRequest clientRequest = ClientRequest.builder().username("kiran").password("pP@1yhnb").build();
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

    @Test
    void testGetAllClients_whenClientExists() throws Exception {
        when(clientService.getClientList()).thenReturn(List.of(ClientResponse.builder().id(1L).username("kiran").password("abcd").build(), ClientResponse.builder().id(1L).username("kiran1234").password("1245").build()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_REQUEST_PATH + "/allClients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        List clientResponse = asObject(response, List.class);
        assertThat(clientResponse).isNotEmpty().hasSize(2);
    }

    @Test
    void testGetAllClients_whenNoClientExist() throws Exception {
        when(clientService.getClientList()).thenReturn(Collections.emptyList());
        when(clientService.getClientList()).thenThrow(NoSuchClientExistException.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_REQUEST_PATH + "/allClients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = asObject(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo("AUTHN-002");
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void testDelete_whenClientExist() throws Exception{
        when(clientService.deleteClient("kiran")).thenReturn(ClientResponse.builder().id(1L).username("kiran").password("pP@1yhnb").build());
        MvcResult mvcResult = mockMvc.perform(delete(CLIENT_REQUEST_PATH + "/delete"+"/kiran")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ClientResponse clientResponse = asObject(response, ClientResponse.class);
        assertThat(clientResponse.getId()).isEqualTo(1L);
        assertThat(clientResponse.getUsername()).isEqualTo("kiran");
    }

    @Test
    void testDelete_whenClientDoNotExist() throws Exception{
        when(clientService.deleteClient("kiran")).thenThrow(NoSuchClientExistException.class);
        MvcResult mvcResult = mockMvc.perform(delete(CLIENT_REQUEST_PATH + "/delete"+"/kiran")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = asObject(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo("AUTHN-002");
    }
    @Test
    void testUpdate_whenClientExistAndRequestIsValid() throws Exception{
        ClientRequest clientRequest = ClientRequest.builder().username("kiranChauhan").password("pP@1yhnb11").build();
        when(clientService.updateClient("kiran",clientRequest)).thenReturn(ClientResponse.builder().id(1L).username("kiranChauhan").password("pP@1yhnb11").build());
        MvcResult mvcResult = mockMvc.perform(put(CLIENT_REQUEST_PATH + "/update"+"/kiran").content(asJsonString(clientRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ClientResponse clientResponse = asObject(response, ClientResponse.class);
        assertThat(clientResponse.getId()).isEqualTo(1L);
        assertThat(clientResponse.getUsername()).isEqualTo("kiranChauhan");
    }
    //Error -----------------------------------------------
    @Test
    void testUpdate_whenClientExistAndRequestIsNotValid() throws Exception{
        ClientRequest clientRequest = ClientRequest.builder().password("pP@1yhnb").build();
        MvcResult mvcResult = mockMvc.perform(put(CLIENT_REQUEST_PATH + "/update"+"/kiran").content(asJsonString(clientRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = asObject(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo("AUTHN-003");

    }
    @Test
    void testUpdate_whenClientDoNotExist() throws Exception{
        ClientRequest clientRequest = ClientRequest.builder().username("kiran").password("pPyhnb1@").build();
        when(clientService.updateClient("kiran",clientRequest)).thenThrow(NoSuchClientExistException.class);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(CLIENT_REQUEST_PATH + "/update"+"/kiran").content(asJsonString(clientRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").exists()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        ErrorResponse errorResponse = asObject(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo("AUTHN-002");
    }

}
