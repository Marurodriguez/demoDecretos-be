package com.sm.admDecretos.Core.Repository.Custom;

import org.springframework.data.domain.Page;

import com.sm.admDecretos.Core.Entity.Db.Usuario;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;

public interface UserRepositoryCustom {
    Page<Usuario> paginate(PaginateList paginateList);
}

