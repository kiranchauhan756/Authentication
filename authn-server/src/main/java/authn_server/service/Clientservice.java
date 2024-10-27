package authn_server.service;

import authn_server.entity.Client;

import java.util.List;

public interface Clientservice {
    Client add(Client client);
    List<Client> getClientList();
}
