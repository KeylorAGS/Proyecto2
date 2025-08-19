package presentation.Medicos;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Medicos_View implements PropertyChangeListener {
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

    public Medicos_View(){
        // Aqui vam todos los listeners de los botones y acciones
    }

 // MVC
    MedicosModel model;
    MedicosController controller;

    public void setModel(MedicosModel model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(MedicosController controller){
        this.controller = controller;
    }
     @Override
    public void propertyChange(PropertyChangeEvent evt) {

     }

}
