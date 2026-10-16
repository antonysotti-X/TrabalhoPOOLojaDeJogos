package repository;

import interfaces.Identificador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repositorio<T extends Identificador> {

    private final List<T> dados = new ArrayList<>();

    public void adicionar(T objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException(
                    "O objeto não pode ser nulo."
            );
        }

        if (buscarPorId(objeto.getId()).isPresent()) {
            throw new IllegalArgumentException(
                    "Já existe um registro com esse ID."
            );
        }

        dados.add(objeto);
    }

    public List<T> listarTodos() {
        return new ArrayList<>(dados);
    }

    public Optional<T> buscarPorId(int id) {
        return dados.stream()
                .filter(objeto -> objeto.getId() == id)
                .findFirst();
    }

    public boolean remover(int id) {
        return dados.removeIf(
                objeto -> objeto.getId() == id
        );
    }

    public int quantidade() {
        return dados.size();
    }

    public boolean estaVazio() {
        return dados.isEmpty();
    }
}
