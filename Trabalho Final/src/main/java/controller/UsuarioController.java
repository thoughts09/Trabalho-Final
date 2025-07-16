package controller;

import model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private List<Usuario> usuarios = new ArrayList<>();

    public boolean addUsuario(Usuario u) {
        for (Usuario existente : usuarios) {
            if (existente.getEmail().equalsIgnoreCase(u.getEmail())) {
                return false;
            }
        }
        usuarios.add(u);
        return true;
    }

    public Usuario autenticar(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }
}