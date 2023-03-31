package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.AbstractEntity;
import com.sm.admDecretos.Core.Entity.Db.Usuario;
import com.sm.admDecretos.Core.Entity.Virtual.UsuarioDTO;
import com.sm.admDecretos.Core.Repository.UserRepository;
import com.sm.admDecretos.Exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Permite asociar la persona con el usuario.
 */
@Service
public class UsuarioService extends AbstractService<AbstractEntity> {
    @Autowired
    UserRepository usuarioRepository;

    /**
     * Devuelve el Usuario según el UUID.
     *
     * @param uuid
     * @return Usuario
     * @throws Exception
     */
    public Usuario getUsuarioByUuid(String uuid) throws Exception {
        try {
            Usuario usuario = this.usuarioRepository.findFirstByUuidEqualsAndStatusEquals(uuid, 0);
            if (usuario == null) {
                throw new CustomException("No fue encontrado el usuario con uuid: " + uuid);
            }

            return usuario;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public UsuarioDTO getUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario(usuario);
        return usuarioDTO;
    }
}


