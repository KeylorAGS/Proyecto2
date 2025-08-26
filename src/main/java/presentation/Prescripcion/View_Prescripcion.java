package presentation.Prescripcion;

import javax.swing.*;
import javax.swing.text.View;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_Prescripcion implements PropertyChangeListener {
    private JPanel panel1;
    private JPanel panel;
    private JPanel control;
    private JButton buscarPaciente;
    private JButton agregarMedicamento;
    private JTextField fechaRetiroT;
    private JButton fechaRetiroB;
    private JLabel paciente;
    private JLabel fechaRetiro;
    private JTable listaMedicamentos;
    private JPanel ajustarPrescripcion;
    private JButton guardar;
    private JButton descartarMedicamentoButton;
    private JButton detallesButton;
    private JButton limpiarButton;



    public JPanel getPanelPrescripcion(){
        return panel1;
    }


    // MVC
    PrescripcionModel model;
    PrescripcionController controller;

    public void setModel(PrescripcionModel model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(PrescripcionController controller){
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
