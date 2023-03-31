package com.sm.admDecretos.Core.Repository;


import com.sm.admDecretos.Core.Entity.Db.Usuario;
import com.sm.admDecretos.Core.Repository.Custom.UserRepositoryCustom;


public interface UserRepository extends EntityRepository<Usuario>, UserRepositoryCustom {
    Usuario findFirstByUsernameAndStatus(String username,int status);
    Usuario findFirstByUuidEqualsAndStatusEquals(String uuid, int status);
    Usuario findFirstByUsernameAndStatusAndIdIsNot(String username,int status, Long id);
}

