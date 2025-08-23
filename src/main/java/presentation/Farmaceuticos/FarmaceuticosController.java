package presentation.Farmaceuticos;

import presentation.Logic.Farmaceutico;
import presentation.Logic.Medico;
import presentation.Logic.Service;
import presentation.Main;

/**
 * Controlador para la gestión de Farmacéuticos.
 *
 * Forma parte del patrón MVC:
 * - Se comunica con la {@link Farmaceuticos_View} (Vista) para manejar interacciones del usuario.
 * - Actualiza el {@link FarmaceuticosModel} (Modelo) en función de las operaciones realizadas.
 * - Utiliza la capa de lógica de negocio {@link Service} para ejecutar operaciones sobre los datos.
 */
public class FarmaceuticosController {

    /** Vista asociada a este controlador */
    private Farmaceuticos_View view;

    /** Modelo que mantiene el estado de la vista */
    private FarmaceuticosModel model;

    /**
     * Constructor del controlador.
     *
     * Inicializa el modelo con la lista completa de farmaceutas y
     * establece las referencias mutuas entre Vista, Modelo y Controlador.
     *
     * @param view Vista de gestión de farmaceutas.
     * @param model Modelo que gestiona el estado y datos de la vista.
     */
    public FarmaceuticosController(Farmaceuticos_View view, FarmaceuticosModel model) {
        model.init(Service.instance().searchFarmaceutico(new Farmaceutico(0, "", "")));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    /**
     * Busca farmaceutas que coincidan con el filtro dado.
     *
     * @param filter Objeto {@link Farmaceutico} usado como filtro de búsqueda (por id/nombre).
     * @throws Exception si ocurre un error en la búsqueda.
     */
    public void search(Farmaceutico filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Farmaceutico(0, "", ""));
        model.setList(Service.instance().searchFarmaceutico(filter));
    }

    /**
     * Guarda un farmaceuta (creación o edición).
     *
     * - Si el modo es {@code MODE_CREATE}, crea un nuevo farmaceuta.
     * - Si el modo es {@code MODE_EDIT}, actualiza el existente.
     *
     * Luego, restablece el filtro y vuelve a ejecutar la búsqueda.
     *
     * @param e Objeto {@link Farmaceutico} a guardar.
     * @throws Exception si ocurre un error durante la persistencia.
     */
    public void save(Farmaceutico e) throws Exception {
        switch (model.getMode()) {
            case Main.MODE_CREATE:
                Service.instance().createFarmaceutico(e);
                break;
            case Main.MODE_EDIT:
                Service.instance().updateFarmaceutico(e);
                break;
        }
        model.setFilter(new Farmaceutico(0, "", ""));
        search(model.getFilter());
    }

    /**
     * Prepara el modelo para editar un farmaceuta existente.
     *
     * @param row Índice de la fila seleccionada en la lista de farmaceutas.
     */
    public void edit(int row) {
        Farmaceutico farmaceutico = model.getList().get(row);
        try {
            model.setMode(Main.MODE_EDIT);
            model.setCurrent(Service.instance().readFarmaceutico(farmaceutico));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Elimina el farmaceuta actualmente seleccionado en el modelo.
     *
     * @throws Exception si ocurre un error durante la eliminación.
     */
    public void delete() throws Exception {
        Service.instance().deleteFarmaceutico(model.getCurrent());
        search(model.getFilter());
    }

    /**
     * Limpia el formulario de edición/creación,
     * restableciendo el modo a "CREAR" y el farmaceuta actual a uno vacío.
     */
    public void clear() {
        model.setMode(Main.MODE_CREATE);
        model.setCurrent(new Farmaceutico(0, "", ""));
    }
}
