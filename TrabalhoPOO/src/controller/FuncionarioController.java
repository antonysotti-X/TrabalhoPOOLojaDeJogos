package controller;

import model.Funcionario;
import repository.DadosLoja;

import java.util.List;

public class FuncionarioController {
    private final DadosLoja dados;

    public FuncionarioController(DadosLoja dados) {
        this.dados = dados;
    }

    public void cadastrar(Funcionario funcionario) {
        dados.getFuncionarios().adicionar(funcionario);
    }

    public List<Funcionario> listarTodos() {
        return dados.getFuncionarios().listarTodos();
    }

    public Funcionario buscarPorId(int id) {
        return dados.getFuncionarios()
                .buscarPorId(id)
                .orElse(null);
    }

    public void remover(int id) {
        if (!dados.getFuncionarios().remover(id)) {
            throw new IllegalArgumentException(
                    "Funcionário não encontrado."
            );
        }
    }
}
