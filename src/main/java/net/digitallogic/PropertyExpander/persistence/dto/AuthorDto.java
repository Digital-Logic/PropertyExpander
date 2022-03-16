package net.digitallogic.PropertyExpander.persistence.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.digitallogic.PropertyExpander.persistence.entity.AuthorEntity;
import net.digitallogic.PropertyExpander.persistence.entity.BookEntity;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthorDto extends DtoBase<UUID> {

	private String firstName;
	private String lastName;
	private String email;

	@Builder.Default
	private List<BookDto.BookDtoLt> books = new ArrayList<>();

	public AuthorDto(AuthorEntity entity) {
		super(entity);

		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.email = entity.getEmail();

		if (pu.isLoaded(entity, "books"))
			this.books = entity.getBooks().stream()
				.map(BookDto.BookDtoLt::new)
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
	public static class AuthorDtoLt extends DtoBase<UUID> {
		private String firstName;
		private String lastName;
		private String email;
		private List<UUID> books = new ArrayList<>();

		public AuthorDtoLt(AuthorEntity entity) {
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

		public AuthorDtoLt(AuthorDto dto) {
			super(dto);

			this.firstName = dto.getFirstName();
			this.lastName = dto.getLastName();
			this.email = dto.getEmail();
			this.books = dto.getBooks().stream()
				.map(BookDto.BookDtoLt::getId)
				.collect(Collectors.toList());
		}
	}
}
