package controller;

import model.Produto;
import repository.DadosLoja;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ProdutoController {
    private final DadosLoja dados;

    public ProdutoController(DadosLoja dados) {
        this.dados = dados;
    }

    public void cadastrar(Produto produto) {
        dados.getProdutos().adicionar(produto);
    }

    public List<Produto> listarTodos() {
        return dados.getProdutos().listarTodos();
    }

    public Produto buscarPorId(int id) {
        return dados.getProdutos()
                .buscarPorId(id)
                .orElse(null);
    }

    public List<Produto> filtrar(
            Predicate<Produto> criterio) {

        return listarTodos().stream()
                .filter(criterio)
                .toList();
    }

    public List<Produto> ordenar(
            Comparator<Produto> comparador) {

        return listarTodos().stream()
                .sorted(comparador)
                .toList();
    }

    public void remover(int id) {
        boolean removido =
                dados.getProdutos().remover(id);

        if (!removido) {
            throw new IllegalArgumentException(
                    "Produto não encontrado."
            );
        }
    }

    public List<Produto> filtrarPorNome(String nome) {
        return filtrar(p ->
                p.getNome().toLowerCase()
                        .contains(nome.toLowerCase())
        );
    }

    public List<Produto> filtrarEstoqueBaixo(int limite) {
        return filtrar(p -> p.getEstoque() <= limite);
    }

    public List<Produto> filtrarPorPreco(
            double minimo, double maximo) {

        if (minimo < 0 || maximo < minimo) {
            throw new IllegalArgumentException(
                    "Faixa de preços inválida."
            );
        }

        return filtrar(p ->
                p.getPreco() >= minimo &&
                        p.getPreco() <= maximo
        );
    }


    public List<Produto> filtrarPorPreco(double maximo) {
        if (!Double.isFinite(maximo) || maximo < 0) {
            throw new IllegalArgumentException(
                    "O preço máximo deve ser válido e não negativo."
            );
        }

        return filtrarPorPreco(0, maximo);
    }


    public List<Produto> filtrarPorCategoria(
            String categoria) {

        return filtrar(p ->
                p.getCategoria().equalsIgnoreCase(categoria)
        );
    }

    public List<Produto> filtrarCombinado(
            double precoMaximo, int estoqueMinimo) {

        Predicate<Produto> precoAcessivel =
                p -> p.getPreco() <= precoMaximo;

        Predicate<Produto> estoqueDisponivel =
                p -> p.getEstoque() >= estoqueMinimo;

        Predicate<Produto> criterios =
                precoAcessivel.and(estoqueDisponivel);

        return filtrar(criterios);
    }

    public List<Produto> ordenarPorNome() {
        return ordenar(
                Comparator.comparing(Produto::getNome)
        );
    }

    public List<Produto> ordenarPorPreco() {
        return ordenar(
                Comparator.comparingDouble(Produto::getPreco)
        );
    }

    public List<String> obterDescricoes() {
        Function<Produto, String> descricao =
                p -> p.getNome() + " - R$ " + p.getPreco();

        return listarTodos().stream()
                .map(descricao)
                .toList();
    }


    public List<String> obterNomes() {
        Function<Produto, String> extrairNome =
                produto -> produto.getNome();

        return dados.getProdutos()
                .listarTodos()
                .stream()
                .map(extrairNome)
                .toList();
    }

}
