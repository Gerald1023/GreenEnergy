package com.example.Usuario.service;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Usuario.model.Usuario;
import com.example.Usuario.repository.UsuarioRepository;


@Service
@Transactional

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolService rolService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolService rolService) {
        this.usuarioRepository = usuarioRepository;
        this.rolService = rolService;
    }


    
    
    //metodo para buscar todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    //metodo para buscar un usuario por id
    public Usuario buscarUsuarioId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    //metodo para crear un usuario
    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getRol() == null || usuario.getRol().getIdrol() == null) {
            throw new RuntimeException("El campo 'rol.id' no puede ser nulo");
        }
        return usuarioRepository.save(usuario);
    }

    //metodo para actualizar un usuario
    public Usuario actualizarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getIdusuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioExistente.setRut(usuario.getRut());
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setContrasena(usuario.getContrasena());
        usuarioExistente.setRol(rolService.BuscarRolId(usuario.getRol().getIdrol()));
        return usuarioRepository.save(usuarioExistente);
    }

    //metodo para eliminar un usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    
    
}
