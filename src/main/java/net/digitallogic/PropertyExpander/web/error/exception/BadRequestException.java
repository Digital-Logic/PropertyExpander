package net.digitallogic.PropertyExpander.web.error.exception;

import net.digitallogic.PropertyExpander.web.error.ErrorMessage;
import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpRequestException {

	public BadRequestException(ErrorMessage errorMessage) {
		super(errorMessage);
	}

	@Override
	public HttpStatus getHttpStatus() {

		return HttpStatus.BAD_REQUEST;
	}
}
