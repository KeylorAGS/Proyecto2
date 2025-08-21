package presentation.Logic;
import presentation.data.*;

import java.util.*;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service(){
        try{
            data= XmlPersister.instance().load();
        }
        catch(Exception e){
            data =  new Data();
        }
    }

    public void stop(){
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//================= Medicos ============

    public void createMedico(Medico medico) throws Exception {
        Medico result = data.getMedicos().stream().filter(i -> i.getId() == medico.getId()).findFirst().orElse(null);
        if (result == null) {
            data.getMedicos().add(medico);
        } else {
            throw new Exception("Medico ya existe");
        }
    }

    public Medico readMedico(Medico medico) throws Exception {
        Medico result = data.getMedicos().stream().filter(i -> i.getId() == medico.getId()).findFirst().orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Medico no existe");
        }
    }

    public void updateMedico(Medico medico) throws Exception {
        Medico result;
        try {
            result = this.readMedico(medico);
            data.getMedicos().remove(result);
            data.getMedicos().add(medico);
        } catch (Exception ex) {
            throw new Exception("Medico no existe");
        }
    }
    public void deleteMedico(Medico medico) throws Exception {
        Medico result = this.readMedico(medico);
        data.getMedicos().remove(result);
    }
    public List<Medico> searchMedico(Medico filter) {
        return data.getMedicos().stream()
                .filter(m -> (filter.getId() == 0 || m.getId() == filter.getId()) &&
                             (filter.getNombre() == null || m.getNombre().contains(filter.getNombre())) &&
                             (filter.getEspecialidad() == null || m.getEspecialidad().contains(filter.getEspecialidad())))
                .sorted(Comparator.comparing(Medico::getId))
                .collect(Collectors.toList());
    }

    //================= Farmaceuticos ============

    public void createFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream().filter(i -> i.getId() == farmaceutico.getId()).findFirst().orElse(null);
        if (result == null) {
            data.getFarmaceuticos().add(farmaceutico);
        } else {
            throw new Exception("farmaceutico ya existe");
        }
    }

    public Farmaceutico readFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream().filter(i -> i.getId() == farmaceutico.getId()).findFirst().orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Farmaceutico no existe");
        }
    }

    public void updateFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result;
        try {
            result = this.readFarmaceutico(farmaceutico);
            data.getFarmaceuticos().remove(result);
            data.getFarmaceuticos().add(farmaceutico);
        } catch (Exception ex) {
            throw new Exception("Farmaceutico no existe");
        }
    }
    public void deleteFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result = this.readFarmaceutico(farmaceutico);
        data.getFarmaceuticos().remove(result);
    }
    public List<Farmaceutico> searchFarmaceutico(Farmaceutico filter) {
        return data.getFarmaceuticos().stream()
                .filter(m -> (filter.getId() == 0 || m.getId() == filter.getId()) &&
                        (filter.getNombre() == null || m.getNombre().contains(filter.getNombre())) &&
                        (filter.getClave() == null || m.getClave().contains(filter.getClave())))
                .sorted(Comparator.comparing(Farmaceutico::getId))
                .collect(Collectors.toList());
    }
}
