package com.sm.admDecretos.Core.Entity.Db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;


@Getter
@Setter
@MappedSuperclass
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @JsonIgnore
    @ColumnDefault("0")
    protected int status;

    @JsonIgnore
    @Transient
    @Column(name = "date_created")
    protected Timestamp dateCreated;


    @NotNull
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false, columnDefinition = "CHAR(36)", unique = true)
    protected String uuid;

    @PrePersist
    public void prePersist() {
        //CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Usuario usuario = currentUser.getUser();

        if (this.uuid == null || this.uuid.equalsIgnoreCase("")) {
            UUID uuid = UUID.randomUUID();
            this.uuid = uuid.toString();

            // Crea el registro
            this.dateCreated = new Timestamp(System.currentTimeMillis());
        }
    }

}
