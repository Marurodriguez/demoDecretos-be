package com.sm.admDecretos.Core.Controller;

import com.sm.admDecretos.Core.Entity.Virtual.UsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.admDecretos.Core.Entity.Db.Usuario;
import com.sm.admDecretos.Core.Service.UsuarioService;
import com.sm.admDecretos.Exceptions.CustomException;
import com.sm.admDecretos.Security.CurrentUser;



@RestController
@CrossOrigin
@RequestMapping(path="/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UsuarioService usuarioService;

    /**
     * Login del Sistema
     * @return Usuario
     * @throws Exception
     */
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login() throws  Exception {
        logger.info("Login del sistema");
        // Obtener el usuario
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = currentUser.getUser();
        if(usuario.getStatus() != 0){
            throw new CustomException("El usuario ha sido desactivado");
        }
        UsuarioDTO userDTO = this.usuarioService.getUsuarioDTO(usuario);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }


}
