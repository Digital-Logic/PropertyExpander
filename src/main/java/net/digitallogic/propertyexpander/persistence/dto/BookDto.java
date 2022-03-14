package net.digitallogic.propertyexpander.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.digitallogic.propertyexpander.persistence.entity.AuthorEntity;
import net.digitallogic.propertyexpander.persistence.entity.BookEntity;
import net.digitallogic.propertyexpander.persistence.entity.BookEntity_;

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
	private List<_AuthorDto> authors = new ArrayList<>();

	@Builder.Default
	private List<String> genre = new ArrayList<>();

	public BookDto(BookEntity entity) {
		super(entity);
		title = entity.getTitle();
		totalPages = entity.getTotalPages();
		price = entity.getPrice();
		isbn = entity.getIsbn();

		if (pu.isLoaded(entity, BookEntity_.AUTHORS)) {
			authors = entity.getAuthors().stream()
				.map(_AuthorDto::new)
				.collect(Collectors.toList());
		}

		if (pu.isLoaded(entity, BookEntity_.PUBLISHER)) {
			publisher = new PublisherDto(entity.getPublisher());
		}

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
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private static class _AuthorDto extends DtoBase<UUID> {
		private String firstName;
		private String lastName;
		private String email;
		private List<UUID> books = new ArrayList<>();

		public _AuthorDto(AuthorEntity entity) {
			super(entity);

			this.firstName = entity.getFirstName();
			this.lastName = entity.getLastName();
			this.email = entity.getEmail();

			if (pu.isLoaded(entity, "books")) {
				books = entity.getBooks().stream()
					.map(BookEntity::getId)
					.collect(Collectors.toList());
			}
		}
	}
}