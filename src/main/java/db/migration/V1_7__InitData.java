package db.migration;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import net.digitallogic.propertyexpander.persistence.dto.AuthorDto;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
public class V1_7__InitData extends BaseJavaMigration {

	private final Faker faker = new Faker();
	private final Clock clock = Clock.systemUTC();
	private final Random random = new Random();

	public void migrate(Context context) throws Exception {

		List<AuthorDto> authors = new ArrayList<>();

		for (int i=0; i<20; ++i) {
			AuthorDto author = AuthorDto.builder()
				.id(UUID.randomUUID())
				.firstName(faker.name().firstName())
				.lastName(faker.name().lastName())
				.email(faker.internet().emailAddress())
				.build();

			authors.add(author);

			Statement st = context.getConnection().createStatement();

			st.executeUpdate("INSERT INTO author (id, first_name, last_name, email) " +
				" VALUES ( '" + author.getId().toString() + "', '" + author.getFirstName() + "', '" + author.getLastName() + "', '" +
				author.getEmail() + "')");
		}

		for (int i=0; i<500; ++i) {

		}
	}
}