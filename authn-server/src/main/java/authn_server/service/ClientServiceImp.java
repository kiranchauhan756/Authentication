package authn_server.serviceImp;

import authn_server.entity.Client;
import authn_server.exception.ClientAlreadyExistException;
import authn_server.repository.ClientRepository;
import authn_server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientServiceImp implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public String add(Client client) {
        Client client1=this.clientRepository.findByUsername(client.getUsername());

        if(client1==null){
            this.clientRepository.save(client);
            return "New Client added successfully";
        }
      else
          throw new ClientAlreadyExistException("Client Already Exist!!");
    }

    @Override
    public List<Client> getClientList() {
        return clientRepository.findAll();
    }

    @Override
    public Client findByUsername(String username) {
        Client client=this.clientRepository.findByUsername(username);
        return client;
    }


}
