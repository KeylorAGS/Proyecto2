package presentation.Logic;

import presentation.data.*;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase de servicio central para la gestión de entidades del sistema.
 *
 * Implementa el patrón Singleton para asegurar una única instancia en toda la aplicación.
 *
 * Funcionalidades principales:
 * - Gestiona los objetos de negocio {@link Medico} y {@link Farmaceutico} {@link Administrador}.
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
                    && data.getPacientes().isEmpty() && data.getAdministradores().isEmpty()) {
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

        Administrador admistrador = new Administrador();
        admistrador.setId("A001");
        admistrador.setNombre("Jose Sanchez");
        admistrador.setClave("A001");

        data.getMedicos().add(medico);
        data.getFarmaceuticos().add(farmaceutico);
        data.getAdministradores().add(admistrador);
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

    // ================= Administradores ================= //

    /**
     * Busca un administrador por su id.
     *
     * @param administrador Objeto con el id del administrador a buscar.
     * @return El administrador encontrado.
     * @throws Exception si no existe el administrador.
     */
    public Administrador readAdministrador(Administrador administrador) throws Exception {
        Administrador result = data.getAdministradores().stream().filter(i->i.getId().equals(administrador.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Administrador no existe");
    }

    /**
     * Actualiza los datos de un administrador.
     *
     * @param administrador Administrador con la información actualizada.
     * @throws Exception si no existe el administrador.
     */
    public void updateAdministrador(Administrador administrador) throws Exception {
        Administrador result;
        try{
            result = this.readAdministrador(administrador);
            data.getAdministradores().remove(result);
            data.getAdministradores().add(administrador);
        }catch (Exception ex) {
            throw new Exception("Administrador no existe");
        }
    }

    // ================= Login ================= //

    //Buscar Usuario para iniciar sesion
    public Usuario login(String id, String clave) {
        for (Medico medico : data.getMedicos()) {
            if (medico.getId().equals(id) && medico.getClave().equals(clave)) { return medico; }
        }
        for (Farmaceutico farmaceutico : data.getFarmaceuticos()) {
            if (farmaceutico.getId().equals(id) && farmaceutico.getClave().equals(clave)) { return farmaceutico; }
        }
        for (Administrador administrador : data.getAdministradores()) {
            if (administrador.getId().equals(id) && administrador.getClave().equals(clave)) { return administrador; }
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
        for (Administrador administrador : data.getAdministradores()) {
            if (administrador.getId().equals(id)) { return administrador; }
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
            } else if (usuario instanceof Administrador) {
                this.updateAdministrador((Administrador) usuario);
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
    public void createMedicamento(Medicamento medicamento) throws Exception {
        Medicamento result = data.getMedicamentos().stream().filter(i->i.getId().equals(medicamento.getId())).findFirst().orElse(null);
        if (result==null) data.getMedicamentos().add(medicamento);
        else throw new Exception("Medicamento ya existe");
    }

    public Medicamento readMedicamento(Medicamento medicamento) throws Exception {
        Medicamento result = data.getMedicamentos().stream().filter(i->i.getId().equals(medicamento.getId())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Paciente no existe");
    }

    public void updateMedicamento(Medicamento medicamento) throws Exception {
        Medicamento result;
        try{
            result = this.readMedicamento(medicamento);
            data.getMedicamentos().remove(result);
            data.getMedicamentos().add(medicamento);
        }catch (Exception ex) {
            throw new Exception("Paciente no existe");
        }
    }

    public void deleteMedicamento(Medicamento medicamento) throws Exception {
        data.getMedicamentos().remove(medicamento);
    }

    public List<Medicamento> searchMedicamento(Medicamento medicamento) {
        return data.getMedicamentos().stream()
                .filter(i->i.getNombre().contains(medicamento.getNombre()))
                .sorted(Comparator.comparing(Medicamento::getNombre))
                .collect(Collectors.toList());
    }

    public List<Medicamento> findAll() {
        return data.getMedicamentos();
    }

    // =============== Prescripcion ===============

    public List<Prescripcion> searchPrescripcionEnRecetas(Prescripcion filtro) {
        List<Prescripcion> resultado = new ArrayList<>();

        for (Receta receta : data.getRecetas()) {
            for (Prescripcion prescripcion : receta.getPrescripcions()) {
                boolean coincide = true;

                if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
                    if (!prescripcion.getNombre().toLowerCase().contains(filtro.getNombre().toLowerCase())) {
                        coincide = false;
                    }
                }

                if (filtro.getPresentacion() != null && !filtro.getPresentacion().isEmpty()) {
                    if (!prescripcion.getPresentacion().equals(filtro.getPresentacion())) {
                        coincide = false;
                    }
                }

                if (filtro.getCantidad() != null && !filtro.getCantidad().isEmpty()) {
                    if (!prescripcion.getCantidad().equalsIgnoreCase(filtro.getCantidad())) {
                        coincide = false;
                    }
                }

                if (filtro.getIndicaciones() != null && !filtro.getIndicaciones().isEmpty()) {
                    if (!prescripcion.getIndicaciones().equalsIgnoreCase(filtro.getIndicaciones())) {
                        coincide = false;
                    }
                }

                if (filtro.getDuracion() != null && !filtro.getDuracion().isEmpty()) {
                    if (!prescripcion.getDuracion().equalsIgnoreCase(filtro.getDuracion())) {
                        coincide = false;
                    }
                }

                if (coincide) {
                    resultado.add(prescripcion);
                }
            }
        }

        return resultado.stream()
                .sorted(Comparator.comparing(Prescripcion::getNombre))
                .collect(Collectors.toList());
    }

    /**
     * Encuentra una prescripción específica dentro de una receta
     */
    public Prescripcion findPrescripcionEnReceta(String idReceta, String nombrePrescripcion) throws Exception {
        Receta receta = data.getRecetas().stream()
                .filter(r -> r.getIdReceta().equals(idReceta))
                .findFirst()
                .orElseThrow(() -> new Exception("Receta no encontrada"));

        return receta.getPrescripcions().stream()
                .filter(p -> p.getNombre().equals(nombrePrescripcion))
                .findFirst()
                .orElseThrow(() -> new Exception("Prescripción no encontrada en la receta"));
    }

    /**
     * Actualiza una prescripción específica dentro de una receta
     */
    public void updatePrescripcionEnReceta(String idReceta, Prescripcion prescripcionVieja, Prescripcion prescripcionNueva) throws Exception {
        Receta receta = data.getRecetas().stream()
                .filter(r -> r.getIdReceta().equals(idReceta))
                .findFirst()
                .orElseThrow(() -> new Exception("Receta no encontrada"));

        List<Prescripcion> prescripciones = receta.getPrescripcions();
        int index = -1;

        for (int i = 0; i < prescripciones.size(); i++) {
            Prescripcion p = prescripciones.get(i);
            if (p.getNombre().equals(prescripcionVieja.getNombre()) &&
                    p.getPresentacion().equals(prescripcionVieja.getPresentacion())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            prescripciones.set(index, prescripcionNueva);
        } else {
            throw new Exception("Prescripción no encontrada en la receta");
        }
    }

    /**
     * Elimina una prescripción específica de una receta
     */
    public void deletePrescripcionDeReceta(String idReceta, Prescripcion prescripcion) throws Exception {
        Receta receta = data.getRecetas().stream()
                .filter(r -> r.getIdReceta().equals(idReceta))
                .findFirst()
                .orElseThrow(() -> new Exception("Receta no encontrada"));

        boolean removed = receta.getPrescripcions().removeIf(p ->
                p.getNombre().equals(prescripcion.getNombre()) &&
                        p.getPresentacion().equals(prescripcion.getPresentacion())
        );

        if (!removed) {
            throw new Exception("Prescripción no encontrada en la receta");
        }
    }

    // =============== RECETAS ===============
    public void createReceta(Receta receta) throws Exception {
        Receta result = data.getRecetas().stream().filter(i->i.getIdReceta().equals(receta.getIdReceta())).findFirst().orElse(null);
        if (result==null) data.getRecetas().add(receta);
        else throw new Exception("Receta ya existe");
    }

    public Receta readReceta(Receta receta) throws Exception {
        Receta result = data.getRecetas().stream().filter(i->i.getIdReceta().equals(receta.getIdReceta())).findFirst().orElse(null);
        if (result!=null) return result;
        else throw new Exception("Receta no existe");
    }

    public void updateReceta(Receta receta) throws Exception {
        Receta result;
        try{
            result = this.readReceta(receta);
            data.getRecetas().remove(result);
            data.getRecetas().add(receta);
        }catch (Exception ex) {
            throw new Exception("Receta no existe");
        }
    }

    public void deleteReceta(Receta receta) {
        String estadoActual = receta.getEstado();

        if ("Lista".equals(estadoActual)) {
            receta.setEstado("Entregada");
            JOptionPane.showMessageDialog(null, "La receta ha sido entregada exitosamente");
        } else if ("Entregada".equals(estadoActual)) {
            JOptionPane.showMessageDialog(null, "La receta ya fue entregada anteriormente");
        } else {
            JOptionPane.showMessageDialog(null,
                    "La receta no está lista para entregar.\nEstado actual: " + estadoActual);
        }
    }

    public void modificarEstadoReceta(Receta receta) {
        String estadoActual = receta.getEstado();

        switch (estadoActual) {
            case "Confeccionada":
                receta.setEstado("Proceso");
                JOptionPane.showMessageDialog(null,
                        "Estado actualizado: Confeccionada → Proceso");
                break;

            case "Proceso":
                receta.setEstado("Lista");
                JOptionPane.showMessageDialog(null,
                        "Estado actualizado: Proceso → Lista");
                break;

            case "Lista":
                JOptionPane.showMessageDialog(null,
                        "La receta está lista para entregar.\nUse el botón 'Entregar' para completar la entrega.");
                break;

            case "Entregada":
                JOptionPane.showMessageDialog(null,
                        "Esta receta ya fue entregada y no se puede modificar.");
                break;

            default:
                JOptionPane.showMessageDialog(null,
                        "Estado no reconocido: " + estadoActual);
                break;
        }
    }

    public List<Receta> searchRecetaNoEntregadas(Receta receta) {
        return data.getRecetas().stream()
                .filter(r -> !r.getEstado().equals("Entregada"))
                .filter(r -> r.getIdReceta().contains(receta.getIdReceta()))
                .sorted(Comparator.comparing(Receta::getIdReceta))
                .collect(Collectors.toList());
    }

    public List<Receta> findRecetasPorEstado(String estado) {
        return data.getRecetas().stream()
                .filter(receta -> receta.getEstado().equals(estado))
                .sorted(Comparator.comparing(Receta::getIdReceta))
                .collect(Collectors.toList());
    }

    public List<Receta> findRecetasNoEntregadas() {
        return data.getRecetas().stream()
                .filter(receta -> !receta.getEstado().equals("Entregada"))
                .sorted(Comparator.comparing(Receta::getIdReceta))
                .collect(Collectors.toList());
    }

    public List<Receta> searchRecetas(Receta receta) {
        return data.getRecetas().stream()
                .filter(i->i.getIdPaciente().contains(receta.getIdReceta()))
                .sorted(Comparator.comparing(Receta::getIdReceta))
                .collect(Collectors.toList());
    }

    public List<Receta> findAllRecetas() {
        return data.getRecetas();
    }
}
