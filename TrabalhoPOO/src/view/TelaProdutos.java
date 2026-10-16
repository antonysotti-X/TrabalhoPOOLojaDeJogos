package view;

import controller.ProdutoController;
import enums.Genero;
import enums.Plataforma;
import model.Acessorio;
import model.Console;
import model.Jogo;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaProdutos extends JFrame {

    private final ProdutoController controller;

    private final JTextField txtId = new JTextField(15);
    private final JTextField txtNome = new JTextField(15);
    private final JTextField txtPreco = new JTextField(15);
    private final JTextField txtEstoque = new JTextField(15);

    private final JComboBox<String> cmbCategoria =
            new JComboBox<>(new String[]{
                    "Jogo",
                    "Console",
                    "Acessório"
            });

    private final JComboBox<Plataforma> cmbPlataforma =
            new JComboBox<>(Plataforma.values());

    private final JComboBox<Genero> cmbGenero =
            new JComboBox<>(Genero.values());

    private final JTextField txtClassificacao = new JTextField(15);

    private final JTextField txtFabricante = new JTextField(15);
    private final JTextField txtModelo = new JTextField(15);
    private final JTextField txtArmazenamento = new JTextField(15);

    private final JTextField txtTipo = new JTextField(15);
    private final JTextField txtCompatibilidade = new JTextField(15);

    private final JPanel painelEspecifico =
            new JPanel(new GridBagLayout());

    private final DefaultTableModel modeloTabela =
            new DefaultTableModel(
                    new Object[]{
                            "ID",
                            "Nome",
                            "Categoria",
                            "Preço",
                            "Estoque"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(int linha, int coluna) {
                    return false;
                }
            };

    private final JTable tabelaProdutos = new JTable(modeloTabela);

    public TelaProdutos(JFrame pai, ProdutoController controller) {
        this.controller = controller;

        setTitle("GameStore - Cadastro de Produtos");
        setSize(850, 650);
        setLocationRelativeTo(pai);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        criarInterface();
        atualizarTabela();
    }

    private void criarInterface() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int linha = 0;

        adicionarCampo(painelFormulario, gbc, linha++,
                "ID:", txtId);

        adicionarCampo(painelFormulario, gbc, linha++,
                "Nome:", txtNome);

        adicionarCampo(painelFormulario, gbc, linha++,
                "Preço (R$):", txtPreco);

        adicionarCampo(painelFormulario, gbc, linha++,
                "Estoque:", txtEstoque);

        adicionarCampo(painelFormulario, gbc, linha++,
                "Categoria:", cmbCategoria);

        configurarPainelEspecifico();

        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        painelFormulario.add(painelEspecifico, gbc);

        JButton btnCadastrar = new JButton("Cadastrar produto");
        JButton btnLimpar = new JButton("Limpar campos");

        btnCadastrar.addActionListener(e -> cadastrarProduto());
        btnLimpar.addActionListener(e -> limparCampos());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelFormulario, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        tabelaProdutos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollTabela = new JScrollPane(tabelaProdutos);
        scrollTabela.setBorder(
                BorderFactory.createTitledBorder("Produtos cadastrados")
        );

        add(painelSuperior, BorderLayout.NORTH);
        add(scrollTabela, BorderLayout.CENTER);

        cmbCategoria.addActionListener(e -> atualizarCamposEspecificos());
    }

    private void adicionarCampo(
            JPanel painel,
            GridBagConstraints gbc,
            int linha,
            String texto,
            Component componente
    ) {
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.gridx = 0;
        gbc.gridy = linha;

        painel.add(new JLabel(texto), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;

        painel.add(componente, gbc);
    }

    private void configurarPainelEspecifico() {
        painelEspecifico.setBorder(
                BorderFactory.createTitledBorder("Dados específicos")
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int linha = 0;

        adicionarCampo(painelEspecifico, gbc, linha++,
                "Classificação indicativa:", txtClassificacao);

        adicionarCampo(painelEspecifico, gbc, linha++,
                "Plataforma:", cmbPlataforma);

        adicionarCampo(painelEspecifico, gbc, linha++,
                "Gênero:", cmbGenero);

        adicionarCampo(painelEspecifico, gbc, linha++,
                "Fabricante:", txtFabricante);

        adicionarCampo(painelEspecifico, gbc, linha++,
                "Modelo:", txtModelo);

        adicionarCampo(painelEspecifico, gbc, linha++,
                "Armazenamento (GB):", txtArmazenamento);

        adicionarCampo(painelEspecifico, gbc, linha++,
                "Tipo de acessório:", txtTipo);

        adicionarCampo(painelEspecifico, gbc, linha,
                "Compatibilidade:", txtCompatibilidade);

        atualizarCamposEspecificos();
    }

    private void atualizarCamposEspecificos() {
        String categoria = (String) cmbCategoria.getSelectedItem();

        boolean jogo = "Jogo".equals(categoria);
        boolean console = "Console".equals(categoria);
        boolean acessorio = "Acessório".equals(categoria);

        definirVisibilidade("Classificação indicativa:",
                txtClassificacao, jogo);

        definirVisibilidade("Plataforma:",
                cmbPlataforma, jogo);

        definirVisibilidade("Gênero:",
                cmbGenero, jogo);

        definirVisibilidade("Fabricante:",
                txtFabricante, console);

        definirVisibilidade("Modelo:",
                txtModelo, console);

        definirVisibilidade("Armazenamento (GB):",
                txtArmazenamento, console);

        definirVisibilidade("Tipo de acessório:",
                txtTipo, acessorio);

        definirVisibilidade("Compatibilidade:",
                txtCompatibilidade, acessorio);

        painelEspecifico.revalidate();
        painelEspecifico.repaint();

        pack();
        setSize(850, Math.max(getHeight(), 550));
    }

    private void definirVisibilidade(
            String texto,
            Component componente,
            boolean visivel
    ) {
        componente.setVisible(visivel);

        for (Component item : painelEspecifico.getComponents()) {
            if (item instanceof JLabel
                    && ((JLabel) item).getText().equals(texto)) {
                item.setVisible(visivel);
            }
        }
    }

    private void cadastrarProduto() {
        try {
            validarObrigatorio(txtId.getText(), "ID");
            validarObrigatorio(txtNome.getText(), "Nome");
            validarObrigatorio(txtPreco.getText(), "Preço");
            validarObrigatorio(txtEstoque.getText(), "Estoque");

            int id = Integer.parseInt(txtId.getText().trim());

            String nome = txtNome.getText().trim();

            double preco = Double.parseDouble(
                    txtPreco.getText().trim().replace(",", ".")
            );

            int estoque = Integer.parseInt(
                    txtEstoque.getText().trim()
            );

            String categoria = (String) cmbCategoria.getSelectedItem();

            Produto produto;

            switch (categoria) {
                case "Jogo":
                    validarObrigatorio(
                            txtClassificacao.getText(),
                            "Classificação indicativa"
                    );

                    produto = new Jogo(
                            id,
                            nome,
                            preco,
                            estoque,
                            (Plataforma) cmbPlataforma.getSelectedItem(),
                            (Genero) cmbGenero.getSelectedItem(),
                            Integer.parseInt(
                                    txtClassificacao.getText().trim()
                            )
                    );
                    break;

                case "Console":
                    validarObrigatorio(
                            txtFabricante.getText(),
                            "Fabricante"
                    );

                    validarObrigatorio(
                            txtModelo.getText(),
                            "Modelo"
                    );

                    validarObrigatorio(
                            txtArmazenamento.getText(),
                            "Armazenamento"
                    );

                    produto = new Console(
                            id,
                            nome,
                            preco,
                            estoque,
                            txtFabricante.getText().trim(),
                            txtModelo.getText().trim(),
                            Integer.parseInt(
                                    txtArmazenamento.getText().trim()
                            )
                    );
                    break;

                case "Acessório":
                    validarObrigatorio(
                            txtTipo.getText(),
                            "Tipo de acessório"
                    );

                    validarObrigatorio(
                            txtCompatibilidade.getText(),
                            "Compatibilidade"
                    );

                    produto = new Acessorio(
                            id,
                            nome,
                            preco,
                            estoque,
                            txtTipo.getText().trim(),
                            txtCompatibilidade.getText().trim()
                    );
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Selecione uma categoria válida."
                    );
            }

            controller.cadastrar(produto);

            atualizarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(
                    this,
                    "Produto cadastrado com sucesso!",
                    "GameStore",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Confira os campos numéricos:\n"
                            + "ID, preço, estoque e dados específicos.",
                    "Dados inválidos",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Não foi possível cadastrar",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void validarObrigatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                    "O campo \"" + campo + "\" é obrigatório."
            );
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        for (Produto produto : controller.listarTodos()) {
            modeloTabela.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getCategoria(),
                    String.format("R$ %.2f", produto.getPreco()),
                    produto.getEstoque()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");

        txtClassificacao.setText("");
        txtFabricante.setText("");
        txtModelo.setText("");
        txtArmazenamento.setText("");
        txtTipo.setText("");
        txtCompatibilidade.setText("");

        cmbCategoria.setSelectedIndex(0);
        cmbPlataforma.setSelectedIndex(0);
        cmbGenero.setSelectedIndex(0);

        atualizarCamposEspecificos();

        txtId.requestFocusInWindow();
    }
}
