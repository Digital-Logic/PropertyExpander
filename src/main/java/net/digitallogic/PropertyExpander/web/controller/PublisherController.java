package net.digitallogic.PropertyExpander.web.controller;

import net.digitallogic.PropertyExpander.persistence.dto.PublisherDto;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public interface PublisherController {

	List<PublisherDto> getPublishers(int page, int limit, String sort, @Nullable String expand, HttpServletResponse response);
	PublisherDto getPublisher(UUID id, @Nullable String expand);
}
