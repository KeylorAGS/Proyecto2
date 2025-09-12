package presentation.Dashboard;

import presentation.AbstractTableModel;

import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class DashboardTableModel extends AbstractTableModel<DashboardRowData> implements TableModel {

    public static final int MEDICAMENTO = 0;
    // Las columnas de meses se definirán dinámicamente a partir del índice 1

    private List<String> mesesColumnas;

    public DashboardTableModel(int[] cols, List<DashboardRowData> rows, List<String> mesesColumnas) {
        super(cols, rows);
        this.mesesColumnas = mesesColumnas;
    }

    @Override
    protected Object getPropetyAt(DashboardRowData rowData, int col) {
        if (cols[col] == MEDICAMENTO) {
            return rowData.getNombreMedicamento();
        } else {
            // Para las columnas de meses, el índice real es col-1 porque MEDICAMENTO es 0
            int mesIndex = cols[col] - 1;
            if (mesIndex >= 0 && mesIndex < rowData.getCantidadesPorMes().size()) {
                return rowData.getCantidadesPorMes().get(mesIndex);
            }
            return 0;
        }
    }

    @Override
    protected void initColNames() {
        // Verificar que mesesColumnas no sea null
        if (mesesColumnas == null) {
            mesesColumnas = new ArrayList<>();
        }

        // El tamaño será 1 (medicamento) + número de meses
        colNames = new String[1 + mesesColumnas.size()];

        // Primera columna siempre es "Medicamento"
        colNames[MEDICAMENTO] = "Medicamento";

        // Las siguientes columnas son los meses
        for (int i = 0; i < mesesColumnas.size(); i++) {
            colNames[i + 1] = mesesColumnas.get(i);
        }
    }

    /**
     * Crear array de columnas dinámicamente basado en el número de meses
     */
    public static int[] crearColumnasArray(int numeroMeses) {
        int[] cols = new int[1 + numeroMeses]; // 1 para medicamento + meses
        cols[0] = MEDICAMENTO;

        // Las columnas de meses van de 1 a numeroMeses
        for (int i = 1; i <= numeroMeses; i++) {
            cols[i] = i;
        }

        return cols;
    }

    @Override
    public int getColumnCount() {
        return colNames != null ? colNames.length : 1;
    }

    public List<String> getMesesColumnas() {
        return mesesColumnas;
    }
}