package com.zeldev.zel_e_comm.util;

import com.zeldev.zel_e_comm.domain.Response;
import com.zeldev.zel_e_comm.exception.GenericException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;
import java.util.function.BiFunction;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class ApiResponseWriter {
    private final JsonMapper jsonMapper;

    public Response getResponse(HttpServletRequest request, Map<?,?> data, String message, HttpStatus status){
        return new Response(now().toString(), status.value(), request.getRequestURI(), HttpStatus.valueOf(status.value()), message, data);
    }

    public void handleErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception exception){
        Response apiResponse = getErrorResponse(request, response, exception);
        writeResponse(response, apiResponse);
    }

    private void writeResponse (HttpServletResponse httpServletResponse, Response response) {
        try (var outputStream = httpServletResponse.getOutputStream()) {

            jsonMapper.writeValue(outputStream, response);
            outputStream.flush();
        }catch (JacksonException exception) {
            throw new GenericException("JSON Serialization failed: " + exception.getMessage());
        }
        catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    private Response getErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        var status = getHttpStatus(exception);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        return new Response(now().toString(), status.value(), request.getRequestURI(), status, errorReason.apply(exception, status), emptyMap());
    }

    private HttpStatus getHttpStatus(Exception exception){
        if (exception instanceof BadCredentialsException) return UNAUTHORIZED;

        if (exception instanceof AccessDeniedException) return FORBIDDEN;

        if (exception instanceof DisabledException) return FORBIDDEN;

        if (exception instanceof LockedException) return FORBIDDEN;

        return INTERNAL_SERVER_ERROR;
    }

    private final BiFunction<Exception, HttpStatus, String> errorReason = (exception, status) -> {
        if (exception instanceof DisabledException || exception instanceof LockedException || exception instanceof BadCredentialsException || exception instanceof GenericException){
            return exception.getMessage();
        }
        if (status.isSameCodeAs(FORBIDDEN)) {return "You do not have permission to access this resource";}
        if (status.isSameCodeAs(UNAUTHORIZED)) {return "Invalid credentials";}
        if (status.is5xxServerError()){
            return "An internal server error has occurred";
        }else {return "An error has occurred. Please try again";}
    };
}
