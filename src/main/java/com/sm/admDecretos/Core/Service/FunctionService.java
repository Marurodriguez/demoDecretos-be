package com.sm.admDecretos.Core.Service;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Funciones adicionales
 */
@Service
public class FunctionService {

    /**
     * Obtiene la fecha de un timestamp
     * @param fecha TIMESTAMP
     * @param formato formato predefinido = "dd/MM/yyyy"
     * @return
     */
    public String getStringFromTimestamp(Timestamp fecha, String formato) {
        if (formato == ""){
            formato = "dd/MM/yyyy";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);

        Date date = new Date(fecha.getTime());
        String strResult = dateFormat.format(date);
        return strResult;
    }


   public Timestamp getTimeStampFromString(String fecha) throws Exception{
       String[] result = fecha.split("-");
       int year = 0,mes=0,dia = 0;
       if(result.length != 3){
           throw new Exception("Fecha invalida:" + fecha);
       }
       if(result[0].length() > 2){ //'yyyy-mm-dd'
           year = Integer.parseInt(result[0]);
           dia = Integer.parseInt(result[2]);
       }else{ //'dd-mm-yyyy'
           dia = Integer.parseInt(result[0]);
           year = Integer.parseInt(result[2]);
       }
       mes = Integer.parseInt(result[1]);
       Calendar calendar = Calendar.getInstance();
       calendar.set(year,mes-1,dia);
       java.sql.Timestamp  tFecha = new Timestamp(calendar.getTimeInMillis());
       return tFecha;
   }
}


