package presentation.Prescripcion;

import presentation.Prescripcion.Main;
import presentation.Logic.Medicamento;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_Prescripcion implements PropertyChangeListener {
    private JTable table1;
    private JButton Guardar;
    private JPanel panel;

    public View_Prescripcion() {
        Guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medicamento n = take();
                try {
                    controller.create(n);
                    JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        model.addPropertyChangeListener(this);
    }

    public Medicamento take() {
        Medicamento e = new Medicamento();
        e.setId("123");
        e.setNombre("coca");
        e.setPresentacion("500mg");
        return e;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PrescripcionModel.LIST:
                int[] cols = {PrescripcionTableModel.ID,PrescripcionTableModel.NOMBRE, PrescripcionTableModel.PRESENTACION};
                table1.setModel(new PrescripcionTableModel(cols,model.getList()));
                break;
        }
        this.panel.revalidate();
    }
}
