package com.project.mamoryJar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mamoryJar.model.entity.Jar;

public interface JarRepository extends JpaRepository<Jar, Long>{
    // traer todos los jars de un usuario
    List<Jar> findByUsuarioId(Long UsuarioId);

    // Buscar un jar especifico que pertenezca a un usuario
    Optional<Jar> findByIdAndUsuarioId(Long jarId, Long usuarioId);
}
