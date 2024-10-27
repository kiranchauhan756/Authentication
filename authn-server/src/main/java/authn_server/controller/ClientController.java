package authn_server.controller;

import authn_server.entity.Client;
import authn_server.service.Clientservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {


    private Clientservice clientService;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        Client client1=new Client();
        client1.setUsername(client.getUsername());
        client1.setPassword(encodedPassword);
       return ResponseEntity.status(HttpStatus.CREATED).body(client1);
    }

    @GetMapping("/allClients")
    public ResponseEntity<List<Client>> getAllClients(){
        List<Client>list=this.clientService.getClientList();
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }
}
