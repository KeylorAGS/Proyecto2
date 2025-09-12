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

public class Dashboard_View implements PropertyChangeListener {
    private JPanel panel;
    private JPanel DatosPanel;
    private JPanel medicamentosPanel;
    private JPanel RecetasPanel;
    private JComboBox<String> comboMesDesde;
    private JComboBox<String> comboMesHasta;
    private JComboBox<String> comboMedicamento1;
    private JComboBox<String> comboMedicamento2;
    private JComboBox<String> comboMedicamento3;
    private JButton seleccionUnica;
    private JButton seleccionMultiple;
    private JTable tableDatos;
    private JButton borrarUno;
    private JButton borrarTodas;

    // Paneles para gráficos
    private ChartPanel chartPanelLineas;
    private ChartPanel chartPanelPastel;

    private DashboardController controller;
    private DashboardModel model;

    public Dashboard_View() {
        initComponents();
        setupEventListeners();
    }

    private void initComponents() {
        panel = new JPanel(new BorderLayout());

        // Panel superior con controles
        JPanel controlPanel = createControlPanel();
        panel.add(controlPanel, BorderLayout.NORTH);

        // Panel central con gráficos
        JPanel chartsPanel = createChartsPanel();
        panel.add(chartsPanel, BorderLayout.CENTER);
    }

