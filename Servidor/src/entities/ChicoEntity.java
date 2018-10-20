package entities;

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
public class ChicoEntity {
	
	@Id
	@Column (name = "id_chico", nullable = false)
	@GeneratedValue
	private int id;

	@Column (name = "nro_chico")
	private int numeroChico;

	@Transient
	private PartidoEntity partido; //se utiliza para reemplazar los observers

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "id_chico")
	private List<ManoEntity> manos;

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn (name = "id_chico")
	private List<PuntajeParejaEntity> puntajes;

	@Column (name = "puntaje_maximo")
	private int puntajeMaximo;
	
	@Column (columnDefinition = "bit")
	private boolean terminado;


	public ChicoEntity() {
		
	}

	public ChicoEntity(PartidoEntity partido,int numeroChico, int puntajeMaximo, List<ParejaEntity> parejas) {
		this.numeroChico = numeroChico;
		this.partido = partido;
		this.manos = new ArrayList<ManoEntity>();
		this.puntajes = new ArrayList<PuntajeParejaEntity>();
		this.puntajeMaximo = puntajeMaximo;
		this.terminado = false;
		
		this.puntajes.add(new PuntajeParejaEntity(parejas.get(0), 0));
		this.puntajes.add(new PuntajeParejaEntity(parejas.get(1), 0));

		List<JugadorEntity> ordenInicial = new ArrayList<JugadorEntity>();
		ordenInicial.add(parejas.get(0).getJugador1());
		ordenInicial.add(parejas.get(1).getJugador1());
		ordenInicial.add(parejas.get(0).getJugador2());
		ordenInicial.add(parejas.get(1).getJugador2());

		this.manos.add(new ManoEntity(this, 1, ordenInicial, puntajes));
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

	public void setManos(List<ManoEntity> manos) {
		this.manos = manos;
	}

	public void setPuntajes(List<PuntajeParejaEntity> puntajes) {
		this.puntajes = puntajes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ManoEntity> getManos() {
		return manos;
	}

	public void setManos(ArrayList<ManoEntity> manos) {
		this.manos = manos;
	}

	public List<PuntajeParejaEntity> getPuntajes() {
		return puntajes;
	}

	public int getPuntajeMaximo() {
		return puntajeMaximo;
	}

	public void setPuntajeMaximo(int puntajeMaximo) {
		this.puntajeMaximo = puntajeMaximo;
	}

	public ManoEntity obtenerUltimaMano() {
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

	public void actualizarPuntajePareja(int puntaje, ParejaEntity pareja) throws PartidoException, JugadorException {
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
		ManoEntity ultimaMano = obtenerUltimaMano();

		List<JugadorEntity> ordenJuego = ultimaMano.getOrdenJuego();

		// Recalculo orden juego. El jugador que estaba primero lo pongo al final de la lista		
		JugadorEntity jug = ordenJuego.get(0);
		ordenJuego.remove(jug);
		ordenJuego.add(jug);

		ManoEntity nuevaMano = new ManoEntity(this, ultimaMano.getNumeroMano() + 1, ordenJuego, puntajes);
		manos.add(nuevaMano);
	}

	public ParejaEntity obtenerParejaGanadora() {
		if (terminado) {
			if(puntajes.get(0).getPuntaje() >= puntajeMaximo)
				return puntajes.get(0).getPareja();
			else
				return puntajes.get(1).getPareja();
		}

		return null;		
	}
	
	public JugadorEntity obtenerTurnoJugador() {
		
		return obtenerUltimaMano().getJugadorActual();
//		if(tocaCarta() == true) { // no hay que contestar envite, toca tirar carta
//			Mano mano = obtenerUltimaMano();
//			return mano.obtenerTurnoJugadorMano(); // devuelve el jugador que le toca jugar
//		}
//
//		return null;
	}
	
	public boolean tocaCarta() {
		ManoEntity mano = obtenerUltimaMano(); // obtengo la ultima mano

		return mano.tocaCartaMano();
	}

	public void agregarMovimiento(JugadorEntity jugador, MovimientoEntity movimiento) throws PartidoException, BazaException, JugadorException {
		obtenerUltimaMano().agregarMovimiento(jugador, movimiento);
	}

	public void levantar(PartidoEntity partido) {
		//Solo me interesa Levantar aquel que esta activo
		if(partido.obtenerChicoActivo().getId() == this.id){
			this.partido = partido;
			for(ManoEntity mano: manos){
				mano.levantar(this);
			}
		}
	}
	
	public ManoEntity obtenerAnteUltimaMano() {
		if(manos.size() >= 2)
			return manos.get(manos.size()-2);
		else
			return null;
	}

	public List<JugadorEntity> getOrdenInicial() {
		List<JugadorEntity> orden = new ArrayList<JugadorEntity>();
		
		orden.add(partido.getParejas().get(0).getJugador1());
		orden.add(partido.getParejas().get(1).getJugador1());
		orden.add(partido.getParejas().get(0).getJugador2());
		orden.add(partido.getParejas().get(1).getJugador2());
		
		return orden;
	}

	public boolean tenesMovimiento(MovimientoDTO ultimoMovimiento) {
		
		for(ManoEntity mano: manos){
			
			if(mano.tenesMovimiento(ultimoMovimiento))
			{
				return true;
			}
		}
		return false;
	}

	public List<MovimientoEntity> getProximoMovimiento(MovimientoDTO ultimoMovimiento) throws ChicoException, ManoException {
		//no va a entrar aca sin saber que tiene el movimiento
		for(ManoEntity mano: manos){
			if(mano.tenesMovimiento(ultimoMovimiento)==true)
				return mano.getProximoMovimiento(ultimoMovimiento);
		}
		
		throw new ChicoException("Error al obtener el Proximo Movimiento en Chico");
	}

	public List<CartaJugadorEntity> getCartasJugadoresPartido(MovimientoDTO movimiento) throws ChicoException {
		
		
		for(ManoEntity mano: manos){
			
			if(mano.tenesMovimiento(movimiento)){
				return mano.getCartasJugador();
			}
		}
		
		throw new ChicoException("Error grave: Nunca puede pedir cartas de un movimiento que no Existe");
	}
		
}
