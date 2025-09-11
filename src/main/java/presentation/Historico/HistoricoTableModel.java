package presentation.Historico;

import presentation.AbstractTableModel;
import presentation.Logic.Medico;
import presentation.Logic.Receta;

import javax.swing.table.TableModel;
import java.util.List;

public class HistoricoTableModel extends AbstractTableModel <Receta> implements TableModel {

    public static final int ID_PACIENTE= 0;
    public static final int ID_DOCTOR= 1;
    public static final int ESTADO= 2;
    public static final int ID_RECETA= 3;

    public HistoricoTableModel(int[] cols, List<Receta> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(Receta e, int col) {
        switch (cols[col]){
            case ID_PACIENTE: return e.getIdPaciente();
            case ID_DOCTOR: return e.getIdDoctor();
            case ESTADO: return e.getEstado();
            case ID_RECETA: return e.getIdReceta();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[5];
        colNames[ID_PACIENTE]= "Id del paciente";
        colNames[ID_DOCTOR]= "Id del doctor";
        colNames[ESTADO]= "Estado";
        colNames [ID_RECETA] = "Id de la receta";
    }
}