    private JPanel createControlPanel() {
        JPanel mainControlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Panel de Datos
        JPanel datosPanel = new JPanel(new GridBagLayout());
        datosPanel.setBorder(BorderFactory.createTitledBorder("Datos"));
        GridBagConstraints datosGbc = new GridBagConstraints();
        datosGbc.insets = new Insets(2, 2, 2, 2);

        // Filtros de fecha
        datosGbc.gridx = 0; datosGbc.gridy = 0;
        datosPanel.add(new JLabel("Desde:"), datosGbc);
        datosGbc.gridx = 1;
        comboMesDesde = new JComboBox<>();
        comboMesDesde.setPreferredSize(new Dimension(80, 25));
        datosPanel.add(comboMesDesde, datosGbc);

        datosGbc.gridx = 0; datosGbc.gridy = 1;
        datosPanel.add(new JLabel("Hasta:"), datosGbc);
        datosGbc.gridx = 1;
        comboMesHasta = new JComboBox<>();
        comboMesHasta.setPreferredSize(new Dimension(80, 25));
        datosPanel.add(comboMesHasta, datosGbc);

        // Label Medicamentos
        datosGbc.gridx = 0; datosGbc.gridy = 2;
        datosGbc.gridwidth = 2;
        datosPanel.add(new JLabel("Medicamentos"), datosGbc);

        // ComboBoxes de medicamentos
        datosGbc.gridx = 0; datosGbc.gridy = 3;
        datosGbc.gridwidth = 1;
        comboMedicamento1 = new JComboBox<>();
        comboMedicamento1.setPreferredSize(new Dimension(120, 25));
        datosPanel.add(comboMedicamento1, datosGbc);

        datosGbc.gridx = 1;
        comboMedicamento2 = new JComboBox<>();
        comboMedicamento2.setPreferredSize(new Dimension(120, 25));
        datosPanel.add(comboMedicamento2, datosGbc);

        datosGbc.gridx = 0; datosGbc.gridy = 4;
        comboMedicamento3 = new JComboBox<>();
        comboMedicamento3.setPreferredSize(new Dimension(120, 25));
        datosPanel.add(comboMedicamento3, datosGbc);

        // Botones de selección
        datosGbc.gridx = 1; datosGbc.gridy = 4;
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));

        seleccionUnica = new JButton("✓");
        seleccionUnica.setPreferredSize(new Dimension(25, 25));
        seleccionUnica.setBackground(Color.GREEN);
        botonesPanel.add(seleccionUnica);

        seleccionMultiple = new JButton("✓");
        seleccionMultiple.setPreferredSize(new Dimension(25, 25));
        seleccionMultiple.setBackground(Color.GREEN);
        botonesPanel.add(seleccionMultiple);

        datosPanel.add(botonesPanel, datosGbc);

        // Panel central con tabla
        JPanel tablaPanel = new JPanel(new BorderLayout());
        String[] columnas = {"Medicamento", "2025-8", "2025-9", "2025-10"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        tableDatos = new JTable(tableModel);
        tableDatos.setPreferredScrollableViewportSize(new Dimension(300, 100));
        JScrollPane scrollPane = new JScrollPane(tableDatos);
        tablaPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel derecho con botones de borrar
        JPanel borrarPanel = new JPanel(new GridLayout(2, 1, 2, 2));

        borrarUno = new JButton("●");
        borrarUno.setPreferredSize(new Dimension(25, 25));
        borrarUno.setBackground(Color.RED);
        borrarUno.setForeground(Color.WHITE);
        borrarPanel.add(borrarUno);

        borrarTodas = new JButton("●");
        borrarTodas.setPreferredSize(new Dimension(25, 25));
        borrarTodas.setBackground(Color.RED);
        borrarTodas.setForeground(Color.WHITE);
        borrarPanel.add(borrarTodas);

        // Ensamblar panel de control principal
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainControlPanel.add(datosPanel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        mainControlPanel.add(tablaPanel, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        mainControlPanel.add(borrarPanel, gbc);

        return mainControlPanel;
    }

    private JPanel createChartsPanel() {
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        // Panel para gráfico de medicamentos (líneas)
        medicamentosPanel = new JPanel(new BorderLayout());
        medicamentosPanel.setBorder(BorderFactory.createTitledBorder("Medicamentos"));

        // Crear gráfico inicial con datos de ejemplo
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
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

        chartPanelLineas = new ChartPanel(lineChart);
        medicamentosPanel.add(chartPanelLineas, BorderLayout.CENTER);

        // Panel para gráfico de recetas (pastel)
        RecetasPanel = new JPanel(new BorderLayout());
        RecetasPanel.setBorder(BorderFactory.createTitledBorder("Recetas"));

        // Crear gráfico de pastel inicial
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Recetas", pieDataset, true, true, false);

        // Personalizar colores del gráfico de pastel
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setSectionPaint("CONFECCIONADA", Color.YELLOW);
        piePlot.setSectionPaint("PROCESO", Color.BLUE);
        piePlot.setSectionPaint("LISTA", Color.GREEN);
        piePlot.setSectionPaint("ENTREGADA", Color.RED);

        chartPanelPastel = new ChartPanel(pieChart);
        RecetasPanel.add(chartPanelPastel, BorderLayout.CENTER);

        chartsPanel.add(medicamentosPanel);
        chartsPanel.add(RecetasPanel);

        return chartsPanel;
    }

    private void setupEventListeners() {
        seleccionUnica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });

        seleccionMultiple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.seleccionarTodosMedicamentos();
                }
            }
        });

        borrarUno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableDatos.getSelectedRow();
                if (selectedRow >= 0) {
                    DefaultTableModel tableModel = (DefaultTableModel) tableDatos.getModel();
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

        borrarTodas.addActionListener(new ActionListener() {
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

        String mesDesde = (String) comboMesDesde.getSelectedItem();
        String mesHasta = (String) comboMesHasta.getSelectedItem();

        List<String> medicamentosSeleccionados = new ArrayList<>();
        String med1 = (String) comboMedicamento1.getSelectedItem();
        String med2 = (String) comboMedicamento2.getSelectedItem();
        String med3 = (String) comboMedicamento3.getSelectedItem();

        if (med1 != null && !med1.isEmpty() && !med1.equals("")) medicamentosSeleccionados.add(med1);
        if (med2 != null && !med2.isEmpty() && !med2.equals("")) medicamentosSeleccionados.add(med2);
        if (med3 != null && !med3.isEmpty() && !med3.equals("")) medicamentosSeleccionados.add(med3);

        controller.actualizarFiltroFecha(mesDesde, mesHasta);
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

        // Actualizar combos de fechas
        comboMesDesde.removeAllItems();
        comboMesHasta.removeAllItems();

        // Agregar meses específicos como en la imagen
        String[] mesesEspecificos = {"2025-8", "2025-9", "2025-10"};
        for (String mes : mesesEspecificos) {
            comboMesDesde.addItem(mes);
            comboMesHasta.addItem(mes);
        }

        // Seleccionar valores por defecto
        if (comboMesDesde.getItemCount() > 0) {
            comboMesDesde.setSelectedIndex(0);
            comboMesHasta.setSelectedIndex(comboMesHasta.getItemCount() - 1);
        }

        // Actualizar combos de medicamentos
        comboMedicamento1.removeAllItems();
        comboMedicamento2.removeAllItems();
        comboMedicamento3.removeAllItems();

        comboMedicamento1.addItem("");
        comboMedicamento2.addItem("");
        comboMedicamento3.addItem("");

        for (Medicamento med : model.getMedicamentosDisponibles()) {
            comboMedicamento1.addItem(med.getNombre());
            comboMedicamento2.addItem(med.getNombre());
            comboMedicamento3.addItem(med.getNombre());
        }
    }

    private void actualizarTablaDatos() {
        if (model == null) return;

        DefaultTableModel tableModel = (DefaultTableModel) tableDatos.getModel();
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

    public Component getPanel() {
        return panel;
    }
}