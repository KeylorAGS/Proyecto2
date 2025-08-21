package presentation.Farmaceuticos;

import presentation.AbstractModel;
import presentation.Logic.Farmaceutico;
import presentation.Main;

import java.util.List;

public class FarmaceuticosModel extends AbstractModel {
    Farmaceutico filter;
    List<Farmaceutico> list;
    Farmaceutico current;
    int mode;

    public static final String LIST="list";
    public static final String CURRENT="current";
    public static final String FILTER="filter";

    public FarmaceuticosModel() {

    }

    public void init(List<Farmaceutico> list) {
        this.list = list;
        this.current= new Farmaceutico(0, "", "");
        this.filter= new Farmaceutico(0, "", "");
        this.mode= Main.MODE_CREATE;
    }

    public List<Farmaceutico> getList() { return list; }

    public void setList(List<Farmaceutico> list) {
        this.list= list;
        firePropertyChange(LIST);
    }
    public Farmaceutico getCurrent() {
        return current;
    }

    public void setCurrent(Farmaceutico current) {
        this.current= current;
        firePropertyChange(CURRENT);
    }
    public Farmaceutico getFilter() {
        return filter;
    }
    public void setFilter(Farmaceutico filter) {
        this.filter= filter;
        firePropertyChange(FILTER);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode= mode;
    }
}
