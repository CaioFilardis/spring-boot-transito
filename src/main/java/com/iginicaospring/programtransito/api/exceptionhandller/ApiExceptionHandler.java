package com.iginicaospring.programtransito.api.exceptionhandller;

import com.iginicaospring.programtransito.domain.exception.EntidadeNaoEncontradaException;
import com.iginicaospring.programtransito.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource; // instancia para processar mensagens personalizadas

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Um ou mais campos estão inválidos");
        problemDetail.setType(URI.create("http://www.programtransito.com/erros/campos-invalidos")); // url exemplo(não existe)

        Map<String, String> fields = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(objectError -> ((FieldError) objectError).getField(),
                        objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale()))); // retorna local padrão

        problemDetail.setProperty("fields", fields);


        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }



    @ExceptionHandler(NegocioException.class)
    public ProblemDetail handleNegocio(NegocioException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("http://www.programtransito.com/erros/regra-de-negocio"));

        return problemDetail;
    }


    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ProblemDetail handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND); // indica status de 'não encontrado'
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("http://www.programtransito.com/erros/nao-encontrado"));

        return problemDetail;
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT); // ERRO 409, erro mais específico
        problemDetail.setTitle("Recurso está em uso");
        problemDetail.setType(URI.create("https://www.programtransito.com/erros/recurso-em-uso"));

        return problemDetail;
    }
}
