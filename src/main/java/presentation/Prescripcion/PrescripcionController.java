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
import java.util.ArrayList;
import java.util.List;

public class PrescripcionController {
    private View_Prescripcion view;
    private PrescripcionModel model;

    // Variables para manejar la receta temporal mientras se construye
    private List<Prescripcion> prescripcionesTemporales;
    private String recetaTemporalId; // Para saber si estamos editando una receta existente

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
        // Inicializar lista temporal de prescripciones
        this.prescripcionesTemporales = new ArrayList<>();
        this.recetaTemporalId = null;

        // Inicializar el modelo con la lista temporal vacía
        model.init(this.prescripcionesTemporales);

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

    /**
     * Busca prescripciones - ahora solo muestra las prescripciones temporales
     */
    public void search(Prescripcion filter) throws Exception {
        model.setFilter(filter);
        model.setMode(InterfazAdministrador.MODE_CREATE);
        model.setCurrent(new Prescripcion());

        // Mostrar solo las prescripciones de la receta temporal actual
        model.setList(new ArrayList<>(prescripcionesTemporales));
    }

    /**
     * Agrega una prescripción a la lista temporal (no la guarda en XML todavía)
     */
    public void addPrescripcionTemporal(Prescripcion prescripcion) throws Exception {
        // Verificar si ya existe una prescripción con el mismo nombre y presentación
        boolean existe = prescripcionesTemporales.stream()
                .anyMatch(p -> p.getNombre().equals(prescripcion.getNombre()) &&
                        p.getPresentacion().equals(prescripcion.getPresentacion()));

        if (!existe) {
            prescripcionesTemporales.add(prescripcion);
            model.setList(new ArrayList<>(prescripcionesTemporales));
        } else {
            throw new Exception("Ya existe una prescripción con el mismo medicamento y presentación");
        }
    }

    /**
     * Crear una nueva prescripción y agregarla a la lista temporal
     */
    public void createPrescripcionTemporal(String nombre, String presentacion, String cantidad, String indicaciones, String duracion) {
        Prescripcion nueva = new Prescripcion(nombre, presentacion, cantidad, indicaciones, duracion);
        try {
            addPrescripcionTemporal(nueva);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * No se usa más - las prescripciones se guardan como parte de la receta
     */
    public void save(Prescripcion p) throws Exception {
        // Este método ya no se usa - las prescripciones se guardan con la receta
        throw new UnsupportedOperationException("Las prescripciones se guardan como parte de la receta");
    }

    public void edit(int row) {
        if (row >= 0 && row < prescripcionesTemporales.size()) {
            Prescripcion prescripcion = prescripcionesTemporales.get(row);
            model.setMode(InterfazAdministrador.MODE_EDIT);
            model.setCurrent(prescripcion);
        }
    }

    /**
     * Elimina una prescripción de la lista temporal
     */
    public void delete() throws Exception {
        Prescripcion current = model.getCurrent();
        if (current != null && current.getNombre() != null) {
            prescripcionesTemporales.removeIf(p ->
                    p.getNombre().equals(current.getNombre()) &&
                            p.getPresentacion().equals(current.getPresentacion())
            );
            model.setList(new ArrayList<>(prescripcionesTemporales));
        }
    }

    public void clear() {
        model.setMode(InterfazAdministrador.MODE_CREATE);
        model.setCurrent(new Prescripcion());
    }

    /**
     * Limpia completamente la lista temporal de prescripciones
     */
    public void clearTemporalList() {
        prescripcionesTemporales.clear();
        model.setList(new ArrayList<>(prescripcionesTemporales));
        model.setCurrentPaciente(null);
        recetaTemporalId = null;
    }

    public void setCambios(String cantidad, String duracion, String indicaciones){
        cantidadAux = cantidad;
        duracionAux = duracion;
        indicacionesAux = indicaciones;
    }

    /**
     * Aplica cambios a una prescripción en la lista temporal
     */
    public void aplicarCambios() throws Exception {
        Prescripcion vieja = model.getCurrent();

        // Validar que haya un nombre
        if (vieja.getNombre() == null || vieja.getNombre().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un Medicamento", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear nueva prescripción con los cambios
        Prescripcion nueva = new Prescripcion(
                vieja.getNombre(),
                vieja.getPresentacion(),
                cantidadAux,
                indicacionesAux,
                duracionAux
        );

        // Reemplazar en la lista temporal
        for (int i = 0; i < prescripcionesTemporales.size(); i++) {
            Prescripcion p = prescripcionesTemporales.get(i);
            if (p.getNombre().equals(vieja.getNombre()) &&
                    p.getPresentacion().equals(vieja.getPresentacion())) {
                prescripcionesTemporales.set(i, nueva);
                break;
            }
        }

        // Actualizar modelo
        model.setList(new ArrayList<>(prescripcionesTemporales));
        model.setCurrent(nueva);
    }

    public void createReceta(Receta receta) throws Exception {
        // Asegurar que la receta tenga las prescripciones temporales
        receta.setPrescripcions(new ArrayList<>(prescripcionesTemporales));

        // Asegurar que la receta tenga objetos completos de Paciente y Doctor
        if (receta.getPaciente() != null && receta.getPaciente().getId() != null) {
            try {
                Paciente pacienteCompleto = Service.instance().readPaciente(receta.getPaciente());
                receta.setPaciente(pacienteCompleto);
            } catch (Exception e) {
                // Si no se puede leer, usar el que se tiene
            }
        }

        if (receta.getDoctor() != null && receta.getDoctor().getId() != null) {
            try {
                Usuario usuarioDoctor = Service.instance().buscarUsuario(receta.getDoctor().getId());
                if (usuarioDoctor instanceof Medico) {
                    receta.setDoctor((Medico) usuarioDoctor);
                }
            } catch (Exception e) {
                // Si no se puede leer, usar el que se tiene
            }
        }

        Service.instance().createReceta(receta);
        Service.instance().stop();

        // Limpiar la lista temporal después de guardar
        clearTemporalList();
    }

    /**
     * Obtiene la lista temporal de prescripciones
     */
    public List<Prescripcion> getPrescripcionesTemporales() {
        return new ArrayList<>(prescripcionesTemporales);
    }
}