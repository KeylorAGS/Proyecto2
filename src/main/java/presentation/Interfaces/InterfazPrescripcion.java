package presentation.Interfaces;

import presentation.Acerca_De;
import presentation.Logic.Service;
import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.Pacientes_View;
import presentation.Prescripcion.PrescripcionController;
import presentation.Prescripcion.PrescripcionModel;
import presentation.Prescripcion.View_Prescripcion;
import presentation.Prescripcion.View_buscarPaciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InterfazPrescripcion {
    public static JFrame window;
    public static PrescripcionController prescripcionController;
    public static PacientesController pacientesController;

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;
    public static final Color BACKGROUND_ERROR = new Color(255, 102, 102);

    public static void ventanaPrescripcion() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {}

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

        // --- Prescripción
        PrescripcionModel prescripcionModel = new PrescripcionModel();
        View_Prescripcion prescripcionView = new View_Prescripcion();
        prescripcionController = new PrescripcionController(prescripcionView, prescripcionModel);
        tabbedPane.addTab("Prescripción", null, prescripcionView.getPanel());

        //Acerca de
        Acerca_De acercaDe = new Acerca_De();
        tabbedPane.addTab("Acerca de...", null, acercaDe.getPanel());

        // Ventana principal
        window.setSize(900, 450);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Gestión de Prescripción");
        window.setVisible(true);
    }
}
