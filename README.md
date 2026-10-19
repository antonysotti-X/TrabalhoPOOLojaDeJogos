# Games.Sotti — Sistema de uma Loja de Jogos e Acessorios

## 1. Objetivo

É um sistema desenvolvido em Java com o objetivo de gerenciar as principais operações de uma loja de jogos. A aplicação permite cadastrar e consultar produtos, clientes e funcionários, além de realizar vendas, controlar o estoque e registrar devoluções.

## 2. Domínio escolhido

O domínio escolhido é o **comércio de jogos eletrônicos, consoles e acessórios**

## 3. Integrantes

- [Antony]

## 4. Principais funcionalidades

- Cadastro e consulta de produtos.
- Cadastro e consulta de clientes.
- Cadastro e consulta de funcionários.
- Gerenciamento de jogos, consoles e acessórios.
- Controle de estoque dos produtos.
- Realização de vendas associadas a clientes e funcionários.
- Atualização automática do estoque após uma venda.
- Devolução de vendas e reposição dos produtos ao estoque.
- Consulta do histórico de vendas.
- Validação de dados e prevenção de operações inválidas, como vendas sem estoque suficiente e devoluções duplicadas.

## 5. Lista das entidades

As principais entidades do sistema são:

- **Produto:** classe abstrata que representa os produtos comercializados pela loja.
- **Jogo:** representa os jogos eletrônicos disponíveis para venda.
- **Console:** representa os consoles comercializados.
- **Acessorio:** representa os acessórios disponíveis na loja.
- **Cliente:** representa os clientes cadastrados.
- **Funcionario:** representa os funcionários da loja.
- **Venda:** representa uma transação de venda realizada.
- **ItemVenda:** representa um produto e sua quantidade dentro de uma venda.

## 6. Lista dos cadastros

Os cadastros disponíveis no sistema são:

- **Cadastro de produtos:** permite registrar jogos, consoles e acessórios, com suas respectivas informações e quantidades em estoque.
- **Cadastro de clientes:** permite registrar os dados dos clientes.
- **Cadastro de funcionários:** permite registrar os dados dos funcionários.

As vendas também são registradas pelo sistema, por meio da associação entre cliente, funcionário e itens vendidos.

## 7. Lista das consultas

O sistema disponibiliza as seguintes consultas:

- **Consulta de produtos:** permite visualizar os produtos cadastrados e suas informações.
- **Consulta de clientes:** permite visualizar os clientes registrados.
- **Consulta de funcionários:** permite visualizar os funcionários registrados.
- **Consulta de estoque:** permite verificar os produtos e suas quantidades disponíveis.
- **Consulta de vendas:** permite visualizar as vendas realizadas.
- **Consulta do histórico de vendas:** permite consultar as transações e seus respectivos status, incluindo vendas devolvidas.

## 8. Lista dos movimentos

- **Realização de venda:** registra uma venda, associa o cliente e o funcionário responsáveis e reduz as quantidades dos produtos vendidos no estoque.
- **Devolução de venda:** altera o status da venda para devolvida e repõe ao estoque as quantidades correspondentes aos itens devolvidos.

O sistema valida as operações para evitar estoque insuficiente e impedir que uma mesma venda seja devolvida mais de uma vez.

## 9. Instruções para execução

### Pré-requisitos

- Java Development Kit (JDK) compatível com a versão utilizada no projeto (Liberica 27 ou Equivalente).
- IntelliJ IDEA ou outra IDE compatível com projetos Java.

### Passos para executar

1. Baixe ou clone o repositório do projeto.
2. Abra o projeto na IDE.
3. Localize a classe `view.TelaPrincipal`.
6. Execute o programa.

