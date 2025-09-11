package presentation.Logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Receta {
    @XmlID
    private String idPaciente;
    private String idDoctor;
    private String estado;
    private String idReceta;
    private List<Prescripcion>prescripciones;
    private String fecha;


    public Receta(String idReceta,String estado, String idPaciente, String idDoctor, String fecha) {
        this.idReceta = idReceta;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.estado = estado;
        this.prescripciones = new ArrayList<Prescripcion>();
        this.fecha = fecha;
    }

    public Receta(){
        this("","", "","","");
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

    public String getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(String idReceta) {
        this.idReceta = idReceta;
    }

    public List<Prescripcion> getPrescripcions() {
        return prescripciones;
    }

    public void setPrescripcions(List<Prescripcion> prescripcions) {
        this.prescripciones = prescripcions;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
