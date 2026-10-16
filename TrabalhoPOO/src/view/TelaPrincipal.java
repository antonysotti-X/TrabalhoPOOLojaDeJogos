package view;

import controller.ClienteController;
import controller.FuncionarioController;
import controller.ProdutoController;
import controller.VendaController;
import repository.DadosLoja;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private final DadosLoja dados;

    private final ProdutoController produtoController;
    private final ClienteController clienteController;
    private final FuncionarioController funcionarioController;
    private final VendaController vendaController;

    public TelaPrincipal() {
        dados = new DadosLoja();

        produtoController = new ProdutoController(dados);
        clienteController = new ClienteController(dados);
        funcionarioController = new FuncionarioController(dados);
        vendaController = new VendaController(dados);

        setTitle("Games.Sotti - Sistema de Controle");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        criarInterface();
    }

    private void criarInterface() {
        JPanel painel = new JPanel(new BorderLayout(15, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(
                25, 25, 25, 25
        ));

        JLabel titulo = new JLabel(
                "Games.Sotti - Loja de Jogos",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));

        JPanel botoes = new JPanel(new GridLayout(3, 2, 12, 12));

        JButton btnProdutos = new JButton("Cadastrar produtos");
        JButton btnClientes = new JButton("Cadastrar clientes");
        JButton btnFuncionarios = new JButton("Cadastrar funcionários");
        JButton btnEstoque = new JButton("Consultar estoque");
        JButton btnVendas = new JButton("Registrar venda");
        JButton btnHistorico = new JButton("Consultar vendas");

        btnProdutos.addActionListener(e ->
                new TelaProdutos(this, produtoController).setVisible(true)
        );

        btnClientes.addActionListener(e ->
                new TelaClientes(this, clienteController).setVisible(true)
        );

        btnFuncionarios.addActionListener(e ->
                new TelaFuncionarios(this, funcionarioController)
                        .setVisible(true)
        );

        btnEstoque.addActionListener(e ->
                new TelaEstoque(this, produtoController).setVisible(true)
        );

        btnVendas.addActionListener(e ->
                new TelaVendas(
                        this,
                        produtoController,
                        clienteController,
                        funcionarioController,
                        vendaController
                ).setVisible(true)
        );

        btnHistorico.addActionListener(e ->
                new TelaHistoricoVendas(this, vendaController).setVisible(true)
        );

        botoes.add(btnProdutos);
        botoes.add(btnClientes);
        botoes.add(btnFuncionarios);
        botoes.add(btnEstoque);
        botoes.add(btnVendas);
        botoes.add(btnHistorico);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(botoes, BorderLayout.CENTER);

        setContentPane(painel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaPrincipal().setVisible(true)
        );
    }
}
