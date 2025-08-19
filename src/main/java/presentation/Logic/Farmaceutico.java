package presentation.Logic;

public class Farmaceutico extends Usuario {

    public Farmaceutico(int id, String nombre, String clave) {
        super(id, nombre, clave);
    }

    @Override
    public String toString() {
        return "Farmaceutico{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

