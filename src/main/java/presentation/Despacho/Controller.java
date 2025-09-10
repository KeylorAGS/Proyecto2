package presentation.Despacho;

import presentation.Logic.Receta;
import presentation.Logic.Service;

public class Controller {


    View_despacho view;
    Model model;

    public Controller(View_despacho view, Model model) {
        this.view = view;
        this.model = model;
        view.setModel(model);
        view.setController(this);
        model.setList(Service.instance().findAllRecetas());
    }

    public void modificarEstado(Receta receta) throws Exception{
        Service.instance().modificarEstadoReceta(receta);
        model.setList(Service.instance().findAllRecetas());
    }

    public void delete(Receta receta) throws Exception{
        Service.instance().deleteReceta(receta);
        model.setList(Service.instance().findAllRecetas());
    }
}
