package exceptions; 

public class ControladorException extends Exception {

	private static final long serialVersionUID = 1L;

	public ControladorException(String mensaje) {
		super(mensaje);
	}
}
