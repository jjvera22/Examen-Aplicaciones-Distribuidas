package gimnasioapp.controladores;

import gimnasioapp.gimnasioDAL.MembresiaDAL;
import gimnasioapp.gimnasioDAL.PagoDAL;
import gimnasioapp.gimnasioDAL.PlanDAL;
import gimnasioapp.modelos.Pago;
import gimnasioapp.modelos.Plan;
import gimnasioapp.vistas.componentes.PanelFormularioPago;
import gimnasioapp.vistas.componentes.PanelTablaPago;
import gimnasioapp.modelos.Membresia;
import java.sql.Connection;

import java.util.List;

public class ControladorPago {
    private final PagoDAL pagoDAL;
    private final Connection conn;
    private PanelFormularioPago panelFormulario;
    private PanelTablaPago panelTabla;

    public ControladorPago(PagoDAL pagoDAL, Connection conn) {
        this.pagoDAL = pagoDAL;
        this.conn = conn;
    }

    public void setPanelFormulario(PanelFormularioPago panelFormulario) {
        this.panelFormulario = panelFormulario;
        
        obtenerMembresias();
    }

    public void setPanelTabla(PanelTablaPago panelTabla) {
        this.panelTabla = panelTabla;
    }

    public void guardarPago(Pago pago) {
        try {
            boolean exito;
            if (pago.getId() == 0) {
                exito = pagoDAL.insertar(pago);
            } else {
                exito = pagoDAL.actualizar(pago);
            }

            if (exito) {
                if (panelFormulario != null) {
                    panelFormulario.mostrarMensaje("Pago guardado correctamente.");
                    panelFormulario.limpiarCampos();
                }
                actualizarTabla();
            } else {
                if (panelFormulario != null) {
                    panelFormulario.mostrarMensaje("Error al guardar el pago.");
                }
            }
        } catch (Exception e) {
            if (panelFormulario != null) {
                panelFormulario.mostrarMensaje("Excepción: " + e.getMessage());
            }
        }
    }

    public void eliminarPago(int id) {
        try {
            boolean exito = pagoDAL.eliminar(id);
            if (panelFormulario != null) {
                panelFormulario.mostrarMensaje(
                    exito ? "Pago eliminado correctamente." : "Error al eliminar el pago."
                );
            }
            if (exito) {
                actualizarTabla();
            }
        } catch (Exception e) {
            if (panelFormulario != null) {
                panelFormulario.mostrarMensaje("Excepción: " + e.getMessage());
            }
        }
    }

    public void cargarPagoEnFormulario(Pago pago) {
        if (panelFormulario != null) {
            panelFormulario.setDatosPago(pago);
        }
    }

    public void actualizarTabla() {
        try {
            if (panelTabla != null) {
                List<Object[]> pagos = pagoDAL.obtenerPagosParaTabla();
                panelTabla.cargarPagos(pagos);
            }
        } catch (Exception e) {
            if (panelFormulario != null) {
                panelFormulario.mostrarMensaje("Error al actualizar la tabla: " + e.getMessage());
            }
        }
    }
    
    public void obtenerMembresias() {
        MembresiaDAL membresiaDAL = new MembresiaDAL(conn);
        List<String> membresias = membresiaDAL.obtenerMembresiaDataComboBox();
        panelFormulario.cargarMembresias(membresias);
    }

    
   
}
