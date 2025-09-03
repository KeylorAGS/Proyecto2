package presentation.Loggin;

import presentation.Interfaces.InterfazPrescripcion;
import presentation.Logic.Administrador;
import presentation.Logic.Farmaceutico;
import presentation.Logic.Medico;
import presentation.Logic.Usuario;
import presentation.Interfaces.InterfazAdministrador;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_Login implements PropertyChangeListener {
    private JPanel panelPrincipal;
    private JTextField idField;
    private JPasswordField claveField;
    private JButton botonCambiarClave;
    private JButton botonInicioSesion;
    private JButton botonCancelar;
    private JLabel idLabel;
    private JLabel claveLabel;
    private JLabel iniciarSesionLogo;

    LoginModel model;
    LoginController controller;
    View_CambiarClave view_CambiarClave;

    public void setModel(LoginModel model){
        if(this.model != null) {
            this.model.removePropertyChangeListener(this); // evita que se notifique dos veces
        }
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    public void setController(LoginController controller){this.controller=controller; }

    public View_Login() {

        botonCambiarClave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = idField.getText().trim();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor digite el ID del usuario");
                    return;
                }

                View_CambiarClave cambiarClaveView = new View_CambiarClave();
                LoginModel cambiarClaveModel = new LoginModel();
                CambiarClaveController cambiarClaveController = new CambiarClaveController(cambiarClaveModel, cambiarClaveView);

                cambiarClaveView.setIdUsuario(id);
                cambiarClaveController.login(id);
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelPrincipal);
                topFrame.dispose();

                JFrame frame = new JFrame("Cambiar Clave");
                frame.setSize(600, 300);
                frame.setContentPane(cambiarClaveView.getPanelCambiarClave());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
                claveField.setText("");
            }
        });
        botonInicioSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String clave = new String(claveField.getPassword());

                if (id.isEmpty() || clave.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor complete todos los campos");
                    return;
                }
                controller.login(id, clave);
            }
        });
    }

    public JPanel getPanelLogin(){
        return panelPrincipal;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("currentUser")) {
            Usuario usuario = (Usuario) evt.getNewValue();
            if (usuario instanceof Medico) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelPrincipal);
                topFrame.dispose();
                InterfazPrescripcion.ventanaPrescripcion();
            } else if (usuario instanceof Farmaceutico) {
                System.out.println("Se abre la ventana de farmacéutico");
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelPrincipal);
                topFrame.dispose();
            } else if (usuario instanceof Administrador) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelPrincipal);
                topFrame.dispose();
                InterfazAdministrador.ventanaMedicos();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró al usuario");
            }
        }
    }

}
