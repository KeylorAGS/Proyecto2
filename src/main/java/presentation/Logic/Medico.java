package presentation.Logic;

public class Medico extends Usuario {
    private String especialidad;

    public Medico(int id, String nombre, String clave, String especialidad) {
        super(id, nombre, clave);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especialidad='" + especialidad + '\'' +
                '}';
    }
}
