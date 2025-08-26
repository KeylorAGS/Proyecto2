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

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e){};

        window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();
        window.setContentPane(tabbedPane);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.instance().stop();
            }
        });

        presentation.Medicos.MedicosModel medicosModel = new presentation.Medicos.MedicosModel();
        presentation.Medicos.Medicos_View medicosView = new presentation.Medicos.Medicos_View();
        medicosController = new presentation.Medicos.MedicosController(medicosView, medicosModel);
        Icon medicosIcon = new ImageIcon(Main.class.getResource(""));
        tabbedPane.addTab("Medicos", medicosIcon, medicosView.getPanel());

//        View_Login view =  new View_Login();
//        LoginModel model = new LoginModel();
//        LoginController controller = new LoginController(model,view);
//
//        view.setModel(model);
//        view.setController(controller);
//
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600,300);
//        frame.setTitle("Clinica");
//        frame.setContentPane(view.getPanelLogin());
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);

        // HOLA

        window.setSize(900, 450);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Main.class.getResource(""))).getImage());
        window.setTitle("Hospital");
        window.setVisible(true);
    }

    public static JFrame window;
    public static presentation.Medicos.MedicosController medicosController;

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;
    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

}