package net.digitallogic.propertyexpander.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.digitallogic.propertyexpander.persistence.entity.GenreEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, of = {"name"})
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto extends DtoBase<UUID> {

	private String name;

	@Builder.Default
	private List<BookDto> books = new ArrayList<>();

	public GenreDto(GenreEntity entity) {
		super(entity);
		this.name = entity.getName();
	}

	@Data
	@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
	@ToString(callSuper = true, of = {"name"})
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class GenreDtoLt extends DtoBase<UUID> {
		private String name;

		public GenreDtoLt(GenreEntity entity) {
			super(entity);

			this.name = entity.getName();
		}
	}
}
