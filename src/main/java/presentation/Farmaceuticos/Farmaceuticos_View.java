package presentation.Farmaceuticos;

import javax.swing.*;
import javax.swing.text.View;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Farmaceuticos_View  implements PropertyChangeListener {
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










    //MVC
    FarmaceuticosModel model;
    FarmaceuticosController controller;

    public void setModel(FarmaceuticosModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(FarmaceuticosController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
