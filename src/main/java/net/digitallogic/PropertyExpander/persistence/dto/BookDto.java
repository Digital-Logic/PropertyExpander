package net.digitallogic.PropertyExpander.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.digitallogic.PropertyExpander.persistence.entity.AuthorEntity;
import net.digitallogic.PropertyExpander.persistence.entity.BookEntity;
import net.digitallogic.PropertyExpander.persistence.entity.BookEntity_;
import net.digitallogic.PropertyExpander.persistence.entity.GenreEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, of = {"title"})
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookDto extends DtoBase<UUID> {
	private String title;
	private int totalPages;
	private float price;
	private String isbn;
	private PublisherDto publisher;

	@Builder.Default
	private List<AuthorDto.AuthorDtoLt> authors = new ArrayList<>();

	@Builder.Default
	private List<GenreDto.GenreDtoLt> genres = new ArrayList<>();

	public BookDto(BookEntity entity) {
		super(entity);
		title = entity.getTitle();
		totalPages = entity.getTotalPages();
		price = entity.getPrice();
		isbn = entity.getIsbn();

		if (pu.isLoaded(entity, BookEntity_.AUTHORS)) {
			authors = entity.getAuthors().stream()
				.map(AuthorDto.AuthorDtoLt::new)
				.collect(Collectors.toList());
		}

		if (pu.isLoaded(entity, BookEntity_.PUBLISHER)) {
			publisher = new PublisherDto(entity.getPublisher());
		}

		if (pu.isLoaded(entity, BookEntity_.GENRES))
			genres = entity.getGenres().stream()
				.map(GenreDto.GenreDtoLt::new)
				.toList();

	}

	public BookDto(BookDto dto) {
		super(dto);
		title = dto.getTitle();
		totalPages = dto.getTotalPages();
		price = dto.getPrice();
		isbn = dto.getIsbn();

		this.authors = dto.getAuthors();
	}

	@Data
	@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
	@ToString(callSuper = true, of = {"title"})
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class BookDtoLt extends DtoBase<UUID> {
		private String title;
		private int totalPages;
		private float price;
		private String isbn;
		private UUID publisherDto;
		private List<UUID> authors = new ArrayList<>();
		private List<UUID> genres = new ArrayList<>();

		public BookDtoLt(BookEntity entity) {
			super(entity);

			this.title = entity.getTitle();
			this.totalPages = entity.getTotalPages();
			this.price = entity.getPrice();
			this.isbn = entity.getIsbn();

			if (pu.isLoaded(entity, BookEntity_.PUBLISHER))
				this.publisherDto = entity.getPublisher().getId();

			if (pu.isLoaded(entity, BookEntity_.AUTHORS)) {
				authors = entity.getAuthors().stream()
					.map(AuthorEntity::getId)
					.collect(Collectors.toList());
			}

			if (pu.isLoaded(entity, BookEntity_.GENRES)) {
				genres = entity.getGenres().stream()
					.map(GenreEntity::getId)
					.toList();
			}
		}
	}
}