package presentation.Prescripcion;

import presentation.Interfaces.InterfazAdministrador;
import presentation.Logic.*;
import presentation.Medicamentos.MedicamentosController;
import presentation.Medicamentos.MedicamentosModel;
import presentation.Medicamentos.Medicamentos_View;
import presentation.Prescripcion.View_buscarMedicamento;
import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.Pacientes_View;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class PrescripcionController {
    private View_Prescripcion view;
    private PrescripcionModel model;
    private JFrame buscarPacienteFrame;
    private JFrame buscarMedicamentoFrame;

    public PrescripcionController(View_Prescripcion view, PrescripcionModel model) {
        model.init(Service.instance().searchPrescripcion(new Prescripcion()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void seleccionarPaciente(Paciente p) {
        model.setCurrentPaciente(p);
    }

    public void search(Prescripcion filter) throws Exception {
        model.setFilter(filter);
        model.setMode(InterfazAdministrador.MODE_CREATE);
        model.setCurrent(new Prescripcion());
        List<Prescripcion> result = Service.instance().searchPrescripcion(new Prescripcion()).stream()
                .filter(p -> filter.getId() == null || filter.getId().isEmpty() || p.getId().equalsIgnoreCase(filter.getId()))
                .filter(p -> filter.getNombre() == null || filter.getNombre().isEmpty() || p.getNombre().toLowerCase().contains(filter.getNombre().toLowerCase()))
                .filter(p -> filter.getPresentacion() == null || filter.getPresentacion().isEmpty() || p.getPresentacion().equals(filter.getPresentacion()))
                .collect(Collectors.toList());

        model.setList(result);
    }

    public void save(Prescripcion p) throws Exception {
        switch (model.getMode()) {
            case InterfazAdministrador.MODE_CREATE:
                Service.instance().createPrescripcion(p);
                break;
            case InterfazAdministrador.MODE_EDIT:
                Service.instance().updatePrescripcion(p);
                break;
        }
        model.setFilter(new Prescripcion());
        search(model.getFilter());
    }

    public void edit(int row) {
        Prescripcion prescripcion = model.getList().get(row);
        try {
            model.setMode(InterfazAdministrador.MODE_EDIT);
            model.setCurrent(Service.instance().readPrescripcion(prescripcion));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().deletePrescripcion(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(InterfazAdministrador.MODE_CREATE);
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
            View_buscarMedicamento buscarView = new View_buscarMedicamento();
            MedicamentosModel medicamentosModel = new MedicamentosModel();
            Medicamentos_View medicamentosView = new Medicamentos_View();
            MedicamentosController medicamentosController = new MedicamentosController(medicamentosView, medicamentosModel);

            buscarView.setModel(medicamentosModel);
            buscarView.setController(medicamentosController);
            buscarView.setControllerPr(this);

            buscarMedicamentoFrame = new JFrame("Agregar Medicamento");
            buscarMedicamentoFrame.setSize(900, 450);
            buscarMedicamentoFrame.setResizable(false);
            buscarMedicamentoFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            buscarMedicamentoFrame.setContentPane(buscarView.getPanel());
        }
        buscarMedicamentoFrame.setVisible(true);
    }

    public void cerrarventanabuscarPaciente() {
        buscarPacienteFrame.dispose();
    }

    public void cerrarventanabuscarMedicamento() {
        buscarMedicamentoFrame.dispose();
    }
}