package presentation.Pacientes;

import presentation.Farmaceuticos.FarmaceuticosModel;
import presentation.Farmaceuticos.Farmaceuticos_View;
import presentation.Logic.Farmaceutico;
import presentation.Logic.Paciente;
import presentation.Logic.Service;
import presentation.Main;

public class PacientesController {
        private Pacientes_View view;
        private PacientesModel model;

        public PacientesController(Pacientes_View view, PacientesModel model) {
            model.init(Service.instance().searchPaciente(new Paciente(0, "", null, "")));
            this.view = view;
            this.model = model;
            view.setController(this);
            view.setModel(model);
        }

        public void search(Paciente filter) throws Exception {
            model.setFilter(filter);
            model.setMode(Main.MODE_CREATE);
            model.setCurrent(new Paciente(0, "", null, ""));
            model.setList(Service.instance().searchPaciente(filter));
        }

        public void save(Paciente e) throws Exception {
            switch (model.getMode()) {
                case Main.MODE_CREATE:
                    Service.instance().createPaciente(e);
                    break;
                case Main.MODE_EDIT:
                    Service.instance().updatePaciente(e);
                    break;
            }
            model.setFilter(new Paciente(0, "", null, ""));
            search(model.getFilter());
        }

        public void edit(int row) {
            Paciente paciente = model.getList().get(row);
            try {
                model.setMode(Main.MODE_EDIT);
                model.setCurrent(Service.instance().readPaciente(paciente));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void delete() throws Exception {
            Service.instance().deletePaciente(model.getCurrent());
            search(model.getFilter());
        }

        public void clear() {
            model.setMode(Main.MODE_CREATE);
            model.setCurrent(new Paciente(0, "", null, ""));
        }
}
