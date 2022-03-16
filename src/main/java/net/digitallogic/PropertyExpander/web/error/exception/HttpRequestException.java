package net.digitallogic.PropertyExpander.web.error.exception;

import lombok.Getter;
import net.digitallogic.PropertyExpander.web.error.ErrorMessage;
import org.springframework.http.HttpStatus;

@Getter
public abstract class HttpRequestException extends RuntimeException {
	protected final ErrorMessage errorMessage;

	public HttpRequestException(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public abstract HttpStatus getHttpStatus();
}
