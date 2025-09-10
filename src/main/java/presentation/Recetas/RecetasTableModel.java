package presentation.Recetas;

import presentation.AbstractTableModel;
import presentation.Logic.Receta;

import javax.swing.table.TableModel;
import java.util.List;

//public class RecetasTableModel extends AbstractTableModel<Receta> implements TableModel {
//    public static final int NOMBRE = 0;
//    public static final int PRESENTACION = 1;
//    public static final int CANTIDAD = 2;
//    public static final int INDICACIONES = 3;
//    public static final int DURACION = 4;
//    public static final int IDPACIENTE = 5;
//    public static final int IDDOCTOR = 6;
//
//    public RecetasTableModel(int[] cols, List<Receta> rows) {
//        super(cols, rows);
//    }
//
//    @Override
//    protected Object getPropetyAt(Receta e, int col) {
//        switch (cols[col]) {
//            case NOMBRE:
//                return e.getNombre();
//            case PRESENTACION:
//                return e.getPresentacion();
//            case CANTIDAD:
//                return e.getCantidad();
//            case INDICACIONES:
//                return e.getIndicaciones();
//            case DURACION:
//                return e.getDuracion();
//            case IDPACIENTE:
//                return e.getIdPaciente();
//            case IDDOCTOR:
//                return e.getIdDoctor();
//            default:
//                return "";
//        }
//    }
//
//    @Override
//    protected void initColNames() {
//        colNames = new String[7];
//        colNames[NOMBRE] = "Nombre";
//        colNames[PRESENTACION] = "Presentacion";
//        colNames[CANTIDAD] = "Cantidad";
//        colNames[INDICACIONES] = "Indicaciones";
//        colNames[DURACION] = "Duracion";
//        colNames[IDPACIENTE] = "IdPaciente";
//        colNames[IDDOCTOR] = "IdDoctor";
//    }
//
