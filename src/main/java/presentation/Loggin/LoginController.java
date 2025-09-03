package presentation.Loggin;

import javax.swing.*;
import presentation.Logic.Service;
import presentation.Logic.Usuario;

public class LoginController {
    private LoginModel model;
    private View_Login loginView;
    private View_CambiarClave cambiarClaveView;

    public LoginController(LoginModel model, View_Login loginView) {
        this.model = model;
        this.loginView = loginView;
        loginView.setController(this);
        loginView.setModel(model);
    }

    public LoginController(LoginModel model, View_CambiarClave cambiarClaveView) {
        this.model = model;
        this.cambiarClaveView = cambiarClaveView;
        cambiarClaveView.setController(this);
        cambiarClaveView.setModel(model);
    }

    // Si también se necesita la vista de cambiar clave
    public void setCambiarClaveView(View_CambiarClave view) {
        this.cambiarClaveView = view;
        view.setController(this);
        view.setModel(model);
    }

    // ---- LOGIN ----
    public void login(String id, String clave) {
        Usuario tempUser = Service.instance().login(id, clave);
        model.setCurrentUser(tempUser);
    }

    // ---- BUSCAR USUARIO (para cambiar clave) ----
    public void buscarUsuario(String id) {
        Usuario tempUser = Service.instance().buscarUsuario(id);
        model.setCurrentUser(tempUser);
    }

    // ---- CAMBIAR CLAVE ----
    public void cambiarClave() {
        if (cambiarClaveView == null) {
            JOptionPane.showMessageDialog(null, "No se ha asignado la vista de cambiar clave");
            return;
        }

        String claveActual = cambiarClaveView.getClaveActual();
        String claveNueva = cambiarClaveView.getClaveNueva();
        String claveConfirmacion = cambiarClaveView.getClaveNuevaConfirmacion();

        boolean claveCambiada = model.cambiarClaveUsuario(claveActual, claveNueva, claveConfirmacion);

        if (claveCambiada) {
            Service.instance().actualizarUsuario(model.getCurrentUser());
        }
    }
}

