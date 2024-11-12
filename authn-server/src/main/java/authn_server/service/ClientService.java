package authn_server.service;


import authn_server.entity.Client;
import authn_server.exception.ClientAlreadyExistException;
import authn_server.exception.NoSuchClientExistException;
import authn_server.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public String add(Client client) {
        Client client1 = this.clientRepository.findByUsername(client.getUsername());

        if (client1 == null) {
            clientRepository.save(client);
            return "New Client added successfully";
        } else
            throw new ClientAlreadyExistException("Client Already Exist!!");
    }


    public List<Client> getClientList() {
        return clientRepository.findAll();
    }


    public Client findByUsername(String username) {
        Client client = clientRepository.findByUsername(username);
        return client;
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
}
