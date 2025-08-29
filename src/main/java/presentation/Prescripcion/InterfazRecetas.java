package presentation.Prescripcion;

import presentation.Logic.Service;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InterfazRecetas {
    public static JFrame window;
    public static presentation.Prescripcion.PrescripcionController prescripcionController;

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;
    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

    public static void ventanaPrescripcion() {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e){}

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

        // --- Prescripcion
        presentation.Prescripcion.PrescripcionModel prescripcionModel = new presentation.Prescripcion.PrescripcionModel();
        presentation.Prescripcion.View_Prescripcion prescripcionView = new  presentation.Prescripcion.View_Prescripcion();
        prescripcionController = new presentation.Prescripcion.PrescripcionController(prescripcionView, prescripcionModel);
        tabbedPane.addTab("Prescribir", null, prescripcionView.getPanelPrescripcion());

        // Ventana principal
        window.setSize(900, 450);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Recetas");
        window.setVisible(true);
    }
}
