package presentation;

import presentation.Loggin.LoginController;
import presentation.Loggin.LoginModel;
import presentation.Loggin.View_Login;
import presentation.Logic.Service;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main { //Puto
    public static void main(String[] args) {
        try {
            // Look and Feel más moderno
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {}

        // Crear MVC del Login
        View_Login view = new View_Login();
        LoginModel model = new LoginModel();
        LoginController controller = new LoginController(model, view);

        view.setModel(model);
        view.setController(controller);

        // Crear la ventana principal del login
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setTitle("Clinica - Login");
        frame.setContentPane(view.getPanelLogin());
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true);
    }

}