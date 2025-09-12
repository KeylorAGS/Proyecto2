package presentation.Logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Receta {
    @XmlID
    private String idReceta;
    private String estado;
    private Paciente paciente;   // antes era idPaciente
    private Medico doctor;       // antes era idDoctor
    private List<Prescripcion> prescripciones;
    private String fecha;

    public Receta(String idReceta, String estado, Paciente paciente, Medico doctor, String fecha) {
        this.idReceta = idReceta;
        this.estado = estado;
        this.paciente = paciente;
        this.doctor = doctor;
        this.prescripciones = new ArrayList<>();
        this.fecha = fecha;
    }

    public Receta(){
        this("","", new Paciente(), new Medico(), "");
    }

    // Getters y setters originales
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getDoctor() { return doctor; }
    public void setDoctor(Medico doctor) { this.doctor = doctor; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getIdReceta() { return idReceta; }
    public void setIdReceta(String idReceta) { this.idReceta = idReceta; }

    public List<Prescripcion> getPrescripcions() { return prescripciones; }
    public void setPrescripcions(List<Prescripcion> prescripcions) { this.prescripciones = prescripcions; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // Métodos auxiliares para no romper código viejo
    public String getIdPaciente() {
        return (paciente != null) ? paciente.getId() : "";
    }

    public String getIdDoctor() {
        return (doctor != null) ? doctor.getId() : "";
    }

    // ===============================
    // NUEVOS SETTERS para compatibilidad con View_Prescripcion
    // ===============================
    public void setIdPaciente(String idPaciente) {
        if (this.paciente == null) this.paciente = new Paciente();
        this.paciente.setId(idPaciente);
    }

    public void setIdDoctor(String idDoctor) {
        if (this.doctor == null) this.doctor = new Medico();
        this.doctor.setId(idDoctor);
    }
}
