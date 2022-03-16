package net.digitallogic.PropertyExpander.web.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorMessage {
	private final String message;
	private final Map<String, String> details;

	public ErrorMessage(String message) {
		this(message, null);
	}

	public ErrorMessage(String message, @Nullable Map<String, String> details) {
		this.message = message;
		this.details = details;
	}
}
