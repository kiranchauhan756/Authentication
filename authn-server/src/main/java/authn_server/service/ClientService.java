package authn_server.service;

import authn_server.entity.Client;

import java.util.List;

public interface ClientService {
    String add(Client client);
    List<Client> getClientList();
    Client findByUsername(String username);


}
