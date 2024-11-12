package authn_server.exception;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientErrorResponse {
    private String errorCode;
    private String message;

}
