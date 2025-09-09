package presentation.Despacho;

import javax.swing.*;
import java.awt.*;

public class View_despacho {
    private JLabel searchIdLbl;
    private JTextField searchId;
    private JButton clear;
    private JButton search;
    private JTable list;
    private JButton verMedicamentosButton;
    private JPanel panel;
    private JButton entregarButton;
    private JButton avanzarEstadoButton;

    public Component getPanel() {
        return panel;
    }
}
