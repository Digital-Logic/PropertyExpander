package net.digitallogic.PropertyExpander.config;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.persistence.entity.*;
import net.digitallogic.PropertyExpander.persistence.repository.factory.AdvancedJpaRepository;
import net.digitallogic.PropertyExpander.persistence.repository.factory.GraphBuilder;
import net.digitallogic.PropertyExpander.persistence.repository.factory.RepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(value = "net.digitallogic.PropertyExpander.persistence")
@EnableJpaRepositories(
	basePackages = "net.digitallogic.PropertyExpander.persistence",
	repositoryFactoryBeanClass = RepositoryFactoryBean.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AdvancedJpaRepository.class)
	}
)
@Slf4j
public class RepositoryConfig {

	@Bean
	public GraphBuilder<BookEntity> bookEntityGraphBuilder() {
		log.debug("Creating BookEntityGraphBuilder.");

		return GraphBuilder.builder(BookEntity.class)
			.addProperty(BookEntity_.authors)
			.addProperty(BookEntity_.publisher)
			.addProperty(BookEntity_.genres)
			.build();
	}

	@Bean
	public GraphBuilder<AuthorEntity> authorEntityGraphBuilder() {
		log.debug("Creating AuthorEntityGraphBuilder.");

		return GraphBuilder.builder(AuthorEntity.class)
			.addProperty(AuthorEntity_.books)
			.build();
	}

	@Bean
	public GraphBuilder<PublisherEntity> publisherEntityGraphBuilder() {
		log.debug("Creating PublisherEntityGraphBuilder.");

		return GraphBuilder.builder(PublisherEntity.class)
			.addProperty(PublisherEntity_.books)
			.build();
	}

	@Bean
	public GraphBuilder<GenreEntity> genreEntityGraphBuilder() {
		log.debug("Creating GenreEntityGraphBuilder.");

		return GraphBuilder.builder(GenreEntity.class)
			.addProperty(GenreEntity_.books)
			.build();
	}
}
