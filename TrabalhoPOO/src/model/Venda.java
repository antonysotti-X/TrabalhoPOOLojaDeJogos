package model;
import enums.StatusVenda;
import interfaces.Identificador;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Venda implements Identificador {
    private final int id;
    private final Cliente cliente;
    private final Funcionario funcionario;
    private final LocalDateTime data;
    private final List<ItemVenda> itens;
    private StatusVenda status;

    public Venda(
            int id,
            Cliente cliente,
            Funcionario funcionario) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        if (cliente == null || funcionario == null) {
            throw new IllegalArgumentException(
                    "A venda exige cliente e funcionário."
            );
        }

        this.id = id;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.data = LocalDateTime.now();
        this.itens = new ArrayList<>();
        this.status = StatusVenda.CONCLUIDA;
    }

    @Override
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public LocalDateTime getData() {
        return data;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public List<ItemVenda> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void adicionarItem(ItemVenda item) {
        if (status != StatusVenda.CONCLUIDA) {
            throw new IllegalStateException(
                    "Não é possível alterar uma venda devolvida."
            );
        }

        if (item == null) {
            throw new IllegalArgumentException(
                    "O item não pode ser nulo."
            );
        }

        itens.add(item);
    }

    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(ItemVenda::getSubtotal)
                .sum();
    }

    public void marcarComoDevolvida() {
        if (status == StatusVenda.DEVOLVIDA) {
            throw new IllegalStateException(
                    "Esta venda já foi devolvida."
            );
        }

        status = StatusVenda.DEVOLVIDA;
    }
}
