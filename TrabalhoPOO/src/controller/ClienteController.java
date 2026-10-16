package controller;

import model.Cliente;
import repository.DadosLoja;

import java.util.List;

public class ClienteController {
    private final DadosLoja dados;

    public ClienteController(DadosLoja dados) {
        this.dados = dados;
    }

    public void cadastrar(Cliente cliente) {
        for (Cliente existente :
                dados.getClientes().listarTodos()) {

            if (existente.getCpf().equalsIgnoreCase(
                    cliente.getCpf())) {

                throw new IllegalArgumentException(
                        "Já existe um cliente com esse CPF."
                );
            }
        }

        dados.getClientes().adicionar(cliente);
    }

    public List<Cliente> listarTodos() {
        return dados.getClientes().listarTodos();
    }

    public Cliente buscarPorId(int id) {
        return dados.getClientes()
                .buscarPorId(id)
                .orElse(null);
    }

    public void remover(int id) {
        if (!dados.getClientes().remover(id)) {
            throw new IllegalArgumentException(
                    "Cliente não encontrado."
            );
        }
    }
}
