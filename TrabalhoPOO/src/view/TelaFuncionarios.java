package view;

import controller.FuncionarioController;
import enums.Cargo;
import model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaFuncionarios extends JFrame {
    private final FuncionarioController controller;

    private final JTextField txtId = new JTextField();
    private final JTextField txtNome = new JTextField();

    private final JComboBox<Cargo> cmbCargo =
            new JComboBox<>(Cargo.values());

    private final DefaultTableModel modelo =
            new DefaultTableModel(
                    new Object[]{"ID", "Nome", "Cargo"},
                    0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    public TelaFuncionarios(
            JFrame pai,
            FuncionarioController controller) {

        this.controller = controller;

        setTitle("Cadastro de Funcionários");
        setSize(650, 450);
        setLocationRelativeTo(pai);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        criarInterface();
        atualizarTabela();
    }

    private void criarInterface() {
        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Dados do funcionário"
                )
        );

        formulario.add(new JLabel("ID:"));
        formulario.add(txtId);

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNome);

        formulario.add(new JLabel("Cargo:"));
        formulario.add(cmbCargo);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        btnCadastrar.addActionListener(e -> cadastrarFuncionario());
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

    private void cadastrarFuncionario() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());

            Funcionario funcionario = new Funcionario(
                    id,
                    txtNome.getText().trim(),
                    (Cargo) cmbCargo.getSelectedItem()
            );

            controller.cadastrar(funcionario);

            atualizarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(
                    this,
                    "Funcionário cadastrado com sucesso!"
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

        for (Funcionario f : controller.listarTodos()) {
            modelo.addRow(new Object[]{
                    f.getId(),
                    f.getNome(),
                    f.getCargo()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        cmbCargo.setSelectedIndex(0);
    }
}
