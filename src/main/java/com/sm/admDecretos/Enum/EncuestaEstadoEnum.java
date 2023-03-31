package com.sm.admDecretos.Enum;

public enum EncuestaEstadoEnum {
    EnEdicion(0),   //El creador esta editando la encuesta
    Publicada(1),   //Las dependencias pueden ver la encuesta
    Finalizada(2),  //La dependencia guardo la Encuesta
    Confirmada(3);  //El auditor confirmo que la encuesta está correcta.
    private int valor;
    EncuestaEstadoEnum(int valor){
        this.valor = valor;
    }
    public int getOrdinal(){
        return valor;
    }

}
