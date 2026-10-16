package model;

import enums.Cargo;
import interfaces.Identificador;

public class Funcionario implements Identificador {
    private final int id;
    private String nome;
    private Cargo cargo;

    public Funcionario(int id, String nome, Cargo cargo) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome é obrigatório."
            );
        }

        if (cargo == null) {
            throw new IllegalArgumentException(
                    "Selecione um cargo."
            );
        }

        this.id = id;
        this.nome = nome.trim();
        this.cargo = cargo;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        if (cargo == null) {
            throw new IllegalArgumentException(
                    "Selecione um cargo."
            );
        }
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return nome + " - " + cargo;
    }
}
