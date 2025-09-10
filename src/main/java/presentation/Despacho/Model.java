package presentation.Despacho;

import presentation.AbstractModel;
import presentation.Logic.Receta;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Receta current;
    List<Receta> listaReceta;

    public Model(){
        current = new Receta();
        listaReceta = new ArrayList<>();
    }

    public Receta getCurrent() {return current;}

    public void setCurrent(Receta current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public void setList(List<Receta> list){
        this.listaReceta = list;
        firePropertyChange(LIST);
    }

    public List<Receta> getList(){
        return listaReceta;
    }

    public static final String LIST = "list";

    public static final String CURRENT = "current";

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

}
