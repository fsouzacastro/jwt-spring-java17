package com.jwt.auth.controllers;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.auth.embeddeds.Endereco;
import com.jwt.auth.entities.Role;
import com.jwt.auth.entities.Usuario;
import com.jwt.auth.enums.RoleName;
import com.jwt.auth.enums.StatusRegistroEnum;
import com.jwt.auth.request.UsuarioJwtRequest;
import com.jwt.auth.request.UsuarioRequest;
import com.jwt.auth.response.UsuarioJwtResponse;
import com.jwt.auth.response.UsuarioResponse;
import com.jwt.auth.services.Usuarioservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "usuario")
@RequiredArgsConstructor
public class UsuarioController {
	

	@Autowired
	private Usuarioservice usuarioService;
	
	
	@GetMapping("/all")
    public ResponseEntity<List<UsuarioResponse>> findAll() {
		
        List<Usuario> listaUsuario = usuarioService.findAll();

        List<UsuarioResponse> listaUsuarioResponse = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        listaUsuario.forEach(usuario -> {
        	
        	List<RoleName> roleNames = usuario.getRoles().stream()
        	    .map(Role::getName)
        	    .collect(Collectors.toList());
        	
            UsuarioResponse usuarioResponse = mapper.map(usuario, UsuarioResponse.class);
            usuarioResponse.setRoles(roleNames);
            listaUsuarioResponse.add(usuarioResponse);
        });

        return ResponseEntity.ok(listaUsuarioResponse);
    }
	
	@GetMapping("/unico/{id}")
    public ResponseEntity<?> findId(@PathVariable int id) {
		
		Optional<Usuario> usuarioOptional = usuarioService.findById(id);
		if (!usuarioOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
	    }
		
		Usuario usuario = usuarioOptional.get();
		UsuarioResponse usuarioResponse = new UsuarioResponse();
	    BeanUtils.copyProperties(usuario, usuarioResponse);
	    usuarioResponse.setRoles(usuario.getRoles().stream()
        	    .map(Role::getName)
        	    .collect(Collectors.toList()));
	    return ResponseEntity.status(HttpStatus.OK).body(usuarioResponse);
    }
	
	@PostMapping("/create")
    public ResponseEntity<UsuarioResponse> save(@RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioRequest, usuario);
        usuario.setRoles(List.of(Role.builder().name(usuarioRequest.getRole()).build()));
        
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(usuarioRequest.getEndereco(), endereco);
        usuario.setEndereco(endereco);
        usuario.setDataAtualizacao(LocalDateTime.now());
        
        usuarioService.save(usuario);

        UsuarioResponse usuarioResponse = new UsuarioResponse();
        BeanUtils.copyProperties(usuario, usuarioResponse);
        usuarioResponse.setRoles(usuario.getRoles().stream()
        	    .map(Role::getName)
        	    .collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody UsuarioRequest usuarioRequest) {
	    Optional<Usuario> usuarioOptional = usuarioService.findById(id);
	    if (!usuarioOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
	    }

	    Usuario usuario = usuarioOptional.get();	    
	    BeanUtils.copyProperties(usuarioRequest, usuario);
	    
	    Endereco endereco = usuario.getEndereco();
	    if (endereco == null) {
	        endereco = new Endereco();
	    }
	    BeanUtils.copyProperties(usuarioRequest.getEndereco(), endereco);
	    usuario.setEndereco(endereco);
	    usuario.setDataAtualizacao(LocalDateTime.now());
	    
	    usuarioService.save(usuario);
	    
	    UsuarioResponse usuarioResponse = new UsuarioResponse();
	    BeanUtils.copyProperties(usuario, usuarioResponse);
	    usuarioResponse.setRoles(usuario.getRoles().stream()
        	    .map(Role::getName)
        	    .collect(Collectors.toList()));
	    
	    return ResponseEntity.status(HttpStatus.OK).body(usuarioResponse);
	}
	
	@PutMapping("/exclusao/{id}")
	public ResponseEntity<?> exclusaoLogica(@PathVariable int id) {
	    Optional<Usuario> usuarioOptional = usuarioService.findById(id);
	    if (!usuarioOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
	    }

	    Usuario usuario = usuarioOptional.get();
	    usuario.setDataExclusao(LocalDateTime.now());
	    usuario.setDataAtualizacao(LocalDateTime.now());
	    usuario.setStatus(StatusRegistroEnum.EXCLUIDO);
	    
	    usuarioService.save(usuario);
	    
	    UsuarioResponse usuarioResponse = new UsuarioResponse();
	    BeanUtils.copyProperties(usuario, usuarioResponse);
	    usuarioResponse.setRoles(usuario.getRoles().stream()
        	    .map(Role::getName)
        	    .collect(Collectors.toList()));
	    
	    return ResponseEntity.status(HttpStatus.OK).body(usuarioResponse);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		Optional<Usuario> usuarioOptional = usuarioService.findById(id);
	    
	    if (!usuarioOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
	    }
	    
	    Usuario usuario = usuarioOptional.get();
	    UsuarioResponse usuarioResponse = new UsuarioResponse();
	    BeanUtils.copyProperties(usuario, usuarioResponse);
	    usuarioResponse.setRoles(usuario.getRoles().stream()
        	    .map(Role::getName)
        	    .collect(Collectors.toList()));
	    
	    usuarioService.delete(usuario);
	    
	    return ResponseEntity.ok(null);
	}
	
	@PostMapping("/login")
    public ResponseEntity<UsuarioJwtResponse> authenticateUser(@RequestBody UsuarioJwtRequest usuarioJwtRequest) throws AuthenticationException {
		UsuarioJwtResponse token = usuarioService.authenticateUser(usuarioJwtRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
