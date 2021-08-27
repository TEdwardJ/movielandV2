package edu.ted.web.movieland.web.security;

import edu.ted.web.movieland.web.controller.MovieAdminController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice(basePackageClasses = {MovieAdminController.class})
public class EditMovieExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handleConflict(WebRequest webRequest, HttpServletResponse request, Exception exception) {
        return prepareResponse(((ServletWebRequest) webRequest).getRequest().getRequestURI(), exception);
    }

    private ResponseEntity prepareResponse(String path, Exception exception) {
        var info =
                Map.of("timestamp", LocalDateTime.now().toString(),
                        "statusCode", BAD_REQUEST.value(),
                        "statusMessage", BAD_REQUEST.getReasonPhrase(),
                        "message", exception.getMessage(),
                        "path", path);
        var exceptionalResponse = new ResponseEntity(info, FORBIDDEN);
        return exceptionalResponse;
    }
}
