package authn_server.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* this class is used to define table with columns and data type to store client data into the database*/
@Entity
@Table(name = "Client")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;


}
