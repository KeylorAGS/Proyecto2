package presentation.Interfaces;

import presentation.Acerca_De;
import presentation.Logic.Service;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InterfazAdministrador {
    public static JFrame window;
    public static presentation.Medicos.MedicosController medicosController;
    public static presentation.Farmaceuticos.FarmaceuticosController farmaceuticosController;
    public static presentation.Pacientes.PacientesController pacientesController;

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;
    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

    public static void ventanaMedicos() {
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

        // --- Médicos
        presentation.Medicos.MedicosModel medicosModel = new presentation.Medicos.MedicosModel();
        presentation.Medicos.Medicos_View medicosView = new presentation.Medicos.Medicos_View();
        medicosController = new presentation.Medicos.MedicosController(medicosView, medicosModel);
        tabbedPane.addTab("Medicos", null, medicosView.getPanel());

        // --- Farmacéuticos
        presentation.Farmaceuticos.FarmaceuticosModel farmaceuticosModel = new presentation.Farmaceuticos.FarmaceuticosModel();
        presentation.Farmaceuticos.Farmaceuticos_View farmaceuticosView = new presentation.Farmaceuticos.Farmaceuticos_View();
        farmaceuticosController = new presentation.Farmaceuticos.FarmaceuticosController(farmaceuticosView, farmaceuticosModel);
        tabbedPane.addTab("Farmaceuticos", null, farmaceuticosView.getPanel());

        // --- Pacientes
        presentation.Pacientes.PacientesModel pacientesModel = new presentation.Pacientes.PacientesModel();
        presentation.Pacientes.Pacientes_View pacientesView = new presentation.Pacientes.Pacientes_View();
        pacientesController = new presentation.Pacientes.PacientesController(pacientesView, pacientesModel);
        tabbedPane.addTab("Pacientes", null, pacientesView.getPanel());

        // --- Medicamentos
        presentation.Medicamentos.Model medicamentosModel = new presentation.Medicamentos.Model();
        presentation.Medicamentos.Medicamentos_View medicamentosView = new presentation.Medicamentos.Medicamentos_View();
        presentation.Medicamentos.Controller medicamentosController = new presentation.Medicamentos.Controller(medicamentosView, medicamentosModel);
        tabbedPane.addTab("Medicamentos", null, medicamentosView.getPanel());

        //Acerca de
        Acerca_De acercaDe = new Acerca_De();
        tabbedPane.addTab("Acerca de...", null, acercaDe.getPanel());

        // Ventana principal
        window.setSize(900, 450);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Hospital");
        window.setVisible(true);
    }
}
