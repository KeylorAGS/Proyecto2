package presentation;

import presentation.Loggin.LoginController;
import presentation.Loggin.LoginModel;
import presentation.Loggin.View_Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e){};

        View_Login view =  new View_Login();
        LoginModel model = new LoginModel();
        LoginController controller = new LoginController(model,view);

        view.setModel(model);
        view.setController(controller);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,300);
        frame.setTitle("Clinica");
        frame.setContentPane(view.getPanelLogin());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        // HOLA

    }

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;
}