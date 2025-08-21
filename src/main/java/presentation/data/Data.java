package presentation.data;

import jakarta.xml.bind.annotation.*;
import presentation.Logic.Farmaceutico;
import presentation.Logic.Medico;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElementWrapper(name = "Medicos")
    @XmlElement(name = "Medico")
    private List<Medico> Medicos;

    @XmlElementWrapper(name = "Farmaceuticos")
    @XmlElement(name = "Farmaceutico")
    private List<Farmaceutico> Farmaceuticos;

    public Data() {
        Medicos =  new ArrayList<>();
        Farmaceuticos =  new ArrayList<>();
    }

    public List<Medico> getMedicos() {
        return Medicos;
    }
    public List<Farmaceutico> getFarmaceuticos() {return Farmaceuticos;}
}
