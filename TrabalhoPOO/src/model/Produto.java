package model;

import interfaces.Identificador;

public abstract class Produto implements Identificador {
    private final int id;
    private String nome;
    private double preco;
    private int estoque;

    public Produto(int id, String nome, double preco, int estoque) {
        if (id <= 0) {
            throw new IllegalArgumentException("Código inválido.");
        }

        this.id = id;
        setNome(nome);
        setPreco(preco);
        setEstoque(estoque);
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
                    "O nome é obrigatório."
            );
        }
        this.nome = nome.trim();
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (!Double.isFinite(preco) || preco <= 0) {
            throw new IllegalArgumentException(
                    "O preço deve ser maior que zero."
            );
        }
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        if (estoque < 0) {
            throw new IllegalArgumentException(
                    "O estoque não pode ser negativo."
            );
        }
        this.estoque = estoque;
    }

    public void retirarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser positiva."
            );
        }

        if (quantidade > estoque) {
            throw new IllegalArgumentException(
                    "Estoque insuficiente."
            );
        }

        estoque -= quantidade;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser positiva."
            );
        }

        estoque += quantidade;
    }

    public double calcularValorEstoque() {
        return preco * estoque;
    }

    public double calcularValorEstoque(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A quantidade não pode ser negativa."
            );
        }

        return preco * quantidade;
    }

    public abstract String getCategoria();

    @Override
    public String toString() {
        return id + " - " + nome + " (" + getCategoria() + ")";
    }
}
