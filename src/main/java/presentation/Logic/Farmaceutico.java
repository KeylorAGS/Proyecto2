package presentation.Logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
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

