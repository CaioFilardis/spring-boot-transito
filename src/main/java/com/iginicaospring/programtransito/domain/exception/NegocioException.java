package com.iginicaospring.programtransito.domain.exception;

// tratamento de exceção personalizado
public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
