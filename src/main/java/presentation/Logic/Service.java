package presentation.Logic;

import presentation.data.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase de servicio central para la gestión de entidades del sistema.
 *
 * Implementa el patrón Singleton para asegurar una única instancia en toda la aplicación.
 *
 * Funcionalidades principales:
 * - Gestiona los objetos de negocio {@link Medico} y {@link Farmaceutico}.
 * - Realiza operaciones CRUD (crear, leer, actualizar, borrar).
 * - Permite búsquedas filtradas por distintos criterios.
 * - Administra la persistencia de los datos mediante {@link XmlPersister}.
 */
public class Service {
    /** Instancia única del servicio (Singleton). */
    private static Service theInstance;

    /** Contenedor principal de datos (médicos y farmacéuticos). */
    private Data data;

    /**
     * Devuelve la instancia única del servicio.
     * Si aún no existe, la crea.
     *
     * @return Instancia de {@link Service}.
     */
    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    /**
     * Constructor privado (patrón Singleton).
     * Intenta cargar los datos desde el archivo XML.
     * Si no existe o ocurre un error, inicializa una nueva instancia de {@link Data}.
     */
    private Service() {
        try {
            data = XmlPersister.instance().load();
        } catch (Exception e) {
            data = new Data();
        }
    }

    /**
     * Detiene el servicio y almacena los datos en el archivo XML.
     */
    public void stop() {
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ================= MÉDICOS ================= //

    /**
     * Crea un nuevo médico.
     *
     * @param medico Médico a crear.
     * @throws Exception si ya existe un médico con el mismo id.
     */
    public void createMedico(Medico medico) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(i -> i.getId() == medico.getId())
                .findFirst().orElse(null);

        if (result == null) {
            data.getMedicos().add(medico);
        } else {
            throw new Exception("Medico ya existe");
        }
    }

    /**
     * Busca un médico por su id.
     *
     * @param medico Objeto con el id del médico a buscar.
     * @return El médico encontrado.
     * @throws Exception si no existe el médico.
     */
    public Medico readMedico(Medico medico) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(i -> i.getId() == medico.getId())
                .findFirst().orElse(null);

