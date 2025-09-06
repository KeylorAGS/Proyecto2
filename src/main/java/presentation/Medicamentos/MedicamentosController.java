package presentation.Medicamentos;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import presentation.Interfaces.InterfazAdministrador;
import presentation.Logic.Medicamento;
import presentation.Logic.Service;

import java.io.FileOutputStream;
import java.io.IOException;

public class MedicamentosController {
    private Medicamentos_View view;
    private MedicamentosModel medicamentosModel;

    public MedicamentosController(Medicamentos_View view, MedicamentosModel medicamentosModel) {
        medicamentosModel.init(Service.instance().searchPaciente(new Medicamento()));
        this.view = view;
        this.medicamentosModel = medicamentosModel;
        view.setController(this);
        view.setModel(medicamentosModel);
    }

    public void search(Medicamento filter) throws Exception {
        medicamentosModel.setFilter(filter);
        medicamentosModel.setMode(InterfazAdministrador.MODE_CREATE);
        medicamentosModel.setCurrent(new Medicamento());
        medicamentosModel.setList(Service.instance().searchPaciente(medicamentosModel.getFilter()));
    }

    public void save(Medicamento e) throws Exception {
        switch (medicamentosModel.getMode()) {
            case InterfazAdministrador.MODE_CREATE:
                Service.instance().createMedicamento(e);
                break;
            case InterfazAdministrador.MODE_EDIT:
                Service.instance().updateMedicamento(e);
                break;
        }
        medicamentosModel.setFilter(new Medicamento());
        search(medicamentosModel.getFilter());
    }

    public void edit(int row) {
        Medicamento medicamento = medicamentosModel.getList().get(row);
        try {
            medicamentosModel.setMode(InterfazAdministrador.MODE_EDIT);
            medicamentosModel.setCurrent(Service.instance().readMedicamento(medicamento));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().deleteMedicamento(medicamentosModel.getCurrent());
        search(medicamentosModel.getFilter());
    }

    public void clear() {
        medicamentosModel.setMode(InterfazAdministrador.MODE_CREATE);
        medicamentosModel.setCurrent(new Medicamento());
    }

    public void generatePdfReport() throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("PacientesReporte.pdf"));
        document.open();

        document.add(new Paragraph("Reporte de Pacientes"));

        for (Medicamento medicamento : medicamentosModel.getList()) {
            document.add(new Paragraph("ID: " + medicamento.getId()));
            document.add(new Paragraph("Nombre: " + medicamento.getNombre()));
            document.add(new Paragraph("Presentacion: " + medicamento.getPresentacion()));
            document.add(new Paragraph("-----"));
        }

        document.close();
    }
}
