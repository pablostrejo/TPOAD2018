package bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import dtos.*;
import exceptions.*;


/**
 * La Baza es cada una de las 3 'rondas' que conforman la Mano.
 * En cada Baza, cada jugador arroja 1 sola carta. 
**/

@Entity
@Table (name = "Bazas")
public class Baza {

	@Id
	@Column (name = "id_baza", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "id_baza")
	private List<Movimiento> turnosBaza;
	
	@Column (name = "nro_baza")
	private int numeroBaza;
	
	@Transient
	private Mano mano; //se utiliza para reemplazar los observers	

//	@OneToOne (cascade = CascadeType.ALL) /* fetch = FetchType.EAGER)*/
	@OneToOne /* fetch = FetchType.EAGER)*/
	@JoinColumn (name = "id_jugador")
	private Jugador ganador;
	
	@Transient
	/* No se persiste el orden de juego */
	private List<Jugador> ordenJuego;

	@Transient
	private int cantidadCartasTiradas;


	public Baza(Mano mano, int numeroBaza, List<Jugador> ordenJuego) {
		this.mano = mano;
		this.numeroBaza = numeroBaza;
		this.ordenJuego = ordenJuego;
		this.turnosBaza = new ArrayList<Movimiento>();
		this.cantidadCartasTiradas = 0;
	}

	public Baza() {
		
	}

	

	public BazaDTO toDTO() {
		BazaDTO dto = new BazaDTO();
		dto.setId(this.id);
		dto.setNumeroBaza(this.numeroBaza);

//		JugadorDTO jugador;
//		ArrayList<JugadorDTO> orden = new ArrayList<JugadorDTO>();
//		for(int i=0; i<ordenJuego.size(); i++){
//			
//			jugador = ordenJuego.get(i).toDTO();
//			orden.add(jugador);
//		}
//		dto.setOrdenJuego(orden);

		MovimientoDTO mov;
		ArrayList<MovimientoDTO> movimientos = new ArrayList<MovimientoDTO>();
		for(int i=0; i<turnosBaza.size(); i++) {
			mov = turnosBaza.get(i).toDTO();
			movimientos.add(mov);
		}
		dto.setTurnosBaza(movimientos);
		dto.setGanador(this.ganador == null ? null : this.ganador.toDTO());
		
		return dto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Movimiento> getTurnosBaza() {
		return turnosBaza;
	}

	public void setTurnosBaza(ArrayList<Movimiento> turnosBaza) {
		this.turnosBaza = turnosBaza;
	}

	public int getNumeroBaza() {
		return numeroBaza;
	}

	public void setNumeroBaza(int numeroBaza) {
		this.numeroBaza = numeroBaza;
	}

	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}

	public List<Jugador> getOrdenJuego() {
		return ordenJuego;
	}

	public void setOrdenJuego(List<Jugador> ordenJuego) {
		this.ordenJuego = ordenJuego;
	}
	
	public Movimiento obtenerUltimoMovimiento (){
		return turnosBaza.get(turnosBaza.size() - 1);
	}

	public Jugador obtenerTurnoBaza() {
		return ordenJuego.get(cantidadCartasTiradas); 
	}

	// devuelve el jugador que debe contestar el envite
	public Jugador obtenerTurnoContestar() {
		Movimiento aux = obtenerUltimoMovimiento(); // se sabe que es un envite

		if (((Envite) aux).getJugador().getId() == ordenJuego.get(2).getId()) {
			// el ultimo movimiento lo hizo el pie de la primer pareja,
			// por lo tanto debe contestar el pie de la segunda pareja.
			return ordenJuego.get(3);
		}
		// sino el ultimo canto lo hizo el pie de la segunda pareja,
		// por lo tanto debe contestar el pie de la primer pareja.
		return ordenJuego.get(2);
	}

/*

	public int obtenerCantidadCartasTiradas () {
		int cantidadTiradas = 0;

		for(Movimiento mov: turnosBaza) {
			if(mov instanceof CartaTirada)
				cantidadTiradas++;
		}
		return cantidadTiradas;
	}

*/

	public void definirGanador() throws BazaException {
		CartaJugador cartaJugador1 = null;
		CartaJugador cartaJugador2 = null;
		CartaJugador cartaJugador3 = null;
		CartaJugador cartaJugador4 = null;

		CartaTirada cartaTirada;
		int cantidadCartas = 0;

		for(Movimiento mov: turnosBaza) {
			if(mov instanceof CartaTirada) {
				cartaTirada = (CartaTirada) mov;

				if(cantidadCartas == 0)
					cartaJugador1 = cartaTirada.getCartaJugador();
				if(cantidadCartas == 1)
					cartaJugador2 = cartaTirada.getCartaJugador();
				if(cantidadCartas == 2)
					cartaJugador3 = cartaTirada.getCartaJugador();
				if(cantidadCartas == 3)
					cartaJugador4 = cartaTirada.getCartaJugador();
				
				cantidadCartas++;				
			}
		}
		if (cantidadCartas == 4){
			CartaJugador jugadorConCartaMayorPareja1 = cartaJugador1.getCarta().getPosicionValor() < cartaJugador3.getCarta().getPosicionValor() ? cartaJugador1 : cartaJugador3;
			CartaJugador jugadorConCartaMayorPareja2 = cartaJugador2.getCarta().getPosicionValor() < cartaJugador4.getCarta().getPosicionValor() ? cartaJugador2 : cartaJugador4;

			if(jugadorConCartaMayorPareja1.getCarta().getPosicionValor() < jugadorConCartaMayorPareja2.getCarta().getPosicionValor())
				ganador= jugadorConCartaMayorPareja1.getJugador();
			else
				if(jugadorConCartaMayorPareja1.getCarta().getPosicionValor() > jugadorConCartaMayorPareja2.getCarta().getPosicionValor())
					ganador = jugadorConCartaMayorPareja2.getJugador();
				else 
					//Hay un empate
					ganador = null; 
		}
		else
			throw new BazaException("No se puede definir un ganador, no se han lanzado las 4 cartas");
				
	}

	public int getCantidadCartasTiradas() {
		return cantidadCartasTiradas;
	}

	public Jugador getGanador() {
		return ganador;
	}

	public void agregarMovimiento(Jugador jugador, Movimiento movimiento) {
		turnosBaza.add(movimiento);

		if (movimiento instanceof CartaTirada) {
			cantidadCartasTiradas++;
		} else if (movimiento instanceof Envite) {
			
		}
	}

	public Jugador cerrarBaza() throws BazaException {
		definirGanador();
		return ganador;
	}

	public boolean esEmpate() {
		
		if(cantidadCartasTiradas<4)
			return false;
		return true;
		
	}

	public boolean tenesMovimiento(MovimientoDTO ultimoMovimiento) {
		
		for(Movimiento movimiento: turnosBaza)
		{
			if(movimiento.getId() == ultimoMovimiento.getId())
				return true;
		}
		return false;
	}

	public List<Movimiento> getProximoMovimiento(MovimientoDTO ultimoMovimiento) {
		
		List<Movimiento> devolver = new ArrayList<Movimiento>();
		
		for(Movimiento movimiento: turnosBaza){
			//voy agregando los movimientos anteriores hasta incluir el nuevo
			if(movimiento.getId()>ultimoMovimiento.getId())
			{
				//ya me pase en el movimiento
				return devolver;
			}
			else
			{
				devolver.add(movimiento);
			}
		}
		
		return devolver;
	}

}
