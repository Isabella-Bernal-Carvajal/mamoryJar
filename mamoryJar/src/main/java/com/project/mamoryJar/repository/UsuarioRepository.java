package com.project.mamoryJar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mamoryJar.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    // métodos para buscar nombre del usuario y su email

    // findByEmail → login futuro
    // existsByEmail → evitar duplicados
    // Optional → buena práctica, evita NullPointerException
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombre(String nombre);

    Boolean existsByEmail(String email);
    Boolean existsByNombre(String nombre);
}
