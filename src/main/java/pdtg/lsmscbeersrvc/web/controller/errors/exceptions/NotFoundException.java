package pdtg.lsmscbeersrvc.web.controller.errors.exceptions;

/**
 * Created by Diego T. 26-07-2022
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Resource not found");
    }
}
