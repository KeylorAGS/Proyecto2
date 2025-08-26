package presentation.Loggin;

import presentation.Logic.Farmaceutico;
import presentation.Logic.Medico;
import presentation.Logic.Usuario;

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

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelPrincipal);
                topFrame.dispose();

                JFrame frame = new JFrame("Cambiar Clave");
                frame.setSize(600,300);
                frame.setContentPane(new View_CambiarClave().getPanelCambiarClave());
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
                String id = "";
                try {
                   idField.getText();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingresar un numero de cedula");
                }
                String clave = new String(claveField.getPassword());
                controller.login(id,clave);
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
                System.out.println("Se abre la ventana de medico");
            } else if (usuario instanceof Farmaceutico) {
                System.out.println("Se abre la ventana de farmacéutico");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró al usuario");
            }
        }
    }
}
