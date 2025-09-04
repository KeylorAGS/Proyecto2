package presentation.Prescripcion;

import presentation.Logic.Medicamento;
import presentation.Logic.Service;

public class PrescripcionController {
    View_Prescripcion view;
    PrescripcionModel model;

    public PrescripcionController(View_Prescripcion view, PrescripcionModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void create(Medicamento e) throws  Exception{
        Service.instance().createMedicamento(e);
        model.setCurrent(new Medicamento());
        model.setList(Service.instance().findAll());
    }

    public void read(String id) throws Exception {
        Medicamento e = new Medicamento();
        e.setId(id);
        try {
            model.setCurrent(Service.instance().readMedicamento(e));
        } catch (Exception ex) {
            Medicamento b = new Medicamento();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void clear() {
        model.setCurrent(new Medicamento());
    }
}