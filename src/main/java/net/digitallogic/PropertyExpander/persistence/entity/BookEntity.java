package net.digitallogic.PropertyExpander.persistence.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(of = {"title"})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BookEntity")
@Table(name = "book")
public class BookEntity extends EntityBase<UUID> {

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "total_pages", nullable = false)
	private int totalPages;

	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "isbn", unique = true, nullable = false)
	private String isbn;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "publisher")
	private PublisherEntity publisher;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
		name = "book_author",
		joinColumns = @JoinColumn(name = "book"),
		inverseJoinColumns = @JoinColumn(name = "author")
	)
	@Builder.Default
	private Set<AuthorEntity> authors = new HashSet<>();

	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
		name = "book_genre",
		joinColumns = @JoinColumn(name = "book"),
		inverseJoinColumns = @JoinColumn(name = "genre"))
	private Set<GenreEntity> genres = new HashSet<>();

	public void addAuthor(AuthorEntity author) {
		authors.add(author);
	}

	public void removeAuthor(AuthorEntity author) {
		authors.remove(author);
	}

	public void addGenre(GenreEntity genre) {
		genres.add(genre);
	}
	public void removeGenre(GenreEntity genre) {
		genres.remove(genre);
	}
}
