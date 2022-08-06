package pdtg.lsmscbeersrvc.web.controller.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Diego T. 06-08-2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private  final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    @Data
    @RequiredArgsConstructor
    private static class ValidationError{
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message){
        if(Objects.isNull(errors)){
            errors = new ArrayList<>();
        }
        errors.add((new ValidationError(field, message)));
    }
}
