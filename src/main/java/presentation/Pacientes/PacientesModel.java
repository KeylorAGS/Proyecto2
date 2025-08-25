package presentation.Pacientes;

import presentation.AbstractModel;

import presentation.Logic.Paciente;

import java.util.List;

public class PacientesModel extends AbstractModel {

    /** Filtro de búsqueda aplicado a la lista de médicos. */
    Paciente filter;

    /** Lista de médicos obtenida tras la búsqueda. */
    List<Paciente> list;

    /** Médico actualmente seleccionado o en edición. */
    Paciente current;

    /** Modo de operación actual (crear o editar). */
    int mode;

    /** Constantes de nombres de propiedades observables. */
    public static final String LIST = "list";
    public static final String CURRENT = "current";
    public static final String FILTER = "filter";

    /**
     * Registra un listener para cambios de propiedades.
     *
     * Además de registrar el listener, se envían notificaciones
     * iniciales de las propiedades principales: lista, actual y filtro.
     *
     * @param listener Observador de cambios de propiedades.
     */
    @Override
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    /**
     * Constructor vacío.
     */
    public PacientesModel() {}

    /**
     * Inicializa el modelo con una lista inicial de médicos.
     *
     * También reinicia el médico actual y el filtro de búsqueda,
     * dejando el modo en 0 (modo por defecto).
     *
     * @param list Lista inicial de médicos.
     */
    public void init(List<Paciente> list) {
        this.list = list;
        this.current = new Paciente(0, "", null, "");
        this.filter = new Paciente(0, "", null, "");
        this.mode = 0;
    }

    /**
     * Obtiene la lista actual de médicos.
     *
     * @return Lista de médicos.
     */
    public List<Paciente> getList() {
        return list;
    }

    /**
     * Establece una nueva lista de médicos y notifica el cambio.
     *
     * @param list Nueva lista de médicos.
     */
    public void setList(List<Paciente> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    /**
     * Obtiene el médico actualmente seleccionado o en edición.
     *
     * @return Médico actual.
     */
    public Paciente getCurrent() {
        return current;
    }

    /**
     * Establece el médico actual y notifica el cambio.
     *
     * @param current Nuevo médico actual.
     */
    public void setCurrent(Paciente current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    /**
     * Obtiene el filtro de búsqueda actual.
     *
     * @return Filtro de médicos.
     */
    public Paciente getFilter() {
        return filter;
    }

    /**
     * Establece el filtro de búsqueda y notifica el cambio.
     *
     * @param filter Nuevo filtro.
     */
    public void setFilter(Paciente filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    /**
     * Obtiene el modo de operación actual (crear/editar).
     *
     * @return Modo actual.
     */
    public int getMode() {
        return mode;
    }

    /**
     * Establece el modo de operación actual.
     *
     * @param mode Nuevo modo (ej. {@link presentation.Main#MODE_CREATE} o {@link presentation.Main#MODE_EDIT}).
     */
    public void setMode(int mode) {
        this.mode = mode;
    }
}

