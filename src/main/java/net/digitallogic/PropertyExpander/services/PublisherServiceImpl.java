package net.digitallogic.PropertyExpander.services;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.dto.PublisherDto;
import net.digitallogic.PropertyExpander.persistence.entity.PublisherEntity;
import net.digitallogic.PropertyExpander.persistence.repository.PublisherRepository;
import net.digitallogic.PropertyExpander.persistence.repository.factory.GraphBuilder;
import net.digitallogic.PropertyExpander.web.error.ErrorMessage;
import net.digitallogic.PropertyExpander.web.error.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static net.digitallogic.PropertyExpander.Utils.processSortBy;

@Service
@Slf4j
public class PublisherServiceImpl implements PublisherService {

	private final PublisherRepository publisherRepository;
	private final GraphBuilder<PublisherEntity> publisherGraphBuilder;

	@Autowired
	public PublisherServiceImpl(
		PublisherRepository publisherRepository,
		GraphBuilder<PublisherEntity> publisherGraphBuilder) {

		this.publisherRepository = publisherRepository;
		this.publisherGraphBuilder = publisherGraphBuilder;
	}

	@Override
	public Slice<PublisherDto> getPublishers(int page, int limit, String sort, @Nullable String expand) {

		return publisherRepository.findAll(
			PageRequest.of(page, limit, Sort.by(processSortBy(sort))),
			publisherGraphBuilder.createResolver(expand)
		)
			.map(PublisherDto::new);
	}

	@Override
	public PublisherDto getPublisher(UUID id, String expand) {
		return new PublisherDto(publisherRepository.findById(id, publisherGraphBuilder.createResolver(expand))
			.orElseThrow(() ->
				new NotFoundException(
					new ErrorMessage("Publisher by id: " + id + ", not found.")
				)
			)
		);
	}
}





