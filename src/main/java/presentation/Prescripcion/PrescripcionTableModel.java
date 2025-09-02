package presentation.Prescripcion;

import presentation.Logic.Medicamento;
import presentation.AbstractTableModel;
import java.util.List;

public class PrescripcionTableModel extends AbstractTableModel<Medicamento> implements javax.swing.table.TableModel {
    public PrescripcionTableModel(int[] cols, List<Medicamento> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int PRESENTACION = 2;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
        colNames[PRESENTACION] = "Presentacion";
    }
    @Override
    protected Object getPropetyAt(Medicamento e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case NOMBRE:
                return e.getNombre();
            case PRESENTACION:
                return e.getPresentacion();
            default:
                return "";
        }
    }
}
