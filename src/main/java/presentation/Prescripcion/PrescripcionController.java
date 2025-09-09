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
import java.util.ArrayList;

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

        // Inicializar la lista de prescripciones si está vacía
        if (model.getList() == null) {
            model.setList(new ArrayList<>());
        }

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

    public void create(Prescripcion e) throws Exception {
        // Si hay un servicio, usarlo; si no, agregar directamente a la lista local
        try {
            if (Service.instance() != null) {
                Service.instance().createPrescripcion(e);
                model.setList(Service.instance().findAllPrescripcion());
            } else {
                // Modo local - agregar a la lista actual
                model.getList().add(e);
                model.setList(new ArrayList<>(model.getList())); // Trigger property change
            }
        } catch (Exception ex) {
            // Si falla el servicio, agregar localmente
            model.getList().add(e);
            model.setList(new ArrayList<>(model.getList())); // Trigger property change
        }

        // Limpiar current después de agregar
        model.setCurrent(new Prescripcion());
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

    // Método para eliminar un medicamento de la prescripción
    public void eliminarMedicamento(int index) {
        if (index >= 0 && index < model.getList().size()) {
            model.getList().remove(index);
            model.setList(new ArrayList<>(model.getList())); // Trigger property change
        }
    }

    // Método para limpiar toda la lista de prescripciones
    public void limpiarPrescripciones() {
        model.setList(new ArrayList<>());
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
            viewBuscarMedicamento = new View_buscarMedicamento();
            viewBuscarMedicamento.setControllerPr(this);  // conecta con el controlador de prescripción

            Medicamentos_View viewMed = new Medicamentos_View();
            MedicamentosModel medicamentosModel = new MedicamentosModel();
            MedicamentosController controllerMed = new MedicamentosController(viewMed, medicamentosModel);

            viewBuscarMedicamento.setController(controllerMed);
            viewBuscarMedicamento.setModel(medicamentosModel);

            buscarMedicamentoFrame = new JFrame("Agregar Medicamento");
            buscarMedicamentoFrame.setSize(900, 450);
            buscarMedicamentoFrame.setResizable(false);
            buscarMedicamentoFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            buscarMedicamentoFrame.setContentPane(viewBuscarMedicamento.getPanel());
        }
        buscarMedicamentoFrame.setVisible(true);
    }

    public void cerrarventanabuscarPaciente() {
        if (buscarPacienteFrame != null) {
            buscarPacienteFrame.dispose();
            buscarPacienteFrame = null; // Reset para permitir recrear
        }
    }

    public void cerrarventanabuscarMedicamento() {
        if (buscarMedicamentoFrame != null) {
            buscarMedicamentoFrame.dispose();
            buscarMedicamentoFrame = null; // Reset para permitir recrear
        }
    }

    public void crearReceta() {
        // Implementar la lógica para crear receta con los medicamentos seleccionados
        if (model.getCurrentPaciente() != null && !model.getList().isEmpty()) {
            // Aquí puedes implementar la lógica para crear la receta
            // con todos los medicamentos de la prescripción
            System.out.println("Creando receta para paciente: " + model.getCurrentPaciente().getNombre());
            System.out.println("Medicamentos: " + model.getList().size());
        }
    }
}