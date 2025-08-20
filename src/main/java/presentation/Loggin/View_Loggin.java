package presentation.Loggin;

import presentation.Logic.View_CambiarClave;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View_Loggin extends JFrame{
    private JPanel panelPrincipal;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton button1;
    private JButton botonInicioSesion;
    private JButton button3;


    public View_Loggin() {
        button1.addActionListener(new ActionListener() {
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
    }

    public JPanel getPanelLoggin(){
        return panelPrincipal;
    }
}
