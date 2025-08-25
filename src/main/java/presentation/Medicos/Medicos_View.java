package presentation.Medicos;

import com.itextpdf.text.DocumentException;
import presentation.Logic.Medico;
import presentation.Logic.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

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
        // Aqui van todos los listeners de los botones y acciones
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Medico medico = take();
                    try {
                        controller.save(medico);
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.delete();
                    JOptionPane.showMessageDialog(panel, "REGISTRO ELIMINADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Medico filter = new Medico();
                    filter.setNombre(searchNombre.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.generatePdfReport();
                    JOptionPane.showMessageDialog(panel, "REPORTE GENERADO", "PDF", JOptionPane.INFORMATION_MESSAGE);
                } catch (DocumentException | IOException ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error al generar el reporte", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public Medico take (){
        Usuario medico = new Medico();
        medico.setId(Integer.parseInt(IdJtext.getText()));
        medico.setNombre(NombreJtext.getText());
        ((Medico) medico).setEspecialidad(EspecialidadJtext.getText());
        return (Medico) medico;

    }

    public boolean validate(){
        if (NombreJtext.getText().isEmpty()){
            JOptionPane.showMessageDialog(panel, "El nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (EspecialidadJtext.getText().isEmpty()){
            JOptionPane.showMessageDialog(panel, "La especialidad no puede estar vacía", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
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
