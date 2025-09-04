package presentation.Medicamentos;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import presentation.Interfaces.InterfazAdministrador;
import presentation.Logic.Medicamento;
import presentation.Logic.Paciente;
import presentation.Logic.Service;

import java.io.FileOutputStream;
import java.io.IOException;

public class Controller {
    private Medicamentos_View view;
    private Model model;

    public Controller(Medicamentos_View view, Model model) {
        model.init(Service.instance().searchPaciente(new Medicamento()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Medicamento filter) throws Exception {
        model.setFilter(filter);
        model.setMode(InterfazAdministrador.MODE_CREATE);
        model.setCurrent(new Medicamento());
        model.setList(Service.instance().searchPaciente(model.getFilter()));
    }

    public void save(Medicamento e) throws Exception {
        switch (model.getMode()) {
            case InterfazAdministrador.MODE_CREATE:
                Service.instance().createMedicamento(e);
                break;
            case InterfazAdministrador.MODE_EDIT:
                Service.instance().updateMedicamento(e);
                break;
        }
        model.setFilter(new Medicamento());
        search(model.getFilter());
    }

    public void edit(int row) {
        Medicamento medicamento = model.getList().get(row);
        try {
            model.setMode(InterfazAdministrador.MODE_EDIT);
            model.setCurrent(Service.instance().readMedicamento(medicamento));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().deleteMedicamento(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(InterfazAdministrador.MODE_CREATE);
        model.setCurrent(new Medicamento());
    }

    public void generatePdfReport() throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("PacientesReporte.pdf"));
        document.open();

        document.add(new Paragraph("Reporte de Pacientes"));

        for (Medicamento medicamento : model.getList()) {
            document.add(new Paragraph("ID: " + medicamento.getId()));
            document.add(new Paragraph("Nombre: " + medicamento.getNombre()));
            document.add(new Paragraph("Presentacion: " + medicamento.getPresentacion()));
            document.add(new Paragraph("-----"));
        }

        document.close();
    }
}
