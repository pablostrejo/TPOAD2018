package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import dtos.BazaDTO;
import dtos.CartaJugadorDTO;
import dtos.JugadorDTO;
import dtos.ManoDTO;
import dtos.MovimientoDTO;
import enums.TipoEnvite;
import exceptions.BazaException;
import exceptions.JugadorException;
import exceptions.PartidoException;


/**
 * La Mano esta compuesta por varias Bazas
**/

@Entity
@Table (name = "Manos")
public class Mano {
	@Id
	@Column (name = "id_mano", nullable = false)
	@GeneratedValue
	private int id;

	@Column (name = "nro_mano")
	private int numeroMano;

	@Transient
	private Chico chico; //se utiliza para reemplazar los observers

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "id_mano")
	private List<Baza> bazas;

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn (name = "id_mano")
	private List<CartaJugador> cartasJugador;

	@Transient
	private Envite ultimoEnvite;
	@Transient
	private Jugador jugadorActual;
	@Transient
	private Mazo mazo;
	@Transient
	private List<Jugador> ordenJuego;

	@Transient
	private List<PuntajePareja> puntajes;
	@Transient
	private byte envidoJugador1;
	@Transient
	private byte envidoJugador2;
	@Transient
	private byte envidoJugador3;
	@Transient
	private byte envidoJugador4;

	@Transient
	private byte puntajeTruco;


	public Mano() {
		
	}

	public Mano(Chico chico, int numeroMano, List<Jugador> ordenJuego, List<PuntajePareja> puntajes) {
		this.chico = chico;
		this.numeroMano = numeroMano;
		this.puntajes = puntajes;
		this.bazas = new ArrayList<Baza>();
		this.cartasJugador = new ArrayList<CartaJugador>();
		this.ultimoEnvite = null;
		this.puntajeTruco = 1; // es el puntaje minimo que se va a ganar con el Truco
		this.jugadorActual = ordenJuego.get(0);
		this.mazo = new Mazo();

		this.ordenJuego = ordenJuego;
//		this.ordenJuego = new ArrayList<Jugador>();
//		System.arraycopy(ordenJuego, 0, this.ordenJuego, 0, ordenJuego.size());

		repartirCartas(ordenJuego);

		this.bazas.add(new Baza(this, 1, ordenJuego));
	}

	private void repartirCartas(List<Jugador> ordenJuego) {
		int numeroJugador = 0;

		while(cartasJugador.size() < 12) {
			if(numeroJugador > 3)
				numeroJugador = 0;

			CartaJugador carta = new CartaJugador(ordenJuego.get(numeroJugador), mazo.obtenerCarta(), false);
			numeroJugador++;
			cartasJugador.add(carta);
		}
	}
	
	public List<Jugador> getOrdenJuego() {
		return ordenJuego;
	}

	public void setOrdenJuego(List<Jugador> ordenJuego) {
		this.ordenJuego = ordenJuego;
	}

	public void setBazas(List<Baza> bazas) {
		this.bazas = bazas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumeroMano() {
		return numeroMano;
	}

	public void setNumeroMano(int numeroMano) {
		this.numeroMano = numeroMano;
	}

	public List<Baza> getBazas() {
		return bazas;
	}

	public void setBazas(ArrayList<Baza> bazas) {
		this.bazas = bazas;
	}

	public List<CartaJugador> getCartasJugador() {
		return cartasJugador;
	}

	public void setCartasJugador(List<CartaJugador> cartasJugador) {
		this.cartasJugador = cartasJugador;
	}

	public Envite getUltimoEnvite() {
		return ultimoEnvite;
	}

	public byte getPuntajeTruco() {
		return puntajeTruco;
	}

	public void setUltimoEnvite(Envite ultimoEnvite) {
		this.ultimoEnvite = ultimoEnvite;
	}

	public Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}

	private byte obtenerPuntajeEnvido(boolean querido) {
		// recorremos todos los movimientos de la primer Baza (solo aqui se puede cantar Envido)
		// y analizamos cual es el puntaje acumulado del Envido!
		List<Movimiento> movimientos = bazas.get(0).getTurnosBaza();
		String cadenaDeEnvidos = "";

		for (Movimiento mov: movimientos) {
			if (mov instanceof Envite) {
				Envite envite = (Envite) mov;

				if (envite.sosAlgunEnvido())
					cadenaDeEnvidos = cadenaDeEnvidos + (envite.getTipoEnvite().name());
			}
		}

		if (cadenaDeEnvidos.isEmpty())
			return 0;
		else {
			if (querido)
				cadenaDeEnvidos = cadenaDeEnvidos + (TipoEnvite.Quiero.name());
			else
				cadenaDeEnvidos = cadenaDeEnvidos + (TipoEnvite.NoQuiero.name());

			return Envite.obtenerPuntajeEnvido(cadenaDeEnvidos);
		}
	}

	public Pareja obtenerGanadorEnvido() {
		// debemos obtener el Envido de TODOS los Jugadores ya que, segun las reglas,
		// el jugador que es 'mano' debe comenzar a decir su Envido, luego el Jugador
		// a su derecha, y asi, hasta el ultimo pie.
		envidoJugador1 = obtenerEnvidoJugador(ordenJuego.get(0));
		envidoJugador2 = obtenerEnvidoJugador(ordenJuego.get(1));
		envidoJugador3 = obtenerEnvidoJugador(ordenJuego.get(2));
		envidoJugador4 = obtenerEnvidoJugador(ordenJuego.get(3));

		// obtenemos el Envido mas alto de cada Pareja
		byte envidoPareja1 = envidoJugador1 > envidoJugador3 ? envidoJugador1 : envidoJugador3;
		byte envidoPareja2 = envidoJugador2 > envidoJugador4 ? envidoJugador2 : envidoJugador4;

		if (envidoPareja1 > envidoPareja2) {
			return obtenerParejaJugador(ordenJuego.get(0));
		} else if (envidoPareja1 < envidoPareja2) {
			return obtenerParejaJugador(ordenJuego.get(1));
		} else if (envidoPareja1 == envidoPareja2) {
			// obtengo la Pareja a la que pertenece el jugador que es 'mano' - ordenJuego.get(0) -
			return obtenerParejaJugador(ordenJuego.get(0));
		}
		return null;
	}

	private byte obtenerEnvidoJugador(Jugador jugador) {
		List<Carta> cartasJugador;
		byte envidoAcumulado = 0;
		cartasJugador = obtenerCartasDelJugador(jugador);

		if (cartasJugador.get(0).getPalo() == cartasJugador.get(1).getPalo()) {
			if (cartasJugador.get(0).sosCartaNegra() || cartasJugador.get(1).sosCartaNegra()) {
				// alguna de las primeras dos cartas es una figura, o ambas lo son!
				if (cartasJugador.get(0).sosCartaNegra()) {
					// la primer carta es una figura. Me fijo la segunda
					if (cartasJugador.get(1).sosCartaNegra())
						envidoAcumulado = 20;
					else
						envidoAcumulado = (byte) (20 + cartasJugador.get(1).getNumero());
				} else {
					// la segunda carta es una figura
					envidoAcumulado = (byte) (20 + cartasJugador.get(0).getNumero());
				}
			} else {
				// ninguna es una figura!
				envidoAcumulado = (byte) (20 + cartasJugador.get(0).getNumero() + cartasJugador.get(1).getNumero());
			}
		}
		// ahora vemos si la primer carta con la tercera forman envido y si superan el envido calculado anterior
		if (cartasJugador.get(0).getPalo() == cartasJugador.get(2).getPalo()) {
			if (cartasJugador.get(0).sosCartaNegra() || cartasJugador.get(2).sosCartaNegra()) {
				// la primera o la tercera carta es una figura, o ambas lo son!
				if (cartasJugador.get(0).sosCartaNegra()) {
					// la primer carta es una figura. Me fijo la tercera
					if (cartasJugador.get(2).sosCartaNegra())
						envidoAcumulado = (envidoAcumulado == 0) ? (byte) 20 : envidoAcumulado;
					else
						envidoAcumulado = (envidoAcumulado < (byte) (20 + cartasJugador.get(2).getNumero()) ? (byte) (20 + cartasJugador.get(2).getNumero()) : envidoAcumulado);
				} else {
					// la tercera carta es una figura
					envidoAcumulado = (envidoAcumulado < (byte) (20 + cartasJugador.get(0).getNumero()) ? (byte) (20 + cartasJugador.get(0).getNumero()) : envidoAcumulado);
				}
			} else {
				// ninguna es una figura!
				if ((envidoAcumulado < (byte) (20 + cartasJugador.get(0).getNumero() + cartasJugador.get(2).getNumero()))) {
					envidoAcumulado = (byte) (20 + cartasJugador.get(0).getNumero() + cartasJugador.get(2).getNumero());
				}
			}
		}
		// seguimos la competencia de envidos! ahora vemos si la segunda y tercera carta le ganan al envido calculado anterior
		if (cartasJugador.get(1).getPalo() == cartasJugador.get(2).getPalo()) {
			if (cartasJugador.get(1).sosCartaNegra() || cartasJugador.get(2).sosCartaNegra()) {
				// la segunda o la tercera carta es una figura, o ambas lo son!
				if (cartasJugador.get(1).sosCartaNegra()) {
					// la segunda carta es una figura. Me fijo la tercera
					if (cartasJugador.get(2).sosCartaNegra())
						envidoAcumulado = (envidoAcumulado == 0) ? (byte) 20 : envidoAcumulado;
					else
						envidoAcumulado = (envidoAcumulado < (byte) (20 + cartasJugador.get(2).getNumero()) ? (byte) (20 + cartasJugador.get(2).getNumero()) : envidoAcumulado);
				} else {
					// la tercera carta es una figura
					envidoAcumulado = (envidoAcumulado < (byte) (20 + cartasJugador.get(1).getNumero()) ? (byte) (20 + cartasJugador.get(1).getNumero()) : envidoAcumulado);
				}
			} else {
				// ninguna es una figura!
				if ((envidoAcumulado < (byte) (20 + cartasJugador.get(1).getNumero() + cartasJugador.get(2).getNumero()))) {
					envidoAcumulado = (byte) (20 + cartasJugador.get(1).getNumero() + cartasJugador.get(2).getNumero());
				}
			}
		}

		// si las tres cartas son del mismo palo, ya entro en los 3 'if' anteriores
		// nos queda solo el caso en que las tres cartas son de distinto palo! Es decir, no entro a ningun 'if'
		if (envidoAcumulado == 0) {
			if (!cartasJugador.get(0).sosCartaNegra())
				envidoAcumulado = (byte) cartasJugador.get(0).getNumero();
			if (!cartasJugador.get(1).sosCartaNegra())
				envidoAcumulado = (envidoAcumulado < (byte) cartasJugador.get(1).getNumero()) ? (byte) cartasJugador.get(1).getNumero(): envidoAcumulado;
			if (!cartasJugador.get(2).sosCartaNegra())
				envidoAcumulado = (envidoAcumulado < (byte) cartasJugador.get(2).getNumero()) ? (byte) cartasJugador.get(2).getNumero(): envidoAcumulado;
		}

		return envidoAcumulado;
	}

	public Pareja obtenerParejaJugador(Jugador jugador) {
		if (puntajes.get(0).getPareja().tenesJugador(jugador)) {
			return puntajes.get(0).getPareja();
		}
		return puntajes.get(1).getPareja();
	}

	private Pareja obtenerParejaEnemiga(Jugador jugador) {
		if (puntajes.get(0).getPareja().tenesJugador(jugador)) {
			return puntajes.get(1).getPareja();
		}
		return puntajes.get(0).getPareja();
	}

	public void nuevaBaza(int numeroBaza, Jugador ganadorUltimaBaza) {
		List<Jugador> ordenNuevo = null;

		// recalculamos el nuevo orden de juego
		if (ganadorUltimaBaza == null) {
			// hubo un empate en la ultima Baza, se sigue usando el orden del inicio de la Mano
			ordenNuevo = ordenJuego;
		} else {
			// hubo un ganador, la nueva Baza comienza con el ganador de la ultima
			ordenNuevo = new ArrayList<Jugador>();

			ordenNuevo.add(ganadorUltimaBaza);
			for (int i=ordenJuego.indexOf(ganadorUltimaBaza)+1; ordenNuevo.size()<4; i++)
			{
				if (i == 4)
					i = 0;
				ordenNuevo.add(ordenJuego.get(i));
			}
		}
		bazas.add(new Baza(this, numeroBaza, ordenNuevo));
	}

	public ManoDTO toDTO() {
		ManoDTO dto = new ManoDTO();

		dto.setId(this.id);
		dto.setNumeroMano(this.numeroMano);
		if(ultimoEnvite!=null){
			dto.setUltimoEnvite(this.ultimoEnvite.toDTO());
		}
		ArrayList<BazaDTO> bazasDto = new ArrayList<BazaDTO>();
		
		for(int i=0; i<bazas.size(); i++) {
			bazasDto.add(bazas.get(i).toDTO());
			
		}
		dto.setBazas(bazasDto);
		ArrayList<CartaJugadorDTO> cartasDto = new ArrayList<CartaJugadorDTO>();
		
		for(int i=0; i<cartasJugador.size(); i++) {
			cartasDto.add(cartasJugador.get(i).toDTO());
		}
		dto.setCartasJugador(cartasDto);

		return dto;
	}

	public boolean tocaCartaMano() {
		Baza baza = obtenerUltimaBaza(); //obtengo la ultima baza

		if(baza.getGanador() == null){ //la baza no tiene un ganador, o sea sigue activa
			if (baza.getCantidadCartasTiradas() < 4) { //Todavia no tiraron todos sus cartas
				Movimiento mov = baza.obtenerUltimoMovimiento();
				if((mov instanceof CartaTirada) || ultimoEnvite.getTipoEnvite().equals(TipoEnvite.Quiero) || ultimoEnvite.getTipoEnvite().equals(TipoEnvite.NoQuiero))
					// Lo ultimo que se tiro fue una carta, no hay que responder envite 
					return true;
				
				return false;
			} else {
				return false;
			}
		}

		return false;
	}

	public Jugador obtenerTurnoJugadorMano() {
		return obtenerUltimaBaza().obtenerTurnoBaza();
	}

	private Envite enviteAnteriorAlQuiero() {
		List<Movimiento> movimientosBazas = new ArrayList<Movimiento>();

		for (int i=0; i < bazas.size(); i++) {
			for (int j=0; j < bazas.get(i).getTurnosBaza().size(); j++) {
				// acumulo TODOS los movimientos de la mano!
				movimientosBazas.add(bazas.get(i).getTurnosBaza().get(j));
			}
		}

		for (int i = movimientosBazas.size() - 1; i >= 0; i--) {
			if (movimientosBazas.get(i) instanceof Envite) {
				if (((Envite) movimientosBazas.get(i)).getTipoEnvite().equals(TipoEnvite.Quiero)) {
					return ((Envite) movimientosBazas.get(i-1));
				}
			}
		}
		return null;
	}

	public List<MovimientoDTO> obtenerTodosLosMovimientos() {
		List<MovimientoDTO> movimientosBazas = new ArrayList<MovimientoDTO>();
		
		for (int i=0; i < bazas.size(); i++) {
			for (int j=0; j < bazas.get(i).getTurnosBaza().size(); j++) {
				// acumulo TODOS los movimientos de la mano!
				movimientosBazas.add(bazas.get(i).getTurnosBaza().get(j).toDTO());
			}
		}

		return movimientosBazas;
	}

	public boolean puedoEnvido() {
		if (bazas.size() == 1) {
			// es la primer baza, primera condicion para cantar envido
			Baza baza = bazas.get(0);

			if ((tocaCartaMano()) || (ultimoEnvite.getTipoEnvite().equals(TipoEnvite.Truco))) { //no hay que responder un envite anterior
				if (baza.getCantidadCartasTiradas() >= 2) { // los que pueden cantar son el tercero o 4
					if (seCantoEnvido() == false)
						return true;

					return false;					
				}
			}
			return false; // hay que responder un envite
		}
		return false;
	}

	// SIEMPRE nos pedira los envites posibles el jugador que le toca jugar (jugadorActual)!
	public List<TipoEnvite> obtenerEnvitesPosibles() {
		List<TipoEnvite> respuestas = new ArrayList<TipoEnvite>();

		/*

		if (tocaCartaMano()) {
			// significa que no hay un envite pendiente a responder
			if (puedoEnvido()) {
				respuestas.add(TipoEnvite.Envido);
				respuestas.add(TipoEnvite.RealEnvido);
				respuestas.add(TipoEnvite.FaltaEnvido);
				respuestas.add(TipoEnvite.Truco);
				respuestas.add(TipoEnvite.IrAlMazo);

				return respuestas;
			} else {
				// no puede cantar envido pero va a verificar si puede cantar truco
				if (puedoTruco()) {
					respuestas.add(TipoEnvite.Truco);
					respuestas.add(TipoEnvite.IrAlMazo);
				}
				// falta obtener cual de los trucos se canto
			}
		} else {

			// toca responder un envite
			Baza baza = bazas.get(bazas.size()-1);//
			Movimiento mov = baza.obtenerUltimoMovimiento();
			TipoEnvite env = ((Envite) mov).getTipoEnvite();

			switch (env) {

		*/

		if (ultimoEnvite == null) {
			// aun no se canto nada
			if (bazas.size() == 1) {
				if ((obtenerUltimaBaza().getCantidadCartasTiradas() == 2) ||
					(obtenerUltimaBaza().getCantidadCartasTiradas() == 3)) {

					respuestas.add(TipoEnvite.Envido);
					respuestas.add(TipoEnvite.RealEnvido);
					respuestas.add(TipoEnvite.FaltaEnvido);
					respuestas.add(TipoEnvite.Truco);
					respuestas.add(TipoEnvite.IrAlMazo);
				} else {
					// no le enviamos ninguna opcion. Lo obligamos a tirar solamente.
				}
			} else {
				// el juego esta en la segunda o tercer baza y no se canto nada aun!
				respuestas.add(TipoEnvite.Truco);
				
				if ((obtenerUltimaBaza().getCantidadCartasTiradas() == 2) ||
					(obtenerUltimaBaza().getCantidadCartasTiradas() == 3)) {
					respuestas.add(TipoEnvite.IrAlMazo);
				}
			}
		} else {
			switch (ultimoEnvite.getTipoEnvite()) {
				case Envido : {
					respuestas.add(TipoEnvite.EnvidoEnvido);
					respuestas.add(TipoEnvite.RealEnvido);
					respuestas.add(TipoEnvite.FaltaEnvido);
					respuestas.add(TipoEnvite.Quiero);
					respuestas.add(TipoEnvite.NoQuiero);
					break;
				}
				case EnvidoEnvido : {
					respuestas.add(TipoEnvite.RealEnvido);
					respuestas.add(TipoEnvite.FaltaEnvido);
					respuestas.add(TipoEnvite.Quiero);
					respuestas.add(TipoEnvite.NoQuiero);
					break;
				}
				case RealEnvido : {
					respuestas.add(TipoEnvite.FaltaEnvido);
					respuestas.add(TipoEnvite.Quiero);
					respuestas.add(TipoEnvite.NoQuiero);
					break;
				}
				case FaltaEnvido : {
					respuestas.add(TipoEnvite.Quiero);
					respuestas.add(TipoEnvite.NoQuiero);
					break;
				}
				case Truco : {
					if (puedoEnvido()) {
						// el Envido esta primero!
						respuestas.add(TipoEnvite.Envido);
						respuestas.add(TipoEnvite.RealEnvido);
						respuestas.add(TipoEnvite.FaltaEnvido);
					}
					respuestas.add(TipoEnvite.ReTruco);
					respuestas.add(TipoEnvite.Quiero);
					respuestas.add(TipoEnvite.NoQuiero);
					break;
				}
				case ReTruco : {
					respuestas.add(TipoEnvite.ValeCuatro);
					respuestas.add(TipoEnvite.Quiero);
					respuestas.add(TipoEnvite.NoQuiero);
					break;
				}
				case ValeCuatro : {
					respuestas.add(TipoEnvite.Quiero);
					respuestas.add(TipoEnvite.NoQuiero);
					break;
				}
				case IrAlMazo :
					break;
				case NoQuiero : {
					// se supone que dijeron 'NoQuiero' a un Envido! Porque si dijeron 'NoQuiero' a algun truco 
					// entonces no entra nunca aca, porque se reparte una nueva Mano!
					respuestas.add(TipoEnvite.Truco);
					break;
				}
				case Quiero :
					Envite enviteAnterior = enviteAnteriorAlQuiero();

					if (enviteAnterior.sosAlgunEnvido()) {
						respuestas.add(TipoEnvite.Truco);
					} else {
						// antes del Quiero se canto algun Truco!
						// La pareja que dijo 'Quiero' es la que tiene el poder de elevar la apuesta
						if (obtenerParejaJugador(ultimoEnvite.getJugador()).esPareja(obtenerParejaJugador(jugadorActual))) {
							if (enviteAnterior.getTipoEnvite().equals(TipoEnvite.Truco))
								respuestas.add(TipoEnvite.ReTruco);
							else if (enviteAnterior.getTipoEnvite().equals(TipoEnvite.ReTruco))
								respuestas.add(TipoEnvite.ValeCuatro);
						}
					}
					break;
				default :
					break;
			}

			if ((!ultimoEnvite.sosAlgunEnvido()) &&
				((obtenerUltimaBaza().getCantidadCartasTiradas() == 2) ||
				 (obtenerUltimaBaza().getCantidadCartasTiradas() == 3))) {

				respuestas.add(TipoEnvite.IrAlMazo);
			}
		}

		return respuestas;
	}

	public boolean puedoTruco() {
		if(tocaCartaMano() == true) //no toca responder un envite
		{
			if(seCantoTruco() == false)
				return true;
			
		}
		return false;
	}

	public boolean seCantoEnvido() {
		Baza baza = bazas.get(0);

		for(Movimiento mov: baza.getTurnosBaza()) {
			if(mov instanceof Envite) {
				Envite aux = (Envite) mov;

				if (aux.sosAlgunEnvido())
					return true;
			}
		}
		return false;
	}

	public boolean seCantoTruco(){
		for(Baza baza : bazas) {
			for(Movimiento mov: baza.getTurnosBaza())
			{
				if(mov instanceof Envite)
				{
					Envite aux = (Envite) mov;
				
					if(aux.getTipoEnvite() == TipoEnvite.Truco)
						return true;
				}
			}
		}
		return false;
	}

	public Jugador obtenerTurnoContestar() {		
		return obtenerUltimaBaza().obtenerTurnoContestar();
	}

	public Jugador getJugadorActual() {
		return jugadorActual;
	}

	public void setJugadorActual(Jugador jugadorActual) {
		this.jugadorActual = jugadorActual;
	}

	public byte getEnvidoJugador1() {
		return envidoJugador1;
	}

	public byte getEnvidoJugador2() {
		return envidoJugador2;
	}

	public byte getEnvidoJugador3() {
		return envidoJugador3;
	}

	public byte getEnvidoJugador4() {
		return envidoJugador4;
	}

	public Baza obtenerUltimaBaza() {
		return bazas.get(bazas.size() - 1);
	}

	public void agregarMovimiento(Jugador jugador, Movimiento movimiento) throws PartidoException, BazaException, JugadorException {
		Baza ultimaBaza = obtenerUltimaBaza();
		Jugador ganadorBaza = null;

		movimiento.setNumeroTurno(ultimaBaza.getTurnosBaza().size() + 1);
		if (movimiento instanceof CartaTirada) {
			CartaTirada cartaTirada = (CartaTirada) movimiento;

			// obtengo la CartaJugador en estado persistente!
			CartaJugador cartaJugador = obtenerCartaJugador(cartaTirada);
			cartaTirada.setCartaJugador(cartaJugador);

			if (tiroCarta(jugador, cartaTirada))
				throw new PartidoException("No puede arrojar dos veces la misma carta!");

			cartaJugador.setTirada(true);

			ultimaBaza.agregarMovimiento(jugador, cartaTirada);

			if (ultimaBaza.getCantidadCartasTiradas() == 4) {
				// se arrojo la ultima carta de la ronda! hay que cerrar la baza
				ganadorBaza = ultimaBaza.cerrarBaza();
				
				if (ganadorBaza == null) {
					// se produjo un empate en la baza
					if(ultimaBaza.getNumeroBaza() == 1) {
						nuevaBaza(2, ganadorBaza);
						// juega el jugador mano
						jugadorActual = ordenJuego.get(0);
					}
					else
						if(ultimaBaza.getNumeroBaza() == 2) {
							// se produjo un empate en la segunda baza
							if(bazas.get(0).getGanador() == null){ //tambien se empato la primera
								nuevaBaza(3, ganadorBaza);
								// juega el jugador mano
								jugadorActual = ordenJuego.get(0);
							}
							else {
								// hubo un ganador en la primera, por lo tanto ese ganador gana la mano. Se tiene que cerrar la mano
								chico.actualizarPuntajePareja(puntajeTruco, obtenerParejaJugador(bazas.get(0).getGanador()));

								if(!chico.isTerminado()) {
									// Tengo que generar la Nueva Mano
									chico.nuevaMano();
								}
								else {
									// Se termina el chico, pero de esto se ocupa la funcion actualizarPuntajePareja
								}
							}									
						} else {
							// Es la tercer Baza
							if(bazas.get(0).getGanador() == null) {
								// Hubo 3 empates Seguidos. gana el jugador Mano
								chico.actualizarPuntajePareja(puntajeTruco, obtenerParejaJugador(ordenJuego.get(0)));
							}
							else {
								// Hubo un Ganador Distinto en la primera y segunda Baza, El ganador es la pareja que gano la primer Baza
								chico.actualizarPuntajePareja(puntajeTruco, obtenerParejaJugador(bazas.get(0).getGanador()));
							}
							
							// Si el chico no termino, tengo que crear una nueva mano
							if(!chico.isTerminado()) {
								// Tengo que generar la Nueva Mano
								chico.nuevaMano();
							}
						}
				}
				else {
					// hubo un ganador en la Baza
					if (ultimaBaza.getNumeroBaza() == 1) {
						// es la primer Baza, entonces creamos una nueva Baza
						nuevaBaza(2, ganadorBaza);
						jugadorActual = ganadorBaza;
					} else if (ultimaBaza.getNumeroBaza() == 2) {
						// veo quien gano la primer Baza
						if ((bazas.get(0).getGanador() == null) || (obtenerParejaJugador(bazas.get(0).getGanador()) == obtenerParejaJugador(ganadorBaza))) {
							// se habia empatado en la primer Baza o la pareja ganadora de la primer Baza es la misma que la que acaba de ganar la segunda,
							// entonces el ganador es la que acaba de ganar esta segunda Baza
							chico.actualizarPuntajePareja(puntajeTruco, obtenerParejaJugador(ganadorBaza));
							
							// si el chico no termino, tengo que crear una nueva mano
							if(!chico.isTerminado()){
								// tengo que generar la Nueva Mano
								chico.nuevaMano();
							}
						} else {
							// una Pareja gano la primer baza y la otra Pareja gano la segunda
							nuevaBaza(3, ganadorBaza);
							jugadorActual = ganadorBaza;
						}
					} else {
						// hubo un ganador en la tercer Baza, entonces ESE es el ganador de la Mano
						chico.actualizarPuntajePareja(puntajeTruco, obtenerParejaJugador(ganadorBaza));

						// Si el chico no termino, tengo que crear una nueva mano
						if (!chico.isTerminado()) {
							chico.nuevaMano();
						}
					}
				}
			} else {
				// todavia no se termino la Baza
//				jugadorActual = ordenJuego.get(ultimaBaza.getCantidadCartasTiradas());
				jugadorActual = obtenerUltimaBaza().getOrdenJuego().get(ultimaBaza.getCantidadCartasTiradas());
			}

		}
		else if (movimiento instanceof Envite) {
			Envite envite = (Envite) movimiento;

			ultimaBaza.agregarMovimiento(jugador, envite);

//			if ((envite.getTipoEnvite().equals(TipoEnvite.Quiero))	||
//				(envite.getTipoEnvite().equals(TipoEnvite.ReTruco))	||
//				(envite.getTipoEnvite().equals(TipoEnvite.ValeCuatro))) {
			if (envite.getTipoEnvite().equals(TipoEnvite.Quiero)) {
				// primero, verifico si el Envite es un 'Quiero' de alguno de todos los Envidos
				if (ultimoEnvite.sosAlgunEnvido()) {
					// debemos calcular el puntaje que corresponde a la cadena de Envidos...
					byte puntajeEnvido = obtenerPuntajeEnvido(true);
					// debemos analizar quien gana el Envido!
					Pareja ganadorEnvido = obtenerGanadorEnvido();
					// Salvamos el caso de la falta envido
					if (puntajeEnvido == 100){
						puntajeEnvido = calcularPuntajeFaltaEnvido(ganadorEnvido);
					}
					// OJO, quizas el que gano el Envido ya alcanzo los 30 puntos y gana el Chico!
					chico.actualizarPuntajePareja(puntajeEnvido, ganadorEnvido);
					
					/* PREGUNTO SI NO TERMINO EL CHICO */
					if(!chico.isTerminado()){
						jugadorActual = obtenerUltimaBaza().getOrdenJuego().get(obtenerUltimaBaza().getCantidadCartasTiradas());					
					}
				} else
				// ahora, verifico si el Envite es un 'Quiero' de alguno de todos los Trucos, o Retruco o Valecuatro 
				if (ultimoEnvite.sosAlgunTruco()) {
					// quisieron algun Truco...
					puntajeTruco++;
					
					if(envite.getTipoEnvite().equals(TipoEnvite.Quiero))
						//Toca Tirar
						jugadorActual = obtenerUltimaBaza().getOrdenJuego().get(obtenerUltimaBaza().getCantidadCartasTiradas());
					else
						// Toca Responder
						jugadorActual = (ordenJuego.indexOf(envite.getJugador()) == 0 || ordenJuego.indexOf(envite.getJugador()) == 2) ?
								ordenJuego.get(3) : ordenJuego.get(2);
				}
			} else if (envite.getTipoEnvite().equals(TipoEnvite.NoQuiero)) {
				// primero, verifico si el Envite es un 'NoQuiero' de alguno de todos los Envidos
				if (ultimoEnvite.sosAlgunEnvido()) {
					// debemos calcular el puntaje que corresponde a la cadena de Envidos...
					byte puntajeEnvido = obtenerPuntajeEnvido(false);
					// los puntos del Envido se los lleva la Pareja contraria del Jugador que dijo NoQuiero 
					Pareja ganadorEnvido = obtenerParejaEnemiga(envite.getJugador());
					// OJO, quizas el que gano el Envido ya alcanzo los 30 puntos y gana el Chico!

					// CERRAR BAZA? CERRAR MANO?
					chico.actualizarPuntajePareja(puntajeEnvido, ganadorEnvido);

					jugadorActual = ordenJuego.get(obtenerUltimaBaza().getCantidadCartasTiradas());
				} else
				// ahora, verifico si el Envite es un 'NoQuiero' de alguno de todos los Trucos
				if (ultimoEnvite.sosAlgunTruco()) {
					// NO quisieron algun Truco, por lo tanto cerramos la Mano
					
					// los puntos del Truco se los lleva la Pareja contraria del Jugador que dijo NoQuiero 
					Pareja ganadorTruco = obtenerParejaEnemiga(envite.getJugador());
					// OJO, quizas el que gano el Truco ya alcanzo los 30 puntos y gana el Chico!

					// CERRAR BAZA Y LUEGO LA MANO!
					chico.actualizarPuntajePareja(puntajeTruco, ganadorTruco);

					if (!chico.isTerminado()) {
						chico.nuevaMano();
					}
				}
			} else if (envite.getTipoEnvite().equals(TipoEnvite.IrAlMazo)) {

				if ((obtenerUltimaBaza().getNumeroBaza() == 1) && (!seCantoEnvido()) && (puntajeTruco == 1))
					chico.actualizarPuntajePareja(puntajeTruco+1, obtenerParejaEnemiga(envite.getJugador()));
				else
					chico.actualizarPuntajePareja(puntajeTruco, obtenerParejaEnemiga(envite.getJugador()));
				
				
				if (!chico.isTerminado()) {
					chico.nuevaMano();
				}
				
			} else {
				if((envite.getTipoEnvite().equals(TipoEnvite.ReTruco)) || (envite.getTipoEnvite().equals(TipoEnvite.ValeCuatro))) {
					//Verifico si se retruco algun truco, o retruco sin decir quiero antes. Debo sumar 1 al puntajeTruco
					if((ultimoEnvite.getTipoEnvite().equals(TipoEnvite.Truco)) || (ultimoEnvite.getTipoEnvite().equals(TipoEnvite.ReTruco)))
						puntajeTruco++;
				}
				// canto algo! pasamos el turno al siguiente
				// sea cual sea la Baza, hacemos que responda el pie de la otra Pareja. Aunque si es la segunda o tercer baza...canto Truco!.
				// el metodo 'obtenerEnvitesPosibles()' es el que filtra QUIEN puede cantar en cada Baza.
				List<Jugador> ordenJuegoBaza = ultimaBaza.getOrdenJuego();

				jugadorActual = (ordenJuegoBaza.indexOf(envite.getJugador()) == 0 || ordenJuegoBaza.indexOf(envite.getJugador()) == 2) ?
									ordenJuegoBaza.get(3) : ordenJuegoBaza.get(2);
			}
			ultimoEnvite = envite;
		}
	}

	private boolean tiroCarta(Jugador jugador, Movimiento movimiento) {
		for(CartaJugador cartaJugador: cartasJugador) {
			if(cartaJugador.getJugador().equals(jugador) && (((CartaTirada) movimiento).getCartaJugador().equals(cartaJugador)))
				if(cartaJugador.isTirada())
					return true;
				else
					return false;
		}
		return false;
	}

	private byte calcularPuntajeFaltaEnvido(Pareja ganadorEnvido) {
		//Falta envido: equivale al numero de tantos necesarios para que el bando que va por
		//delante gane el chico o el juego. Algunos jugadores apuestan a que el bando ganador
		//lo sera tambien de la partida, aunque vaya por detras en el tanteo.
		PuntajePareja puntajeMax = null;
		
		// Obtengo el puntaje del que va por delante 
		for(PuntajePareja puntaje: puntajes){
			if((puntajeMax == null) || (puntaje.getPuntaje() > puntajeMax.getPuntaje()))
			{
				puntajeMax = puntaje;
			}
		}
		
		// Ahora devuelvo el verdadero puntaje de la falta envido 
		return (byte) (chico.getPuntajeMaximo()-puntajeMax.getPuntaje());
	}

	public List<CartaJugadorDTO> obtenerCartasJugador(JugadorDTO jugador) {
		List<CartaJugadorDTO> devolver = new ArrayList<CartaJugadorDTO>();

		for(CartaJugador cartaJugador: cartasJugador) {
			if(cartaJugador.getJugador().sosJugador(jugador)) {
				devolver.add(cartaJugador.toDTO());
			}
		}
		return devolver;
	}

	public List<Carta> obtenerCartasDelJugador(Jugador jugador) {
		List<Carta> cartas = new ArrayList<Carta>();

		for (CartaJugador cartaJugador: cartasJugador) {
			if (cartaJugador.getJugador().equals(jugador)) {
				cartas.add(cartaJugador.getCarta());
			}
		}
		return cartas;
	}

	public CartaJugador obtenerCartaJugador(CartaTirada carta) {
		for(CartaJugador aux : cartasJugador) {
			if (aux.getCarta().getId() == (carta.getCartaJugador().getCarta().getId()))
				return aux;
		}
		return null;
	}

	public void levantar(Chico chico) {
		
		this.chico = chico;		
		Mano aux = chico.obtenerUltimaMano();
		
		/* Es La Mano Actual debo Actualizar sus Datos */
		
		if(aux.getNumeroMano() == numeroMano){
			
			
			Mano anteUltima = chico.obtenerAnteUltimaMano();
			
		
			
			if(anteUltima == null){
				
				/* ES LA ULTIMA MANO, NO HAY ANTEULTIMA */
				/* LE DOY EL ORDEN INICIAL DE JUEGO */
				ordenJuego = new ArrayList<Jugador>();
				ordenJuego.addAll(chico.getOrdenInicial());
				
			}
			else{
								
				/* Vamos a Obtener el Orden de Juego, 
				 * Asumimos que Hibernate levanta en orden las CartaJugador 
				 * Las primeras 4 cartas te dan el orden de Juego*/
				 
				ordenJuego = new ArrayList<Jugador>();
				
				ordenJuego.add(cartasJugador.get(0).getJugador());
				ordenJuego.add(cartasJugador.get(1).getJugador());
				ordenJuego.add(cartasJugador.get(2).getJugador());
				ordenJuego.add(cartasJugador.get(3).getJugador());
								
				
			}
			/* Obtengo el Envite Actual */
			
			for(Movimiento movimiento: obtenerUltimaBaza().getTurnosBaza())
			{
				if(movimiento instanceof Envite)
				{
					ultimoEnvite = (Envite) movimiento;
				}
			}
			
			puntajes = chico.getPuntajes();
			
			/* FALTA OBTENER EL JUGADOR ACTUAL */
			
			/* FALTA REARMAR LAS BAZAS */
		}
		
	}
	
	public List<JugadorDTO>  obtenerGanadoresBazas (){
		
		List<JugadorDTO> ganadores = new ArrayList<JugadorDTO>();
		
		JugadorDTO aux;
		for(Baza baza: bazas){
			
			if(baza.getGanador() != null)
			{
				ganadores.add(baza.getGanador().toDTO());
			}
			else
			{
				if(baza.esEmpate())
				{
					aux = new JugadorDTO();
					aux.setApodo("Empate");
					ganadores.add(aux);
				}
			}
		}
		
		return ganadores;
		
	}

	public boolean tenesMovimiento(MovimientoDTO ultimoMovimiento) {
		
		for(Baza baza: bazas)
		{
			if(baza.tenesMovimiento(ultimoMovimiento))
				return true;
		}
		return false;
	}

	public List<Movimiento> getProximoMovimiento(MovimientoDTO ultimoMovimiento) {
		
		List<Movimiento> devolver = new ArrayList<Movimiento>();
		//no va a entrar aqui sin saber que tiene el movimiento
		for(Baza baza: bazas)
		{
			if(baza.tenesMovimiento(ultimoMovimiento)==true){
				devolver.addAll(baza.getProximoMovimiento(ultimoMovimiento));
				return devolver;
			}
			else
			{
				devolver.addAll(baza.getTurnosBaza());
			}
		}
		
		return devolver;
	}

}
