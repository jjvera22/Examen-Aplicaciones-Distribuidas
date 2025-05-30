
package gimnasioapp.modelos;

public class Membresia {
    private int id;
    private int idCliente;
    private int idPlan;
    private String fechaInicio;
    private String fechaFin;
    private String estadoPago;
    private String tipo;
    private Cliente cliente;
    private Plan plan;

    public Membresia() {
    }

    public Membresia(int id, int idCliente, int idPlan, String fechaInicio, String fechaFin, String estadoPago, Cliente cliente, Plan plan) {
        this.id = id;
        this.idCliente = idCliente;
        this.idPlan = idPlan;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoPago = estadoPago;
        this.cliente = cliente;
        this.plan = plan;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    @Override
    public String toString() {
        return "Membresia{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", idPlan=" + idPlan +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", estadoPago='" + estadoPago + '\'' +
                '}';
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    
    public Plan getPlan(){
        return plan;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Object getNombreCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getNombrePlan() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setNombreCliente(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setNombrePlan(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
