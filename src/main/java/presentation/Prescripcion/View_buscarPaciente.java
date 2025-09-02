package presentation.Prescripcion;

import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.PacientesTableModel;

import javax.swing.*;
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

    public JPanel getPanel() {
        return panel;
    }

    PacientesModel model;
    PacientesController controller;

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
