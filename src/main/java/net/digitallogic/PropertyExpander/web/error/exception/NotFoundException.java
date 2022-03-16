package net.digitallogic.PropertyExpander.web.error.exception;

import net.digitallogic.PropertyExpander.web.error.ErrorMessage;
import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpRequestException {

	public NotFoundException(ErrorMessage errorMessage) {
		super(errorMessage);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
