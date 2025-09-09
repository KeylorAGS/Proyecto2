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
import java.awt.event.MouseAdapter;
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

                    // Crear prescripción a partir del medicamento seleccionado
                    Prescripcion p = new Prescripcion(m.getId(), m.getNombre(), m.getPresentacion());

                    try {
                        prescripcionController.create(p); // guarda y actualiza tabla automáticamente
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    prescripcionController.cerrarventanabuscarMedicamento();
                } else {
                    JOptionPane.showMessageDialog(panel, "Seleccione un medicamento.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Agregar funcionalidad de doble clic similar a buscar paciente
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic
                    int row = tabla.getSelectedRow();
                    if (row != -1) {
                        // Simular clic en OK
                        OK.doClick();
                    }
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
        }
        this.panel.revalidate();
    }
}