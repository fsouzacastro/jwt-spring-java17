package com.jwt.auth.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EnderecoRequest {
	private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}