package presentation.Medicos;

import presentation.Logic.Medico;
import presentation.Logic.Service;
import presentation.Main;

public class MedicosController {
    Medicos_View view;
    MedicosModel model;

    public MedicosController(Medicos_View view, MedicosModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Medico filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Medico(0, "", "", ""));
        model.setList(Service.instance().searchMedico(filter));
    }

    public void save(Medico current) throws Exception {
       switch (model.getMode()){
           case Main.MODE_CREATE:
               Service.instance().createMedico(current);
               break;
           case Main.MODE_EDIT:
                Service.instance().updateMedico(current);
                break;
       }
       model.setCurrent(new Medico(0, "", "", ""));
       search(model.getFilter());
    }

    public void edit(int row){
        Medico medico = model.getList().get(row);
        try {
            model.setMode(Main.MODE_EDIT);
            model.setCurrent(Service.instance().readMedico(medico));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete() throws Exception{
        Service.instance().deleteMedico(model.getCurrent());
        search(model.getFilter());
    }

    public void clear(){
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Medico(0, "", "", ""));
    }
}
