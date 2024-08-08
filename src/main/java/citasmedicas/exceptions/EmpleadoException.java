package citasmedicas.exceptions;

public class EmpleadoException extends RuntimeException {
    public static final String ID_NO_EXISTE = "ID_NO_EXISTE";
    public static final String NOMBRE_VACIO = "NOMBRE_VACIO";
    public static final String APELLIDO_PATERNO_VACIO = "APELLIDO_PATERNO_VACIO";
    public static final String APELLIDO_MATERNO_VACIO = "APELLIDO_MATERNO_VACIO";
    public static final String TIPO_EMPLEADO_VACIO = "TIPO_EMPLEADO_VACIO";
    public static final String NUMERO_DOCUMENTO_VACIO = "NUMERO_DOCUMENTO_VACIO";
    public static final String NOMBRE_EXCESO_CARACTERES = "NOMBRE_EXCESO_CARACTERES";
    public static final String APELLIDO_PATERNO_EXCESO_CARACTERES = "APELLIDO_PATERNO_EXCESO_CARACTERES";
    public static final String APELLIDO_MATERNO_EXCESO_CARACTERES = "APELLIDO_MATERNO_EXCESO_CARACTERES";
    public static final String NUMERO_DOCUMENTO_EXCESO_CARACTERES = "NUMERO_DOCUMENTO_EXCESO_CARACTERES";
    public static final String EMPLEADO_NO_ENCONTRADO = "EMPLEADO_NO_ENCONTRADO";
    public static final String ERROR_PAGINADO = "ERROR_PAGINADO";

    public EmpleadoException(String message) {
        super(message);
    }
}
