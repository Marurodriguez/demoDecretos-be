package com.sm.admDecretos.Core.Repository.Impl;


import org.springframework.data.domain.Page;

import com.sm.admDecretos.Core.Entity.Db.Usuario;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Repository.RepositoryImpl;
import com.sm.admDecretos.Core.Repository.Custom.UserRepositoryCustom;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class UserRepositoryImpl extends RepositoryImpl<Usuario> implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;


    @PostConstruct
    void setEntity(){
        super.setEntityManager(entityManager);
    }

    @Override
    @Transactional
    public Page<Usuario> paginate(PaginateList paginateList) {
        super.init();
        super.setTableName("USUARIO");
        String sql = " t.status = 0 and t.admin = 0 ";

        super.setWhereConditions(sql);

        Page<Usuario> page = super.createSql(Usuario.class, paginateList.getPage(), paginateList.getSize());
        return page;
    }
}
