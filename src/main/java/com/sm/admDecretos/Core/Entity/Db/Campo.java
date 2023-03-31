package com.sm.admDecretos.Core.Entity.Db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "campo")
public class Campo extends AbstractEntity {
    @JsonIgnore
    private Long id_categoria;
    @JsonIgnore
    private Long id_campo_tipo;
    private String codigo;
    private String nombre;
    @NotNull
    @ColumnDefault("true")
    private Boolean buscable;
    @NotNull
    @ColumnDefault("true")
    private Boolean visible_tabla;

    @OneToOne(targetEntity = CampoTipo.class)
    @JoinColumn(name = "id_campo_tipo", insertable = false, updatable = false)
    private CampoTipo campo_tipo;
}
