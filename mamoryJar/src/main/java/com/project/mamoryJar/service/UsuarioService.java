package com.project.mamoryJar.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mamoryJar.dto.request.LoginRequestDTO;
import com.project.mamoryJar.dto.request.UsuarioRequestDTO;
import com.project.mamoryJar.dto.response.UsuarioResponseDTO;
import com.project.mamoryJar.dto.update.UsuarioUpdateDTO;
import com.project.mamoryJar.mapper.UsuarioMapper;
import com.project.mamoryJar.model.entity.Usuario;
import com.project.mamoryJar.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    // para escrituras & cambios, NO para lecturas simples
    @Transactional 
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto){
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("El correo ya se encuentra registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setRol(dto.getRol());

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setContraseña(dto.getContraseña());

        usuarioRepository.save(usuario);
        return usuarioMapper.mapToResponse(usuario);
    }


    public UsuarioResponseDTO buscarUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuarioMapper.mapToResponse(usuario);
    }


    @Transactional
    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll(); // devuelve una lista (vacía si no hay registros, nunca null).
        List<UsuarioResponseDTO> response = new ArrayList<>();

        for(Usuario usuario : usuarios){
            response.add(usuarioMapper.mapToResponse(usuario));
        }

        return response;
    }

    public void eliminarUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("El usuario ha sido encontrado"));
    
        usuarioRepository.delete(usuario);
    }

    @Transactional
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO dto){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("El usuario no fue encontrado"));

        if(dto.getNombre() != null){
        usuario.setNombre(dto.getNombre());
        }

        if(dto.getAvatar() != null){
            usuario.setAvatar(dto.getAvatar());
        }

        if(dto.getDescripcion() != null){
            usuario.setDescripcion(dto.getDescripcion());
        } 

        usuarioRepository.save(usuario);
        return usuarioMapper.mapToResponse(usuario);
    }
    

    public UsuarioResponseDTO login(LoginRequestDTO dto){
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new IllegalStateException("credenciales inválidas"));

        if (!usuario.getContraseña().equals(dto.getContraseña())) {
            throw new IllegalStateException("credenciales inválidas");
        }

        usuario.setLastLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return usuarioMapper.mapToResponse(usuario);
    }
}
