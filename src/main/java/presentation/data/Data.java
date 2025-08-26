package presentation.data;

import jakarta.xml.bind.annotation.*;
import presentation.Logic.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase contenedora para los datos principales del sistema.
 *
 * Esta clase sirve como raíz para la serialización y deserialización de datos
 * (XML en este caso, usando JAXB). Permite almacenar y recuperar listas de
 * {@link Medico} y {@link Farmaceutico}.
 *
 * Está anotada con {@link XmlRootElement} y {@link XmlAccessorType} para indicar
 * cómo se deben mapear los elementos a XML.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    /**
     * Lista de médicos registrados en el sistema.
     *
     * Se serializa en XML dentro de una etiqueta <Medicos>,
     * y cada médico individual dentro de una etiqueta <Medico>.
     */
    @XmlElementWrapper(name = "Medicos")
    @XmlElement(name = "Medico")
    private List<Medico> Medicos;

    /**
     * Lista de farmaceutas registrados en el sistema.
     *
     * Se serializa en XML dentro de una etiqueta <Farmaceuticos>,
     * y cada farmaceuta individual dentro de una etiqueta <Farmaceutico>.
     */
    @XmlElementWrapper(name = "Farmaceuticos")
    @XmlElement(name = "Farmaceutico")
    private List<Farmaceutico> Farmaceuticos;


    @XmlElementWrapper(name = "Pacientes")
    @XmlElement(name = "Paciente")
    private List<Paciente> Pacientes;

    @XmlElementWrapper(name = "Medicamentos")
    @XmlElement(name = "Medicamento")
    private List<Medicamento> Medicamentos;

    /**
     * Constructor por defecto.
     *
     * Inicializa las listas de médicos y farmaceutas como listas vacías.
     * Esto garantiza que nunca sean nulas al acceder a ellas.
     */
    public Data() {
        Medicos = new ArrayList<>();
        Farmaceuticos = new ArrayList<>();
        Pacientes = new ArrayList<>();
        Medicamentos = new ArrayList<>();
    }

    /**
     * Obtiene la lista de médicos.
     *
     * @return Lista de objetos {@link Medico}.
     */
    public List<Medico> getMedicos() {
        return Medicos;
    }

    /**
     * Obtiene la lista de farmaceutas.
     *
     * @return Lista de objetos {@link Farmaceutico}.
     */
    public List<Farmaceutico> getFarmaceuticos() {
        return Farmaceuticos;
    }

    public List<Paciente> getPacientes() { return Pacientes; }

    public List<Medicamento> getMedicamentos() { return Medicamentos; }
}
