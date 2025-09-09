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
import java.awt.event.MouseAdapter;
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
                // Implementar lógica para guardar la receta completa
                if (model.getCurrentPaciente() == null) {
                    JOptionPane.showMessageDialog(panel, "Debe seleccionar un paciente.",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (model.getList().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Debe agregar al menos un medicamento.",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    controller.crearReceta();
                    JOptionPane.showMessageDialog(panel, "Receta guardada exitosamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error al guardar la receta: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        descartarMedicamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int confirm = JOptionPane.showConfirmDialog(panel,
                            "¿Está seguro de que desea eliminar este medicamento?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        controller.eliminarMedicamento(row);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Seleccione un medicamento para eliminar.",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(panel,
                        "¿Está seguro de que desea limpiar toda la prescripción?",
                        "Confirmar limpieza",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.limpiarPrescripciones();
                    controller.clear();
                }
            }
        });

        // Agregar listener para doble clic en la tabla (opcional)
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        // Mostrar detalles del medicamento seleccionado
                        // Puedes implementar una ventana de detalles aquí
                    }
                }
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
                int[] cols = {PrescripcionTableModel.ID, PrescripcionTableModel.NOMBRE, PrescripcionTableModel.PRESENTACION};
                table.setModel(new PrescripcionTableModel(cols, model.getList()));
                table.repaint();
                table.setRowHeight(30);
                TableColumnModel columnModel = table.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(2).setPreferredWidth(150);

                // Actualizar estado de botones según si hay medicamentos
                boolean hayMedicamentos = !model.getList().isEmpty();
                guardar.setEnabled(hayMedicamentos && model.getCurrentPaciente() != null);
                descartarMedicamento.setEnabled(hayMedicamentos);
                break;

            case PrescripcionModel.CURRENT:
                // Actualizar campos de la UI si es necesario
                break;

            case PrescripcionModel.PACIENTE:
                if (model.getCurrentPaciente() != null) {
                    verPaciente.setText(model.getCurrentPaciente().getNombre());
                    IdPaciente = model.getCurrentPaciente().getId();
                } else {
                    IdPaciente = "N/A";
                    verPaciente.setText("Seleccionar paciente...");
                }

                // Actualizar estado del botón guardar
                hayMedicamentos = !model.getList().isEmpty();
                boolean hayPaciente = (model.getCurrentPaciente() != null);
                guardar.setEnabled(hayMedicamentos && hayPaciente);
                break;
        }
        this.panel.revalidate();
    }
}