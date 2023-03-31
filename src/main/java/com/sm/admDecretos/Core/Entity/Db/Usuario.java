package com.sm.admDecretos.Core.Entity.Db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@Entity(name = "usuario")
public class Usuario extends AbstractEntity {
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String telefono;
    private int admin;
    private String nombre;
    private String codigoInterno;
    private String documento;
    private String genero;
}
