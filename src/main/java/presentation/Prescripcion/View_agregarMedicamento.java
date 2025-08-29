package presentation.Prescripcion;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_agregarMedicamento implements PropertyChangeListener {
    private JPanel panel;
    private JLabel Filtrar;
    private JComboBox FiltrarB;
    private JTextField FiltrarT;
    private JScrollPane Scroll;
    private JTable listaMedicamentos;
    private JButton cancelar;
    private JButton ok;
    private JPanel panel1;

    public JPanel getPanelAgregarMedicamento() { return panel1; }

    PrescripcionModel model;
    PrescripcionController controller;

    public void setModel(PrescripcionModel model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }
    public void setController(PrescripcionController controller){
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
