package presentation.Prescripcion;

import presentation.AbstractTableModel;
import presentation.Logic.Medicamento;
import javax.swing.table.TableModel;
import java.util.List;

public class PrescripcionTableModel extends AbstractTableModel <Medicamento> implements TableModel {

    public static final int CODIGO=0;
    public static final int NOMBRE=1;
    public static final int PRESENTACION=2;

    public PrescripcionTableModel(int[] cols, List<Medicamento> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(Medicamento e, int col) {
        switch (cols[col]){
            case CODIGO: return e.getCodigo();
            case NOMBRE: return e.getNombre();
            case PRESENTACION: return e.getPresentacion();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[3];
        colNames[CODIGO]= "Id";
        colNames[NOMBRE]= "Nombre";
        colNames[PRESENTACION]= "Presentacion";
    }
}
