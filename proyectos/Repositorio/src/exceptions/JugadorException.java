package exceptions; 

import java.rmi.RemoteException;

public class JugadorException extends RemoteException {

	private static final long serialVersionUID = 1L;

	public JugadorException(String mensaje) {
		super(mensaje);
	}

	public JugadorException(String mensaje, Throwable e) {
		super(mensaje, e);
	}
	
}
