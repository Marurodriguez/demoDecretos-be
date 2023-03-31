package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Repository.DecretoRepository;
import com.sm.admDecretos.Core.Entity.Db.Decreto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DecretoServiceImplement  implements DecretoService{
    @Autowired
    private DecretoRepository decretoRepository;
    
    @Override
    @Transactional(readOnly=true)
    public List<Decreto> findAll()
    {
        return (List<Decreto>) decretoRepository.findAll();
    }
    
    @Override
     @Transactional(readOnly=false)
    public Decreto save(Decreto decreto)
    {
        return decretoRepository.save(decreto);
    }
    
    @Override
     @Transactional(readOnly=true)
    public Decreto findById(Integer id)
    {
        return decretoRepository.findById(id).orElse(null);
    }
    
    @Override
     @Transactional(readOnly=false)
    public void delete(Integer id)
    {
        decretoRepository.deleteById(id);
    }
    
}
