package net.digitallogic.PropertyExpander.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.dto.BookDto;
import net.digitallogic.PropertyExpander.services.BookService;
import net.digitallogic.PropertyExpander.web.Headers;
import net.digitallogic.PropertyExpander.web.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = Routes.BOOK_ROUTE)
public class BookControllerImpl implements BookController {

	private final BookService bookService;

	@Autowired
	public BookControllerImpl(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<BookDto> getBooks(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "limit", defaultValue = "25") int limit,
		@RequestParam(name = "sort", defaultValue = "title") String sort,
		@RequestParam(name = "expand", required = false) @Nullable String expand,
		HttpServletResponse response
	) {
		Slice<BookDto> dtoSlice = bookService.getBooks(page, limit, sort, expand);

		response.addHeader(Headers.PAGE_LIMIT_HEADER, Integer.toString(limit));
		response.addHeader(Headers.PAGE_CURRENT, Integer.toString(page));
		response.addHeader(Headers.PAGE_HAS_NEXT, Boolean.toString(dtoSlice.hasNext()));

		return dtoSlice.toList();
	}

	@Override
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BookDto getBook(
		@PathVariable UUID id,
		@RequestParam(name= "expand", required = false ) @Nullable String expand
	) {
		return bookService.getBook(id, expand);
	}
}
