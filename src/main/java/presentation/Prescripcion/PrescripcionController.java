package presentation.Prescripcion;

import presentation.Logic.Medicamento;
import presentation.Logic.Service;
import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.Pacientes_View;

import javax.swing.*;

public class PrescripcionController {
    View_Prescripcion view;
    PrescripcionModel model;
    private JFrame buscarPacienteFrame;

    public PrescripcionController(View_Prescripcion view, PrescripcionModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void create(Medicamento e) throws  Exception{
        Service.instance().createMedicamento(e);
        model.setCurrent(new Medicamento());
        model.setList(Service.instance().findAll());
    }

    public void read(String id) throws Exception {
        Medicamento e = new Medicamento();
        e.setId(id);
        try {
            model.setCurrent(Service.instance().readMedicamento(e));
        } catch (Exception ex) {
            Medicamento b = new Medicamento();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Medicamento());
    }

    public void ventanaBuscarPaciente() {
        if (buscarPacienteFrame == null) {
            View_buscarPaciente buscarView = new View_buscarPaciente();
            Pacientes_View pacientesView = new Pacientes_View();
            PacientesModel pacientesModel = new PacientesModel();
            PacientesController pacientesController = new PacientesController(pacientesView, pacientesModel);

            buscarView.setModel(pacientesModel);
            buscarView.setController(pacientesController);
            buscarView.setControllerPr(this);

            buscarPacienteFrame = new JFrame("Buscar Paciente");
            buscarPacienteFrame.setSize(900, 450);
            buscarPacienteFrame.setResizable(false);
            buscarPacienteFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            buscarPacienteFrame.setContentPane(buscarView.getPanel());
        }
        buscarPacienteFrame.setVisible(true);
    }

    public void cerrarventanabuscarPaciente() {
        buscarPacienteFrame.dispose();
    }

}