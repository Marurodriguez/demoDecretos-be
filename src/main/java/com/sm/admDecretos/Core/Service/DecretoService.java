package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.Decreto;
import java.util.List;

public interface DecretoService {
    public List<Decreto> findAll();
    public Decreto save(Decreto decreto);
    public Decreto findById(Integer id);
    public void delete(Integer id);
    
}
