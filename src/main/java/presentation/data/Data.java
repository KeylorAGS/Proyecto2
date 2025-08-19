package presentation.data;

import jakarta.xml.bind.annotation.*;
import presentation.Logic.Medico;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElementWrapper(name = "Medicos")
    @XmlElement(name = "Medico")
    private List<Medico> Medicos;

    public Data() {
        Medicos =  new ArrayList<>();
    }

    public List<Medico> getMedicos() {
        return Medicos;
    }

}
