package br.com.barrostech.error;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class GeneralExceptionError {
	
	private final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
	@Autowired
	private ApiExceptionHandler apiError;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> errorInternalServerError(Exception exception, Locale locale){
		LOG.error("Error not expected ",exception);
		final String errorCode = "error-1";
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final ErrorResponse errorResponse =  ErrorResponse.of(status, apiError.toApiError(errorCode, locale));
		return ResponseEntity.status(status).body(errorResponse);
	}
}
