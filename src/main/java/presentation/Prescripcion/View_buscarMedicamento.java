package presentation.Prescripcion;

import presentation.Medicamentos.MedicamentosController;
import presentation.Medicamentos.MedicamentosModel;
import presentation.Medicamentos.MedicamentosTableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_buscarMedicamento implements PropertyChangeListener {
    private JPanel panel;
    private JTable tabla;
    private JLabel filtrar;
    private JComboBox filtrarOpciones;
    private JTextField filtrarTexto;
    private JButton buscar;
    private JButton OK;
    private JButton cancelar;

    public JPanel getPanel() {
        return panel;
    }

    MedicamentosModel medicamentosModel;
    MedicamentosController medicamentosController;

    public void setModel(MedicamentosModel medicamentosModel){
        this.medicamentosModel = medicamentosModel;
        medicamentosModel.addPropertyChangeListener(this);
    }

    public void setController(MedicamentosController medicamentosController){
        this.medicamentosController = medicamentosController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (MedicamentosModel.LIST.equals(evt.getPropertyName())) {
            int[] cols = {MedicamentosTableModel.ID, MedicamentosTableModel.NOMBRE, MedicamentosTableModel.PRESENTACION};
            tabla.setModel(new MedicamentosTableModel(cols, medicamentosModel.getList()));
            tabla.setRowHeight(30);
            TableColumnModel columnModel = tabla.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(100);
            columnModel.getColumn(1).setPreferredWidth(150);
            columnModel.getColumn(2).setPreferredWidth(120);
        }
        this.panel.revalidate();
    }
}
