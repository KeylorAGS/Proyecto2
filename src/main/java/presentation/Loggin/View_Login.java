package presentation.Loggin;

import presentation.Interfaces.InterfazDespacho;
import presentation.Interfaces.InterfazPrescripcion;
import presentation.Logic.Administrador;
import presentation.Logic.Farmaceutico;
import presentation.Logic.Medico;
import presentation.Logic.Usuario;
import presentation.Interfaces.InterfazAdministrador;
import presentation.Prescripcion.View_Prescripcion;

import javax.swing.*;
import java.awt.*;
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
    private String guardaUsuario;

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

                // Crear vista de cambiar clave
                View_CambiarClave cambiarClaveView = new View_CambiarClave();

                // Usar el mismo modelo y controlador del login
                LoginModel cambiarClaveModel = new LoginModel();
                LoginController cambiarClaveController = new LoginController(cambiarClaveModel, cambiarClaveView);

                // Pasar el usuario al controlador/modelo
                cambiarClaveView.setIdUsuario(id);
                cambiarClaveController.buscarUsuario(id);

                // Cerrar ventana actual (login)
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelPrincipal);
                topFrame.dispose();

                // Nueva ventana de cambiar clave
                JFrame frame = new JFrame("Cambiar Clave");
                Image icon = Toolkit.getDefaultToolkit().getImage(
                        getClass().getResource("/Imagenes/Logo_Cambiar_Contrasena.png")
                );
                frame.setIconImage(icon);
                frame.setSize(1000, 800);
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
                guardaUsuario = idField.getText();
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
                String idMedico = idField.getText();
                InterfazPrescripcion.ventanaPrescripcion(idMedico);
            } else if (usuario instanceof Farmaceutico) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelPrincipal);
                topFrame.dispose();
                InterfazDespacho.ventanaDespacho();
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
