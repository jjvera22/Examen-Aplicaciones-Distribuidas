package gimnasioapp.modelos;

public class Plan {
    private int id;
    private String nombre;
    private double precio;
    private int duracionDias;

    // Constructores
    public Plan() {}

    public Plan(int id, String nombre, double precio, int duracionDias) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.duracionDias = duracionDias;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    @Override
    public String toString() {
        return nombre + " (" + duracionDias + " días)";
    }
}
