package com.jwt.auth.commandLinnerRuner;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jwt.auth.embeddeds.Endereco;
import com.jwt.auth.entities.Role;
import com.jwt.auth.entities.Usuario;
import com.jwt.auth.enums.RoleName;
import com.jwt.auth.enums.StatusRegistroEnum;
import com.jwt.auth.repositories.UsuarioRepository;

@Configuration
public class StartupConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
    	
        if (usuarioRepository.findByEmail("admin@admin").isEmpty()) { 
        	
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            
            Endereco endereco = new Endereco();
            endereco.setRua("Rua Exemplo");
            endereco.setNumero("123");
            endereco.setBairro("Centro");
            endereco.setCidade("São Paulo");
            endereco.setEstado("SP");
            endereco.setCep("01000-000");

            List<Role> adminRoles = List.of(Role.builder().name(RoleName.ROLE_ADMINISTRADOR).build());

            Usuario usuario = Usuario.builder()
                .email("admin@admin")
                .password(passwordEncoder.encode("123456"))
                .cpf("123.333.999-00")
                .nome("João da Silva")
                .dataNascimento(LocalDate.parse("26/10/1990", dateFormatter))
                .endereco(endereco)
                .roles(adminRoles)
                .status(StatusRegistroEnum.ATIVO)
                .dataCriacao(LocalDateTime.parse("26/10/2024 15:30:45", dateTimeFormatter))
                .usuarioCriacao("admin")
                .dataAtualizacao(LocalDateTime.parse("26/10/2024 15:30:45", dateTimeFormatter))
                .usuarioAtualizacao("admin")
                .dataExclusao(null)
                .usuarioExclusao(null)
                .build();

            usuarioRepository.save(usuario);
            System.out.println("Usuário criado com sucesso!");
            System.out.println("Usuário admin criado!");
        } else {
            System.out.println("Usuário admin já existe, pulando criação.");
        }
    }
}
