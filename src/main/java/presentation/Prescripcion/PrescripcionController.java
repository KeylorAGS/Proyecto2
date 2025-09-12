package presentation.Prescripcion;

import presentation.Interfaces.InterfazAdministrador;
import presentation.Logic.*;
import presentation.Medicamentos.MedicamentosController;
import presentation.Medicamentos.MedicamentosModel;
import presentation.Medicamentos.Medicamentos_View;
import presentation.Pacientes.PacientesController;
import presentation.Pacientes.PacientesModel;
import presentation.Pacientes.Pacientes_View;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class PrescripcionController {
    private View_Prescripcion view;
    private PrescripcionModel model;

    // Removed frame fields (views will handle visibility)
    // Provide Pacientes/Medicamentos models & controllers so view can set them on subviews
    private PacientesModel pacientesModel;
    private Pacientes_View pacientesView;
    private PacientesController pacientesController;

    private MedicamentosModel medicamentosModel;
    private Medicamentos_View medicamentosView;
    private MedicamentosController medicamentosController;

    public String cantidadAux;
    public String duracionAux;
    public String indicacionesAux;

    public PrescripcionController(View_Prescripcion view, PrescripcionModel model) {
        model.init(Service.instance().searchPrescripcion(new Prescripcion()));
        this.view = view;
        this.model = model;

        // Initialize Pacientes module (models/controllers) here so the view can use them
        this.pacientesModel = new PacientesModel();
        this.pacientesView = new Pacientes_View();
        this.pacientesController = new PacientesController(pacientesView, pacientesModel);

        // Initialize Medicamentos module (models/controllers)
        this.medicamentosModel = new MedicamentosModel();
        this.medicamentosView = new Medicamentos_View();
        this.medicamentosController = new MedicamentosController(medicamentosView, medicamentosModel);

        // Wire MVC
        view.setController(this);
        view.setModel(model);
    }

    // Getters so the main view can pass the correct models/controllers to subviews
    public PacientesModel getPacientesModel() {
        return pacientesModel;
    }

    public PacientesController getPacientesController() {
        return pacientesController;
    }

    public MedicamentosModel getMedicamentosModel() {
        return medicamentosModel;
    }

    public MedicamentosController getMedicamentosController() {
        return medicamentosController;
    }

    public void seleccionarPaciente(Paciente p) {
        model.setCurrentPaciente(p);
    }

    public void search(Prescripcion filter) throws Exception {
        model.setFilter(filter);
        model.setMode(InterfazAdministrador.MODE_CREATE);
        model.setCurrent(new Prescripcion());
        List<Prescripcion> result = Service.instance().searchPrescripcion(new Prescripcion()).stream()
                .filter(p -> filter.getNombre() == null || filter.getNombre().isEmpty() || p.getNombre().toLowerCase().contains(filter.getNombre().toLowerCase()))
                .filter(p -> filter.getPresentacion() == null || filter.getPresentacion().isEmpty() || p.getPresentacion().equals(filter.getPresentacion()))
                .filter(p -> filter.getCantidad() == null || filter.getCantidad().isEmpty() || p.getCantidad().equalsIgnoreCase(filter.getCantidad()))
                .filter(p -> filter.getIndicaciones() == null || filter.getIndicaciones().isEmpty() || p.getIndicaciones().equalsIgnoreCase(filter.getIndicaciones()))
                .filter(p -> filter.getDuracion() == null || filter.getDuracion().isEmpty() || p.getDuracion().equalsIgnoreCase(filter.getDuracion()))
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

    public void setCambios(String cantidad, String duracion, String indicaciones){
        cantidadAux = cantidad;
        duracionAux = duracion;
        indicacionesAux = indicaciones;
    }

    public void aplicarCambios() throws Exception {
        Prescripcion vieja = model.getCurrent();

        // Validar que haya un nombre
        if (vieja.getNombre() == null || vieja.getNombre().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un Medicamento", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // salir del método, no crear nada
        }

        Prescripcion nueva = new Prescripcion(
                vieja.getNombre(),
                vieja.getPresentacion(),
                cantidadAux,
                indicacionesAux,
                duracionAux
        );

        Service.instance().deletePrescripcion(vieja);
        Service.instance().createPrescripcion(nueva);

        search(model.getFilter());

        // Selecciona la nueva como "current"
        model.setCurrent(nueva);
    }


    public void createReceta(Receta receta) throws Exception {
        // Asegurar que la receta tenga objetos completos de Paciente y Doctor

        // Completar información del paciente si está incompleta
        if (receta.getPaciente() != null && receta.getPaciente().getId() != null) {
            try {
                Paciente pacienteCompleto = Service.instance().readPaciente(receta.getPaciente());
                receta.setPaciente(pacienteCompleto);
            } catch (Exception e) {
            }
        }

        // Completar información del doctor si está incompleta
        if (receta.getDoctor() != null && receta.getDoctor().getId() != null) {
            try {
                Usuario usuarioDoctor = Service.instance().buscarUsuario(receta.getDoctor().getId());
                if (usuarioDoctor instanceof Medico) {
                    receta.setDoctor((Medico) usuarioDoctor);
                }
            } catch (Exception e) {
            }
        }

        Service.instance().createReceta(receta);
        Service.instance().stop();
    }
}
