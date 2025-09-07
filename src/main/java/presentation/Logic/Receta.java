package presentation.Logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
public class Receta {
    @XmlID
    private String nombre;
    private String presentacion;
    private String cantidad;
    private String indicaciones;
    private String duracion;
    private String idPaciente;
    private String idDoctor;

    public Receta(String cantidad, String nombre, String indicaciones, String duracion, String idPaciente, String idDoctor, String presentacion) {
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracion = duracion;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
    }

    public Receta(){this("", "", "", "", "", "", "");}

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
    }
}
