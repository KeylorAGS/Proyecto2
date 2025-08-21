package presentation.Farmaceuticos;

import presentation.Logic.Farmaceutico;
import presentation.Logic.Service;
import presentation.Main;

public class FarmaceuticosController {
    Farmaceuticos_View view;
    FarmaceuticosModel model;

    public FarmaceuticosController(Farmaceuticos_View view, FarmaceuticosModel model) {
        model.init(Service.instance().searchFarmaceutico(new Farmaceutico(0, "", "")));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Farmaceutico filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Farmaceutico(0,"",""));
        model.setList(Service.instance().searchFarmaceutico(filter));
    }

    public void save(Farmaceutico e) throws Exception {
        switch (model.getMode()){
            case Main.MODE_CREATE:
                Service.instance().createFarmaceutico(e);
                break;
            case Main.MODE_EDIT:
                Service.instance().updateFarmaceutico(e);
                break;
        }
        model.setFilter(new Farmaceutico(0,"",""));
        search(model.getFilter());
    }

    public void edit(int row) {

    }
}
