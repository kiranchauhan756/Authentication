package authn_server.controller.client;

import authn_server.entity.Client;
import authn_server.service.ClientService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ClientResponse> addClient(@Valid @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.add(clientRequest));
    }

    @GetMapping("/allClients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> list = this.clientService.getClientList();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Client> getClient(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.findByUsername(username));
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<String> updateClient(@PathVariable("username") String username, @RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClient(username, client));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteClient(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.deleteClient(username));
    }
}
