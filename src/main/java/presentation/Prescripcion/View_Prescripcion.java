package presentation.Prescripcion;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    public View_Prescripcion() {
        buscarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View_buscarPaciente view = new View_buscarPaciente();

                JFrame ventana = new JFrame("Buscar Paciente");
                ventana.setContentPane(view.getPanelBuscarPaciente());
                ventana.setSize(500, 400);
                ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);

            }
        });
        agregarMedicamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View_agregarMedicamento view = new View_agregarMedicamento();

                JFrame ventana = new JFrame("Agregar Medicamento");
                ventana.setContentPane(view.getPanelAgregarMedicamento());
                ventana.setSize(500, 400);
                ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
            }
        });
    }

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
