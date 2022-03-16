package net.digitallogic.PropertyExpander.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.dto.GenreDto;
import net.digitallogic.PropertyExpander.services.GenreService;
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
@RequestMapping(path = Routes.GENRE_ROUTE)
public class GenreControllerImpl implements GenreController {

	private final GenreService genreService;

	@Autowired
	public GenreControllerImpl(GenreService genreService) {
		this.genreService = genreService;
	}

	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<GenreDto> getGenres(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "limit", defaultValue = "25") int limit,
		@RequestParam(name = "sort", defaultValue = "name") String sort,
		@RequestParam(name = "expand", required = false) @Nullable String expand,
		HttpServletResponse response) {
		Slice<GenreDto> genres = genreService.getGenres(page, limit, sort, expand);

		response.addHeader(Headers.PAGE_LIMIT_HEADER, Integer.toString(limit));
		response.addHeader(Headers.PAGE_CURRENT, Integer.toString(page));
		response.addHeader(Headers.PAGE_HAS_NEXT, Boolean.toString(genres.hasNext()));

		return genres.toList();
	}

	@Override
	@GetMapping(path = "/{id}")
	public GenreDto getGenre(
		@PathVariable UUID id,
		@RequestParam(name = "expan", required = false) @Nullable String expand) {
		return genreService.getGenre(id, expand);
	}
}
