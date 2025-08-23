package presentation.Medicos;

import presentation.AbstractModel;
import presentation.Logic.Medico;

import java.util.List;

/**
 * Modelo de presentación para la gestión de médicos.
 *
 * Mantiene el estado actual que se muestra en la vista:
 * - Lista de médicos encontrados.
 * - Médico actual seleccionado/siendo editado.
 * - Filtro de búsqueda.
 * - Modo de operación (crear/editar).
 *
 * Implementa notificación de cambios de propiedades para
 * actualizar automáticamente la vista cuando cambie el estado.
 */
public class MedicosModel extends AbstractModel {

    /** Filtro de búsqueda aplicado a la lista de médicos. */
    Medico filter;

    /** Lista de médicos obtenida tras la búsqueda. */
    List<Medico> list;

    /** Médico actualmente seleccionado o en edición. */
    Medico current;

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
    public MedicosModel() {}

    /**
     * Inicializa el modelo con una lista inicial de médicos.
     *
     * También reinicia el médico actual y el filtro de búsqueda,
     * dejando el modo en 0 (modo por defecto).
     *
     * @param list Lista inicial de médicos.
     */
    public void init(List<Medico> list) {
        this.list = list;
        this.current = new Medico(0, "", "", "");
        this.filter = new Medico(0, "", "", "");
        this.mode = 0; // ⚠️ Se recomienda usar Main.MODE_CREATE aquí
    }

    /**
     * Obtiene la lista actual de médicos.
     *
     * @return Lista de médicos.
     */
    public List<Medico> getList() {
        return list;
    }

    /**
     * Establece una nueva lista de médicos y notifica el cambio.
     *
     * @param list Nueva lista de médicos.
     */
    public void setList(List<Medico> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    /**
     * Obtiene el médico actualmente seleccionado o en edición.
     *
     * @return Médico actual.
     */
    public Medico getCurrent() {
        return current;
    }

    /**
     * Establece el médico actual y notifica el cambio.
     *
     * @param current Nuevo médico actual.
     */
    public void setCurrent(Medico current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    /**
     * Obtiene el filtro de búsqueda actual.
     *
     * @return Filtro de médicos.
     */
    public Medico getFilter() {
        return filter;
    }

    /**
     * Establece el filtro de búsqueda y notifica el cambio.
     *
     * @param filter Nuevo filtro.
     */
    public void setFilter(Medico filter) {
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
