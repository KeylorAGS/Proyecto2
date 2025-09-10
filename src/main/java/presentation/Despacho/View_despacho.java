package presentation.Despacho;

import presentation.Logic.Receta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_despacho implements PropertyChangeListener {
    private JLabel searchIdLbl;
    private JTextField searchId;
    private JButton clear;
    private JButton search;
    private JTable tabla;
    private JButton verMedicamentosButton;
    private JPanel panel;
    private JButton entregarButton;
    private JButton avanzarEstadoButton;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    Model model;

    public View_despacho() {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        verMedicamentosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame window = new JFrame();
                View_VerMedicamentos view = new View_VerMedicamentos();
                window.setSize(600, 350);
                window.setResizable(false);
                window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // mejor que EXIT_ON_CLOSE si no quieres cerrar toda la app
                window.setTitle("Recetas");
                window.setLocationRelativeTo(null);
                window.add(view.getPanel());
                window.setVisible(true);
            }
        });

        avanzarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    Receta seleccionado = model.getList().get(fila);
                    try {
                        controller.modificarEstado(seleccionado);
                    } catch (Exception ex) {
                    }
                }
            }
        });
        entregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    Receta seleccionado = model.getList().get(fila);
                    try {
                        controller.delete(seleccionado);
                    } catch (Exception ex) {
                    }
                }
            }
        });
    }

    @Override //Metodo por el cual pasa cualquier cambio en la vista y sera el encargado de mostrar en pantalla lo que se necesite
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {DespachoTableModel.NOMBRE,DespachoTableModel.ESTADO, DespachoTableModel.IDPACIENTE, DespachoTableModel.IDDOCTOR};
                tabla.setModel(new DespachoTableModel(cols,model.getList()));
                tabla.setRowHeight(30);
                break;
        }
    }


    public Component getPanel() {
        return panel;
    }
}
