package presentation.Prescripcion;

import presentation.Logic.Medicamento;
import presentation.Logic.Paciente;
import presentation.Logic.Service;
import presentation.Medicamentos.MedicamentosController;
import presentation.Medicamentos.Medicamentos_View;
import presentation.Medicamentos.MedicamentosModel;
import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.Pacientes_View;

import javax.swing.*;

public class PrescripcionController {
    View_Prescripcion view;
    PrescripcionModel model;
    private JFrame buscarPacienteFrame;
    private JFrame buscarMedicamentoFrame;

    public PrescripcionController(View_Prescripcion view, PrescripcionModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void seleccionarPaciente(Paciente p) {
        model.setCurrentPaciente(p);
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

    public void ventanaBuscarMedicamento() {
        if (buscarMedicamentoFrame == null) {
            View_buscarMedicamento View = new View_buscarMedicamento();
            Medicamentos_View ViewP = new Medicamentos_View();
            MedicamentosModel medicamentosModel = new MedicamentosModel();
            MedicamentosController controller = new MedicamentosController(ViewP, medicamentosModel);
            View.setController(controller);
            View.setModel(medicamentosModel);

            buscarMedicamentoFrame = new JFrame("Agregar Medicamento");
            buscarMedicamentoFrame.setSize(900, 450);
            buscarMedicamentoFrame.setResizable(false);
            buscarMedicamentoFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            buscarMedicamentoFrame.setContentPane(View.getPanel());
        }
        buscarMedicamentoFrame.setVisible(true);
    }

    public void cerrarventanabuscarPaciente() {
        buscarPacienteFrame.dispose();
    }

}