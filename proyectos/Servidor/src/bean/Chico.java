package bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import dtos.*;
import exceptions.BazaException;
import exceptions.ChicoException;
import exceptions.JugadorException;
import exceptions.ManoException;
import exceptions.PartidoException;

/**
 * El Chico es cada una de las 3 Partidas cortas de 30 puntos
**/

@Entity
@Table (name = "Chicos")
public class Chico {
	
	@Id
	@Column (name = "id_chico", nullable = false)
	@GeneratedValue
	private int id;

	@Column (name = "nro_chico")
	private int numeroChico;

	@Transient
	private Partido partido; //se utiliza para reemplazar los observers

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "id_chico")
	private List<Mano> manos;

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn (name = "id_chico")
	private List<PuntajePareja> puntajes;

	@Column (name = "puntaje_maximo")
	private int puntajeMaximo;
	
	@Column (columnDefinition = "bit")
	private boolean terminado;


	public Chico() {
		
	}

	public Chico(Partido partido,int numeroChico, int puntajeMaximo, List<Pareja> parejas) {
		this.numeroChico = numeroChico;
		this.partido = partido;
		this.manos = new ArrayList<Mano>();
		this.puntajes = new ArrayList<PuntajePareja>();
		this.puntajeMaximo = puntajeMaximo;
		this.terminado = false;
		
		this.puntajes.add(new PuntajePareja(parejas.get(0), 0));
		this.puntajes.add(new PuntajePareja(parejas.get(1), 0));

		List<Jugador> ordenInicial = new ArrayList<Jugador>();
		ordenInicial.add(parejas.get(0).getJugador1());
		ordenInicial.add(parejas.get(1).getJugador1());
		ordenInicial.add(parejas.get(0).getJugador2());
		ordenInicial.add(parejas.get(1).getJugador2());

		this.manos.add(new Mano(this, 1, ordenInicial, puntajes));
	}

	public ChicoDTO toDto (){
		ChicoDTO dto = new ChicoDTO();
		dto.setId(this.id);
		dto.setPuntajeMaximo(this.puntajeMaximo);
		ArrayList<PuntajeParejaDTO> puntajesDto = new ArrayList<PuntajeParejaDTO>();
		for(int i=0; i<puntajes.size(); i++){
			puntajesDto.add(puntajes.get(i).toDTO());
		}
		dto.setPuntajes(puntajesDto);
		
		ArrayList<ManoDTO> manosDto = new ArrayList<ManoDTO>();
		for(int i=0; i<manos.size(); i++)
		{
			manosDto.add(manos.get(i).toDTO());
		}
		dto.setManos(manosDto);
		dto.setNumeroChico(this.numeroChico);
		dto.setTerminado(this.terminado);
		return dto;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	public int getNumeroChico() {
		return numeroChico;
	}

	public void setNumeroChico(int numeroChico) {
		this.numeroChico = numeroChico;
	}

	public void setManos(List<Mano> manos) {
		this.manos = manos;
	}

	public void setPuntajes(List<PuntajePareja> puntajes) {
		this.puntajes = puntajes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Mano> getManos() {
		return manos;
	}

	public void setManos(ArrayList<Mano> manos) {
		this.manos = manos;
	}

	public List<PuntajePareja> getPuntajes() {
		return puntajes;
	}

	public int getPuntajeMaximo() {
		return puntajeMaximo;
	}

	public void setPuntajeMaximo(int puntajeMaximo) {
		this.puntajeMaximo = puntajeMaximo;
	}

	public Mano obtenerUltimaMano() {
//		Mano mano = null;
//
//		for(int i=0; i<manos.size(); i++) {
//			if(mano == null) {
//				mano = manos.get(i);
//			}
//			else {
//				if(mano.getNumeroMano()<manos.get(i).getNumeroMano()) {
//					mano = manos.get(i);
//				}
//			}
//		}
//
//		return mano;

		return manos.get(manos.size() - 1);
	}

	public void actualizarPuntajePareja(int puntaje, Pareja pareja) throws PartidoException, JugadorException {
		for(int i=0; i<puntajes.size(); i++){
			if(puntajes.get(i).getPareja().getNumeroPareja() == pareja.getNumeroPareja()){
				
				if(puntajes.get(i).getPuntaje()+puntaje >= 30){
					puntajes.get(i).setPuntaje(30);
					terminado = true;
					partido.cerrarChico();
				}
				else{
					int aux = puntajes.get(i).getPuntaje();
					puntajes.get(i).setPuntaje(aux+puntaje);
				}
					
			}
		}
		
	}

	public void nuevaMano() {
		Mano ultimaMano = obtenerUltimaMano();

		List<Jugador> ordenJuego = ultimaMano.getOrdenJuego();

		// Recalculo orden juego. El jugador que estaba primero lo pongo al final de la lista		
		Jugador jug = ordenJuego.get(0);
		ordenJuego.remove(jug);
		ordenJuego.add(jug);

		Mano nuevaMano = new Mano(this, ultimaMano.getNumeroMano() + 1, ordenJuego, puntajes);
		manos.add(nuevaMano);
	}

	public Pareja obtenerParejaGanadora() {
		if (terminado) {
			if(puntajes.get(0).getPuntaje() >= puntajeMaximo)
				return puntajes.get(0).getPareja();
			else
				return puntajes.get(1).getPareja();
		}

		return null;		
	}
	
	public Jugador obtenerTurnoJugador() {
		
		return obtenerUltimaMano().getJugadorActual();
//		if(tocaCarta() == true) { // no hay que contestar envite, toca tirar carta
//			Mano mano = obtenerUltimaMano();
//			return mano.obtenerTurnoJugadorMano(); // devuelve el jugador que le toca jugar
//		}
//
//		return null;
	}
	
	public boolean tocaCarta() {
		Mano mano = obtenerUltimaMano(); // obtengo la ultima mano

		return mano.tocaCartaMano();
	}

	public void agregarMovimiento(Jugador jugador, Movimiento movimiento) throws PartidoException, BazaException, JugadorException {
		obtenerUltimaMano().agregarMovimiento(jugador, movimiento);
	}

	public void levantar(Partido partido) {
		//Solo me interesa Levantar aquel que esta activo
		if(partido.obtenerChicoActivo().getId() == this.id){
			this.partido = partido;
			for(Mano mano: manos){
				mano.levantar(this);
			}
		}
	}
	
	public Mano obtenerAnteUltimaMano() {
		if(manos.size() >= 2)
			return manos.get(manos.size()-2);
		else
			return null;
	}

	public List<Jugador> getOrdenInicial() {
		List<Jugador> orden = new ArrayList<Jugador>();
		
		orden.add(partido.getParejas().get(0).getJugador1());
		orden.add(partido.getParejas().get(1).getJugador1());
		orden.add(partido.getParejas().get(0).getJugador2());
		orden.add(partido.getParejas().get(1).getJugador2());
		
		return orden;
	}

	public boolean tenesMovimiento(MovimientoDTO ultimoMovimiento) {
		
		for(Mano mano: manos){
			
			if(mano.tenesMovimiento(ultimoMovimiento))
			{
				return true;
			}
		}
		return false;
	}

	public List<Movimiento> getProximoMovimiento(MovimientoDTO ultimoMovimiento) throws ChicoException, ManoException {
		//no va a entrar aca sin saber que tiene el movimiento
		for(Mano mano: manos){
			if(mano.tenesMovimiento(ultimoMovimiento)==true)
				return mano.getProximoMovimiento(ultimoMovimiento);
		}
		
		throw new ChicoException("Error al obtener el Proximo Movimiento en Chico");
	}

	public List<CartaJugador> getCartasJugadoresPartido(MovimientoDTO movimiento) throws ChicoException {
		
		
		for(Mano mano: manos){
			
			if(mano.tenesMovimiento(movimiento)){
				return mano.getCartasJugador();
			}
		}
		
		throw new ChicoException("Error grave: Nunca puede pedir cartas de un movimiento que no Existe");
	}
		
}
