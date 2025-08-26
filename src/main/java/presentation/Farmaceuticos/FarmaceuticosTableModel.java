package presentation.Farmaceuticos;

import presentation.AbstractTableModel;
import presentation.Logic.Farmaceutico;
import presentation.Logic.Medico;

import javax.swing.table.TableModel;

public class FarmaceuticosTableModel extends AbstractTableModel <Farmaceutico> implements TableModel {
    public static final int ID=0;
    public static final int NOMBRE=1;

    public FarmaceuticosTableModel(int[] cols, java.util.List<Farmaceutico> rows) {
        super(cols, rows);
    }

    @Override
    protected Object getPropetyAt(Farmaceutico e, int col) {
        switch (cols[col]){
            case ID: return e.getId();
            case NOMBRE: return e.getNombre();
            default: return "";
        }
    }

    @Override
    protected void initColNames(){
        colNames = new String[2];
        colNames[ID]= "Id";
        colNames[NOMBRE]= "Nombre";
    }
}
