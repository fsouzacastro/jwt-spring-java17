# Instruções para Execução do Projeto

Este documento descreve os passos necessários para executar o projeto utilizando Docker Compose.

## Build e Execução dos Containers

Para criar e executar os containers, utilize o seguinte comando na raiz do projeto:

```bash
docker-compose up -d --build
```

Este comando criará 3 containers:

1. `auth_phpmyadmin_1`: Administrador do banco de dados MySQL
2. `auth_db_1`: Banco de dados MySQL
3. `usuarios-api`: Aplicação principal

## Acesso ao phpMyAdmin

Para acessar o phpMyAdmin, utilize:

- URL: [http://localhost:8000/](http://localhost:8000/)
- Usuário: root
- Senha: usuario@123

![image](https://github.com/user-attachments/assets/b3f5d02c-1419-4a8e-8e67-3046c90fd4b5)

Usuário Administrativo Inicial
A aplicação cria automaticamente um usuário administrador ao iniciar. Use estas credenciais para seu primeiro acesso:

Login: admin@admin
Senha: 123456

Este usuário possui a role "ADMINISTRADOR" e deve ser utilizado para gerar o token inicial, permitindo assim a criação dos primeiros cadastros de usuários no sistema.


## Coleção do Postman

Na raiz do projeto, você encontrará um arquivo de importação com a coleção do Postman contendo todas as requisições necessárias.

## Autenticação

Para utilizar as rotas protegidas da API, é necessário:

1. Realizar login através da rota `/usuario/login`
2. Utilizar o token JWT retornado nas requisições subsequentes

**Importante**: O token deve ser incluído no header das requisições como `Bearer Token`.

## Verificação dos Containers

Para verificar se os containers estão em execução, utilize:

```bash
docker ps
```

## Logs dos Containers

Para visualizar os logs de um container específico:

```bash
docker logs <nome-do-container>
```

Por exemplo:
```bash
docker logs usuarios-api
```
