package authn_server.service;


import authn_server.controller.client.ClientRequest;
import authn_server.controller.client.ClientResponse;
import authn_server.converter.Converter;
import authn_server.entity.Client;
import authn_server.exception.ClientAlreadyExistException;
import authn_server.exception.NoSuchClientExistException;
import authn_server.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {


    private final ClientRepository clientRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final Converter converter;

    public ClientService(ClientRepository clientRepository, PasswordEncoder bCryptPasswordEncoder, Converter converter) {
        this.clientRepository = clientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.converter = converter;
    }


    public ClientResponse add(ClientRequest clientRequest) {
        Client client = (Client) converter.convert(clientRequest, new Client());
        Client client1 = this.clientRepository.findByUsername(client.getUsername());

        if (client1 == null) {
            client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
            Client savedClient = clientRepository.save(client);
            return (ClientResponse) converter.convert(savedClient, new ClientResponse());
        } else
            throw new ClientAlreadyExistException("Client Already Exist!!");
    }


    public List<Client> getClientList() {
        return clientRepository.findAll();
    }


    public Client findByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    public String updateClient(String username, Client client) {
        Client client1 = clientRepository.findByUsername(username);
        if (client1 != null) {
            client1.setUsername(client.getUsername());
            client1.setPassword(client.getPassword());
            clientRepository.save(client1);
            return "Client details updated successfully!!";
        } else
            throw new NoSuchClientExistException("Client with this username does not exist!!");
    }

    public String deleteClient(String username) {
        Client client1 = clientRepository.findByUsername((username));
        if (client1 != null) {
            clientRepository.delete(client1);
            return "Client Record deleted  successfully!!";
        } else
            throw new NoSuchClientExistException("Client with this username does not exist!!");
    }
}
