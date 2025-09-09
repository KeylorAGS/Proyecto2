package presentation.Prescripcion;

import presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import presentation.Logic.Paciente;
import presentation.Logic.Prescripcion;

public class PrescripcionModel extends AbstractModel {
    Prescripcion current;
    List<Prescripcion> list;
    Prescripcion filter;
    int mode;

    private Paciente currentPaciente;
    public static final String PACIENTE = "paciente";


    public Paciente getCurrentPaciente() {
        return currentPaciente;
    }

    public void setCurrentPaciente(Paciente currentPaciente) {
        this.currentPaciente = currentPaciente;
        firePropertyChange(PACIENTE);
    }

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String FILTER = "filter";

    public PrescripcionModel() {
        current = new Prescripcion();
        list = new ArrayList<Prescripcion>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(FILTER);
    }

    public Prescripcion getCurrent() {
        return current;
    }

    public void setCurrent(Prescripcion current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Prescripcion> getList() {
        return list;
    }

    public void setList(List<Prescripcion> list) {
        this.list = list;
        firePropertyChange(LIST);
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