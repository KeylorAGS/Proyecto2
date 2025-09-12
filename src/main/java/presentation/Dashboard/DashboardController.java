package presentation.Dashboard;

import presentation.Logic.Medicamento;
import presentation.Logic.Receta;
import presentation.Logic.Service;

import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    private DashboardModel model;
    private Dashboard_View view;

    public DashboardController(Dashboard_View view, DashboardModel model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        inicializarDatos();
    }

    private void inicializarDatos() {
        try {
            // Cargar todas las recetas
            List<Receta> recetas = Service.instance().findAllRecetas();
            model.setRecetas(recetas);

            // Cargar todos los medicamentos disponibles
            List<Medicamento> medicamentos = Service.instance().findAll();
            model.setMedicamentosDisponibles(medicamentos);

            // Configurar filtros por defecto
            if (!model.getMesesDisponibles().isEmpty()) {
                model.setMesDesde(model.getMesesDisponibles().get(0));
                model.setMesHasta(model.getMesesDisponibles().get(model.getMesesDisponibles().size() - 1));
            }

            // Seleccionar todos los medicamentos por defecto
            List<String> todosMedicamentos = new ArrayList<>();
            for (Medicamento med : medicamentos) {
                todosMedicamentos.add(med.getNombre());
            }
            model.setMedicamentosSeleccionados(todosMedicamentos);

        } catch (Exception e) {
            System.err.println("Error al inicializar datos del dashboard: " + e.getMessage());
        }
    }

    public void actualizarFiltroFecha(String mesDesde, String mesHasta) {
        model.setMesDesde(mesDesde);
        model.setMesHasta(mesHasta);
        actualizarGraficos();
    }

    public void actualizarFiltroMedicamentos(List<String> medicamentosSeleccionados) {
        model.setMedicamentosSeleccionados(medicamentosSeleccionados);
        actualizarGraficos();
    }

    public void seleccionarTodosMedicamentos() {
        List<String> todosMedicamentos = new ArrayList<>();
        for (Medicamento med : model.getMedicamentosDisponibles()) {
            todosMedicamentos.add(med.getNombre());
        }
        model.setMedicamentosSeleccionados(todosMedicamentos);
    }

    public void deseleccionarTodosMedicamentos() {
        model.setMedicamentosSeleccionados(new ArrayList<>());
    }

    private void actualizarGraficos() {
        // Los gráficos se actualizarán automáticamente a través del PropertyChange
        // cuando el modelo cambie
    }

    public void refrescarDatos() {
        inicializarDatos();
    }

    // Métodos para obtener datos específicos para los gráficos
    public String[] getMesesParaGrafico() {
        return model.getMesesDisponibles().toArray(new String[0]);
    }

    public int[] getDatosMedicamento(String nombreMedicamento) {
        var datosFiltrados = model.getDatosFiltrados();
        var datosMedicamento = datosFiltrados.get(nombreMedicamento);

        if (datosMedicamento == null) {
            return new int[model.getMesesDisponibles().size()];
        }

        int[] datos = new int[model.getMesesDisponibles().size()];
        for (int i = 0; i < model.getMesesDisponibles().size(); i++) {
            String mes = model.getMesesDisponibles().get(i);
            datos[i] = datosMedicamento.getOrDefault(mes, 0);
        }

        return datos;
    }

    public String[] getEstadosRecetas() {
        return model.getRecetasPorEstado().keySet().toArray(new String[0]);
    }

    public int[] getCantidadesPorEstado() {
        var estados = getEstadosRecetas();
        int[] cantidades = new int[estados.length];

        for (int i = 0; i < estados.length; i++) {
            cantidades[i] = model.getRecetasPorEstado().get(estados[i]);
        }

        return cantidades;
    }

    public double[] getPorcentajesPorEstado() {
        int[] cantidades = getCantidadesPorEstado();
        int total = 0;

        for (int cantidad : cantidades) {
            total += cantidad;
        }

        double[] porcentajes = new double[cantidades.length];
        for (int i = 0; i < cantidades.length; i++) {
            porcentajes[i] = total > 0 ? (cantidades[i] * 100.0) / total : 0;
        }

        return porcentajes;
    }
}
