package presentation.Prescripcion;

import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.Pacientes_View;

import javax.swing.*;
import java.awt.*;

public class MainPrueba {
    public static void main(String[] args) {
        try {UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        View_Prescripcion view = new View_Prescripcion();
        PrescripcionModel model = new PrescripcionModel();
        PrescripcionController controller = new PrescripcionController(view, model);

        Pacientes_View view2 = new Pacientes_View();
        PacientesModel model2 = new PacientesModel();
        PacientesController controller2 = new PacientesController(view2, model2);
        View_buscarPaciente view3 = new View_buscarPaciente();

        view3.setModel(model2);
        view3.setController(controller2);

        JFrame window = new JFrame();
        window.setSize(600,400);
        window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Pruebas");
        window.setContentPane(view3.getPanel());
        window.setVisible(true);
    }

    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);
}