package presentation.Logic;

public class Medicamento {
    private String codigo;
    private String nombre;
    private String presentacion;

    public  Medicamento(String codigo, String nombre, String presentacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.presentacion = presentacion;
    }

    public String getCodigo() {return codigo;}
    public String getNombre() {return nombre;}
    public String getPresentacion() {return presentacion;}

    public void setCodigo(String codigo) {this.codigo = codigo;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setPresentacion(String presentacion) {this.presentacion = presentacion;}

    @Override
    public String toString() {
        return "Medicamento{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", presentacion='" + presentacion + '\'' +
                '}';
    }
}
