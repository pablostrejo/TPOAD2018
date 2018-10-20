package dtos;

import java.io.Serializable;
import java.util.ArrayList;

public class JuegoNuevoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	ArrayList<CartaJugadorDTO> cartas;
	ArrayList<JugadorDTO> posicionesMesa; // con la posicion en mesa yo ya se quien es mi compañero
	JugadorDTO mano; // a quien le toca jugar

}
