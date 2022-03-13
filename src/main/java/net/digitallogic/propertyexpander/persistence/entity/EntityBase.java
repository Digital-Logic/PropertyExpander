package net.digitallogic.propertyexpander.persistence.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class EntityBase<ID extends Serializable> implements Persistable<ID> {

	@Id
	@Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
	protected ID id;

	@Version
	@Builder.Default
	@Setter(value = AccessLevel.PRIVATE)
	@Column(name = "version", nullable = false)
	protected int version = 0;


	@CreatedDate
	@Column(name = "created_date", updatable = false)
	protected LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	protected LocalDateTime lastModifiedDate;

	@Transient
	@Builder.Default
	@Setter(AccessLevel.PROTECTED)
	protected boolean isNew = true;

	@PostPersist
	@PostLoad
	private void toggleIsNew() {
		isNew = false;
	}
}
