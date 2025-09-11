package presentation.data;

import jakarta.xml.bind.annotation.*;
import presentation.Logic.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElementWrapper(name = "Medicos")
    @XmlElement(name = "Medico")
    private List<Medico> Medicos;

    @XmlElementWrapper(name = "Administradores")
    @XmlElement(name = "Administrador")
    private List<Administrador> administradores;

    @XmlElementWrapper(name = "Farmaceuticos")
    @XmlElement(name = "Farmaceutico")
    private List<Farmaceutico> Farmaceuticos;


    @XmlElementWrapper(name = "Pacientes")
    @XmlElement(name = "Paciente")
    private List<Paciente> Pacientes;

    @XmlElementWrapper(name = "Recetas")
    @XmlElement(name = "Receta")
    private List<Receta> recetas;


    public Data() {
        Medicos = new ArrayList<>();
        Farmaceuticos = new ArrayList<>();
        Pacientes = new ArrayList<>();
        medicamentos = new ArrayList<>();
        administradores = new ArrayList<>();
        prescripciones = new ArrayList<>();
        recetas = new ArrayList<>();
    }

    public List<Medico> getMedicos() {
        return Medicos;
    }

    public List<Administrador> getAdministradores() { return administradores; }

    public List<Farmaceutico> getFarmaceuticos() {
        return Farmaceuticos;
    }

    public List<Paciente> getPacientes() { return Pacientes; }

    //----------------------------Medicamento--------------------------
    private List<Medicamento> medicamentos;

    public List<Medicamento> getMedicamentos() {return medicamentos;}

    //----------------------------Receta--------------------------

    public List<Receta> getRecetas() {return recetas;}

    //----------------------------Prescripcion--------------------------
    private List<Prescripcion> prescripciones;

    public List<Prescripcion> getPrescripciones() {return prescripciones;}
}
