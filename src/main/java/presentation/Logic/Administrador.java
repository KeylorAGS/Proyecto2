package presentation.Logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
public class Administrador extends  Usuario {

    public Administrador() {
        this("", "", "");
    }

    public Administrador(String id, String nombre, String clave) {
        super(id, nombre, clave);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
