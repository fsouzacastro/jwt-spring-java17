package com.jwt.auth.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jwt.auth.entities.Usuario;
import com.jwt.auth.repositories.UsuarioRepository;
import com.jwt.auth.request.UsuarioJwtRequest;
import com.jwt.auth.response.UsuarioJwtResponse;
import com.jwt.auth.security.authentication.JwtTokenService;
import com.jwt.auth.security.config.SecurityConfiguration;
import com.jwt.auth.security.userdetails.UserDetailsImpl;
import com.jwt.auth.services.Usuarioservice;

@Service
public class UsuarioServiceImpl implements Usuarioservice {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private SecurityConfiguration securityConfiguration;

	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private JwtTokenService jwtTokenService;
	
	@Override
	public Optional<Usuario> findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public Optional<Usuario> findByCpf(String email) {
		return usuarioRepository.findByCpf(email);
	}

	@Override
	public Usuario save(Usuario usuario) {
		usuario.setPassword(securityConfiguration.passwordEncoder().encode(usuario.getPassword()));
		return usuarioRepository.save(usuario);
	}
	
	@Override
	public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
	
	public Optional<Usuario> findById(int id) {
        return usuarioRepository.findById(id);
    }
	
	public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }
	
	public UsuarioJwtResponse authenticateUser(UsuarioJwtRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(), 
                    request.getPassword()
                )
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtTokenService.generateToken(userDetails);

            return new UsuarioJwtResponse(token);
            
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciais inválidas", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro na autenticação: " + e.getMessage(), e);
        }
    }

}
