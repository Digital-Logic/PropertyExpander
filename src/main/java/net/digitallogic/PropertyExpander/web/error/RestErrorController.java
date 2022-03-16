package net.digitallogic.PropertyExpander.web.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping(path = "${server.error.path}")
public class RestErrorController extends AbstractErrorController {

	protected final String errorPath;
	protected final ErrorProperties.IncludeStacktrace includeStacktrace;
	protected final ErrorAttributes errorAttributes;

	@Autowired
	public RestErrorController(
		@Value("${server.error.path}") String errorPath,
		@Value("${server.error.include-stacktrace}") ErrorProperties.IncludeStacktrace includeStacktrace,
		ErrorAttributes errorAttributes
	) {
		super(errorAttributes, Collections.emptyList());

		this.errorPath = errorPath;
		this.includeStacktrace = includeStacktrace;
		this.errorAttributes = errorAttributes;
	}
}
