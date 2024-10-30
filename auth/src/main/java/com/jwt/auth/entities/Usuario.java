package com.jwt.auth.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwt.auth.embeddeds.Endereco;
import com.jwt.auth.enums.StatusRegistroEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Usuario{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull(message = "Email é obrigatório")
	@Column(nullable = false, unique = true)
	private String email;

	@JsonIgnore
	@NotNull(message = "Password é obrigatório")
	private String password;

	@NotNull(message = "CPF é obrigatório")
    @Column(nullable = false, unique = true)
    private String cpf; 

	@NotNull(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;
	
	@NotNull(message = "Data de nascimento é obrigatório")
    @Column(nullable = false)
    private LocalDate dataNascimento;

	@NotNull(message = "Endereço é obrigatório")
    @Embedded
    private Endereco endereco;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    private StatusRegistroEnum status;

    private LocalDateTime dataCriacao;
    private String usuarioCriacao;

    private LocalDateTime dataAtualizacao;
    private String usuarioAtualizacao;

    private LocalDateTime dataExclusao;
    private String usuarioExclusao;
}
