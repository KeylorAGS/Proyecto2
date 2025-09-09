package presentation.Prescripcion;

import presentation.Logic.*;
import presentation.Medicamentos.MedicamentosController;
import presentation.Medicamentos.Medicamentos_View;
import presentation.Medicamentos.MedicamentosModel;
import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.Pacientes_View;
import presentation.Recetas.RecetasModel;

import javax.swing.*;

public class PrescripcionController {
    View_Prescripcion view;
    View_buscarMedicamento viewBuscarMedicamento;
    PrescripcionModel model;
    RecetasModel recetasModel;
    private JFrame buscarPacienteFrame;
    private JFrame buscarMedicamentoFrame;

    public PrescripcionController(View_Prescripcion view, PrescripcionModel model) {
        this.view = view;
        this.model = model;
        this.recetasModel = new RecetasModel();

        view.setController(this);
        view.setRecetasModel(recetasModel);
        view.setModel(model);
    }

    public void seleccionarPrescripcion(Prescripcion m) {
        model.setCurrent(m);
    }

    public void seleccionarPaciente(Paciente p) {
        model.setCurrentPaciente(p);
    }

    public void create(Prescripcion e) throws  Exception{
        Service.instance().createPrescripcion(e);
        model.setCurrent(new Prescripcion());
        model.setList(Service.instance().findAllPrescripcion());
    }

    public void read(String id) throws Exception {
        Prescripcion e = new Prescripcion();
        e.setId(id);
        try {
            model.setCurrent(Service.instance().readPrescripcion(e));
        } catch (Exception ex) {
            Prescripcion b = new Prescripcion();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Prescripcion());
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
            View_buscarMedicamento view = new View_buscarMedicamento();
            view.setControllerPr(this);  // conecta con el controlador de prescripción

            Medicamentos_View viewMed = new Medicamentos_View();
            MedicamentosModel medicamentosModel = new MedicamentosModel();
            MedicamentosController controllerMed = new MedicamentosController(viewMed, medicamentosModel);

            view.setController(controllerMed);
            view.setModel(medicamentosModel);

            buscarMedicamentoFrame = new JFrame("Agregar Medicamento");
            buscarMedicamentoFrame.setSize(900, 450);
            buscarMedicamentoFrame.setResizable(false);
            buscarMedicamentoFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            buscarMedicamentoFrame.setContentPane(view.getPanel());
        }
        buscarMedicamentoFrame.setVisible(true);
    }


    public void cerrarventanabuscarPaciente() {
        buscarPacienteFrame.dispose();
    }
    public void cerrarventanabuscarMedicamento() {
        buscarMedicamentoFrame.dispose();
    }

    public void crearReceta(){
        //kelor, agragar ambos id, los 3 ultimos se van en vacio⬇️
        Receta r = new Receta(viewBuscarMedicamento.getAuxNombre(), viewBuscarMedicamento.getAuxPresentacion(), " ", " ", " ", " ", " ");
    }
}