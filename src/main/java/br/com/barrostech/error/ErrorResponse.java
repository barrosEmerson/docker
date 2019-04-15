package br.com.barrostech.error;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static lombok.AccessLevel.PRIVATE;
import lombok.RequiredArgsConstructor;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;


@JsonAutoDetect(fieldVisibility = ANY)//serve para informar que o retorno é Json
@RequiredArgsConstructor(access = PRIVATE)//cria um construtor pelo Lombok
public class ErrorResponse {
	
	@SuppressWarnings("unused")
	private final int statusCode;
	@SuppressWarnings("unused")
	private final List<ApiError>erros;
	
	static  public ErrorResponse of(HttpStatus status, List<ApiError>erros) {
		return new ErrorResponse(status.value(),erros);
	}
	static  public ErrorResponse of(HttpStatus status, ApiError error) {
		return of(status, Collections.singletonList(error));
	}
	
	@JsonAutoDetect(fieldVisibility = ANY)
	@RequiredArgsConstructor
	static class ApiError{
		private final String code;
		private final String message;	
	}

}
