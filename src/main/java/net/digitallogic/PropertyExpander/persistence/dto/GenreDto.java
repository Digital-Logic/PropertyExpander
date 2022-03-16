package net.digitallogic.PropertyExpander.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.digitallogic.PropertyExpander.persistence.entity.BookEntity;
import net.digitallogic.PropertyExpander.persistence.entity.GenreEntity;
import net.digitallogic.PropertyExpander.persistence.entity.GenreEntity_;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, of = {"name"})
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenreDto extends DtoBase<UUID> {

	private String name;

	@Builder.Default
	private List<BookDto.BookDtoLt> books = new ArrayList<>();

	public GenreDto(GenreEntity entity) {
		super(entity);
		this.name = entity.getName();

		if (pu.isLoaded(entity, GenreEntity_.BOOKS)) {
			books = entity.getBooks().stream()
				.map(BookDto.BookDtoLt::new)
				.toList();
		}
	}

	@Data
	@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
	@ToString(callSuper = true, of = {"name"})
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class GenreDtoLt extends DtoBase<UUID> {
		private String name;
		private List<UUID> books = new ArrayList<>();

		public GenreDtoLt(GenreEntity entity) {
			super(entity);

			this.name = entity.getName();

			if (pu.isLoaded(entity, GenreEntity_.BOOKS))
				books = entity.getBooks().stream()
					.map(BookEntity::getId)
					.toList();
		}
	}
}
