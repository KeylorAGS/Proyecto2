package presentation.Prescripcion;

import com.github.lgooddatepicker.components.DatePicker;
import presentation.Interfaces.InterfazAdministrador;
import presentation.Logic.Prescripcion;
import presentation.Logic.Receta;
import presentation.Logic.Service;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Random;

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
    private String doctorIngresado;

    public String getDoctorIngresado() {
        return doctorIngresado;
    }

    public void setDoctorIngresado(String doctorIngresado) {
        this.doctorIngresado = doctorIngresado;
    }

    // Subventanas (ahora cada una gestiona su propio dialogo)
    private View_buscarPaciente buscarPacienteView;
    private View_buscarMedicamento buscarMedicamentoView;
    private View_modificarMedicamento modificarMedicamentoView;

    public View_Prescripcion() {
        // Instanciar las subventanas (ellas mismas configuran su JDialog en su constructor)
        buscarPacienteView = new View_buscarPaciente();
        buscarMedicamentoView = new View_buscarMedicamento();
        modificarMedicamentoView = new View_modificarMedicamento();

        // Botones principales: sólo muestran las subventanas
        buscarPaciente.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPacienteView.setVisible(true);
            }
        });

        agregarMedicamento.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarMedicamentoView.setVisible(true);
            }
        });

        descartarMedicamento.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.delete();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        table.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                controller.edit(row);
            }
        }));

        detalles.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarMedicamentoView.setVisible(true);
            }
        });

        guardar.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Receta receta = new Receta();
                List<Prescripcion> lista = model.getList();
                for (Prescripcion objeto : lista) {
                    receta.getPrescripcions().add(objeto);
                }
                String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                Random random = new Random();
                StringBuilder sb = new StringBuilder();

                // Crear un código de 5 caracteres
                for (int i = 0; i < 5; i++) {
                    int index = random.nextInt(caracteres.length());
                    sb.append(caracteres.charAt(index));
                }
                receta.setIdReceta(sb.toString());
                receta.setEstado("Confeccionada");
                receta.setIdDoctor(getDoctorIngresado());
                receta.setIdPaciente(model.getCurrentPaciente().getId());
                receta.setFecha(elegirFecha.getText());
                try {
                    controller.createReceta(receta);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elegirFecha.clear();   // limpia el DatePicker
                verPaciente.setText("Paciente");  // restaura el label del paciente
                model.setList(new java.util.ArrayList<>()); // limpia la tabla
                model.setCurrentPaciente(null);
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    PrescripcionController controller;
    PrescripcionModel model;

    public void setController(PrescripcionController controller) {
        this.controller = controller;

        buscarPacienteView.setModel(controller.getPacientesModel());
        buscarPacienteView.setController(controller.getPacientesController());
        buscarPacienteView.setControllerPr(controller);

        // Subventana buscarMedicamento necesita el MedicamentosModel y MedicamentosController
        buscarMedicamentoView.setModel(controller.getMedicamentosModel());
        buscarMedicamentoView.setController(controller.getMedicamentosController());
        buscarMedicamentoView.setControllerPr(controller);

        // Subventana modificarMedicamento necesita el PrescripcionController (para setCambios/aplicar)
        modificarMedicamentoView.setController(controller);
        // El model de prescripción se asignará en setModel(...) que se llama inmediatamente después en el controller
    }

    public void setModel(PrescripcionModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        // Pasar el modelo de prescripción a la vista de modificar medicamento
        modificarMedicamentoView.setModel(model);

        // (Nota: los modelos de Pacientes/Medicamentos fueron creados en el controller y se asignaron en setController)
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case PrescripcionModel.LIST:
                int[] cols={PrescripcionTableModel.NOMBRE, PrescripcionTableModel.PRESENTACION, PrescripcionTableModel.CANTIDAD, PrescripcionTableModel.INDICACIONES, PrescripcionTableModel.DURACION};
                table.setModel(new PrescripcionTableModel(cols, model.getList()));
                table.setRowHeight(30);
                TableColumnModel columnModel = table.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(2).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                columnModel.getColumn(4).setPreferredWidth(150);
                break;
            case PrescripcionModel.PACIENTE:
                if (model.getCurrentPaciente() != null) {
                    verPaciente.setText(model.getCurrentPaciente().getNombre());
                } else {
                    verPaciente.setText("Paciente");
                }
                break;
        }
        this.panel.revalidate();
    }
}
