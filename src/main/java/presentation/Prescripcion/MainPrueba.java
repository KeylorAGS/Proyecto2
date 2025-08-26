package presentation.Prescripcion;

import presentation.Loggin.LoginController;
import presentation.Loggin.LoginModel;
import presentation.Loggin.View_Login;
import presentation.Prescripcion.PrescripcionController;
import presentation.Prescripcion.PrescripcionModel;
import presentation.Prescripcion.View_Prescripcion;

import javax.swing.*;

public class MainPrueba {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e){};

        View_Prescripcion view =  new View_Prescripcion();
        PrescripcionModel model = new PrescripcionModel();
        PrescripcionController controller = new PrescripcionController(view, model);

        view.setModel(model);
        view.setController(controller);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,300);
        frame.setTitle("Prescripcion");
        frame.setContentPane(view.getPanelPrescripcion());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;
}
