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

            // Si el XML existe pero está vacío, precargar datos de ejemplo
            if (data.getMedicos().isEmpty() && data.getFarmaceuticos().isEmpty()
                    && data.getPacientes().isEmpty()) {
                System.out.println("Hospital.xml está vacío. Precargando datos de ejemplo...");
                precargarDatos();
                stop(); // Guardar de inmediato en XML
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar Hospital.xml. Creando datos iniciales...");
            data = new Data();
            precargarDatos();
            stop();
        }
    }

    private void precargarDatos() { //Este metodo sirve para darle datos al xml en caso de estar vacio, porque sino como lo probamos

        Medico medico = new Medico();
        medico.setId("M001");
        medico.setNombre("Dr. Gregory House");
        medico.setEspecialidad("Diagnóstico");
        medico.setClave("M001");

        Farmaceutico farmaceutico = new Farmaceutico();
        farmaceutico.setId("F001");
        farmaceutico.setNombre("Dra. Gray");
        farmaceutico.setClave("F001");

        data.getMedicos().add(medico);
        data.getFarmaceuticos().add(farmaceutico);
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
        Medico result = data.getMedicos().stream().filter(i->i.getId().equals(medico.getId())).findFirst().orElse(null);
        if (result==null) data.getMedicos().add(medico);
        else throw new Exception("Cliente ya existe");
    }

    /**
     * Busca un médico por su id.
     *
     * @param medico Objeto con el id del médico a buscar.
     * @return El médico encontrado.
     * @throws Exception si no existe el médico.
     */
    public Medico readMedico(Medico medico) throws Exception {
        Medico result = data.getMedicos().stream().filter(i->i.getId().equals(medico.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Medico no existe");
    }

    /**
     * Actualiza los datos de un médico.
     *
     * @param medico Médico con la información actualizada.
     * @throws Exception si no existe el médico.
     */
    public void updateMedico(Medico medico) throws Exception {
        Medico result;
        try{
            result = this.readMedico(medico);
            data.getMedicos().remove(result);
            data.getMedicos().add(medico);
        }catch (Exception ex) {
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
        data.getMedicos().remove(medico);
    }

    /**
     * Busca médicos aplicando un filtro opcional por id, nombre o especialidad.
     *
     * @param medico Objeto {@link Medico} con los criterios de búsqueda.
     * @return Lista de médicos que cumplen con los criterios.
     */
    public List<Medico> searchMedico(Medico medico) {
        return data.getMedicos().stream()
                .filter(i->i.getNombre().contains(medico.getNombre()))
                .sorted(Comparator.comparing(Medico::getNombre))
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
      Farmaceutico result = data.getFarmaceuticos().stream().filter(i->i.getId().equals(farmaceutico.getId())).findFirst().orElse(null);
        if (result==null) data.getFarmaceuticos().add(farmaceutico);
        else throw new Exception("Farmaceutico ya existe");
    }

    /**
     * Busca un farmacéutico por su id.
     *
     * @param farmaceutico Objeto con el id del farmacéutico a buscar.
     * @return El farmacéutico encontrado.
     * @throws Exception si no existe el farmacéutico.
     */
    public Farmaceutico readFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream().filter(i->i.getId().equals(farmaceutico.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Farmaceutico no existe");
    }

    /**
     * Actualiza los datos de un farmacéutico.
     *
     * @param farmaceutico Farmacéutico con la información actualizada.
     * @throws Exception si no existe el farmacéutico.
     */
    public void updateFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        Farmaceutico result;
        try{
            result = this.readFarmaceutico(farmaceutico);
            data.getFarmaceuticos().remove(result);
            data.getFarmaceuticos().add(farmaceutico);
        }catch (Exception ex) {
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
        data.getFarmaceuticos().remove(farmaceutico);
    }

    /**
     * Busca farmacéuticos aplicando un filtro opcional por id, nombre o clave.
     *
     * @param farmaceutico Objeto {@link Farmaceutico} con los criterios de búsqueda.
     * @return Lista de farmacéuticos que cumplen con los criterios.
     */
    public List<Farmaceutico> searchFarmaceutico(Farmaceutico farmaceutico) {
        return data.getFarmaceuticos().stream()
                .filter(i->i.getNombre().contains(farmaceutico.getNombre()))
                .sorted(Comparator.comparing(Farmaceutico::getNombre))
                .collect(Collectors.toList());
    }

    //Buscar Usuario para iniciar sesion
    public Usuario login(String id, String clave) {
        for (Medico medico : data.getMedicos()) {
            if (medico.getId().equals(id) && medico.getClave().equals(clave)) { return medico; }
        }
        for (Farmaceutico farmaceutico : data.getFarmaceuticos()) {
            if (farmaceutico.getId().equals(id) && farmaceutico.getClave().equals(clave)) { return farmaceutico; }
        }
        return null;
    }

    //Buscar usuario para luego cambiarle la clave (la idea sería intentar usar el de arriba para las dos cosas)
    public Usuario buscarUsuario(String id) {
        for (Medico medico : data.getMedicos()) {
            if (medico.getId().equals(id)) { return medico; }
        }
        for (Farmaceutico farmaceutico : data.getFarmaceuticos()) {
            if (farmaceutico.getId().equals(id)) { return farmaceutico; }
        }
        return null;
    }

    //Se actualiza la clave del usuario
    public void actualizarUsuario(Usuario usuario) {
        try {
            if (usuario instanceof Medico) {
                this.updateMedico((Medico) usuario);
            } else if (usuario instanceof Farmaceutico) {
                this.updateFarmaceutico((Farmaceutico) usuario);
            }
            stop();
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }



    // ================= Pacientes ================= //

    public void createPaciente(Paciente paciente) throws Exception {
        Paciente result = data.getPacientes().stream().filter(i->i.getId().equals(paciente.getId())).findFirst().orElse(null);
        if (result==null) data.getPacientes().add(paciente);
        else throw new Exception("Paciente ya existe");
    }

    public Paciente readPaciente(Paciente paciente) throws Exception {
        Paciente result = data.getPacientes().stream().filter(i->i.getId().equals(paciente.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Paciente no existe");
    }

    public void updatePaciente(Paciente paciente) throws Exception {
        Paciente result;
        try{
            result = this.readPaciente(paciente);
            data.getPacientes().remove(result);
            data.getPacientes().add(paciente);
        }catch (Exception ex) {
            throw new Exception("Paciente no existe");
        }
    }

    public void deletePaciente(Paciente paciente) throws Exception {
        data.getPacientes().remove(paciente);
    }

    public List<Paciente> searchPaciente(Paciente paciente) {
        return data.getPacientes().stream()
                .filter(i->i.getNombre().contains(paciente.getNombre()))
                .sorted(Comparator.comparing(Paciente::getNombre))
                .collect(Collectors.toList());
    }

    // =============== MEDICAMENTOS ===============
    public void create(Medicamento e) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getMedicamentos().add(e);
        } else {
            throw new Exception("Medicamento ya existe");
        }
    }

    public Medicamento read(Medicamento e) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Medicamento no existe");
        }
    }

    public List<Medicamento> findAll() {
        return data.getMedicamentos();
    }
}
