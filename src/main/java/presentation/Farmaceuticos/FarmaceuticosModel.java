package presentation.Farmaceuticos;

import presentation.AbstractModel;
import presentation.Logic.Farmaceutico;
import presentation.Interfaces.InterfazAdministrador;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Modelo para la gestión de Farmacéuticos en el patrón MVC.
 *
 * - Mantiene el estado actual de la vista de farmaceutas.
 * - Contiene la lista de resultados, el filtro de búsqueda y el farmaceuta actualmente seleccionado.
 * - Notifica a la vista cuando cambian los datos mediante {@link #firePropertyChange(String)} heredado de {@link AbstractModel}.
 */
public class FarmaceuticosModel extends AbstractModel {

    /** Filtro actual para realizar búsquedas de farmaceutas. */
     Farmaceutico filter;

    /** Lista de farmaceutas obtenida de la búsqueda. */
     List<Farmaceutico> list;

    /** Farmaceuta actualmente seleccionado o en edición. */
     Farmaceutico current;

    /** Modo actual del modelo (crear o editar). */
     int mode;

    /** Constante usada para notificar cambios en la lista. */
    public static final String LIST = "list";

    /** Constante usada para notificar cambios en el elemento actual. */
    public static final String CURRENT = "current";

    /** Constante usada para notificar cambios en el filtro. */
    public static final String FILTER = "filter";

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    /**
     * Constructor por defecto.
     */
    public FarmaceuticosModel() {
    }

    /**
     * Inicializa el modelo con una lista de farmaceutas.
     * También crea instancias vacías para {@code current} y {@code filter},
     * y establece el modo en {@code MODE_CREATE}.
     *
     * @param list Lista inicial de farmaceutas.
     */
    public void init(List<Farmaceutico> list) {
        this.list = list;
        this.current = new Farmaceutico();
        this.filter = new Farmaceutico();
        this.mode = InterfazAdministrador.MODE_CREATE;
    }

    public List<Farmaceutico> getList() {
        return list;
    }

    public void setList(List<Farmaceutico> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public Farmaceutico getCurrent() {
        return current;
    }

    public void setCurrent(Farmaceutico current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Farmaceutico getFilter() {
        return filter;
    }

    public void setFilter(Farmaceutico filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
