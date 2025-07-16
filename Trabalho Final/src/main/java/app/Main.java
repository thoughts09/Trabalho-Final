package app;

import controller.UsuarioController;
import view.LoginView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioController uc = new UsuarioController();
            new LoginView(uc).setVisible(true);
        });
    }
}
