package view;

import controller.ClienteController;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaClientes extends JFrame {
    private final ClienteController controller;

    private final JTextField txtId = new JTextField();
    private final JTextField txtNome = new JTextField();
    private final JTextField txtCpf = new JTextField();
    private final JTextField txtTelefone = new JTextField();
    private final JTextField txtEmail = new JTextField();

    private final DefaultTableModel modelo =
            new DefaultTableModel(
                    new Object[]{"ID", "Nome", "CPF", "Telefone", "E-mail"},
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    public TelaClientes(
            JFrame pai,
            ClienteController controller) {

        this.controller = controller;

        setTitle("Cadastro de Clientes");
        setSize(750, 500);
        setLocationRelativeTo(pai);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        criarInterface();
        atualizarTabela();
    }

    private void criarInterface() {
        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.setBorder(
                BorderFactory.createTitledBorder("Dados do cliente")
        );

        formulario.add(new JLabel("ID:"));
        formulario.add(txtId);

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNome);

        formulario.add(new JLabel("CPF:"));
        formulario.add(txtCpf);

        formulario.add(new JLabel("Telefone:"));
        formulario.add(txtTelefone);

        formulario.add(new JLabel("E-mail:"));
        formulario.add(txtEmail);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        btnCadastrar.addActionListener(e -> cadastrarCliente());
        btnLimpar.addActionListener(e -> limparCampos());

        JPanel botoes = new JPanel();
        botoes.add(btnCadastrar);
        botoes.add(btnLimpar);

        JPanel topo = new JPanel(new BorderLayout(8, 8));
        topo.add(formulario, BorderLayout.CENTER);
        topo.add(botoes, BorderLayout.SOUTH);

        JTable tabela = new JTable(modelo);

        setLayout(new BorderLayout(10, 10));
        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void cadastrarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());

            Cliente cliente = new Cliente(
                    id,
                    txtNome.getText().trim(),
                    txtCpf.getText().trim(),
                    txtTelefone.getText().trim(),
                    txtEmail.getText().trim()
            );

            controller.cadastrar(cliente);

            atualizarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente cadastrado com sucesso!"
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "O ID deve ser um número inteiro.",
                    "Dados inválidos",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro no cadastro",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);

        for (Cliente c : controller.listarTodos()) {
            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getNome(),
                    c.getCpf(),
                    c.getTelefone(),
                    c.getEmail()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
    }
}
