package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.AbstractEntity;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityRepository<T extends AbstractEntity> extends CrudRepository<T, Long>, JpaSpecificationExecutor<T> {
    //T findByUuidEqualsAndStatusEquals(String uuid, int status);
   // Page<T> paginate(PaginateList paginateList);
}

