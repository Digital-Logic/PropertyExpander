package net.digitallogic.PropertyExpander.web.error;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.web.error.exception.HttpRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	@Autowired
	public ControllerAdvice(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(HttpRequestException.class)
	protected ResponseEntity<ErrorMessage> handleHttpRequestException(HttpRequestException ex) {
		log.info("HttpRequestException: {}", ex.getMessage());

		return new ResponseEntity<>(
			ex.getErrorMessage(),
			ex.getHttpStatus()
		);
	}
}
