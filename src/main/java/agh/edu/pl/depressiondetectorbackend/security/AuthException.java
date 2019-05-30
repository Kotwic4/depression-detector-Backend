package agh.edu.pl.depressiondetectorbackend.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bad Auth")
public class AuthException extends Exception {
    public HttpStatus getStatus(){
        return HttpStatus.NOT_FOUND;
    }

    public String getReason(){
        return "Bad Auth";
    }
}
