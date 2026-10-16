package model;

import enums.Genero;
import enums.Plataforma;

public class Jogo extends Produto{
    private Plataforma plataforma;
    private Genero genero;
    private int classificacaoIndicativa;

    public Jogo(
            int id,
            String nome,
            double preco,
            int estoque,
            Plataforma plataforma,
            Genero genero,
            int classificacaoIndicativa) {

        super(id, nome, preco, estoque);
        this.plataforma = plataforma;
        this.genero = genero;
        setClassificacaoIndicativa(classificacaoIndicativa);
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(int idade) {
        if (idade < 0 || idade > 18) {
            throw new IllegalArgumentException(
                    "A classificação deve ficar entre 0 e 18."
            );
        }
        classificacaoIndicativa = idade;
    }

    @Override
    public String getCategoria() {
        return "Jogo";
    }
}
