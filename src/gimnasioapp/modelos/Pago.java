package gimnasioapp.modelos;

 

public class Pago {
    private int id;
    private int idMembresia;
    private String fechaPago;
    private double monto;
    private String metodoPago;
    private String observaciones;
    private Membresia membresia;

    public Pago() {}

    public Pago(int idMembresia, String fechaPago, double monto, String metodoPago, String observaciones, Membresia membresia) {
        this.idMembresia = idMembresia;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.observaciones = observaciones;
        this.membresia = membresia;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdMembresia() { return idMembresia; }
    public void setIdMembresia(int idMembresia) { this.idMembresia = idMembresia; }

    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String fechaPago) { this.fechaPago = fechaPago; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Membresia getMembresia() { return membresia; }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", idMembresia=" + idMembresia +
                ", fechaPago='" + fechaPago + '\'' +
                ", monto=" + monto +
                ", metodoPago='" + metodoPago + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", membresia=" + (membresia != null ? membresia.toString() : "null") +
                '}';
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }
}
