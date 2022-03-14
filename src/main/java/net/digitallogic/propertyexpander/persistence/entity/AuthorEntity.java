package net.digitallogic.propertyexpander.persistence.entity;

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
@ToString(of = {"firstName", "lastName"}, callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity(name = "AuthorEntity")
@Table(name = "author")
public class AuthorEntity extends EntityBase<UUID> {

	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Builder.Default
	@ManyToMany(mappedBy = BookEntity_.AUTHORS)
	private Set<BookEntity> books = new HashSet<>();
}