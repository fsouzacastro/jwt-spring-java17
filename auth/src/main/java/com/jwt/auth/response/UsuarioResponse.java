package com.jwt.auth.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwt.auth.embeddeds.Endereco;
import com.jwt.auth.enums.RoleName;
import com.jwt.auth.enums.StatusRegistroEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
	private String email;
    private String cpf; 
    private String nome;
    private LocalDate dataNascimento;
    
    @Embedded
    private Endereco endereco;
    
    private List<RoleName> roles;

    @Enumerated(EnumType.STRING)
    private StatusRegistroEnum status;

    private LocalDateTime dataCriacao;
    private String usuarioCriacao;
    private LocalDateTime dataAtualizacao;
    private String usuarioAtualizacao;
    private LocalDateTime dataExclusao;
    private String usuarioExclusao;
}
