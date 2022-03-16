package net.digitallogic.PropertyExpander.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.dto.PublisherDto;
import net.digitallogic.PropertyExpander.services.PublisherService;
import net.digitallogic.PropertyExpander.web.Headers;
import net.digitallogic.PropertyExpander.web.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = Routes.PUBLISHER_ROUTE)
public class PublisherControllerImpl implements PublisherController {

	private final PublisherService publisherService;

	@Autowired
	public PublisherControllerImpl(PublisherService publisherService) {
		this.publisherService = publisherService;
	}

	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PublisherDto> getPublishers(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "limit", defaultValue = "25") int limit,
		@RequestParam(name = "sort", defaultValue = "name") String sort,
		@RequestParam(name = "expand", required = false) @Nullable String expand,
		HttpServletResponse response) {

		Slice<PublisherDto> publishers = publisherService.getPublishers(page, limit, sort, expand);

		response.addHeader(Headers.PAGE_LIMIT_HEADER, Integer.toString(limit));
		response.addHeader(Headers.PAGE_CURRENT, Integer.toString(page));
		response.addHeader(Headers.PAGE_HAS_NEXT, Boolean.toString(publishers.hasNext()));

		return publishers.toList();
	}

	@Override
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PublisherDto getPublisher(
		@PathVariable UUID id,
		@RequestParam(name = "expand", required = false) @Nullable String expand) {

		return publisherService.getPublisher(id, expand);
	}
}
