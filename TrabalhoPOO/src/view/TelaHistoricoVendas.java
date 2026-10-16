package view;

import controller.VendaController;
import model.ItemVenda;
import model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TelaHistoricoVendas extends JFrame{
    private final VendaController controller;

    private final DefaultTableModel modelo =
            new DefaultTableModel(
                    new Object[]{
                            "ID", "Data", "Cliente", "Funcionário",
                            "Total (R$)", "Status"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable tabela = new JTable(modelo);

    private final DateTimeFormatter formatoData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaHistoricoVendas(
            JFrame pai,
            VendaController controller) {

        this.controller = controller;

        setTitle("Histórico de Vendas");
        setSize(900, 500);
        setLocationRelativeTo(pai);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        criarInterface();
        atualizarTabela();
    }

    private void criarInterface() {
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnDetalhes = new JButton("Ver itens da venda");

        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnDetalhes.addActionListener(e -> mostrarDetalhes());

        JPanel botoes = new JPanel();
        botoes.add(btnAtualizar);
        botoes.add(btnDetalhes);

        setLayout(new BorderLayout(10, 10));
        add(botoes, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);

        for (Venda venda : controller.listarTodas()) {
            modelo.addRow(new Object[]{
                    venda.getId(),
                    venda.getData().format(formatoData),
                    venda.getCliente().getNome(),
                    venda.getFuncionario().getNome(),
                    String.format("%.2f", venda.calcularTotal()),
                    venda.getStatus()
            });
        }
    }

    private void mostrarDetalhes() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma venda primeiro."
            );
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);

        Venda venda = controller.buscarPorId(id);

        if (venda == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Venda não encontrada."
            );
            return;
        }

        StringBuilder detalhes = new StringBuilder();

        detalhes.append("Venda: ").append(venda.getId())
                .append("\nCliente: ")
                .append(venda.getCliente().getNome())
                .append("\nStatus: ")
                .append(venda.getStatus())
                .append("\n\nItens:\n");

        for (ItemVenda item : venda.getItens()) {
            detalhes.append("- ")
                    .append(item.getProduto().getNome())
                    .append(" | Quantidade: ")
                    .append(item.getQuantidade())
                    .append(" | Subtotal: R$ ")
                    .append(String.format(
                            "%.2f", item.getSubtotal()
                    ))
                    .append("\n");
        }

        detalhes.append("\nTotal: R$ ")
                .append(String.format(
                        "%.2f", venda.calcularTotal()
                ));

        JTextArea area = new JTextArea(detalhes.toString());
        area.setEditable(false);
        area.setCaretPosition(0);

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(area),
                "Detalhes da venda",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
