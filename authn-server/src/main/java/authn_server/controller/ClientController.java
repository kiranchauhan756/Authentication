package authn_server.controller;

import authn_server.entity.Client;
import authn_server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @PostMapping("/add")
    public String addClient(@RequestBody Client client) throws Exception{
        Client client1=this.clientService.findByUsername(client.getUsername());
        if(client1==null){
            throw new Exception();
        }
        return clientService.add(client);
    }



    @GetMapping("/allClients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> list = this.clientService.getClientList();
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @GetMapping("/{username}")
    public Client getClient(@PathVariable("username") String username) {
        return clientService.findByUsername(username);
    }

}
