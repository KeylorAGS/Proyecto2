package presentation.Historico;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import presentation.Interfaces.InterfazAdministrador;
import presentation.Logic.Receta;
import presentation.Logic.Service;

import java.io.FileOutputStream;
import java.io.IOException;

public class HistoricosController {
    Historico_View view;
    HistoricosModel model;

    public HistoricosController(Historico_View view, HistoricosModel model) {
        model.init(Service.instance().searchRecetas(new Receta()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void search(Receta filter) throws Exception {
        model.setFilter(filter);
        model.setMode(InterfazAdministrador.MODE_CREATE);
        model.setCurrent(new Receta());
        model.setList(Service.instance().searchRecetas(filter));
    }


    public void save(Receta current) throws Exception {
        switch (model.getMode()) {
            case InterfazAdministrador.MODE_CREATE:
                Service.instance().createReceta(current);
                break;
            case InterfazAdministrador.MODE_EDIT:
                Service.instance().updateReceta(current);
                break;
        }
        model.setCurrent(new Receta());
        search(model.getFilter());
    }


    public void edit(int row) {
        Receta receta = model.getList().get(row);
        try {
            model.setMode(InterfazAdministrador.MODE_EDIT);
            model.setCurrent(Service.instance().readReceta(receta));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete() throws Exception {
        Service.instance().deleteReceta(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(InterfazAdministrador.MODE_CREATE);
        model.setCurrent(new Receta());
    }

    public void generatePdfReport() throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("RecetasReporte.pdf"));
        document.open();
        document.add(new Paragraph("Reporte de Recetas"));

        for (Receta receta : model.getList()) {
            document.add(new Paragraph("ID del paciente: " + receta.getIdPaciente()));
            document.add(new Paragraph("ID del doctor: " + receta.getIdDoctor()));
            document.add(new Paragraph("Estado: " + receta.getEstado()));
            document.add(new Paragraph("Id de la receta"+ receta.getIdReceta()));
            document.add(new Paragraph("--------------------------------------------------"));
        }
        document.close();
    }
}
