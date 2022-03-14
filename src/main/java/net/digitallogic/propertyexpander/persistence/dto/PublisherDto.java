package net.digitallogic.propertyexpander.persistence.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.digitallogic.propertyexpander.persistence.entity.PublisherEntity;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, of = {"name"})
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDto extends DtoBase<UUID> {
	private String name;



	public PublisherDto(PublisherEntity entity) {
		super(entity);
		this.name = entity.getName();
	}

	// Copy constructor
	public PublisherDto(PublisherDto dto) {
		super(dto);
		this.name = dto.getName();
	}
}
