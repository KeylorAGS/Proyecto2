package presentation.Prescripcion;

import presentation.Logic.Medicamento;
import presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import presentation.Logic.Paciente;

public class PrescripcionModel extends AbstractModel {
    Medicamento current;
    List<Medicamento> list;

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

    public PrescripcionModel() {
        current = new Medicamento();
        list = new ArrayList<Medicamento>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Medicamento getCurrent() {
        return current;
    }

    public void setCurrent(Medicamento current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Medicamento> getList() {
        return list;
    }

    public void setList(List<Medicamento> list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}