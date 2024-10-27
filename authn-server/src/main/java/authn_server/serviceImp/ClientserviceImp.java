package authn_server.serviceImp;

import authn_server.entity.Client;
import authn_server.repository.ClientRepository;
import authn_server.service.Clientservice;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientserviceImp implements Clientservice {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client add(Client client) {
      return   clientRepository.save(client);
    }

    @Override
    public List<Client> getClientList() {
        return clientRepository.findAll();
    }


}
