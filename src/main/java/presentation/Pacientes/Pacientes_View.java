package presentation.Pacientes;

import presentation.Logic.Paciente;
import presentation.Medicos.MedicosController;
import presentation.Medicos.MedicosModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Pacientes_View implements PropertyChangeListener {
    private JPanel panel;
    private JLabel searchNombreLbl;
    private JTextField searchNombre;
    private JButton report;
    private JButton search;
    private JTable list;
    private JLabel idLbl;
    private JTextField IdJtext;
    private JLabel EspecialidadLbl;
    private JTextField EspecialidadJtext;
    private JLabel NombreLbl;
    private JTextField NombreJtext;
    private JButton save;
    private JButton delete;
    private JButton clear;

    public JPanel getPanel() {
        return panel;
    }

    public Pacientes_View() {
        // Aqui vam todos los listeners de los botones y acciones
    }

    // MVC
    PacientesModel model;
    PacientesController controller;

    public void setModel(PacientesModel model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(PacientesController controller){
        this.controller = controller;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
