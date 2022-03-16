package net.digitallogic.PropertyExpander.services;

import net.digitallogic.PropertyExpander.persistence.dto.GenreDto;
import org.springframework.data.domain.Slice;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface GenreService {

	Slice<GenreDto> getGenres(int page, int limit, String sort, @Nullable String expand);
	GenreDto getGenre(UUID id,@Nullable String expand);
}
