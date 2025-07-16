package view;

import controller.UsuarioController;
import model.Usuario;
import util.PlaceholderUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CadastroUsuarioView extends JFrame {
    private UsuarioController usuarioController;
    
    public CadastroUsuarioView(UsuarioController uc) {
        this.usuarioController = uc;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Cadastro de Usuário");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblNome = new JLabel("Nome completo:");
        lblNome.setBounds(20, 20, 120, 25);
        add(lblNome);

        JTextField txtNome = new JTextField();
        txtNome.setBounds(150, 20, 200, 25);
        add(txtNome);
        PlaceholderUtil.setPlaceholder(txtNome, "Digite seu nome completo");

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 60, 120, 25);
        add(lblEmail);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(150, 60, 200, 25);
        add(txtEmail);
        PlaceholderUtil.setPlaceholder(txtEmail, "Digite seu email");

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(20, 100, 120, 25);
        add(lblSenha);

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBounds(150, 100, 200, 25);
        add(txtSenha);

        JCheckBox chkMostrarSenha = new JCheckBox("Mostrar senha");
        chkMostrarSenha.setBounds(150, 130, 150, 25);
        chkMostrarSenha.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                txtSenha.setEchoChar((char) 0);
            } else {
                txtSenha.setEchoChar('*');
            }
        });
        add(chkMostrarSenha);

        JLabel lblConfirmarSenha = new JLabel("Confirmar senha:");
        lblConfirmarSenha.setBounds(20, 160, 120, 25);
        add(lblConfirmarSenha);

        JPasswordField txtConfirmarSenha = new JPasswordField();
        txtConfirmarSenha.setBounds(150, 160, 200, 25);
        add(txtConfirmarSenha);

        JCheckBox chkMostrarConfirmarSenha = new JCheckBox("Mostrar senha");
        chkMostrarConfirmarSenha.setBounds(150, 190, 150, 25);
        chkMostrarConfirmarSenha.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                txtConfirmarSenha.setEchoChar((char) 0);
            } else {
                txtConfirmarSenha.setEchoChar('*');
            }
        });
        add(chkMostrarConfirmarSenha);

        JLabel lblErro = new JLabel("");
        lblErro.setBounds(20, 220, 350, 25);
        lblErro.setForeground(Color.RED);
        add(lblErro);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(100, 260, 100, 25);
        btnConfirmar.addActionListener(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            String confirmarSenha = new String(txtConfirmarSenha.getPassword());
            
            if (validarCampos(nome, email, senha, confirmarSenha, lblErro, txtNome, txtEmail, txtSenha, txtConfirmarSenha)) {
                if (!senha.equals(confirmarSenha)) {
                    lblErro.setText("As senhas não coincidem!");
                    return;
                }
                
                Usuario novoUsuario = new Usuario(nome, email, senha);
                if (usuarioController.addUsuario(novoUsuario)) {
                    JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    lblErro.setText("Email já cadastrado!");
                }
            }
        });
        add(btnConfirmar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(220, 260, 100, 25);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }
    
    private boolean validarCampos(String nome, String email, String senha, String confirmarSenha, 
                                JLabel lblErro, JTextField txtNome, JTextField txtEmail, 
                                JPasswordField txtSenha, JPasswordField txtConfirmarSenha) {
        boolean valido = true;
        lblErro.setText("");
        resetarBordas(txtNome, txtEmail, txtSenha, txtConfirmarSenha);
        
        if (nome.isEmpty() || nome.equals("Digite seu nome completo")) {
            txtNome.setBorder(BorderFactory.createLineBorder(Color.RED));
            valido = false;
        }
        if (email.isEmpty() || email.equals("Digite seu email")) {
            txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
            valido = false;
        }
        if (senha.isEmpty()) {
            txtSenha.setBorder(BorderFactory.createLineBorder(Color.RED));
            valido = false;
        }
        if (confirmarSenha.isEmpty()) {
            txtConfirmarSenha.setBorder(BorderFactory.createLineBorder(Color.RED));
            valido = false;
        }
        
        if (!valido) {
            lblErro.setText("Preencha todos os campos corretamente!");
        }
        
        return valido;
    }
    
    private void resetarBordas(JTextField txtNome, JTextField txtEmail, 
                             JPasswordField txtSenha, JPasswordField txtConfirmarSenha) {
        txtNome.setBorder(UIManager.getBorder("TextField.border"));
        txtEmail.setBorder(UIManager.getBorder("TextField.border"));
        txtSenha.setBorder(UIManager.getBorder("TextField.border"));
        txtConfirmarSenha.setBorder(UIManager.getBorder("TextField.border"));
    }
}