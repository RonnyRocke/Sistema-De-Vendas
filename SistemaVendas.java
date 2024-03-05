import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SistemaVendas extends JFrame implements ActionListener {
    private JTextField textFieldUsuario;
    private JPasswordField passwordFieldSenha;
    private JButton buttonCadastro;
    private JButton buttonLogin;

    private String usuarioCadastrado = "admin";
    private String senhaCadastrada = "admin";

    public SistemaVendas() {
        setTitle("Sistema de Vendas");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.setBackground(new Color(255, 255, 240)); // Cor de fundo creme

        panel.add(new JLabel("Usuário:"));
        textFieldUsuario = new JTextField(20);
        panel.add(textFieldUsuario);

        panel.add(new JLabel("Senha:"));
        passwordFieldSenha = new JPasswordField(20);
        panel.add(passwordFieldSenha);

        buttonCadastro = new JButton("Cadastro");
        buttonCadastro.addActionListener(this);
        configurarBotao(buttonCadastro);
        panel.add(buttonCadastro);

        buttonLogin = new JButton("Login");
        buttonLogin.addActionListener(this);
        configurarBotao(buttonLogin);
        panel.add(buttonLogin);

        add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonCadastro) {
            String usuario = textFieldUsuario.getText();
            String senha = new String(passwordFieldSenha.getPassword());
            usuarioCadastrado = usuario;
            senhaCadastrada = senha;
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso.");
        } else if (e.getSource() == buttonLogin) {
            String usuario = textFieldUsuario.getText();
            String senha = new String(passwordFieldSenha.getPassword());

            if (usuario.equals(usuarioCadastrado) && senha.equals(senhaCadastrada)) {
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso. Redirecionando para o sistema de vendas...");

                // Aqui você irá abrir a nova janela do sistema de vendas
                abrirSistemaVendas();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirSistemaVendas() {
        // Crie uma nova janela ou painel para o sistema de vendas e exiba-a aqui
        // Exemplo:
        TelaSistemaVendas telaSistemaVendas = new TelaSistemaVendas();
        telaSistemaVendas.setVisible(true);
    }

    private void configurarBotao(JButton button) {
        button.setBackground(new Color(70, 130, 180)); // Cor de fundo azul
        button.setForeground(Color.WHITE); // Cor do texto branco
        button.setFocusPainted(false); // Remove a borda de foco
        button.setBorder(BorderFactory.createRaisedBevelBorder()); // Adiciona borda elevada
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Muda o cursor ao passar sobre o botão
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaVendas sistemaVendas = new SistemaVendas();
            sistemaVendas.setVisible(true);
        });
    }
}

class TelaSistemaVendas extends JFrame {
    private DefaultListModel<String> produtosListModel;
    private JList<String> produtosList;
    private JTextField textFieldNovoProduto;
    private JTextField textFieldNovoPreco; // Adicionando campo de texto para preço
    private JButton buttonAdicionarProduto;
    private JButton buttonExcluirProduto;
    private JLabel labelTotalFaturado;
    private double totalFaturado;

    public TelaSistemaVendas() {
        setTitle("Sistema de Vendas - Tela Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 240)); // Cor de fundo creme

        // Lista de produtos vendidos
        produtosListModel = new DefaultListModel<>();
        produtosList = new JList<>(produtosListModel);
        JScrollPane scrollPaneProdutos = new JScrollPane(produtosList);

        // Adicionar novo produto
        textFieldNovoProduto = new JTextField(20);
        textFieldNovoPreco = new JTextField(10); // Campo de texto para preço
        buttonAdicionarProduto = new JButton("Adicionar Produto");
        buttonAdicionarProduto.addActionListener(e -> adicionarProduto());
        configurarBotao(buttonAdicionarProduto);

        // Excluir produto selecionado
        buttonExcluirProduto = new JButton("Excluir Produto");
        buttonExcluirProduto.addActionListener(e -> excluirProduto());
        configurarBotao(buttonExcluirProduto);

        // Total faturado
        labelTotalFaturado = new JLabel("Total faturado: R$ 0.00");
        labelTotalFaturado.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Adiciona espaçamento

        // Layout
        JPanel produtosPanel = new JPanel(new BorderLayout());
        produtosPanel.setBackground(new Color(240, 248, 255)); // Cor de fundo azul claro
        produtosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Produtos Vendidos"));
        produtosPanel.add(scrollPaneProdutos, BorderLayout.CENTER);
        produtosPanel.add(buttonExcluirProduto, BorderLayout.SOUTH);

        JPanel novoProdutoPanel = new JPanel();
        novoProdutoPanel.setBackground(new Color(240, 248, 255)); // Cor de fundo azul claro
        novoProdutoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Adicionar Novo Produto"));
        novoProdutoPanel.add(new JLabel("Nome: "));
        novoProdutoPanel.add(textFieldNovoProduto);
        novoProdutoPanel.add(new JLabel("Preço: "));
        novoProdutoPanel.add(textFieldNovoPreco);
        novoProdutoPanel.add(buttonAdicionarProduto);

        panel.add(produtosPanel, BorderLayout.CENTER);
        panel.add(novoProdutoPanel, BorderLayout.NORTH);
        panel.add(labelTotalFaturado, BorderLayout.SOUTH);

        add(panel);
    }

    private void adicionarProduto() {
        String novoProduto = textFieldNovoProduto.getText();
        String precoProdutoStr = textFieldNovoPreco.getText(); // Obter preço do campo de texto
        double precoProduto = 0.0;

        try {
            precoProduto = Double.parseDouble(precoProdutoStr); // Converter para double
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um preço válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!novoProduto.isEmpty()) {
            produtosListModel.addElement(novoProduto + " - R$ " + precoProduto); // Adicionar nome e preço
            totalFaturado += precoProduto; // Adicionar ao total faturado
            atualizarTotalFaturado();
        }
    }

    private void excluirProduto() {
        int indexSelecionado = produtosList.getSelectedIndex();
        if (indexSelecionado != -1) {
            String produtoSelecionado = produtosListModel.getElementAt(indexSelecionado);
            double precoProduto = extrairPrecoProduto(produtoSelecionado); // Extrair preço
            produtosListModel.remove(indexSelecionado);
            totalFaturado -= precoProduto; // Deduzir do total faturado
            atualizarTotalFaturado();
        }
    }

    private double extrairPrecoProduto(String produto) {
        String[] partes = produto.split(" - R\\$ ");
        if (partes.length == 2) {
            try {
                return Double.parseDouble(partes[1]);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    private void atualizarTotalFaturado() {
        labelTotalFaturado.setText("Total faturado: R$ " + String.format("%.2f", totalFaturado));
    }

    private void configurarBotao(JButton button) {
        button.setBackground(new Color(70, 130, 180)); // Cor de fundo azul
        button.setForeground(Color.WHITE); // Cor do texto branco
        button.setFocusPainted(false); // Remove a borda de foco
        button.setBorder(BorderFactory.createRaisedBevelBorder()); // Adiciona borda elevada
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Muda o cursor ao passar sobre o botão
    }
}
