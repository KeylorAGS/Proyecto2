package presentation.Pacientes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import presentation.Logic.Paciente;
import presentation.Logic.Service;
import presentation.Interfaces.InterfazAdministrador;

import java.io.FileOutputStream;
import java.io.IOException;

public class PacientesController {
        private Pacientes_View view;
        private PacientesModel model;

        public PacientesController(Pacientes_View view, PacientesModel model) {
            model.init(Service.instance().searchPaciente(new Paciente()));
            this.view = view;
            this.model = model;
            view.setController(this);
            view.setModel(model);
        }

        public void search(Paciente filter) throws Exception {
            model.setFilter(filter);
            model.setMode(InterfazAdministrador.MODE_CREATE);
            model.setCurrent(new Paciente());
            model.setList(Service.instance().searchPaciente(model.getFilter()));
        }

        public void save(Paciente e) throws Exception {
            switch (model.getMode()) {
                case InterfazAdministrador.MODE_CREATE:
                    Service.instance().createPaciente(e);
                    break;
                case InterfazAdministrador.MODE_EDIT:
                    Service.instance().updatePaciente(e);
                    break;
            }
            model.setFilter(new Paciente());
            search(model.getFilter());
        }

        public void edit(int row) {
            Paciente paciente = model.getList().get(row);
            try {
                model.setMode(InterfazAdministrador.MODE_EDIT);
                model.setCurrent(Service.instance().readPaciente(paciente));
            } catch (Exception ex) {}
        }

        public void delete() throws Exception {
            Service.instance().deletePaciente(model.getCurrent());
            search(model.getFilter());
        }

        public void clear() {
            model.setMode(InterfazAdministrador.MODE_CREATE);
            model.setCurrent(new Paciente());
        }

    public void generatePdfReport() throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("PacientesReporte.pdf"));
        document.open();

        document.add(new Paragraph("Reporte de Pacientes"));

        for (Paciente paciente : model.getList()) {
            document.add(new Paragraph("ID: " + paciente.getId()));
            document.add(new Paragraph("Nombre: " + paciente.getNombre()));
            document.add(new Paragraph("Fecha de Nacmiento: " + paciente.getFechaNacimiento()));
            document.add(new Paragraph("Telefono: " + paciente.getTelefono()));
            document.add(new Paragraph("-----"));
        }

        document.close();
    }
}
