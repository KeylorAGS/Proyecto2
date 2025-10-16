package presentation.Logic;

import presentation.data.*;
import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private UsuarioDao usuarioDao;
    private RecetaDao recetaDao;
    private MedicamentoDao medicamentoDao;
    private PacienteDao pacienteDao;

    private Service() {
        try {
            usuarioDao = new UsuarioDao();
            recetaDao = new RecetaDao();
            medicamentoDao = new MedicamentoDao();
            pacienteDao = new PacienteDao();

        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public void stop() {
        try {
            Database.instance().close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ================= MÉDICOS ================= //

    public void createMedico(Medico medico) throws Exception {
        usuarioDao.createMedico(medico);
    }

    public Medico readMedico(Medico medico) throws Exception {
        return usuarioDao.readMedico(medico);
    }

    public void updateMedico(Medico medico) throws Exception {
        usuarioDao.updateMedico(medico);
    }

    public void deleteMedico(Medico medico) throws Exception {
        usuarioDao.delete(medico.getId());
    }

    public List<Medico> searchMedico(Medico medico) {
        return usuarioDao.findMedicosByNombre(medico.getNombre());
    }

    // ================= FARMACÉUTICOS ================= //

    public void createFarmaceutico(Farmaceutico farmaceutico) throws Exception {
      usuarioDao.createFarmaceutico(farmaceutico);
    }

    public Farmaceutico readFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        return usuarioDao.readFarmaceutico(farmaceutico.getId());
    }

    public void updateFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        usuarioDao.updateFarmaceutico(farmaceutico);
    }

    public void deleteFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        usuarioDao.delete(farmaceutico.getId());
    }

    public List<Farmaceutico> searchFarmaceutico(Farmaceutico farmaceutico) {
        return usuarioDao.findFarmaceuticosByNombre(farmaceutico.getNombre());
    }

    // ================= Administradores ================= //

    public Administrador readAdministrador(Administrador administrador) throws Exception {
        return usuarioDao.readAdministrador(administrador.getId());
    }

    public void updateAdministrador(Administrador administrador) throws Exception {
        usuarioDao.updateAdministrador(administrador);
    }

    // ================= Login ================= //

    public Usuario login(String id, String clave) {
        for (Medico medico : usuarioDao.findAllMedicos()) {
            if (medico.getId().equals(id) && medico.getClave().equals(clave)) { return medico; }
        }
        for (Farmaceutico farmaceutico : usuarioDao.findAllFarmaceuticos()) {
            if (farmaceutico.getId().equals(id) && farmaceutico.getClave().equals(clave)) { return farmaceutico; }
        }
        for (Administrador administrador : usuarioDao.findAllAdministradores()) {
            if (administrador.getId().equals(id) && administrador.getClave().equals(clave)) { return administrador; }
        }
        return null;
    }

    public Usuario buscarUsuario(String id) {
        for (Medico medico : usuarioDao.findAllMedicos()) {
            if (medico.getId().equals(id)) { return medico; }
        }
        for (Farmaceutico farmaceutico : usuarioDao.findAllFarmaceuticos()) {
            if (farmaceutico.getId().equals(id)) { return farmaceutico; }
        }
        for (Administrador administrador : usuarioDao.findAllAdministradores()) {
            if (administrador.getId().equals(id)) { return administrador; }
        }
        return null;
    }

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
        pacienteDao.create(paciente);
    }

    public Paciente readPaciente(Paciente paciente) throws Exception {
        return pacienteDao.read(paciente.getId());
    }

    public void updatePaciente(Paciente paciente) throws Exception {
        pacienteDao.update(paciente);
    }

    public void deletePaciente(Paciente paciente) throws Exception {
        pacienteDao.delete(paciente);
    }

    public List<Paciente> searchPaciente(Paciente paciente) {
        return pacienteDao.findByNombre(paciente.getNombre());
    }

    // =============== MEDICAMENTOS ===============
    public void createMedicamento(Medicamento medicamento) throws Exception {
        medicamentoDao.create(medicamento);
    }

    public Medicamento readMedicamento(Medicamento medicamento) throws Exception {
        return medicamentoDao.read(medicamento.getId());
    }

    public void updateMedicamento(Medicamento medicamento) throws Exception {
        medicamentoDao.update(medicamento);
    }

    public void deleteMedicamento(Medicamento medicamento) throws Exception {
        medicamentoDao.delete(medicamento);
    }

    public List<Medicamento> searchMedicamento(Medicamento medicamento) {
        return medicamentoDao.findByNombre(medicamento.getNombre());
    }

    public List<Medicamento> findAll() {
        return medicamentoDao.findAll();
    }

    // =============== RECETAS ===============
    public void createReceta(Receta receta) throws Exception {
        recetaDao.create(receta);
    }

    public Receta readReceta(Receta receta) throws Exception {
        return recetaDao.read(receta.getIdReceta());
    }

    public void updateReceta(Receta receta) throws Exception {
        recetaDao.update(receta);
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
        return recetaDao.findAll().stream()
                .filter(r -> !r.getEstado().equals("Entregada"))
                .filter(r -> r.getIdReceta().contains(receta.getIdReceta()))
                .sorted(Comparator.comparing(Receta::getIdReceta))
                .collect(Collectors.toList());
    }

    public List<Receta> findRecetasPorEstado(String estado) {
        return recetaDao.findAll().stream()
                .filter(receta -> receta.getEstado().equals(estado))
                .sorted(Comparator.comparing(Receta::getIdReceta))
                .collect(Collectors.toList());
    }

    public List<Receta> findRecetasNoEntregadas() {
        return recetaDao.findAll().stream()
                .filter(receta -> !receta.getEstado().equals("Entregada"))
                .sorted(Comparator.comparing(Receta::getIdReceta))
                .collect(Collectors.toList());
    }

    public List<Receta> searchRecetas(Receta receta) {
        return recetaDao.findAll().stream()
                .filter(i->i.getIdPaciente().contains(receta.getIdReceta()))
                .sorted(Comparator.comparing(Receta::getIdReceta))
                .collect(Collectors.toList());
    }

    public List<Receta> findAllRecetas() {
        return recetaDao.findAll();
    }

}
