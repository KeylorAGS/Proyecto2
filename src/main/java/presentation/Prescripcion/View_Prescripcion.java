package presentation.Prescripcion;

import com.github.lgooddatepicker.components.DatePicker;
import presentation.Logic.Medicamento;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.PacientesTableModel;
import presentation.Recetas.RecetasModel;
import presentation.Recetas.RecetasTableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_Prescripcion implements PropertyChangeListener {
    private JPanel panel;
    private JButton buscarPaciente;
    private JButton agregarMedicamento;
    private JTable table;
    private JLabel FechaRetiro;
    private DatePicker elegirFecha;
    private JLabel verPaciente;
    private JButton guardar;
    private JButton detalles;
    private JButton descartarMedicamento;
    private JButton limpiar;
    private String IdPaciente;
    private Medicamento medicamento;

    public View_Prescripcion() {
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buscarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ventanaBuscarPaciente();
            }
        });
        agregarMedicamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ventanaBuscarMedicamento();
            }
        });
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    PrescripcionController controller;
    PrescripcionModel model;
    RecetasModel recetasModel;

    public void setController(PrescripcionController controller) {
        this.controller = controller;
    }

    public void setModel(PrescripcionModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setRecetasModel(RecetasModel recetasModel) {
        this.recetasModel = recetasModel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PrescripcionModel.LIST:
                int[] cols={RecetasTableModel.NOMBRE, RecetasTableModel.PRESENTACION, RecetasTableModel.CANTIDAD, RecetasTableModel.INDICACIONES, RecetasTableModel.DURACION};
                table.setModel(new RecetasTableModel(cols, recetasModel.getList()));
                table.setRowHeight(30);
                TableColumnModel columnModel = table.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(2).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                columnModel.getColumn(4).setPreferredWidth(150);
                break;
            case PrescripcionModel.CURRENT:
                // Aquí puedes actualizar campos de tu UI si los tienes
                // Por ejemplo:
                // nombreField.setText(model.getCurrent().getNombre());
                break;

            // Cuando cambia el paciente seleccionado
            case PrescripcionModel.PACIENTE:
                if (model.getCurrentPaciente() != null) {
                    verPaciente.setText(model.getCurrentPaciente().getNombre());
                    IdPaciente = model.getCurrentPaciente().getId();
                } else {
                    IdPaciente = "N/A";
                    verPaciente.setText("");
                }
                break;
        }
        this.panel.revalidate();
    }


}
