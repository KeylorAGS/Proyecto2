package presentation.Historico;

import com.itextpdf.text.DocumentException;
import presentation.Interfaces.InterfazAdministrador;
import presentation.Logic.Medico;
import presentation.Logic.Receta;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class Historico_View implements PropertyChangeListener  {
    private JPanel panel;
    private JLabel searchNombreLbl;
    private JTextField searchNombre;
    private JButton report;
    private JButton search;
    private JTable list;

    public JPanel getPanel() {
        return panel;
    }

    public Historico_View(){
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Receta filter = new Receta();
                    filter.setIdReceta(searchNombre.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                controller.edit(row);
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


    HistoricosModel model;
    HistoricosController controller;

    public void setModel(HistoricosModel model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(HistoricosController controller){
        this.controller = controller;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case HistoricosModel.LIST:
                int[] cols = {HistoricoTableModel.ID_PACIENTE, HistoricoTableModel.ID_DOCTOR, HistoricoTableModel.ESTADO, HistoricoTableModel.ID_RECETA};
                list.setModel(new HistoricoTableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(2).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
            case HistoricosModel.FILTER:
                searchNombre.setText(model.getFilter().getIdPaciente());
                break;
        }
        this.panel.revalidate();
    }
}
