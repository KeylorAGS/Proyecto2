package presentation.Prescripcion;

import presentation.Logic.Medicamento;
import presentation.Logic.Paciente;
import presentation.Logic.Prescripcion;
import presentation.Medicamentos.MedicamentosController;
import presentation.Medicamentos.MedicamentosModel;
import presentation.Medicamentos.MedicamentosTableModel;
import presentation.Recetas.RecetasTableModel;

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

        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabla.getSelectedRow();
                if (row != -1) {
                    Medicamento m = medicamentosModel.getList().get(row);
                    Prescripcion p = prescripcionModel.getList().get(row);
                    medicamentosController.seleccionarMedicamento(m);
                    prescripcionController.seleccionarPrescripcion(p);
                    prescripcionController.cerrarventanabuscarMedicamento();

                } else {
                    JOptionPane.showMessageDialog(panel, "Seleccione un paciente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    MedicamentosModel medicamentosModel;
    PrescripcionModel prescripcionModel;
    MedicamentosController medicamentosController;
    PrescripcionController prescripcionController;

    public void setModel(MedicamentosModel medicamentosModel){
        this.medicamentosModel = medicamentosModel;
        medicamentosModel.addPropertyChangeListener(this);
    }

    public void setController(MedicamentosController medicamentosController){
        this.medicamentosController = medicamentosController;
    }

    public String getAuxNombre() {
        return auxNombre;
    }

    public String getAuxPresentacion() {
        return auxPresentacion;
    }

    public void setAuxNombre(String auxNombre) {
        this.auxNombre = auxNombre;
    }

    private String auxNombre;

    public void setAuxPresentacion(String auxPresentacion) {
        this.auxPresentacion = auxPresentacion;
    }

    private String auxPresentacion;

    public void setControllerPr(PrescripcionController controllerPr) {
        this.prescripcionController = controllerPr;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case MedicamentosModel.LIST:
                int[] cols = {MedicamentosTableModel.ID, MedicamentosTableModel.NOMBRE, MedicamentosTableModel.PRESENTACION};
                tabla.setModel(new MedicamentosTableModel(cols, medicamentosModel.getList()));
                tabla.setRowHeight(30);
                TableColumnModel columnModel = tabla.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(100);
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(2).setPreferredWidth(120);
                break;
            // Cuando cambia el paciente seleccionado
            case MedicamentosModel.CURRENT:
                if (medicamentosModel.getCurrentMedicamento() != null) {
                    setAuxNombre(medicamentosModel.getCurrentMedicamento().getNombre());
                    setAuxPresentacion(medicamentosModel.getCurrentMedicamento().getPresentacion());
                } else {
                    auxNombre = "N/A";
                    auxPresentacion = "N/A";
                }
                break;
        }

        this.panel.revalidate();
    }
}
