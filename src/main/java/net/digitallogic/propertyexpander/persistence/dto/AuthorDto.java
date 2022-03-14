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
@ToString(callSuper = true, of = {"firstName", "lastName"})
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthorDto extends DtoBase<UUID> {

	private String firstName;
	private String lastName;
	private String email;

	@Builder.Default
	private List<BookDto> books = new ArrayList<>();

	public AuthorDto(AuthorEntity entity) {
		super(entity);

		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.email = entity.getEmail();

		if (pu.isLoaded(entity, "books"))
			this.books = entity.getBooks().stream()
				.map(BookDto::new)
				.collect(Collectors.toList());
	}

	// copy constructor
	public AuthorDto(AuthorDto dto) {
		super(dto);

		this.firstName = dto.getFirstName();
		this.lastName = dto.getLastName();
		this.email = dto.getEmail();
	}

	@Data
	@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private static class _BookDto extends DtoBase<UUID> {
		private String title;
		private int totalPages;
		private float price;
		private String isbn;
		private PublisherDto publisherDto;
		private List<UUID> authors = new ArrayList<>();

		public _BookDto(BookEntity entity) {
			super(entity);

			this.title = entity.getTitle();
			this.totalPages = entity.getTotalPages();
			this.price = entity.getPrice();
			this.isbn = entity.getIsbn();

			if (pu.isLoaded(entity, BookEntity_.PUBLISHER))
				this.publisherDto = new PublisherDto(entity.getPublisher());

			if (pu.isLoaded(entity, BookEntity_.AUTHORS)) {
				authors = entity.getAuthors().stream()
					.map(AuthorEntity::getId)
					.collect(Collectors.toList());
			}
		}
	}
}
