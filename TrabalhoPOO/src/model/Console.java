package model;

public class Console extends Produto {
    private String fabricante;
    private String modelo;
    private int armazenamentoGb;

    public Console(
            int id,
            String nome,
            double preco,
            int estoque,
            String fabricante,
            String modelo,
            int armazenamentoGb) {

        super(id, nome, preco, estoque);

        if (fabricante == null || fabricante.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe o fabricante."
            );
        }

        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe o modelo."
            );
        }

        if (armazenamentoGb < 0) {
            throw new IllegalArgumentException(
                    "Armazenamento inválido."
            );
        }

        this.fabricante = fabricante.trim();
        this.modelo = modelo.trim();
        this.armazenamentoGb = armazenamentoGb;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public int getArmazenamentoGb() {
        return armazenamentoGb;
    }

    @Override
    public String getCategoria() {
        return "Console";
    }
}
