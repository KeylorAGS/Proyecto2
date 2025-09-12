package presentation.Dashboard;

import presentation.Logic.Medicamento;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dashboard_View1 implements PropertyChangeListener {
    // Componentes generados por UI Designer
    private JPanel panel;
    private JPanel datosPanel;
    private JPanel medicamentosPanel;
    private JPanel recetasPanel;
    private JComboBox desdeAños;
    private JComboBox desdeFecha;
    private JComboBox hastaAño;
    private JComboBox hastaFecha;
    private JComboBox medicamentosBox;
    private JButton Seleccionar;
    private JButton todos;
    private JTable table1;
    private JButton borrarUno;
    private JButton borrarTodos;
    private JLabel dedeLBL;
    private JLabel hastaLBL;
    private JLabel medicamentosLBL;

    // Paneles para gráficos
    private ChartPanel chartPanelLineas;
    private ChartPanel chartPanelPastel;

    // Controlador y modelo
    private DashboardController controller;
    private DashboardModel model;

    public Dashboard_View1() {
        initializeComponents();
        setupEventListeners();
    }

    private void initializeComponents() {
        // Configurar la tabla con el modelo de datos
        setupTable();

        // Inicializar los gráficos en sus respectivos paneles
        initializeCharts();

        // Configurar los ComboBoxes
        setupComboBoxes();
    }

    private void setupTable() {
        // Configurar modelo de la tabla
        String[] columnas = {"Medicamento", "2025-8", "2025-9", "2025-10"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        table1.setModel(tableModel);

        // Configurar selección de fila
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initializeCharts() {
        // Inicializar gráfico de líneas para medicamentos
        initializeMedicamentosChart();

        // Inicializar gráfico de pastel para recetas
        initializeRecetasChart();
    }

    private void initializeMedicamentosChart() {
        // Crear dataset inicial vacío
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Crear gráfico de líneas
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Medicamentos", "Mes", "Cantidad", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        // Personalizar el gráfico
        CategoryPlot plot = lineChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        plot.setRenderer(renderer);

        // Crear panel del gráfico y agregarlo al panel de medicamentos
        chartPanelLineas = new ChartPanel(lineChart);
        medicamentosPanel.setLayout(new BorderLayout());
        medicamentosPanel.add(chartPanelLineas, BorderLayout.CENTER);
    }

    private void initializeRecetasChart() {
        // Crear dataset inicial vacío
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        // Crear gráfico de pastel
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Recetas", pieDataset, true, true, false);

        // Personalizar colores del gráfico de pastel
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setSectionPaint("CONFECCIONADA", Color.YELLOW);
        piePlot.setSectionPaint("PROCESO", Color.BLUE);
        piePlot.setSectionPaint("LISTA", Color.GREEN);
        piePlot.setSectionPaint("ENTREGADA", Color.RED);

        // Crear panel del gráfico y agregarlo al panel de recetas
        chartPanelPastel = new ChartPanel(pieChart);
        recetasPanel.setLayout(new BorderLayout());
        recetasPanel.add(chartPanelPastel, BorderLayout.CENTER);
    }

    private void setupComboBoxes() {
        // Configurar ComboBoxes de años
        String[] años = {"2024", "2025", "2026"};
        DefaultComboBoxModel<String> añosModel = new DefaultComboBoxModel<>(años);
        desdeAños.setModel(añosModel);
        hastaAño.setModel(añosModel);

        // Seleccionar 2025 por defecto
        desdeAños.setSelectedItem("2025");
        hastaAño.setSelectedItem("2025");

        // Configurar ComboBoxes de meses
        String[] meses = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        DefaultComboBoxModel<String> mesesModel1 = new DefaultComboBoxModel<>(meses);
        DefaultComboBoxModel<String> mesesModel2 = new DefaultComboBoxModel<>(meses);
        desdeFecha.setModel(mesesModel1);
        hastaFecha.setModel(mesesModel2);

        // Seleccionar rango por defecto (mes 8 a 10)
        desdeFecha.setSelectedItem("8");
        hastaFecha.setSelectedItem("10");

        // Configurar ComboBox de medicamentos (se llenará desde el modelo)
        medicamentosBox.setModel(new DefaultComboBoxModel<>());
    }

    private void setupEventListeners() {
        // Listener para selección individual
        Seleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });

        // Listener para seleccionar todos los medicamentos
        todos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.seleccionarTodosMedicamentos();
                }
            }
        });

        // Listener para borrar uno
        borrarUno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow >= 0) {
                    DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
                    String medicamento = (String) tableModel.getValueAt(selectedRow, 0);

                    List<String> medicamentosActuales = new ArrayList<>(model.getMedicamentosSeleccionados());
                    medicamentosActuales.remove(medicamento);

                    if (controller != null) {
                        controller.actualizarFiltroMedicamentos(medicamentosActuales);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Por favor seleccione una fila para eliminar.");
                }
            }
        });

        // Listener para borrar todos
        borrarTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.deseleccionarTodosMedicamentos();
                }
            }
        });
    }

    private void aplicarFiltros() {
        if (controller == null) return;

        // Construir fechas desde los ComboBoxes separados
        String añoDesde = (String) desdeAños.getSelectedItem();
        String mesDesde = (String) desdeFecha.getSelectedItem();
        String fechaDesde = añoDesde + "-" + mesDesde;

        String añoHasta = (String) hastaAño.getSelectedItem();
        String mesHasta = (String) hastaFecha.getSelectedItem();
        String fechaHasta = añoHasta + "-" + mesHasta;

        // Obtener medicamento seleccionado
        List<String> medicamentosSeleccionados = new ArrayList<>();
        String medicamentoSeleccionado = (String) medicamentosBox.getSelectedItem();

        if (medicamentoSeleccionado != null && !medicamentoSeleccionado.isEmpty() && !medicamentoSeleccionado.equals("")) {
            medicamentosSeleccionados.add(medicamentoSeleccionado);
        }

        // Aplicar filtros
        controller.actualizarFiltroFecha(fechaDesde, fechaHasta);
        controller.actualizarFiltroMedicamentos(medicamentosSeleccionados);
    }

    private void actualizarGraficoLineas() {
        if (model == null) return;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        var datosFiltrados = model.getDatosFiltrados();

        for (String medicamento : datosFiltrados.keySet()) {
            Map<String, Integer> datos = datosFiltrados.get(medicamento);
            for (String mes : model.getMesesDisponibles()) {
                if (model.esMesEnRango(mes)) {
                    dataset.addValue(datos.getOrDefault(mes, 0), medicamento, mes);
                }
            }
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Medicamentos", "Mes", "Cantidad", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        // Personalizar el gráfico
        CategoryPlot plot = lineChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        plot.setRenderer(renderer);

        chartPanelLineas.setChart(lineChart);
    }

    private void actualizarGraficoPastel() {
        if (model == null) return;

        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> recetasPorEstado = model.getRecetasPorEstado();

        int total = recetasPorEstado.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : recetasPorEstado.entrySet()) {
            double porcentaje = total > 0 ? (entry.getValue() * 100.0) / total : 0;
            String label = entry.getKey().toUpperCase() + " (" + String.format("%.0f%%", porcentaje) + ")";
            dataset.setValue(label, entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Recetas", dataset, true, true, false);

        // Personalizar colores
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("CONFECCIONADA (21%)", Color.YELLOW);
        plot.setSectionPaint("PROCESO (29%)", Color.BLUE);
        plot.setSectionPaint("LISTA (29%)", Color.GREEN);
        plot.setSectionPaint("ENTREGADA (21%)", Color.RED);

        chartPanelPastel.setChart(pieChart);
    }

    private void actualizarComboBoxes() {
        if (model == null) return;

        // Actualizar ComboBox de medicamentos
        DefaultComboBoxModel<String> medicamentosModel = new DefaultComboBoxModel<>();
        medicamentosModel.addElement(""); // Opción vacía

        for (Medicamento med : model.getMedicamentosDisponibles()) {
            medicamentosModel.addElement(med.getNombre());
        }

        medicamentosBox.setModel(medicamentosModel);
    }

    private void actualizarTablaDatos() {
        if (model == null) return;

        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        tableModel.setRowCount(0);

        var datosFiltrados = model.getDatosFiltrados();
        for (String medicamento : datosFiltrados.keySet()) {
            Map<String, Integer> datos = datosFiltrados.get(medicamento);
            Object[] fila = {
                    medicamento,
                    datos.getOrDefault("2025-8", 0),
                    datos.getOrDefault("2025-9", 0),
                    datos.getOrDefault("2025-10", 0)
            };
            tableModel.addRow(fila);
        }
    }

    // Métodos para establecer controlador y modelo
    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case DashboardModel.MEDICAMENTOS_MES:
                actualizarGraficoLineas();
                actualizarTablaDatos();
                break;
            case DashboardModel.RECETAS_ESTADO:
                actualizarGraficoPastel();
                break;
            case DashboardModel.MEDICAMENTOS_DISPONIBLES:
            case DashboardModel.MESES_DISPONIBLES:
                actualizarComboBoxes();
                break;
            case DashboardModel.FILTROS:
                actualizarGraficoLineas();
                actualizarTablaDatos();
                break;
        }
    }

    // Getter para el panel principal
    public JPanel getPanel() {
        return panel;
    }
}