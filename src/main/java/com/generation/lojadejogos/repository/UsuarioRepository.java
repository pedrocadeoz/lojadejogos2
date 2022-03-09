package com.generation.lojadejogos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.lojadejogos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByUsuario(String usuario);
	
	public Optional<Usuario> findByIdAndUsuario(Long id, String usuario);
}
