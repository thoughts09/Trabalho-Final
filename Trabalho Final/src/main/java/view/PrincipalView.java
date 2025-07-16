package view;

import controller.TarefaController;
import controller.UsuarioController;
import model.Tarefa;
import util.PlaceholderUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrincipalView extends JFrame {
    private UsuarioController usuarioController;
    private TarefaController tarefaController;
    private DefaultTableModel tableModel;
    private JTable tabelaTarefas;
    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JFormattedTextField txtData;
    private JRadioButton rbConcluida;
    private JRadioButton rbPendente;
    
    public PrincipalView(UsuarioController uc) {
        this.usuarioController = uc;
        this.tarefaController = new TarefaController();
        initComponents();
        carregarTarefas();
    }
    
    private void initComponents() {
        setTitle("Gerenciador de Tarefas");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(20, 20, 80, 25);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(100, 20, 200, 25);
        add(txtTitulo);
        PlaceholderUtil.setPlaceholder(txtTitulo, "Digite o título da tarefa");

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(20, 60, 80, 25);
        add(lblDescricao);

        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBounds(100, 60, 200, 80);
        add(scrollDescricao);

        JLabel lblData = new JLabel("Data:");
        lblData.setBounds(20, 150, 80, 25);
        add(lblData);

        txtData = new JFormattedTextField();
        txtData.setBounds(100, 150, 200, 25);
        txtData.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        add(txtData);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(20, 190, 80, 25);
        add(lblStatus);

        rbConcluida = new JRadioButton("Concluída");
        rbConcluida.setBounds(100, 190, 100, 25);
        add(rbConcluida);

        rbPendente = new JRadioButton("Pendente");
        rbPendente.setBounds(200, 190, 100, 25);
        rbPendente.setSelected(true);
        add(rbPendente);

        ButtonGroup bgStatus = new ButtonGroup();
        bgStatus.add(rbConcluida);
        bgStatus.add(rbPendente);

        JLabel lblErro = new JLabel("");
        lblErro.setBounds(20, 230, 350, 25);
        lblErro.setForeground(Color.RED);
        add(lblErro);

        // Initialize table before buttons that reference it
        tabelaTarefas = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Título", "Descrição", "Status", "Data"}, 0);
        tabelaTarefas.setModel(tableModel);
        JScrollPane scrollTabela = new JScrollPane(tabelaTarefas);
        scrollTabela.setBounds(20, 310, 550, 150);
        add(scrollTabela);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(20, 270, 100, 25);
        btnSalvar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            String descricao = txtDescricao.getText();
            boolean concluida = rbConcluida.isSelected();
            LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            if (validarCampos(titulo, descricao, lblErro)) {
                Tarefa tarefa = new Tarefa(titulo, descricao, concluida, data);
                tarefaController.addTarefa(tarefa);
                carregarTarefas();
                limparCampos();
            }
        });
        add(btnSalvar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(130, 270, 100, 25);
        btnLimpar.addActionListener(e -> limparCampos());
        add(btnLimpar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(240, 270, 100, 25);
        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaTarefas.getSelectedRow();
            if (selectedRow >= 0) {
                tarefaController.excluirTarefa(selectedRow);
                carregarTarefas();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma tarefa para excluir", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(btnExcluir);

        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(350, 270, 100, 25);
        btnSair.addActionListener(e -> {
            new LoginView(usuarioController).setVisible(true);
            dispose();
        });
        add(btnSair);

        tabelaTarefas.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tabelaTarefas.getSelectedRow();
            if (selectedRow >= 0) {
                Tarefa tarefa = tarefaController.getTarefa(selectedRow);
                txtTitulo.setText(tarefa.getTitulo());
                txtDescricao.setText(tarefa.getDescricao());
                if (tarefa.isConcluida()) {
                    rbConcluida.setSelected(true);
                } else {
                    rbPendente.setSelected(true);
                }
                txtData.setValue(tarefa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        });
    }
    
    private void carregarTarefas() {
        tableModel.setRowCount(0);
        for (Tarefa tarefa : tarefaController.getListaDeTarefas()) {
            tableModel.addRow(new Object[]{
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.isConcluida() ? "Concluída" : "Pendente",
                tarefa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            });
        }
    }
    
    private boolean validarCampos(String titulo, String descricao, JLabel lblErro) {
        boolean valido = true;
        lblErro.setText("");
        resetarBordas();
        
        if (titulo.isEmpty() || titulo.equals("Digite o título da tarefa")) {
            txtTitulo.setBorder(BorderFactory.createLineBorder(Color.RED));
            valido = false;
        }
        if (descricao.isEmpty()) {
            txtDescricao.setBorder(BorderFactory.createLineBorder(Color.RED));
            valido = false;
        }
        
        if (!valido) {
            lblErro.setText("Preencha todos os campos corretamente!");
        }
        
        return valido;
    }
    
    private void resetarBordas() {
        txtTitulo.setBorder(UIManager.getBorder("TextField.border"));
        txtDescricao.setBorder(UIManager.getBorder("TextArea.border"));
    }
    
    private void limparCampos() {
        txtTitulo.setText("");
        txtDescricao.setText("");
        txtData.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        rbPendente.setSelected(true);
        resetarBordas();
    }
}