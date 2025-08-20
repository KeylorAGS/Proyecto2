package presentation;

import presentation.Loggin.View_Loggin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (Exception e){};

        View_Loggin view =  new View_Loggin();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,300);
        frame.setTitle("Clinica");
        frame.setContentPane(view.getPanelLoggin());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        // HOLA

    }

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;
}