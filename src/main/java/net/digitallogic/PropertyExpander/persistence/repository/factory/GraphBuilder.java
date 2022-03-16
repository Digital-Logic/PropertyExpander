package net.digitallogic.PropertyExpander.persistence.repository.factory;

import lombok.extern.slf4j.Slf4j;
import net.digitallogic.PropertyExpander.Utils;
import net.digitallogic.PropertyExpander.web.error.ErrorMessage;
import net.digitallogic.PropertyExpander.web.error.exception.BadRequestException;
import org.springframework.lang.Nullable;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class GraphBuilder<T> {
	private final Class<T> entityType;
	private final Map<String, Consumer<EntityGraph<T>>> graphMapper;

	private GraphBuilder(Class<T> entityType,
	                     Map<String, Consumer<EntityGraph<T>>> graphMapper) {
		this.entityType = entityType;
		this.graphMapper = graphMapper;
	}

	@Nullable
	public GraphResolver<T> createResolver(@Nullable String expandAttributes) {
		if (expandAttributes == null || expandAttributes.isBlank())
			return null;

		return new GraphResolver<>(entityType, graphMapper, expandAttributes);
	}

	public GraphResolver<T> createResolver(Attribute<T, ?> attribute) {
		return new GraphResolver<>(entityType, graphMapper, attribute);
	}


	public static <E>EntityGraphBuilder<E> builder(Class<E> type) {
		return new EntityGraphBuilder<>(type);
	}

	/**
	 *
	 */
	public static class GraphResolver<T> {
		private final List<String> properties;
		private final Map<String, Consumer<EntityGraph<T>>> graphMapper;
		private final Class<T> entityType;

		public GraphResolver(Class<T> entityType,
		                     Map<String, Consumer<EntityGraph<T>>> graphMapper,
		                     Attribute<T, ?> attribute) {
			this(entityType, graphMapper, attribute.getName());
		}

		public GraphResolver(Class<T> entityType,
		                     Map<String, Consumer<EntityGraph<T>>> graphMapper,
		                     String expandAttributes) {

			// Split attributes list and transform to camelCase if needed.
			this.entityType = entityType;
			this.graphMapper = graphMapper;
			properties = Utils.toCamel(
				Utils.splitOnComma(expandAttributes)
			);

			log.debug("GraphMapper properties: {}", properties);

			List<String> invalidProperties = properties.stream()
				.filter(Predicate.not(graphMapper::containsKey))
				.toList();


			if (!invalidProperties.isEmpty()) {
				log.debug("Invalid Properties detected in GraphBuilder: {}", invalidProperties);

				throw new BadRequestException(new ErrorMessage("Invalid expansion properties",
					invalidProperties.stream()
						.collect(Collectors.toMap(s -> s, i -> "Invalid expansion property."))
					));
			}
		}

		public EntityGraph<T> createGraph(EntityManager entityManager) {

			final EntityGraph<T> graph = entityManager.createEntityGraph(entityType);

			properties.stream()
				.map(graphMapper::get)
				.forEach(g -> g.accept(graph));

			return graph;
		}
	}

	/**
	 * Create an GraphBuilder
	 * @param <T>
	 */

	public static class EntityGraphBuilder<T> {

		private final Class<T> entityType;
		private final Map<String, Consumer<EntityGraph<T>>> graphMapper = new HashMap<>();

		private EntityGraphBuilder(Class<T> entityType) {
			this.entityType = entityType;
		}


		public EntityGraphBuilder<T> addProperty(String property) {
			return addProperty(property, graph -> graph.addSubgraph(property));
		}

		public EntityGraphBuilder<T> addProperty(Attribute<T, ?> attribute) {
			return addProperty(attribute.getName(), graph -> graph.addSubgraph(attribute));
		}

		public EntityGraphBuilder<T> addProperty(String property, Consumer<EntityGraph<T>> mapper) {
			graphMapper.put(property, mapper);
			return this;
		}

		public GraphBuilder<T> build() {
			return new GraphBuilder<>(entityType, graphMapper);
		}

	}
}
