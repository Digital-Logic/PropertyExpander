package net.digitallogic.PropertyExpander.persistence.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(of = "name")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genre")
@Entity(name = "GenreEntity")
public class GenreEntity extends EntityBase<UUID> {

	@Column(name = "name")
	private String name;

	@Builder.Default
	@ManyToMany(mappedBy = BookEntity_.GENRES)
	private Set<BookEntity> books = new HashSet<>();
}
