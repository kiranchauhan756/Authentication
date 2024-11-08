package authn_server.exception;

public class ClientAlreadyExistException extends RuntimeException{
    private String message;

    public ClientAlreadyExistException(){}

    public ClientAlreadyExistException (String msg){
        super(msg);
        this.message=msg;
    }

}
