package authn_server.controller.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* This class is a DTO(Data Transfer Object) class which is mapped to Client  I
why do we need DTO class?
Reason : if a persistent object is used as an argument of a method annotated with @RequestMapping,
         it’s possible from a specially crafted user input, to change the content of unexpected fields into the database
    This class will help to send a request to server
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequest {
    @NotNull(message = "username can't be empty")
    @Size(max=200,message="username must be 200 characters long")
    @Pattern(regexp="^[A-Za-z]*$",message = "username must not include numbers or special characters")
    private String username;

    @NotNull(message= "password can't be empty")
    @Size(min=8,max=20,message="password must be of length between 8 to 20")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,?!])(?=\\S+$).*$"
            ,message="password must include one lowercase one uppercase , one number and one special character")
    private String password;

}
