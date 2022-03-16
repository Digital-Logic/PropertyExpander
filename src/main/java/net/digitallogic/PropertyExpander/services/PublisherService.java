package net.digitallogic.PropertyExpander.services;

import net.digitallogic.PropertyExpander.persistence.dto.PublisherDto;
import org.springframework.data.domain.Slice;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface PublisherService {

	Slice<PublisherDto> getPublishers(int page, int limit, String sort, @Nullable String expand);
	PublisherDto getPublisher(UUID id, @Nullable String expand);
}
