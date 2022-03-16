package net.digitallogic.PropertyExpander.web.controller;

import net.digitallogic.PropertyExpander.persistence.dto.BookDto;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public interface BookController {

	List<BookDto> getBooks(int page, int limit, String sort, @Nullable String expand, HttpServletResponse response);
	BookDto getBook(UUID id, @Nullable String expand);
}
