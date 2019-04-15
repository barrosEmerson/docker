package br.com.barrostech.error;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.com.barrostech.error.ErrorResponse.ApiError;
import br.com.barrostech.service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

	private static final String NO_MESSAGE_AVAILABLE = "no message available";
	private final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);
	private final MessageSource apiErrorMessageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException exception, Locale locale){
		Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
		
		List<ApiError> apiErrors = errors
				.map(ObjectError::getDefaultMessage)
				.map(code -> toApiError(code, locale))
				.collect(Collectors.toList());
		
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
		
		return ResponseEntity.badRequest().body(errorResponse);
		
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception, Locale locale){
		final String errorCode = "generic-1";
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale,exception.getValue()));
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, Locale locale){
		final String errorCode = exception.getCode();
		final HttpStatus status = exception.getStatus();
		
		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleDeleteNotFoundException( Locale locale){
		final String errorCode = "cervejas-5";
		final HttpStatus status = HttpStatus.NO_CONTENT;
		final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
		
		return ResponseEntity.status(status).body(errorResponse);
	}
	
	public ApiError toApiError(String code,Locale locale, Object... value) {
		String message;
		try {
		message = apiErrorMessageSource.getMessage(code,value, locale);
		}catch(NoSuchMessageException m) {
			LOG.error("Could not find any message for {} code under{} locale",code);
			message = NO_MESSAGE_AVAILABLE;
		}
		return new ApiError(code, message);
	}
}
