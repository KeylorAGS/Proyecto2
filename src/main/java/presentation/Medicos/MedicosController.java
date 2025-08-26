package presentation.Medicos;

import presentation.Logic.Medico;
import presentation.Logic.Service;
import presentation.Main;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Controlador para la gestión de médicos en la aplicación.
 *
 * Implementa la lógica de interacción entre la vista {@link Medicos_View},
 * el modelo {@link MedicosModel} y el servicio central {@link Service}.
 *
 * Forma parte del patrón MVC (Modelo-Vista-Controlador).
 */
public class MedicosController {
    /** Referencia a la vista de médicos. */
    Medicos_View view;

    /** Modelo de datos asociado a la vista de médicos. */
    MedicosModel model;

    /**
     * Constructor del controlador.
     *
     * @param view  Vista asociada.
     * @param model Modelo asociado.
     */
    public MedicosController(Medicos_View view, MedicosModel model) {
        model.init(Service.instance().searchMedico(new Medico()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    /**
     * Realiza una búsqueda de médicos aplicando un filtro.
     *
     * Actualiza el modelo con la lista encontrada y reinicia el estado a "crear".
     *
     * @param filter Objeto {@link Medico} con los criterios de búsqueda.
     * @throws Exception si ocurre un error en la búsqueda.
     */
    public void search(Medico filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Medico());
        model.setList(Service.instance().searchMedico(filter));
    }

    /**
     * Guarda un médico en el sistema.
     *
     * Dependiendo del modo en el que esté el modelo:
     * - {@link Main#MODE_CREATE}: crea un nuevo médico.
     * - {@link Main#MODE_EDIT}: actualiza un médico existente.
     *
     * Al finalizar, reinicia el médico actual y vuelve a ejecutar la búsqueda.
     *
     * @param current Médico a guardar.
     * @throws Exception si ocurre un error al crear/actualizar.
     */
    public void save(Medico current) throws Exception {
        switch (model.getMode()) {
            case Main.MODE_CREATE:
                Service.instance().createMedico(current);
                break;
            case Main.MODE_EDIT:
                Service.instance().updateMedico(current);
                break;
        }
        model.setCurrent(new Medico());
        search(model.getFilter());
    }

    /**
     * Activa el modo edición para el médico en una fila determinada.
     *
     * @param row Índice de la fila seleccionada en la tabla/lista.
     */
    public void edit(int row) {
        Medico medico = model.getList().get(row);
        try {
            model.setMode(Main.MODE_EDIT);
            model.setCurrent(Service.instance().readMedico(medico));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Elimina el médico actualmente seleccionado en el modelo.
     *
     * @throws Exception si el médico no existe o no puede eliminarse.
     */
    public void delete() throws Exception {
        Service.instance().deleteMedico(model.getCurrent());
        search(model.getFilter());
    }

    /**
     * Limpia el modelo y lo reinicia en modo creación.
     *
     * Prepara la vista para ingresar un nuevo médico.
     */
    public void clear() {
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Medico());
    }

    public void generatePdfReport() throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("MedicosReporte.pdf"));
        document.open();
        document.add(new Paragraph("Reporte de Cajeros"));

        for (Medico medico : model.getList()) {
            document.add(new Paragraph("ID: " + medico.getId()));
            document.add(new Paragraph("Nombre: " + medico.getNombre()));
            document.add(new Paragraph("Especialidad: " + medico.getEspecialidad()));
            document.add(new Paragraph("--------------------------------------------------"));
        }
        document.close();
    }
}
