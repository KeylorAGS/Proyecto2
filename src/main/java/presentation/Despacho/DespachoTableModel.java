package presentation.Despacho;

import presentation.AbstractTableModel;
import presentation.Logic.Receta;

import java.util.List;

public class DespachoTableModel extends AbstractTableModel<Receta> implements javax.swing.table.TableModel {

    public DespachoTableModel(int[] cols, List<Receta> rows) { //Recibe la cantidad de columnas y filas(dependen de lo larga que sea la lista)
        super(cols, rows);
    }

    public static final int NOMBRE = 0;
    public static final int ESTADO = 1;
    public static final int IDPACIENTE = 2;
    public static final int IDDOCTOR = 3;

    @Override
    protected void initColNames() { //Nombre de las columnas
        colNames = new String[4];
        colNames[NOMBRE] = "Nombre";
        colNames[ESTADO] = "Estado";
        colNames[IDPACIENTE] = "Id Pasciente";
        colNames[IDDOCTOR] = "Id Doctor";
    }

    @Override
    protected Object getPropetyAt(Receta r, int col) {
        switch (cols[col]) {
            case NOMBRE:
                return r.getNombre();
            case ESTADO:
                return r.getEstado();
            case IDPACIENTE:
                return r.getIdPaciente();
            case IDDOCTOR:
                return r.getIdDoctor();
            default:
                return "";
        }
    }
}

