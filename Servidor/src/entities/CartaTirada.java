package entities;

import javax.persistence.*;

import dtos.CartaTiradaDTO;


@Entity
@DiscriminatorValue("ct")
public class CartaTirada extends Movimiento {

//	@OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "id_cartaJugador")
	private CartaJugador cartaJugador;

	public CartaTirada(CartaJugador cartaJugador) {
		super();
		this.cartaJugador = cartaJugador;
	}

	public CartaTirada() {
	}
	
	public CartaTiradaDTO toDTO() {
		CartaTiradaDTO dto = new CartaTiradaDTO();

		dto.setId(this.id);
		dto.setNumeroTurno(this.numeroTurno);
		dto.setFechaHora(this.fechaHora);

		dto.setCartaJugador(this.cartaJugador.toDTO());

		return dto;
	}
	
	public CartaJugador getCartaJugador() {
		return cartaJugador;
	}

	public void setCartaJugador(CartaJugador cartaJugador) {
		this.cartaJugador = cartaJugador;
	}
	
	
}
