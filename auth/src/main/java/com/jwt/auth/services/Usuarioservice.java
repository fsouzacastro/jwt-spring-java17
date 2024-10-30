package com.jwt.auth.services;

import java.util.List;
import java.util.Optional;

import javax.security.sasl.AuthenticationException;

import com.jwt.auth.entities.Usuario;
import com.jwt.auth.request.UsuarioJwtRequest;
import com.jwt.auth.response.UsuarioJwtResponse;

public interface Usuarioservice {
	
	Optional<Usuario> findByEmail(String email);
	
	Optional<Usuario> findByCpf(String email);

    Usuario save(Usuario usuario);
    
    List<Usuario> findAll();

	Optional<Usuario> findById(int id);
	
	void delete(Usuario usuario);
	
	public UsuarioJwtResponse authenticateUser(UsuarioJwtRequest usuarioJwtRequest) throws AuthenticationException;
}
