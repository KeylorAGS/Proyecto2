package presentation.Logic;

public class Farmaceutico extends Usuario {

    public Farmaceutico() {
        this("", "", "");
    }

    public Farmaceutico(String id, String nombre, String clave) {
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

