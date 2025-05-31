package gimnasioapp.controladores;

import gimnasioapp.gimnasioDAL.ClienteDAL;
import gimnasioapp.modelos.Cliente;
import gimnasioapp.vistas.componentes.PanelFormularioCliente;
import gimnasioapp.vistas.componentes.PanelTablaCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;

public class ControladorCliente {

    private final ClienteDAL clienteDAL;
    private PanelFormularioCliente panelFormulario;
    private PanelTablaCliente panelTabla;

    public ControladorCliente(Connection conn) {
        this.clienteDAL = new ClienteDAL(conn);
    }

    public void setPanelFormulario(PanelFormularioCliente panelFormulario) {
        this.panelFormulario = panelFormulario;
    }

    public void setPanelTabla(PanelTablaCliente panelTabla) {
        this.panelTabla = panelTabla;
    }

    // ✅ Nuevo método para conectar el botón con el formulario
    public void guardarCliente() {
        if (panelFormulario != null) {
            Cliente cliente = panelFormulario.obtenerClienteFormulario();
            guardarCliente(cliente);
        } else {
            JOptionPane.showMessageDialog(null, "Formulario no disponible.");
        }
    }

    public void guardarCliente(Cliente cliente) {
        if (clienteDAL.insertar(cliente)) {
            JOptionPane.showMessageDialog(null, "✅ Cliente guardado correctamente.");
            actualizarTabla();
            panelFormulario.limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error al guardar el cliente.");
        }
    }

    public void actualizarCliente(Cliente cliente) {
        if (cliente.getId() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente para actualizar.");
            return;
        }

        if (clienteDAL.actualizar(cliente)) {
            JOptionPane.showMessageDialog(null, "✅ Cliente actualizado correctamente.");
            actualizarTabla();
            panelFormulario.limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(null, "❌ Error al actualizar el cliente.");
        }
    }

    public void eliminarCliente(int id) {
        int confirm = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (clienteDAL.eliminar(id)) {
                JOptionPane.showMessageDialog(null, "🗑️ Cliente eliminado correctamente.");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "❌ Error al eliminar el cliente.");
            }
        }
    }

    public void cargarClientesEnTabla(JTable tabla) {
        List<Cliente> lista = clienteDAL.obtenerTodos();
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getCedula(),
                c.getTelefono(),
                c.getCorreo(),
                c.getDireccion()
            });
        }
    }

    public Cliente obtenerClienteDesdeFila(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila.");
            return null;
        }

        Cliente c = new Cliente();
        c.setId((int) tabla.getValueAt(fila, 0));
        c.setNombre((String) tabla.getValueAt(fila, 1));
        c.setCedula((String) tabla.getValueAt(fila, 2));
        c.setTelefono((String) tabla.getValueAt(fila, 3));
        c.setCorreo((String) tabla.getValueAt(fila, 4));
        c.setDireccion((String) tabla.getValueAt(fila, 5));
        return c;
    }

    public void cargarClienteEnFormulario(Cliente cliente) {
        if (panelFormulario != null) {
            panelFormulario.cargarClienteFormulario(cliente);
        }
    }

    private void actualizarTabla() {
        if (panelTabla != null) {
            cargarClientesEnTabla(panelTabla.getTabla());
        }
    }
    public Cliente buscarPorCedula(String cedula) {
    return clienteDAL.obtenerPorCedula(cedula);
}

    
}
