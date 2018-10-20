package entities;

import javax.persistence.*;

import dtos.CartaTiradaDTO;


@Entity
@DiscriminatorValue("ct")
public class CartaTiradaEntity extends MovimientoEntity {

//	@OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "id_cartaJugador")
	private CartaJugadorEntity cartaJugador;

	public CartaTiradaEntity(CartaJugadorEntity cartaJugador) {
		super();
		this.cartaJugador = cartaJugador;
	}

	public CartaTiradaEntity() {
	}
	
	public CartaTiradaDTO toDTO() {
		CartaTiradaDTO dto = new CartaTiradaDTO();

		dto.setId(this.id);
		dto.setNumeroTurno(this.numeroTurno);
		dto.setFechaHora(this.fechaHora);

		dto.setCartaJugador(this.cartaJugador.toDTO());

		return dto;
	}
	
	public CartaJugadorEntity getCartaJugador() {
		return cartaJugador;
	}

	public void setCartaJugador(CartaJugadorEntity cartaJugador) {
		this.cartaJugador = cartaJugador;
	}
	
	
}
