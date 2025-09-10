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
        descartarMedicamento.addActionListener(new ActionListener() {
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
        detalles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ventanaModificarMedicamento();
            }
        });
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(getDoctorIngresado());
                Receta receta = new Receta();
                List<Prescripcion> lista = model.getList();
                for (Prescripcion objeto : lista) {
                    System.out.println(objeto);
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
                try {
                    controller.createReceta(receta);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
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
    }

    public void setModel(PrescripcionModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
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
                        verPaciente.setText("");
                    }
                    break;
        }
        this.panel.revalidate();
    }
}