        if (result != null) {
            return result;
        } else {
            throw new Exception("Medico no existe");
        }
    }

    /**
     * Actualiza los datos de un médico.
     *
     * @param medico Médico con la información actualizada.
     * @throws Exception si no existe el médico.
     */
    public void updateMedico(Medico medico) throws Exception {
        try {
            Medico result = this.readMedico(medico);
            data.getMedicos().remove(result);
            data.getMedicos().add(medico);
        } catch (Exception ex) {
            throw new Exception("Medico no existe");
        }
    }

    /**
     * Elimina un médico existente.
     *
     * @param medico Médico a eliminar.
     * @throws Exception si no existe el médico.
     */
    public void deleteMedico(Medico medico) throws Exception {
        Medico result = this.readMedico(medico);
        data.getMedicos().remove(result);
    }

    /**
     * Busca médicos aplicando un filtro opcional por id, nombre o especialidad.
     *
     * @param filter Objeto {@link Medico} con los criterios de búsqueda.
     * @return Lista de médicos que cumplen con los criterios.
     */
    public List<Medico> searchMedico(Medico filter) {
        return data.getMedicos().stream()
                .filter(m -> (filter.getId() == 0 || m.getId() == filter.getId()) &&
                        (filter.getNombre() == null || m.getNombre().contains(filter.getNombre())) &&
                        (filter.getEspecialidad() == null || m.getEspecialidad().contains(filter.getEspecialidad())))
                .sorted(Comparator.comparing(Medico::getId))
                .collect(Collectors.toList());
    }

    // ================= FARMACÉUTICOS ================= //

    /**
     * Crea un nuevo farmacéutico.
     *
     * @param farmaceutico Farmacéutico a crear.
     * @throws Exception si ya existe un farmacéutico con el mismo id.
     */
    public void createFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(i -> i.getId() == farmaceutico.getId())
                .findFirst().orElse(null);

        if (result == null) {
            data.getFarmaceuticos().add(farmaceutico);
        } else {
            throw new Exception("Farmaceutico ya existe");
        }
    }

    /**
     * Busca un farmacéutico por su id.
     *
     * @param farmaceutico Objeto con el id del farmacéutico a buscar.
     * @return El farmacéutico encontrado.
     * @throws Exception si no existe el farmacéutico.
     */
    public Farmaceutico readFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(i -> i.getId() == farmaceutico.getId())
                .findFirst().orElse(null);

        if (result != null) {
            return result;
        } else {
            throw new Exception("Farmaceutico no existe");
        }
    }

    /**
     * Actualiza los datos de un farmacéutico.
     *
     * @param farmaceutico Farmacéutico con la información actualizada.
     * @throws Exception si no existe el farmacéutico.
     */
    public void updateFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        try {
            Farmaceutico result = this.readFarmaceutico(farmaceutico);
            data.getFarmaceuticos().remove(result);
            data.getFarmaceuticos().add(farmaceutico);
        } catch (Exception ex) {
            throw new Exception("Farmaceutico no existe");
        }
    }

    /**
     * Elimina un farmacéutico existente.
     *
     * @param farmaceutico Farmacéutico a eliminar.
     * @throws Exception si no existe el farmacéutico.
     */
    public void deleteFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result = this.readFarmaceutico(farmaceutico);
        data.getFarmaceuticos().remove(result);
    }

    /**
     * Busca farmacéuticos aplicando un filtro opcional por id, nombre o clave.
     *
     * @param filter Objeto {@link Farmaceutico} con los criterios de búsqueda.
     * @return Lista de farmacéuticos que cumplen con los criterios.
     */
    public List<Farmaceutico> searchFarmaceutico(Farmaceutico filter) {
        return data.getFarmaceuticos().stream()
                .filter(m -> (filter.getId() == 0 || m.getId() == filter.getId()) &&
                        (filter.getNombre() == null || m.getNombre().contains(filter.getNombre())) &&
                        (filter.getClave() == null || m.getClave().contains(filter.getClave())))
                .sorted(Comparator.comparing(Farmaceutico::getId))
                .collect(Collectors.toList());
    }

    //Buscar Usuario
    public Usuario login(int id, String clave) {
        for (Medico medico : data.getMedicos()) {
            if (medico.getId() == id && medico.getClave().equals(clave)) { return medico; }
        }
        for (Farmaceutico farmaceutico : data.getFarmaceuticos()) {
            if (farmaceutico.getId() == id && farmaceutico.getClave().equals(clave)) { return farmaceutico; }
        }
        return null;
    }



    // ================= Pacientes ================= //


    public void createPaciente(Paciente paciente) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(i -> i.getId() == paciente.getId())
                .findFirst().orElse(null);

        if (result == null) {
            data.getPacientes().add(paciente);
        } else {
            throw new Exception("Paciente ya existe");
        }
    }

    public Paciente readPaciente(Paciente paciente) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(i -> i.getId() == paciente.getId())
                .findFirst().orElse(null);

        if (result != null) {
            return result;
        } else {
            throw new Exception("Paciente no existe");
        }
    }

    public void updatePaciente(Paciente paciente) throws Exception {
        try {
            Paciente result = this.readPaciente(paciente);
            data.getPacientes().remove(result);
            data.getPacientes().add(paciente);
        } catch (Exception ex) {
            throw new Exception("Paciente no existe");
        }
    }

    public void deletePaciente(Paciente paciente) throws Exception {
        Paciente result = this.readPaciente(paciente);
        data.getFarmaceuticos().remove(result);
    }

    public List<Paciente> searchPaciente(Paciente filter) {
        return data.getPacientes().stream()
                .filter(p -> (filter.getId() == 0 || p.getId() == filter.getId()) &&
                        (filter.getNombre() == null || p.getNombre().toLowerCase().contains(filter.getNombre().toLowerCase())) &&
                        (filter.getFechaNacimiento() == null || p.getFechaNacimiento().equals(filter.getFechaNacimiento())) &&
                        (filter.getTelefono() == null || p.getTelefono().contains(filter.getTelefono())))
                .sorted(Comparator.comparing(Paciente::getId))
                .collect(Collectors.toList());
    }

}
