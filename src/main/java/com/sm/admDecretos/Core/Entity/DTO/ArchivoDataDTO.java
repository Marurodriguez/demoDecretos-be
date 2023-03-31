package com.sm.admDecretos.Core.Entity.DTO;

import com.sm.admDecretos.Core.Entity.Db.Archivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoDataDTO {
    private String uuid = ""; // Si esta en cero entonces crea
    private String data = "";

    private long fileSize = 0;

}
