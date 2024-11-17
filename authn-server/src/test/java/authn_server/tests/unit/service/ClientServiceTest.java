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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
}