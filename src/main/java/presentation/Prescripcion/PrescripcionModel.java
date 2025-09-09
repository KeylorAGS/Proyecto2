package presentation.Prescripcion;

import presentation.AbstractModel;
import presentation.Interfaces.InterfazAdministrador;
import presentation.Logic.Prescripcion;

import java.beans.PropertyChangeListener;
import java.util.List;

public class PrescripcionModel extends AbstractModel {
    Prescripcion filter;
    List<Prescripcion> list;
    Prescripcion current;
    int mode;

    public static final String LIST = "list";
    public static final String CURRENT = "current";
    public static final String FILTER = "filter";
    private Prescripcion currentPrescripcion;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    public Prescripcion getCurrentPrescripcion() {
        return currentPrescripcion;
    }

    public void setCurrentPrescripcion(Prescripcion currentPrescripcion) {
        this.currentPrescripcion = currentPrescripcion;
        firePropertyChange(CURRENT);
    }

    public PrescripcionModel() {
    }

    public void init(List<Prescripcion> list) {
        this.list = list;
        this.current = new Prescripcion();
        this.filter = new Prescripcion();
        this.mode = InterfazAdministrador.MODE_CREATE;
    }

    public List<Prescripcion> getList() {
        return list;
    }

    public void setList(List<Prescripcion> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public Prescripcion getCurrent() {
        return current;
    }

    public void setCurrent(Prescripcion current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Prescripcion getFilter() {
        return filter;
    }

    public void setFilter(Prescripcion filter) {
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