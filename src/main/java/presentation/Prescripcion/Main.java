package presentation.Prescripcion;

import presentation.Prescripcion.PrescripcionController;
import presentation.Prescripcion.PrescripcionModel;
import presentation.Prescripcion.View_Prescripcion;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        View_Prescripcion view = new View_Prescripcion();
        PrescripcionModel model = new PrescripcionModel();
        PrescripcionController controller = new PrescripcionController(view, model);

        JFrame window = new JFrame();
        window.setSize(600,400);
        window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Personas");
        window.setContentPane(view.getPanel());
        window.setVisible(true);
    }

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);
}
