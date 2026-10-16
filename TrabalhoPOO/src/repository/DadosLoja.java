package repository;

import model.Cliente;
import model.Funcionario;
import model.Produto;
import model.Venda;

public class DadosLoja {
    private final Repositorio<Produto> produtos =
            new Repositorio<>();

    private final Repositorio<Cliente> clientes =
            new Repositorio<>();

    private final Repositorio<Funcionario> funcionarios =
            new Repositorio<>();

    private final Repositorio<Venda> vendas =
            new Repositorio<>();

    public Repositorio<Produto> getProdutos() {
        return produtos;
    }

    public Repositorio<Cliente> getClientes() {
        return clientes;
    }

    public Repositorio<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public Repositorio<Venda> getVendas() {
        return vendas;
    }
}
