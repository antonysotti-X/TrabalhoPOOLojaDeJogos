package view;

import controller.ClienteController;
import controller.FuncionarioController;
import controller.ProdutoController;
import controller.VendaController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TelaVendas extends JFrame {
    private final ProdutoController produtoController;
    private final ClienteController clienteController;
    private final FuncionarioController funcionarioController;
    private final VendaController vendaController;

    private final JComboBox<Cliente> comboClientes = new JComboBox<>();
    private final JComboBox<Funcionario> comboFuncionarios = new JComboBox<>();
    private final JComboBox<Produto> comboProdutos = new JComboBox<>();

    private final JTextField txtIdVenda = new JTextField(8);
    private final JTextField txtQuantidade = new JTextField(5);
    private final JTextField txtIdDevolucao = new JTextField(8);

    private final DefaultTableModel modeloCarrinho =
            new DefaultTableModel(
                    new Object[]{"Produto", "Quantidade", "Preço unitário", "Subtotal"},
                    0
            ) {
                @Override
                public boolean isCellEditable(int linha, int coluna) {
                    return false;
                }
            };

    private final JTable tabelaCarrinho = new JTable(modeloCarrinho);
    private final JLabel lblTotal = new JLabel("Total: R$ 0,00");

    private final List<ItemVenda> itensTemporarios = new ArrayList<>();

    public TelaVendas(
            JFrame pai,
            ProdutoController produtoController,
            ClienteController clienteController,
            FuncionarioController funcionarioController,
            VendaController vendaController
    ) {
        this.produtoController = produtoController;
        this.clienteController = clienteController;
        this.funcionarioController = funcionarioController;
        this.vendaController = vendaController;

        setTitle("GameStore - Registrar venda");
        setSize(850, 600);
        setLocationRelativeTo(pai);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        criarInterface();
        atualizarCombos();
    }

    private void criarInterface() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelSuperior = new JPanel(new GridLayout(3, 2, 8, 8));
        painelSuperior.setBorder(
                BorderFactory.createTitledBorder("Dados da venda")
        );

        painelSuperior.add(new JLabel("ID da venda:"));
        painelSuperior.add(txtIdVenda);

        painelSuperior.add(new JLabel("Cliente:"));
        painelSuperior.add(comboClientes);

        painelSuperior.add(new JLabel("Funcionário:"));
        painelSuperior.add(comboFuncionarios);

        JPanel painelProduto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelProduto.setBorder(
                BorderFactory.createTitledBorder("Adicionar produto")
        );

        painelProduto.add(new JLabel("Produto:"));
        painelProduto.add(comboProdutos);

        painelProduto.add(new JLabel("Quantidade:"));
        painelProduto.add(txtQuantidade);

        JButton btnAdicionar = new JButton("Adicionar item");
        btnAdicionar.addActionListener(e -> adicionarItem());
        painelProduto.add(btnAdicionar);

        JButton btnRemover = new JButton("Remover item");
        btnRemover.addActionListener(e -> removerItem());
        painelProduto.add(btnRemover);

        JPanel painelCentro = new JPanel(new BorderLayout(5, 5));
        painelCentro.add(painelProduto, BorderLayout.NORTH);
        painelCentro.add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(10, 10));

        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        painelInferior.add(lblTotal, BorderLayout.WEST);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnFinalizar = new JButton("Finalizar venda");
        btnFinalizar.addActionListener(e -> finalizarVenda());

        JButton btnAtualizar = new JButton("Atualizar cadastros");
        btnAtualizar.addActionListener(e -> atualizarCombos());

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFinalizar);

        painelInferior.add(painelBotoes, BorderLayout.EAST);

        JPanel painelDevolucao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelDevolucao.setBorder(
                BorderFactory.createTitledBorder("Devolução")
        );

        painelDevolucao.add(new JLabel("ID da venda:"));
        painelDevolucao.add(txtIdDevolucao);

        JButton btnDevolver = new JButton("Devolver venda");
        btnDevolver.addActionListener(e -> devolverVenda());
        painelDevolucao.add(btnDevolver);

        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.add(painelInferior, BorderLayout.NORTH);
        painelRodape.add(painelDevolucao, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentro, BorderLayout.CENTER);
        add(painelRodape, BorderLayout.SOUTH);
    }

    private void atualizarCombos() {
        comboClientes.removeAllItems();
        comboFuncionarios.removeAllItems();
        comboProdutos.removeAllItems();

        for (Cliente cliente : clienteController.listarTodos()) {
            comboClientes.addItem(cliente);
        }

        for (Funcionario funcionario : funcionarioController.listarTodos()) {
            comboFuncionarios.addItem(funcionario);
        }

        for (Produto produto : produtoController.listarTodos()) {
            comboProdutos.addItem(produto);
        }
    }

    private void adicionarItem() {
        Produto produto = (Produto) comboProdutos.getSelectedItem();

        if (produto == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cadastre pelo menos um produto antes de realizar uma venda."
            );
            return;
        }

        try {
            int quantidade = Integer.parseInt(txtQuantidade.getText().trim());

            ItemVenda item = new ItemVenda(produto, quantidade);
            itensTemporarios.add(item);

            atualizarCarrinho();
            txtQuantidade.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite uma quantidade válida."
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void removerItem() {
        int linha = tabelaCarrinho.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um item do carrinho para removê-lo."
            );
            return;
        }

        itensTemporarios.remove(linha);
        atualizarCarrinho();
    }

    private void atualizarCarrinho() {
        modeloCarrinho.setRowCount(0);

        double total = 0;

        for (ItemVenda item : itensTemporarios) {
            modeloCarrinho.addRow(new Object[]{
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    String.format("R$ %.2f", item.getPrecoUnitario()),
                    String.format("R$ %.2f", item.getSubtotal())
            });

            total += item.getSubtotal();
        }

        lblTotal.setText(String.format("Total: R$ %.2f", total));
    }

    private void finalizarVenda() {
        try {
            int id = Integer.parseInt(txtIdVenda.getText().trim());

            Cliente cliente = (Cliente) comboClientes.getSelectedItem();
            Funcionario funcionario =
                    (Funcionario) comboFuncionarios.getSelectedItem();

            if (cliente == null || funcionario == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cadastre e selecione um cliente e um funcionário."
                );
                return;
            }

            if (itensTemporarios.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Adicione pelo menos um produto ao carrinho."
                );
                return;
            }

            Venda venda = vendaController.realizarVenda(
                    id,
                    cliente,
                    funcionario,
                    new ArrayList<>(itensTemporarios)
            );

            JOptionPane.showMessageDialog(
                    this,
                    String.format(
                            "Venda #%d realizada com sucesso!%nTotal: R$ %.2f",
                            venda.getId(),
                            venda.calcularTotal()
                    )
            );

            itensTemporarios.clear();
            atualizarCarrinho();
            txtIdVenda.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite um ID de venda válido."
            );
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }


    private void devolverVenda() {
        try {
            String textoId = txtIdDevolucao.getText().trim();

            if (textoId.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Digite o ID da venda que deseja devolver.",
                        "Campo obrigatório",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int id = Integer.parseInt(textoId);

            vendaController.devolverVenda(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Venda devolvida com sucesso! O estoque foi atualizado.",
                    "Devolução concluída",
                    JOptionPane.INFORMATION_MESSAGE
            );

            txtIdDevolucao.setText("");
            atualizarCombos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite um ID de venda válido, utilizando apenas números.",
                    "ID inválido",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Operação não permitida",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Dados inválidos",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

}
