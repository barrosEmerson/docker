package br.com.barrostech.service.exception;

import org.springframework.http.HttpStatus;

public class CervejaAlreadyExistException extends BusinessException {

	public CervejaAlreadyExistException() {
		super("cervejas-5", HttpStatus.BAD_REQUEST);
	}
	
	

}
