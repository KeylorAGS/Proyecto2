package presentation.Prescripcion;

import presentation.Logic.Medicamento;
import presentation.Logic.Paciente;
import presentation.Medicamentos.MedicamentosController;
import presentation.Medicamentos.MedicamentosModel;
import presentation.Medicamentos.MedicamentosTableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public View_buscarMedicamento() {
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = filtrarOpciones.getSelectedIndex();
                try {
                    Medicamento filter = new Medicamento();
                    switch (selectedIndex) {
                        case 0: filter.setId(filtrarTexto.getText()); break;
                        case 1: filter.setNombre(filtrarTexto.getText()); break;
                        case 2: filter.setPresentacion(filtrarTexto.getText()); break;
                    }
                    medicamentosController.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prescripcionController.cerrarventanabuscarMedicamento();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    MedicamentosModel medicamentosModel;
    MedicamentosController medicamentosController;
    PrescripcionController prescripcionController;

    public void setModel(MedicamentosModel medicamentosModel){
        this.medicamentosModel = medicamentosModel;
        medicamentosModel.addPropertyChangeListener(this);
    }

    public void setController(MedicamentosController medicamentosController){
        this.medicamentosController = medicamentosController;
    }

    public void setControllerPr(PrescripcionController controllerPr) {
        this.prescripcionController = controllerPr;
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
