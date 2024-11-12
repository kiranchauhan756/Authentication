package authn_server.exception;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientErrorResponse {
    private int errorCode;
    private String message;

}
