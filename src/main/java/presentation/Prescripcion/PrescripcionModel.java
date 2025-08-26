package presentation.Prescripcion;

import presentation.AbstractModel;
import presentation.Logic.Farmaceutico;
import presentation.Logic.Medicamento;
import presentation.Main;

import java.util.List;

public class PrescripcionModel extends AbstractModel {

    private Medicamento filter;

    private List<Medicamento> list;

    private Medicamento current;

    private int mode;

    public static final String LIST = "list";

    public static final String CURRENT = "current";

    public static final String FILTER = "filter";

    public PrescripcionModel() {
    }

    public void init(List<Medicamento> list) {
        this.list = list;
        this.current = new Medicamento("", "", "");
        this.filter = new Medicamento("", "", "");
        this.mode = Main.MODE_CREATE;
    }

    public List<Medicamento> getList() {
        return list;
    }

    public void setList(List<Medicamento> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public Medicamento getCurrent() {
        return current;
    }

    public void setCurrent(Medicamento current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Medicamento getFilter() {
        return filter;
    }

    public void setFilter(Medicamento filter) {
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
