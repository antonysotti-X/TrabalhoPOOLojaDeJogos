package model;

import interfaces.Identificador;

public class Cliente implements Identificador {
    private final int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente(int id, String nome, String cpf) {
        this(id, nome, cpf, "", "");
    }

    public Cliente(
            int id,
            String nome,
            String cpf,
            String telefone,
            String email) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        this.id = id;
        setNome(nome);
        setCpf(cpf);
        this.telefone = telefone == null ? "" : telefone;
        this.email = email == null ? "" : email;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do cliente é obrigatório."
            );
        }
        this.nome = nome.trim();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException(
                    "O CPF é obrigatório."
            );
        }
        this.cpf = cpf.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone == null ? "" : telefone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? "" : email.trim();
    }

    @Override
    public String toString() {
        return nome + " - CPF: " + cpf;
    }
}
