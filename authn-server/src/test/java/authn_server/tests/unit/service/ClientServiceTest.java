package authn_server.tests.unit.service;

import authn_server.controller.client.ClientRequest;
import authn_server.controller.client.ClientResponse;
import authn_server.converter.Converter;
import authn_server.entity.Client;
import authn_server.exception.ClientAlreadyExistException;
import authn_server.exception.NoSuchClientExistException;
import authn_server.repository.ClientRepository;
import authn_server.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @Mock
    private Converter converter;


    @Test
    void test_addNonExistingClient() {
        ClientRequest clientRequest = ClientRequest.builder().username("kiran").password("pP@1yhnb").build();
        Client client = Client.builder().id(1L).username("kiran").password("pP@1yhnb").build();
        ClientResponse clientResponse = ClientResponse.builder().id(1L).username("kiran").password("%$%^%^^%^^%^%$%$%$&").build();
        when(clientRepository.findByUsername(clientRequest.getUsername())).thenReturn(Optional.empty());
        doReturn("%$%^%^^%^^%^%$%$%$&").when(bCryptPasswordEncoder).encode(clientRequest.getPassword());
        when(converter.convert(any(ClientRequest.class), any(Client.class))).thenReturn(client);
        when(converter.convert(any(Client.class), any(ClientResponse.class))).thenReturn(clientResponse);
        when(clientRepository.save(client)).thenReturn(client);
        ClientResponse response = clientService.add(clientRequest);
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("kiran");
    }

    @Test
    void test_addExistingClient() throws Exception {
        ClientRequest clientRequest = ClientRequest.builder().username("kiran").password("pP@1yhnb").build();
        Client client = Client.builder().id(1L).username("kiran").password("pP@1yhnb").build();
        when(clientRepository.findByUsername(clientRequest.getUsername())).thenReturn(Optional.ofNullable(client));
        assertThrows(ClientAlreadyExistException.class, () -> clientService.add(clientRequest));
    }

    @Test
    void test_findByExistingUsername() {
        when(clientRepository.findByUsername("kiran")).thenReturn(Optional.ofNullable(Client.builder().id(1L).username("kiran").password("password").build()));
        Client client = clientService.findByUsername("kiran");
        assertThat(client).isNotNull();
        assertThat(client.getUsername()).isEqualTo("kiran");
    }

    @Test
    void test_findByNonExistingUsername() {
        when(clientRepository.findByUsername("kiran")).thenReturn(Optional.empty());
        assertThrows(NoSuchClientExistException.class, () -> clientService.findByUsername("kiran"));
    }

    @Test
    void test_getAllClientsWhenExists() {
        Client client1 = Client.builder().username("kiran").password("pP@1yhnb").build();
        Client client2 = Client.builder().username("kiranChauhan").password("pP@1yhnb23").build();
        Client client3 = Client.builder().username("kiran Chauhan").password("pP@1yhnb12434").build();
        ClientResponse clientResponse1 = ClientResponse.builder().id(1L).username("kiran").password("%$%^%^^%^^%^%$%$%$&").build();
        ClientResponse clientResponse2 = ClientResponse.builder().id(1L).username("kiranChauhan").password("%$%^%^^%^^%^%$%$%$&").build();
        ClientResponse clientResponse3 = ClientResponse.builder().id(1L).username("kiran Chauhan").password("%$%^%^^%^^%^%$%$%$&").build();
        when(converter.convert(client1, new ClientResponse())).thenReturn(clientResponse1);
        when(converter.convert(client2, new ClientResponse())).thenReturn(clientResponse2);
        when(converter.convert(client3, new ClientResponse())).thenReturn(clientResponse3);
        when(clientRepository.findAll()).thenReturn(List.of(client1, client2, client3));
        List<ClientResponse> clientList = clientService.getClientList();
        assertThat(clientList).isNotEmpty().hasSize(3);

    }

    @Test
    void test_getAllClientsWhenNoClientExists() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NoSuchClientExistException.class, () -> clientService.getClientList());

    }

    @Test
    void test_updateExistingClient() {
//        ClientRequest clientRequest = ClientRequest.builder().username("kiranChauhan").password("pP@1yhnb1").build();
//        Client client = Client.builder().id(1L).username("kiran").password("pP@1yhnb").build();
//        ClientResponse clientResponse = ClientResponse.builder().id(1L).username("kiranChauhan").password("%$%^%^^%^^%^%$%$%$&").build();
//        when(clientRepository.findByUsername(clientRequest.getUsername())).thenReturn(Optional.ofNullable(client));
//        when(converter.convert(ClientRequest.class, Client.class)).thenReturn(clientRequest);
//        when(converter.convert(Client.class, ClientResponse.class)).thenReturn(clientResponse);
//        ClientResponse response=clientService.updateClient(clientResponse.getUsername(), clientRequest);
//        when(clientRepository.save(client)).thenReturn(client);
//        assertThat(response).isNotNull();
//        assertThat(client.getUsername()).isEqualTo("kiranChauhan");

    }

    @Test
    void test_updateNonExistingClient() {

    }

    @Test
    void test_deleteExistingClient() {
        Client client = Client.builder().username("kiran").password("1234").build();
        when(clientRepository.findByUsername("kiran")).thenReturn(Optional.ofNullable(client));
        assertDoesNotThrow(() -> clientService.deleteClient("kiran"));

    }

    @Test
    void test_deleteNonExistingClient() throws Exception {
        when(clientRepository.findByUsername("kiran")).thenReturn(Optional.empty());
        assertThrows(NoSuchClientExistException.class, () -> clientService.deleteClient("kiran"));
    }
}