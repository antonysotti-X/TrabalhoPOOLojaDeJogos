package model;

public class Acessorio extends Produto{
    private String tipo;
    private String compatibilidade;

    public Acessorio(
            int id,
            String nome,
            double preco,
            int estoque,
            String tipo,
            String compatibilidade) {

        super(id, nome, preco, estoque);

        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe o tipo do acessório."
            );
        }

        if (compatibilidade == null || compatibilidade.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe a compatibilidade."
            );
        }

        this.tipo = tipo.trim();
        this.compatibilidade = compatibilidade.trim();
    }

    public String getTipo() {
        return tipo;
    }

    public String getCompatibilidade() {
        return compatibilidade;
    }

    @Override
    public String getCategoria() {
        return "Acessório";
    }
}
