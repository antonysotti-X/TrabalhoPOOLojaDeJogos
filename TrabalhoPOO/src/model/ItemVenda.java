package model;

public class ItemVenda {
    private final Produto produto;
    private final int quantidade;
    private final double precoUnitario;

    public ItemVenda(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException(
                    "Selecione um produto."
            );
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser positiva."
            );
        }

        if (quantidade > produto.getEstoque()) {
            throw new IllegalArgumentException(
                    "Estoque insuficiente."
            );
        }

        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getSubtotal() {
        return precoUnitario * quantidade;
    }
}
