package authn_server.tests.unit.service;

import authn_server.converter.Converter;
import authn_server.entity.Client;
import authn_server.exception.NoSuchClientExistException;
import authn_server.repository.ClientRepository;
import authn_server.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private Converter converter;





    @BeforeEach
    void setup() {
        //clientRequest = ClientRequest.builder().username("kiran").password("pP@1yhnb").build();

    }

    @Test
    void test_addNonExistingClient() {
//      given(clientRepository.findByUsername(clientRequest.getUsername())).willReturn(clientResponse);
//
//      given(clientRepository.save(clientRequest)).willReturn();
    }

    @Test
    void test_addExistingClient() throws Exception {

    }

    @Test
    void test_findByExistingUsername() {
//       when(clientRepository.findByUsername("kiran")).thenReturn(Optional.ofNullable(Client.builder().id(1L).username("kiran").password("password").build()));
//       //when(clientRepository.findByUsername()).thenReturn(Optional.empty());
//        Client client = clientService.findByUsername("kiran");
//        assertThat(client).isNotNull();
//        assertThat(client.getUsername()).isEqualTo("kiran");
//        assertThrows(NoSuchClientExistException.class, ()-> clientService.findByUsername("kiran"));
    }
}