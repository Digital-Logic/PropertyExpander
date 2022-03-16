package net.digitallogic.PropertyExpander.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.dto.AuthorDto;
import net.digitallogic.PropertyExpander.services.AuthorService;
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
@RequestMapping(path = Routes.AUTHOR_ROUTE)
public class AuthorControllerImpl implements AuthorController {

	private final AuthorService authorService;

	@Autowired
	public AuthorControllerImpl(AuthorService authorService) {
		this.authorService = authorService;
	}

	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<AuthorDto> getAuthors(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "limit", defaultValue = "25") int limit,
		@RequestParam(name = "sort", defaultValue = "last_name") String sort,
		@RequestParam(name = "expand", required = false) @Nullable String expand,
		HttpServletResponse response
	) {
		Slice<AuthorDto> authors = authorService.getAuthors(page, limit, sort, expand);

		response.addHeader(Headers.PAGE_LIMIT_HEADER, Integer.toString(limit));
		response.addHeader(Headers.PAGE_CURRENT, Integer.toString(page));
		response.addHeader(Headers.PAGE_HAS_NEXT, Boolean.toString(authors.hasNext()));

		return authors.toList();
	}

	@Override
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AuthorDto getAuthor(
		@PathVariable UUID id,
		@RequestParam(name = "expand", required = false) @Nullable String expand) {

		return authorService.getAuthor(id, expand);
	}
}
