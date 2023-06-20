package io.blueharvest.technicalassignment.domain.user.entity;

import io.blueharvest.technicalassignment.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_session")
public class UserSessionEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private UUID userId;

    private LocalDateTime sessionStartTime;

    private LocalDateTime sessionEndTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserSessionEntity that = (UserSessionEntity) o;
        return getIdentifier() != null && Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
