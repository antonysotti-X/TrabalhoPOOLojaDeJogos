
package controller;

import enums.StatusVenda;
import model.*;
import repository.DadosLoja;

import java.util.ArrayList;
import java.util.List;

public class VendaController {

    private final DadosLoja dados;

    public VendaController(DadosLoja dados) {
        if (dados == null) {
            throw new IllegalArgumentException(
                    "Os dados da loja não podem ser nulos."
            );
        }

        this.dados = dados;
    }

    public Venda realizarVenda(
            int id,
            Cliente cliente,
            Funcionario funcionario,
            List<ItemVenda> itens) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da venda deve ser positivo."
            );
        }

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "A venda precisa ter um cliente."
            );
        }

        if (funcionario == null) {
            throw new IllegalArgumentException(
                    "A venda precisa ter um funcionário."
            );
        }

        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException(
                    "A venda precisa ter pelo menos um item."
            );
        }

        if (dados.getVendas().buscarPorId(id).isPresent()) {
            throw new IllegalArgumentException(
                    "Já existe uma venda com esse ID."
            );
        }

        if (dados.getClientes()
                .buscarPorId(cliente.getId())
                .orElse(null) != cliente) {
            throw new IllegalArgumentException(
                    "O cliente não está cadastrado nesta loja."
            );
        }

        if (dados.getFuncionarios()
                .buscarPorId(funcionario.getId())
                .orElse(null) != funcionario) {
            throw new IllegalArgumentException(
                    "O funcionário não está cadastrado nesta loja."
            );
        }

        for (ItemVenda item : itens) {
            if (item == null) {
                throw new IllegalArgumentException(
                        "A venda contém um item inválido."
                );
            }

            Produto produto = item.getProduto();

            if (produto == null) {
                throw new IllegalArgumentException(
                        "Um item da venda não possui produto."
                );
            }

            if (dados.getProdutos()
                    .buscarPorId(produto.getId())
                    .orElse(null) != produto) {
                throw new IllegalArgumentException(
                        "O produto não está cadastrado nesta loja."
                );
            }
        }

        for (Produto produto : dados.getProdutos().listarTodos()) {
            long quantidadeNecessaria = itens.stream()
                    .filter(item -> item.getProduto() == produto)
                    .mapToLong(ItemVenda::getQuantidade)
                    .sum();

            if (quantidadeNecessaria > produto.getEstoque()) {
                throw new IllegalArgumentException(
                        "Estoque insuficiente para: "
                                + produto.getNome()
                );
            }
        }

        Venda venda = new Venda(id, cliente, funcionario);

        for (ItemVenda item : itens) {
            venda.adicionarItem(item);
        }

        List<ItemVenda> itensBaixados = new ArrayList<>();
        boolean vendaRegistrada = false;

        try {
            dados.getVendas().adicionar(venda);
            vendaRegistrada = true;

            for (ItemVenda item : itens) {
                item.getProduto().retirarEstoque(
                        item.getQuantidade()
                );

                itensBaixados.add(item);
            }

            return venda;

        } catch (RuntimeException ex) {

            for (ItemVenda item : itensBaixados) {
                item.getProduto().adicionarEstoque(
                        item.getQuantidade()
                );
            }

            if (vendaRegistrada) {
                dados.getVendas().remover(id);
            }

            throw ex;
        }
    }

    public void devolverVenda(int idVenda) {
        Venda venda = dados.getVendas()
                .buscarPorId(idVenda)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Venda não encontrada."
                        )
                );

        if (venda.getStatus() == StatusVenda.DEVOLVIDA) {
            throw new IllegalStateException(
                    "Esta venda já foi devolvida."
            );
        }

        for (ItemVenda item : venda.getItens()) {
            item.getProduto().adicionarEstoque(
                    item.getQuantidade()
            );
        }

        venda.marcarComoDevolvida();
    }

    public List<Venda> listarTodas() {
        return dados.getVendas().listarTodos();
    }

    public Venda buscarPorId(int id) {
        return dados.getVendas()
                .buscarPorId(id)
                .orElse(null);
    }
}
