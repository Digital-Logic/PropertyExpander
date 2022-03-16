package net.digitallogic.PropertyExpander.persistence.repository;

import net.digitallogic.PropertyExpander.persistence.entity.BookEntity;
import net.digitallogic.PropertyExpander.persistence.repository.factory.EntityGraphRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity, UUID>,
	EntityGraphRepository<BookEntity, UUID>, JpaSpecificationExecutor<BookEntity> {

}
