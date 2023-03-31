package com.sm.admDecretos.Core.Entity.Db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity(name = "informacion")
public class Informacion extends AbstractEntity {
    @JsonIgnore
    private Long id_documento;
    @JsonIgnore
    private Long id_campo;
    @JsonIgnore
    private Long id_archivo;
    @Column(columnDefinition = "LONGTEXT")
    private String valor;
    @OneToOne(targetEntity = Campo.class)
    @JoinColumn(name = "id_campo", insertable = false, updatable = false)
    private Campo campo;
}
