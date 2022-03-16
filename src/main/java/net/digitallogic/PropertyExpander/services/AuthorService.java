package net.digitallogic.PropertyExpander.services;

import net.digitallogic.PropertyExpander.persistence.dto.AuthorDto;
import org.springframework.data.domain.Slice;

import javax.annotation.Nullable;
import java.util.UUID;

public interface AuthorService {
	Slice<AuthorDto> getAuthors(int page, int limit, String sort, @Nullable String expand);
	AuthorDto getAuthor(UUID id,@Nullable String expand);
}
