package view;

import controller.UsuarioController;
import util.PlaceholderUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private UsuarioController usuarioController;
    
    public LoginView(UsuarioController uc) {
        this.usuarioController = uc;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Login");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 20, 80, 25);
        add(lblEmail);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(100, 20, 200, 25);
        add(txtEmail);
        PlaceholderUtil.setPlaceholder(txtEmail, "Digite seu email");

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(20, 60, 80, 25);
        add(lblSenha);

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBounds(100, 60, 200, 25);
        add(txtSenha);

        JCheckBox chkMostrarSenha = new JCheckBox("Mostrar senha");
        chkMostrarSenha.setBounds(100, 90, 150, 25);
        chkMostrarSenha.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                txtSenha.setEchoChar((char) 0);
            } else {
                txtSenha.setEchoChar('*');
            }
        });
        add(chkMostrarSenha);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(100, 130, 200, 25);
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            
            if (usuarioController.autenticar(email, senha) != null) {
                new PrincipalView(usuarioController).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnLogin);

        JLabel lblCadastro = new JLabel("<html><u>Não tem conta? Cadastre-se</u></html>");
        lblCadastro.setBounds(100, 160, 200, 25);
        lblCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblCadastro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CadastroUsuarioView(usuarioController).setVisible(true);
            }
        });
        add(lblCadastro);
    }
}