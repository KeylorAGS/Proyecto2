package presentation.Loggin;

import javax.swing.*;
import presentation.Logic.Service;
import presentation.Logic.Usuario;

public class CambiarClaveController {
    LoginModel model;
    View_CambiarClave view;

    public CambiarClaveController(LoginModel model, View_CambiarClave view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    public void login(String id) {
        Usuario tempUser = Service.instance().buscarUsuario(id);
        model.setCurrentUser(tempUser);
    }

    public void cambiarClave() {
        String claveActual = view.getClaveActual();
        String claveNueva = view.getClaveNueva();
        String claveConfirmacion = view.getClaveNuevaConfirmacion();

        boolean claveCambiada = model.cambiarClaveUsuario(claveActual, claveNueva, claveConfirmacion);

        if (claveCambiada) {
            Service.instance().actualizarUsuario(model.getCurrentUser());
            JOptionPane.showMessageDialog(null, "Clave cambiada con éxito");
        }
    }
}

