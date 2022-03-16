package net.digitallogic.PropertyExpander.web.controller;

import net.digitallogic.PropertyExpander.persistence.dto.GenreDto;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public interface GenreController {

	List<GenreDto> getGenres(int page, int limit, String sort, @Nullable String expand, HttpServletResponse response);
	GenreDto getGenre(UUID id, @Nullable String expand);
}
