package presentation.Prescripcion;

import presentation.Pacientes.PacientesTableModel;
import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Prescripcion.PrescripcionController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_buscarPaciente implements PropertyChangeListener {
    private JPanel panel;
    private JTable table;
    private JTextField filtrarTexto;
    private JComboBox filtrarOpciones;
    private JButton OK;
    private JButton cancelar;
    private JLabel filtrar;

    public View_buscarPaciente() {
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerPr.cerrarventanabuscarPaciente();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    PacientesModel model;
    PacientesController controller;
    PrescripcionController controllerPr;

    public void setControllerPr(PrescripcionController controllerPr) {
        this.controllerPr = controllerPr;
    }

    public void setModel(PacientesModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }
    public void setController(PacientesController controller){
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int[] cols={PacientesTableModel.ID, PacientesTableModel.NOMBRE, PacientesTableModel.FECHA_NACIMIENTO, PacientesTableModel.TELEFONO};
        table.setModel(new PacientesTableModel(cols,model.getList()));
        this.panel.revalidate();
    }

}
