package presentation.Interfaces;

import presentation.Acerca_De;
import presentation.Despacho.Controller;
import presentation.Despacho.Model;
import presentation.Despacho.View_despacho;
import presentation.Logic.Service;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class InterfazDespacho {

    public static JFrame window;
    public static Controller Controller;

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

        //Acerca de
        Acerca_De acercaDe = new Acerca_De();
        tabbedPane.addTab("Acerca de...", new ImageIcon(Objects.requireNonNull(InterfazDespacho.class.getResource("/Imagenes/Receta.png"))), acercaDe.getPanel());

        // Ventana principal
        window.setSize(900, 450);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Recetas");
        window.setVisible(true);
    }

}
