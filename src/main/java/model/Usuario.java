package model;

public record Usuario(
    String id, 
    String nome, 
    String login, 
    String email,
    String cpf,
    String telefone,
    String password
) {}
