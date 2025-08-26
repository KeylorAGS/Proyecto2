package presentation.Prescripcion;

import presentation.Logic.Farmaceutico;
import presentation.Logic.Medicamento;
import presentation.Logic.Medico;
import presentation.Logic.Service;
import presentation.Main;
import presentation.Medicos.MedicosModel;
import presentation.Medicos.Medicos_View;

public class PrescripcionController {
    PrescripcionModel model;
    View_Prescripcion view;

    public PrescripcionController(View_Prescripcion view, PrescripcionModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Medicamento filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Medicamento("", "", ""));
        model.setList(Service.instance().searchMedicamento(filter));
    }

    public void save(Medicamento e) throws Exception {
        switch (model.getMode()) {
            case Main.MODE_CREATE:
                Service.instance().createMedicamento(e);
                break;
            case Main.MODE_EDIT:
                Service.instance().updateMedicamento(e);
                break;
        }
        model.setFilter(new Medicamento("", "", ""));
        search(model.getFilter());
    }

    public void edit(int row) {
        Medicamento medicamento = model.getList().get(row);
        try {
            model.setMode(Main.MODE_EDIT);
            model.setCurrent(Service.instance().readMedicamento(medicamento));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete() throws Exception {
        Service.instance().deleteMedicamento(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Medicamento("", "", ""));
    }
}
