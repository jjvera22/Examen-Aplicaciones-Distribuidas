package gimnasioapp.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utilidades para manejar fechas.
 */
public class UtilidadesFechas {
    
    // Formato estándar usado en la base de datos y en los formularios
    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Convierte un String en formato yyyy-MM-dd a java.util.Date.
     */
    public static Date stringADate(String fechaTexto) {
        try {
            return FORMATO_FECHA.parse(fechaTexto);
        } catch (ParseException e) {
            System.err.println("❌ Error al convertir String a Date: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convierte un objeto java.util.Date a String con formato yyyy-MM-dd.
     */
    public static String dateAString(Date fecha) {
        return FORMATO_FECHA.format(fecha);
    }

    /**
     * Retorna la fecha actual como Date.
     */
    public static Date obtenerFechaActual() {
        return new Date();
    }

    /**
     * Suma una cantidad de días a una fecha dada.
     */
    public static Date sumarDias(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    /**
     * Compara si una fecha es anterior a otra.
     */
    public static boolean esAnterior(Date fecha1, Date fecha2) {
        return fecha1.before(fecha2);
    }

    /**
     * Compara si una fecha es posterior a otra.
     */
    public static boolean esPosterior(Date fecha1, Date fecha2) {
        return fecha1.after(fecha2);
    }

    public static Object formatearFecha(String fechaInicio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static String parsearFecha(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
