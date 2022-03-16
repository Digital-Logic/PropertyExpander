package net.digitallogic.PropertyExpander.web.controller;

import net.digitallogic.PropertyExpander.persistence.dto.AuthorDto;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public interface AuthorController {

	List<AuthorDto> getAuthors(int page, int limit, String sort, @Nullable String expand, HttpServletResponse response);
	AuthorDto getAuthor(UUID id, @Nullable String expand);
}
