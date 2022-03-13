package net.digitallogic.propertyexpander.persistence.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PublisherEntity")
@Table(name = "publisher")
public class PublisherEntity extends EntityBase<UUID> {

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher")
	private Set<BookEntity> books = new HashSet<>();
}
