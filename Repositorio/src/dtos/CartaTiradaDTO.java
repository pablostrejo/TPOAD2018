package dtos;

import java.io.Serializable;

public class CartaTiradaDTO extends MovimientoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private CartaJugadorDTO cartaJugador;

	public CartaTiradaDTO() {

		super();
	}

	public CartaJugadorDTO getCartaJugador() {
		return cartaJugador;
	}

	public void setCartaJugador(CartaJugadorDTO cartaJugador) {
		this.cartaJugador = cartaJugador;
	}

}
