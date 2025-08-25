package presentation.Medicos;

import presentation.AbstractTableModel;
import presentation.Logic.Medico;

public class MedicosTableModel extends AbstractTableModel <Medico> implements javax.swing.table.TableModel {
    public static final int ID=0;
    public static final int NOMBRE=1;
    public static final int ESPECIALIDAD=2;

    public MedicosTableModel(int[] cols, java.util.List<Medico> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(Medico e, int col) {
        switch (cols[col]){
            case ID: return e.getId();
            case NOMBRE: return e.getNombre();
            case ESPECIALIDAD: return e.getEspecialidad();

            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[4];
        colNames[ID]= "Id";
        colNames[NOMBRE]= "Nombre";
        colNames[ESPECIALIDAD]= "Especialidad";
    }
}
