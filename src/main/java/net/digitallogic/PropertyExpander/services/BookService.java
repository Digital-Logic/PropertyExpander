package net.digitallogic.PropertyExpander.services;

import net.digitallogic.PropertyExpander.persistence.dto.BookDto;
import org.springframework.data.domain.Slice;

import javax.annotation.Nullable;
import java.util.UUID;

public interface BookService {
	Slice<BookDto> getBooks(int page, int limit, String sort, @Nullable String expand);
	BookDto getBook(UUID id, @Nullable String expand);
}
