package presentation.Interfaces;

import presentation.Acerca_De;
import presentation.Dashboard.Dashboard_View;
import presentation.Despacho.Controller;
import presentation.Despacho.Model;
import presentation.Despacho.View_despacho;
import presentation.Historico.Historico_View;
import presentation.Historico.HistoricosController;
import presentation.Historico.HistoricosModel;
import presentation.Logic.Service;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class InterfazDespacho {

    public static JFrame window;
    public static Controller Controller;
    public static HistoricosController historicosController;

    public static void ventanaDespacho() {

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

        Model model = new Model();
        View_despacho view = new View_despacho();
        Controller = new Controller(view,model);
        tabbedPane.addTab("Despacho",new ImageIcon(Objects.requireNonNull(InterfazDespacho.class.getResource("/Imagenes/Despacho.png"))), view.getPanel());

        /// --- Historico
        HistoricosModel historicosModel = new HistoricosModel();
        Historico_View historicoView = new Historico_View();
        historicosController = new HistoricosController(historicoView,historicosModel);
        tabbedPane.addTab("Historico", new ImageIcon(Objects.requireNonNull(InterfazDespacho.class.getResource("/Imagenes/Historico.png"))) , historicoView.getPanel());

        /// --- Dashboard
        presentation.Dashboard.DashboardModel dashboardModel = new presentation.Dashboard.DashboardModel();
        Dashboard_View dashboardView = new Dashboard_View();
        presentation.Dashboard.DashboardController dashboardController = new presentation.Dashboard.DashboardController(dashboardView, dashboardModel);
        tabbedPane.addTab("Dashboard", new ImageIcon(Objects.requireNonNull(InterfazDespacho.class.getResource("/Imagenes/Dashboard.png"))) , dashboardView.getPanel());

        //Acerca de
        Acerca_De acercaDe = new Acerca_De();
        tabbedPane.addTab("Acerca de...", new ImageIcon(Objects.requireNonNull(InterfazDespacho.class.getResource("/Imagenes/Receta.png"))), acercaDe.getPanel());

        // Ventana principal
        window.setSize(1300, 500);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Recetas");
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

}
