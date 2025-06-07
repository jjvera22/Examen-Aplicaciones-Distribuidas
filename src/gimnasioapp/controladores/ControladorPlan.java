package gimnasioapp.controladores;

import gimnasioapp.gimnasioDAL.PlanDAL;
import gimnasioapp.modelos.Plan;
import gimnasioapp.vistas.componentes.PanelFormularioPlan;
import gimnasioapp.vistas.componentes.PanelTablaPlan;

import java.sql.Connection;
import java.util.List;
import javax.swing.JOptionPane;

public class ControladorPlan {

    private final PlanDAL planDAL;
    private final Connection conn;
    private PanelFormularioPlan panelFormulario;
    private PanelTablaPlan panelTabla;

    public ControladorPlan(Connection conn) {
        this.planDAL = new PlanDAL(conn);
        this.conn = conn;
    }

    public void setPanelFormulario(PanelFormularioPlan panelFormulario) {
        this.panelFormulario = panelFormulario;
    }

    public void setPanelTabla(PanelTablaPlan panelTabla) {
        this.panelTabla = panelTabla;
        actualizarTabla();
    }

    public void guardarPlan() {
        Plan plan = panelFormulario.obtenerPlanFormulario();
        boolean resultado;

        if (plan.getId() == 0) {
            resultado = planDAL.insertar(plan);
        } else {
            resultado = planDAL.actualizar(plan);
        }

        if (resultado) {
            panelFormulario.limpiarFormulario();
            actualizarTabla();
        }
    }

    public void cargarPlanEnFormulario(Plan plan) {
        panelFormulario.cargarPlanFormulario(plan);
    }

    public void eliminarPlan(int id) {
        boolean eliminado = planDAL.eliminar(id);
        if (eliminado) {
            actualizarTabla();
        }
    }

    public void actualizarTabla() {
        List<Plan> planes = planDAL.obtenerTodos();
        panelTabla.actualizarTabla(planes);
    }

    public List<Plan> obtenerTodos() {
        return planDAL.obtenerTodos();
    }

    public void cargarPlanes() {
        actualizarTabla();
    }
    
    public void actualizarPlan(Plan plan) {
        if (plan.getId() == 0) {
            return;
        }

        Plan planForm = panelFormulario.obtenerPlanFormulario();
        plan.setNombre(planForm.getNombre());
        plan.setPrecio(planForm.getPrecio());
        plan.setDuracionDias(planForm.getDuracionDias());
        
        if (planDAL.actualizar(plan)) {
            JOptionPane.showMessageDialog(null, "✅ Cliente actualizado correctamente.");
            actualizarTabla();
            panelFormulario.limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error al actualizar el cliente.");
        }
    }

}
