package db.migration;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import net.digitallogic.propertyexpander.persistence.dto.AuthorDto;
import net.digitallogic.propertyexpander.persistence.dto.BookDto;
import net.digitallogic.propertyexpander.persistence.dto.GenreDto;
import net.digitallogic.propertyexpander.persistence.dto.PublisherDto;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Clock;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class V1_7__InitData extends BaseJavaMigration {

	private final Faker faker = new Faker();
	private final Clock clock = Clock.systemUTC();
	private final Random random = new Random();

	public void migrate(Context context) throws Exception {

		// Generate Authors
		List<AuthorDto> authors = new ArrayList<>();

		String insertAuthSt = "insert into author (id, first_name, last_name, email) values (?, ?, ?,?);";
		try (PreparedStatement insert = context.getConnection().prepareStatement(insertAuthSt)) {
			for (int i = 0; i < 50; ++i) {
				AuthorDto author = AuthorDto.builder()
					.id(UUID.randomUUID())
					.firstName(faker.name().firstName())
					.lastName(faker.name().lastName())
					.email(faker.internet().emailAddress())
					.build();

				authors.add(author);

				insert.setObject(1, author.getId());
				insert.setString(2, author.getFirstName());
				insert.setString(3, author.getLastName());
				insert.setString(4, author.getEmail());

				insert.addBatch();
			}
			insert.executeBatch();
			context.getConnection().commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage());
		}

		// Generate Genres
		// Store genres in a hashmap to prevent duplicate names
		Map<String, GenreDto> genres = new HashMap<>();
		for (int i = 0; i < 20; ++i) {
			GenreDto genre = GenreDto.builder()
				.id(UUID.randomUUID())
				.name(faker.book().genre())
				.build();


			genres.put(genre.getName(), genre);
		}

		String insertGenreSt = "insert into genre (id, name) values (?, ?);";

		try (PreparedStatement insert = context.getConnection().prepareStatement(insertGenreSt)) {
			for (GenreDto genre : genres.values()) {

				insert.setObject(1, genre.getId());
				insert.setString(2, genre.getName());

				insert.addBatch();
			}
			insert.executeBatch();
			context.getConnection().commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage());
		}

		// Generate publishers
		Map<String, PublisherDto> publishers = new HashMap<>();
		for (int i = 0; i < 30; ++i) {
			PublisherDto publisher = PublisherDto.builder()
				.id(UUID.randomUUID())
				.name(faker.book().publisher())
				.build();

			publishers.put(publisher.getName(), publisher);
		}

		String insertPubSt = "insert into publisher values (?,?);";
		try (PreparedStatement insert = context.getConnection().prepareStatement(insertPubSt)) {
			for (PublisherDto pub : publishers.values()) {
				insert.setObject(1, pub.getId());
				insert.setString(2, pub.getName());

				insert.addBatch();
			}
			insert.executeBatch();
			context.getConnection().commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage());
		}


		// Generate books
		List<BookDto> books = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("00.00");

		for (int i = 0; i < 500; ++i) {
			BookDto book;

			book = BookDto.builder()
				.id(UUID.randomUUID())
				.isbn(isbnGenerator())
				.title(faker.book().title())
				.totalPages(random.nextInt(25, 700))
				.price(random.nextFloat(2, 15))
				.build();

			// Set publisher, pick random publisher out of the hashmap of publishers
			book.setPublisher(
				publishers.values().toArray(PublisherDto[]::new)[random.nextInt(publishers.size())]
			);

			books.add(book);
		}

		String insertBookSt = "insert into book (id, title, total_pages, price, isbn, publisher) values (?,?,?,?,?,?);";

		try (PreparedStatement insert = context.getConnection().prepareStatement(insertBookSt)) {

			for (BookDto book : books) {

				insert.setObject(1, book.getId());
				insert.setString(2, book.getTitle());
				insert.setInt(3, book.getTotalPages());
				insert.setFloat(4, book.getPrice());
				insert.setString(5, book.getIsbn());
				insert.setObject(6, book.getPublisher().getId());

				insert.addBatch();
			}
			insert.executeBatch();
			context.getConnection().commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage());
		}

		// assign authors to books
		for (BookDto book : books) {
			int ran = random.nextInt(100);
			int numOfAuthors = 3;

			if (ran < 70) {
				numOfAuthors = 1;
			} else if (ran < 90)
				numOfAuthors = 2;

			book.setAuthors(
				new ArrayList<>(
					IntStream.range(0, numOfAuthors)
						.mapToObj(i ->
							authors.get(random.nextInt(authors.size()))
						)
						.collect(Collectors.toSet())
				));
		}

		String insertBookAuth = "insert into book_author (book, author) values (?, ?);";
		try (PreparedStatement insert = context.getConnection().prepareStatement(insertBookAuth)) {
			for (BookDto book: books) {
				for (AuthorDto author : book.getAuthors()) {
					insert.setObject(1, book.getId());
					insert.setObject(2, author.getId());

					insert.addBatch();
				}
			}
			insert.executeBatch();
			context.getConnection().commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage());
		}

		// assign genres to books

		String insertBookGenre = "insert into book_genre (book, genre) values (?, ?);";

		try (PreparedStatement insert = context.getConnection().prepareStatement(insertBookGenre)) {

			for (BookDto book : books) {
				// A single book can have up to 3 genres
				int numOfGenres = random.nextInt(1, 4);

				book.setGenres(new ArrayList(
					IntStream.range(0, numOfGenres)
						.mapToObj(i -> genres.values().toArray(GenreDto[]::new)[random.nextInt(genres.size())])
						.collect(Collectors.toSet()) 	// Use set to prevent same genre from being selected twice.
				));

				for (GenreDto g : book.getGenres()) {
					insert.setObject(1, book.getId());
					insert.setObject(2, g.getId());

					insert.addBatch();
				}
			}
			insert.executeBatch();
			context.getConnection().commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage());
		}
	}

	private String isbnGenerator() {
		String chars = "abcdefghijklmnopqrstuzwxyz0123456789";
		char[] charAry = new char[40];

		for (int i = 0; i < charAry.length; ++i) {
			charAry[i] = chars.charAt(random.nextInt(chars.length()));
		}

		return String.valueOf(charAry);
	}
}