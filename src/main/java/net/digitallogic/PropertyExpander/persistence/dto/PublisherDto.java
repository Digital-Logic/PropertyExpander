package net.digitallogic.PropertyExpander.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.digitallogic.PropertyExpander.persistence.dto.BookDto.BookDtoLt;
import net.digitallogic.PropertyExpander.persistence.entity.BookEntity;
import net.digitallogic.PropertyExpander.persistence.entity.PublisherEntity;
import net.digitallogic.PropertyExpander.persistence.entity.PublisherEntity_;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, of = {"name"})
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PublisherDto extends DtoBase<UUID> {
	private String name;

	@Builder.Default
	private List<BookDtoLt> books = new ArrayList<>();


	public PublisherDto(PublisherEntity entity) {
		super(entity);
		this.name = entity.getName();

		if (pu.isLoaded(entity, PublisherEntity_.BOOKS)) {
			books = entity.getBooks().stream()
				.map(BookDtoLt::new)
				.collect(Collectors.toList());
		}
	}
	// Copy constructor
	public PublisherDto(PublisherDto dto) {
		super(dto);
		this.name = dto.getName();
	}

	@Data
	@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
	@ToString(callSuper = true, of = {"name"})
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class PublisherDtoLt extends DtoBase<UUID> {
		private String name;
		private List<UUID> books = new ArrayList<>();


		public PublisherDtoLt(PublisherEntity entity) {
			super(entity);
			this.name = entity.getName();

			if (pu.isLoaded(entity, PublisherEntity_.BOOKS)) {
				books = entity.getBooks().stream()
					.map(BookEntity::getId)
					.collect(Collectors.toList());
			}
		}
	}
}
