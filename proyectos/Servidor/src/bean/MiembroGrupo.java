package bean;

import javax.persistence.*;

import dtos.JugadorDTO;
import dtos.MiembroGrupoDTO;
import dtos.RankingDTO;

import enums.TipoMiembro;

/**
 * Intermediario entre el grupo y el miembro
**/

@Entity
@Table (name = "Miembros_Grupo")
public class MiembroGrupo {

	@Id
	@Column (name = "id_miembro", nullable = false)
	@GeneratedValue
	private int id;
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "id_jugador")
	private Jugador jugador;
	@OneToOne (cascade = CascadeType.ALL) /* fetch = FetchType.EAGER)*/
	@JoinColumn (name = "id_ranking")
	private Ranking ranking;
	@Column (columnDefinition = "bit")
	private boolean activo;
	@Column (columnDefinition = "int")
	private TipoMiembro tipoMiembro;

	public MiembroGrupo() {
	}

	public MiembroGrupo(Jugador jugador, TipoMiembro tipo) {
		this.jugador = jugador;
		this.ranking = new Ranking();
		this.activo = true;
		this.tipoMiembro = tipo;
	}
	
	public MiembroGrupoDTO toDTO(){
		MiembroGrupoDTO dto = new MiembroGrupoDTO();
		
		dto.setActivo(this.activo);
		dto.setId(this.id);
		dto.setJugador(this.jugador.getApodo());
		dto.setRanking(new RankingDTO()); // this.ranking.toDTO());
		dto.setTipoMiembro(tipoMiembro);
		
		return dto;
	}
	
	
	public TipoMiembro getTipoMiembro() {
		return tipoMiembro;
	}


	public void setTipoMiembro(TipoMiembro tipoMiembro) {
		this.tipoMiembro = tipoMiembro;
	}


	public boolean tenesMiembro(JugadorDTO jugador) {
		
		return this.jugador.getApodo().equals(jugador.getApodo());
	}

	public RankingDTO obtenerRanking() {
		return this.ranking.toDTO();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean tenesMiembro (Jugador jug){
		return jug.getId() == jugador.getId();
	}
	
	public void actualizarRanking (Partido part, int puntos){
		ranking.actualizar(part, puntos);
	}
}
