package au.com.rainmore.centus.domains;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

@MappedSuperclass
public abstract class CreateableModel implements Model {

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (Optional.ofNullable(getCreatedAt()).isEmpty()) {
            setCreatedAt(LocalDateTime.now());
        }
    }

    @Column(nullable = false, updatable = false)
    @NotNull
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
