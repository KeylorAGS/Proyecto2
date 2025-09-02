package presentation.Prescripcion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View_buscarPaciente {
    private JPanel panel1;
    private JComboBox FiltrarB;
    private JTextField FiltrarT;
    private JTable table1;
    private JButton cancelar;
    private JButton ok;
    private JLabel Filtrar;
    private JScrollPane listaPacientes;
    private JPanel panel;


    public View_buscarPaciente() {
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor(panel1);
                if (window != null) {
                    window.dispose();
                }
            }
        });
    }

    public JPanel getPanelBuscarPaciente() { return panel1; }
}
