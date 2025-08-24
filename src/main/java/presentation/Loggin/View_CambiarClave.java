package presentation.Loggin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View_CambiarClave {
    private JButton botonCancelar;
    private JButton botonAceptarCambio;
    private JPasswordField claveActualField;
    private JPasswordField claveNuevaField;
    private JPasswordField claveNuevaFieldConfirmacion;
    private JPanel panelCambiarCleve;
    private JLabel labelClaveActual;
    private JLabel labelClaveNueva;
    private JLabel labelClaveActual_Confirmacion;


    public View_CambiarClave() {

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                claveActualField.setText("");
                claveNuevaField.setText("");
                claveNuevaFieldConfirmacion.setText("");
            }
        });

        botonAceptarCambio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getPanelCambiarClave(){
        return panelCambiarCleve;
    }
}
