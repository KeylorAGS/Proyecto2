package presentation.Logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.util.List;

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
    private String estado;
    private List<String> listaMedicamento;


    public Receta(String nombre, String presentacion,String estado, String idPaciente, String idDoctor, String cantidad, String indicaciones, String duracion) {
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracion = duracion;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.estado = estado;
    }

    public Receta(){this("", "","", "", "", "", "", "");}

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getListaMedicamento() {
        return listaMedicamento;
    }

    public void setListaMedicamento(List<String> listaMedicamento) {
        this.listaMedicamento = listaMedicamento;
    }

}
