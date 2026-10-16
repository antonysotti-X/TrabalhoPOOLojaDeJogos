package view;

import controller.ProdutoController;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TelaEstoque extends JFrame {
    private final ProdutoController controller;

    private final JTextField txtBusca = new JTextField();

    private final JComboBox<String> cmbCategoria =
            new JComboBox<>(new String[]{
                    "Todas", "Jogo", "Console", "Acessório"
            });

    private final DefaultTableModel modelo =
            new DefaultTableModel(
                    new Object[]{
                            "ID", "Nome", "Categoria", "Preço", "Estoque",
                            "Valor em estoque"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    public TelaEstoque(
            JFrame pai,
            ProdutoController controller) {

        this.controller = controller;

        setTitle("Consulta de Estoque");
        setSize(850, 500);
        setLocationRelativeTo(pai);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        criarInterface();
        atualizarTabela(controller.listarTodos());
    }

    private void criarInterface() {
        JPanel filtros = new JPanel(new GridLayout(2, 3, 8, 8));

        filtros.add(new JLabel("Buscar por nome:"));
        filtros.add(txtBusca);

        JButton btnBuscar = new JButton("Buscar");
        filtros.add(btnBuscar);

        filtros.add(new JLabel("Categoria:"));
        filtros.add(cmbCategoria);

        JButton btnFiltrar = new JButton("Aplicar filtro");
        filtros.add(btnFiltrar);

        JButton btnOrdenar = new JButton("Ordenar por preço");
        filtros.add(btnOrdenar);

        btnBuscar.addActionListener(e -> buscar());
        btnFiltrar.addActionListener(e -> filtrarCategoria());
        btnOrdenar.addActionListener(e ->
                atualizarTabela(controller.ordenarPorPreco())
        );

        JTable tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        setLayout(new BorderLayout(10, 10));
        add(filtros, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void buscar() {
        String nome = txtBusca.getText().trim();

        if (nome.isEmpty()) {
            atualizarTabela(controller.listarTodos());
        } else {
            atualizarTabela(controller.filtrarPorNome(nome));
        }
    }

    private void filtrarCategoria() {
        String categoria =
                (String) cmbCategoria.getSelectedItem();

        if ("Todas".equals(categoria)) {
            atualizarTabela(controller.listarTodos());
        } else {
            atualizarTabela(
                    controller.filtrarPorCategoria(categoria)
            );
        }
    }

    private void atualizarTabela(List<Produto> produtos) {
        modelo.setRowCount(0);

        for (Produto p : produtos) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getCategoria(),
                    String.format("%.2f", p.getPreco()),
                    p.getEstoque(),
                    String.format("%.2f", p.calcularValorEstoque())
            });
        }
    }
}
