package presentation.Interfaces;

import presentation.Acerca_De;
import presentation.Dashboard.Dashboard_View;
import presentation.Logic.Service;
import presentation.Medicamentos.MedicamentosController;
import presentation.Medicamentos.MedicamentosModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class InterfazAdministrador {
    public static JFrame window;
    public static presentation.Medicos.MedicosController medicosController;
    public static presentation.Farmaceuticos.FarmaceuticosController farmaceuticosController;
    public static presentation.Pacientes.PacientesController pacientesController;
    public static   presentation.Historico.HistoricosController HistoricosController;

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;
    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

    public static void ventanaMedicos(String idAdmin) {
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
        tabbedPane.addTab("Medicos", new ImageIcon(Objects.requireNonNull(InterfazAdministrador.class.getResource("/Imagenes/Medicos.png"))), medicosView.getPanel());

        // --- Farmacéuticos
        presentation.Farmaceuticos.FarmaceuticosModel farmaceuticosModel = new presentation.Farmaceuticos.FarmaceuticosModel();
        presentation.Farmaceuticos.Farmaceuticos_View farmaceuticosView = new presentation.Farmaceuticos.Farmaceuticos_View();
        farmaceuticosController = new presentation.Farmaceuticos.FarmaceuticosController(farmaceuticosView, farmaceuticosModel);
        tabbedPane.addTab("Farmaceuticos",new ImageIcon(Objects.requireNonNull(InterfazAdministrador.class.getResource("/Imagenes/Farmaceuticos.png"))), farmaceuticosView.getPanel());

        // --- Pacientes
        presentation.Pacientes.PacientesModel pacientesModel = new presentation.Pacientes.PacientesModel();
        presentation.Pacientes.Pacientes_View pacientesView = new presentation.Pacientes.Pacientes_View();
        pacientesController = new presentation.Pacientes.PacientesController(pacientesView, pacientesModel);
        tabbedPane.addTab("Pacientes", new ImageIcon(Objects.requireNonNull(InterfazAdministrador.class.getResource("/Imagenes/Pacientes.png"))), pacientesView.getPanel());

        // --- Medicamentos
        MedicamentosModel medicamentosModel = new MedicamentosModel();
        presentation.Medicamentos.Medicamentos_View medicamentosView = new presentation.Medicamentos.Medicamentos_View();
        MedicamentosController medicamentosController = new MedicamentosController(medicamentosView, medicamentosModel);
        tabbedPane.addTab("Medicamentos", new ImageIcon(Objects.requireNonNull(InterfazAdministrador.class.getResource("/Imagenes/Medicamentos.png"))), medicamentosView.getPanel());

        /// --- Dashboard
        presentation.Dashboard.DashboardModel dashboardModel = new presentation.Dashboard.DashboardModel();
        Dashboard_View dashboardView = new Dashboard_View();
        presentation.Dashboard.DashboardController dashboardController = new presentation.Dashboard.DashboardController(dashboardView, dashboardModel);
        tabbedPane.addTab("Dashboard", new ImageIcon(Objects.requireNonNull(InterfazAdministrador.class.getResource("/Imagenes/Dashboard.png"))) , dashboardView.getPanel());

        /// --- Historico
        presentation.Historico.HistoricosModel historicosModel = new  presentation.Historico.HistoricosModel();
        presentation.Historico.Historico_View historicoView = new presentation.Historico.Historico_View();
        HistoricosController = new presentation.Historico.HistoricosController(historicoView,historicosModel);
        tabbedPane.addTab("Historico", new ImageIcon(Objects.requireNonNull(InterfazAdministrador.class.getResource("/Imagenes/Historico.png"))) , historicoView.getPanel());

        //Acerca de
        Acerca_De acercaDe = new Acerca_De();
        tabbedPane.addTab("Acerca de...", new ImageIcon(Objects.requireNonNull(InterfazAdministrador.class.getResource("/Imagenes/Receta.png"))) , acercaDe.getPanel());

        // Ventana principal
        window.setSize(1300, 500);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Recetas - Administrador - " + idAdmin + " (ADM)" );
        Image icon = Toolkit.getDefaultToolkit().getImage(
                InterfazAdministrador.class.getResource("/Imagenes/Receta.png")
        );
        window.setIconImage(icon);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
