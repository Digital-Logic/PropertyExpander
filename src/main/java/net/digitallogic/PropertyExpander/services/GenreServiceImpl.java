package net.digitallogic.PropertyExpander.services;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.dto.GenreDto;
import net.digitallogic.PropertyExpander.persistence.entity.GenreEntity;
import net.digitallogic.PropertyExpander.persistence.repository.GenreRepository;
import net.digitallogic.PropertyExpander.persistence.repository.factory.GraphBuilder;
import net.digitallogic.PropertyExpander.web.error.ErrorMessage;
import net.digitallogic.PropertyExpander.web.error.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static net.digitallogic.PropertyExpander.Utils.processSortBy;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;
	private final GraphBuilder<GenreEntity> genreGraphBuilder;

	@Autowired
	public GenreServiceImpl(GenreRepository genreRepository, GraphBuilder<GenreEntity> genreGraphBuilder) {
		this.genreRepository = genreRepository;
		this.genreGraphBuilder = genreGraphBuilder;
	}

	@Override
	public Slice<GenreDto> getGenres(int page, int limit, String sort, String expand) {
		return genreRepository.findAll(
			PageRequest.of(page, limit, Sort.by(processSortBy(sort))),
			genreGraphBuilder.createResolver(expand)
		).map(GenreDto::new);

	}

	@Override
	public GenreDto getGenre(UUID id, String expand) {
		return new GenreDto(
			genreRepository.findById(id, genreGraphBuilder.createResolver(expand))
				.orElseThrow(() ->
					new NotFoundException(
						new ErrorMessage("Genre by id: " + id + ", not found.")
					))
		);
	}
}
