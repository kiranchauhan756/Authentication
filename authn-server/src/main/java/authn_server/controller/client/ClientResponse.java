package authn_server.controller.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*  This class is a DTO(Data Transfer Object) class which is mapped to Client
     This class will store the response coming back from rest api calling  on the server
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {

    private Long id;

    private String username;

    private String password;
}
