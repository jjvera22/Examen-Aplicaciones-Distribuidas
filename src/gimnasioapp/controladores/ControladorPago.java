package gimnasioapp.controladores;

import gimnasioapp.gimnasioDAL.MembresiaDAL;
import gimnasioapp.gimnasioDAL.PagoDAL;
import gimnasioapp.gimnasioDAL.PlanDAL;
import gimnasioapp.modelos.ComboItem;
import gimnasioapp.modelos.Pago;
import gimnasioapp.modelos.Plan;
import gimnasioapp.vistas.componentes.PanelFormularioPago;
import gimnasioapp.vistas.componentes.PanelTablaPago;
import gimnasioapp.modelos.Membresia;
import java.sql.Connection;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

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
        List<ComboItem> membresias = membresiaDAL.obtenerMembresiaDataComboBox();
        panelFormulario.cargarMembresias(membresias);
    }

    public Pago obtenerPagoDesdeFila(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila.");
            return null;
        }

        Pago p = new Pago();
        
        int pagoId = (int) tabla.getValueAt(fila, 0);
        int idMembresia = pagoDAL.obtenerIdMembresiaPorPagoId(pagoId);
        
        p.setId(pagoId);
        if (idMembresia != -1) {
            panelFormulario.seleccionarMembresiaPorId(idMembresia);
        }
        p.setFechaPago((String) tabla.getValueAt(fila, 2));
        p.setMonto((Double) tabla.getValueAt(fila, 3));
        p.setMetodoPago((String) tabla.getValueAt(fila, 4));
        p.setObservaciones((String) tabla.getValueAt(fila, 5));
        return p;
    }
    
    public void actualizarPago(Pago pago) {
        if (pago.getId() == 0) {
            return;
        }

        Pago pagoForm = panelFormulario.obtenerPagoFormulario();
        pago.setIdMembresia(pagoForm.getIdMembresia());
        pago.setFechaPago(pagoForm.getFechaPago());
        pago.setMonto(pagoForm.getMonto());
        pago.setMetodoPago(pagoForm.getMetodoPago());
        pago.setObservaciones(pagoForm.getObservaciones());
        
        if (pagoDAL.actualizar(pago)) {
            JOptionPane.showMessageDialog(null, "✅ Cliente actualizado correctamente.");
            actualizarTabla();
            panelFormulario.limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error al actualizar el cliente.");
        }
    }
}
