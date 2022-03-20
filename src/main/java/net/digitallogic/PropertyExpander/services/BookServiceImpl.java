package net.digitallogic.PropertyExpander.services;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.dto.BookDto;
import net.digitallogic.PropertyExpander.persistence.entity.BookEntity;
import net.digitallogic.PropertyExpander.persistence.repository.BookRepository;
import net.digitallogic.PropertyExpander.persistence.repository.factory.GraphBuilder;
import net.digitallogic.PropertyExpander.web.error.ErrorMessage;
import net.digitallogic.PropertyExpander.web.error.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.UUID;

import static net.digitallogic.PropertyExpander.Utils.processSortBy;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final GraphBuilder<BookEntity> bookGraphBuilder;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository,
	                       GraphBuilder<BookEntity> bookGraphBuilder) {
		this.bookRepository = bookRepository;
		this.bookGraphBuilder = bookGraphBuilder;
	}

	@Override
	public Slice<BookDto> getBooks(int page, int limit,
	                               String sort,
	                               @Nullable String expand) {

		return bookRepository.findAll(
				PageRequest.of(page, limit, Sort.by(processSortBy(sort))),
				bookGraphBuilder.createResolver(expand)
			).map(BookDto::new);
	}

	@Override
	public BookDto getBook(UUID id, @Nullable String expand) {

		return new BookDto(bookRepository.findById(id, bookGraphBuilder.createResolver(expand))
			.orElseThrow(() ->
				new NotFoundException(new ErrorMessage("Book by id: " + id + ", not found.")))
		);
	}
}
