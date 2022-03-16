package net.digitallogic.PropertyExpander.services;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.dto.AuthorDto;
import net.digitallogic.PropertyExpander.persistence.entity.AuthorEntity;
import net.digitallogic.PropertyExpander.persistence.repository.AuthorRepository;
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

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;
	private final GraphBuilder<AuthorEntity> authorGraphBuilder;

	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository, GraphBuilder<AuthorEntity> authorGraphBuilder) {
		this.authorRepository = authorRepository;
		this.authorGraphBuilder = authorGraphBuilder;
	}

	@Override
	public Slice<AuthorDto> getAuthors(int page, int limit, String sort, @Nullable String expand) {
		return authorRepository.findAll(
			PageRequest.of(page, limit, Sort.by(processSortBy(sort))),
			authorGraphBuilder.createResolver(expand)
		).map(AuthorDto::new);
	}

	@Override
	public AuthorDto getAuthor(UUID id, @Nullable String expand) {
		return new AuthorDto(authorRepository.findById(id, authorGraphBuilder.createResolver(expand))
			.orElseThrow(() ->
				new NotFoundException(new ErrorMessage("Author by id: " + id + ", not found.")))
		);
	}
}
