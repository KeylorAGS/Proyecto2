package presentation.Prescripcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View_modificarMedicamento {
    private JButton guardar;
    private JSpinner cantidadS;
    private JSpinner spinner2;
    private JButton cancelar;
    private JTextField indicacionesT;
    private JLabel cantidad;
    private JLabel duracion;
    private JLabel indicaciones;
    private JPanel panel;


    public View_modificarMedicamento() {
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cerrarventanaModificarMedicamento();
            }
        });
    }
    public JPanel getPanel() {
        return panel;
    }

    PrescripcionController controller;
    PrescripcionModel model;

    public void setController(PrescripcionController controller) {
        this.controller = controller;
    }

    public void setModel(PrescripcionModel model) {
        this.model = model;
    }
}
