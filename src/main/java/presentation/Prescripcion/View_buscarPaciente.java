package presentation.Prescripcion;

import presentation.Logic.Paciente;
import presentation.Pacientes.PacientesTableModel;
import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;

import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
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
    private JButton buscar;

    public View_buscarPaciente() {
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerPr.cerrarventanabuscarPaciente();
            }
        });

        OK.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                Paciente p = model.getList().get(row);
                controllerPr.seleccionarPaciente(p);
                controllerPr.cerrarventanabuscarPaciente();
            } else {
                JOptionPane.showMessageDialog(panel, "Seleccione un paciente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        table.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                controller.edit(row);
            }
        }));
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = filtrarOpciones.getSelectedIndex();
                try {
                    Paciente filter = new Paciente();
                    if (selectedIndex == 1) {
                        filter.setNombre(filtrarTexto.getText());
                        controller.search(filter);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
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
        switch (evt.getPropertyName()) {
            case PacientesModel.LIST:
                int[] cols = {PacientesTableModel.ID, PacientesTableModel.NOMBRE, PacientesTableModel.FECHA_NACIMIENTO, PacientesTableModel.TELEFONO};
                table.setModel(new PacientesTableModel(cols, model.getList()));
                table.setRowHeight(30);
                TableColumnModel columnModel = table.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(2).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case PacientesModel.FILTER:
                filtrarTexto.setText(model.getFilter().getNombre());
                break;
        }
        this.panel.revalidate();
    }

}
