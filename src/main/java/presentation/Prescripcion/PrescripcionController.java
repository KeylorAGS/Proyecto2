package presentation.Prescripcion;

import presentation.Logic.Farmaceutico;
import presentation.Logic.Medicamento;
import presentation.Logic.Medico;
import presentation.Logic.Service;
import presentation.Main;
import presentation.Medicos.InterfazMedicos;
import presentation.Medicos.MedicosModel;
import presentation.Medicos.Medicos_View;

import javax.swing.*;

public class PrescripcionController {
    PrescripcionModel model;
    View_Prescripcion view;
    View_buscarPaciente buscarPacienteView;

    public PrescripcionController(View_Prescripcion view, PrescripcionModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);

        buscarPacienteView = new View_buscarPaciente();
        JFrame ventana = new JFrame("Buscar Paciente");
        ventana.setContentPane(buscarPacienteView.getPanelBuscarPaciente());
        ventana.setSize(500, 400);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        // Guardamos la referencia para mostrarla luego
        this.buscarPacienteWindow = ventana;
    }

    public void abrirBuscarPaciente() {
        if (buscarPacienteWindow != null) {
            buscarPacienteWindow.setVisible(true);
        }
    }

    private JFrame buscarPacienteWindow;

    public void search(Medicamento filter) throws Exception {
        model.setFilter(filter);
        model.setMode(InterfazMedicos.MODE_CREATE);
        model.setCurrent(new Medicamento("", "", ""));
        model.setList(Service.instance().searchMedicamento(filter));
    }

    public void save(Medicamento e) throws Exception {
        switch (model.getMode()) {
            case InterfazMedicos.MODE_CREATE:
                Service.instance().createMedicamento(e);
                break;
            case InterfazMedicos.MODE_EDIT:
                Service.instance().updateMedicamento(e);
                break;
        }
        model.setFilter(new Medicamento("", "", ""));
        search(model.getFilter());
    }

    public void edit(int row) {
        Medicamento medicamento = model.getList().get(row);
        try {
            model.setMode(InterfazMedicos.MODE_EDIT);
            model.setCurrent(Service.instance().readMedicamento(medicamento));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete() throws Exception {
        Service.instance().deleteMedicamento(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(InterfazMedicos.MODE_CREATE);
        model.setCurrent(new Medicamento("", "", ""));
    }
}
