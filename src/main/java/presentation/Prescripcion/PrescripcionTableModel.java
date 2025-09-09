package presentation.Prescripcion;

import presentation.AbstractTableModel;
import presentation.Logic.Prescripcion;

import java.util.List;

public class PrescripcionTableModel extends AbstractTableModel<Prescripcion> implements javax.swing.table.TableModel {
    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int PRESENTACION = 2;

    public PrescripcionTableModel(int[] cols, List<Prescripcion> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(Prescripcion e, int col) {
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

    @Override
    protected void initColNames() {
        colNames = new String[3];
        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
        colNames[PRESENTACION] = "Presentación";
    }
}
