package entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import daos.GrupoDAO;
import dtos.GrupoDTO;
import dtos.JugadorDTO;
import dtos.MiembroGrupoDTO;
import dtos.ParejaDTO;
import dtos.PartidoDTO;
import dtos.RankingDTO;
import enums.TipoMiembro;
import enums.TipoPartido;



@Entity
@Table (name = "Grupos")
public class GrupoEntity {
	
	@Id
	@Column (name = "id_grupo")
	@GeneratedValue
	private int id;
	@Column
	private String nombre;

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "id_grupo")
	private List<MiembroGrupoEntity> miembros;
	
	
	/* Las Parejas Activas no se persisten */
	@Transient
	private List<ParejaEntity> parejasActivas;

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn (name = "id_grupo")
	private List<PartidoEntity> partidos;
	
	
	public GrupoDTO toDto(){
		GrupoDTO dto = new GrupoDTO();
		dto.setId(this.id);
		dto.setNombre(this.nombre);
		
		ArrayList<MiembroGrupoDTO> miembrosDTO = new ArrayList<MiembroGrupoDTO>();
		for(int i=0; i<miembros.size(); i++){
			
			miembrosDTO.add(miembros.get(i).toDTO());
		}
		dto.setMiembros(miembrosDTO);
		
		ArrayList<PartidoDTO> partidosDTO = new ArrayList<PartidoDTO>();
		for(int i=0; i < getPartidos().size();i++){
			partidosDTO.add(partidos.get(i).toDTO());
		}
		dto.setPartidos(partidosDTO);
		
		ArrayList<ParejaDTO> parejasActivasDTO = new ArrayList<ParejaDTO>();
		for(int i=0;i<parejasActivas.size();i++){
			parejasActivasDTO.add(parejasActivas.get(i).toDTO());
		}
		
		dto.setParejasActivas(parejasActivasDTO);
		
		return dto;
	}
	
	public GrupoEntity() {
		parejasActivas = new ArrayList<ParejaEntity>();
	}

	public GrupoEntity(String nombre, JugadorEntity administrador) {
		this.nombre = nombre;
		this.miembros = new ArrayList<MiembroGrupoEntity>();
		this.parejasActivas = new ArrayList<ParejaEntity>();
		this.partidos = new ArrayList<PartidoEntity>();
		miembros.add(new MiembroGrupoEntity(administrador, TipoMiembro.Administrador));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<MiembroGrupoEntity> getMiembros() {
		return miembros;
	}

	public void setMiembros(ArrayList<MiembroGrupoEntity> miembros) {
		this.miembros = miembros;
	}

	public List<ParejaEntity> getParejasActivas() {
		return parejasActivas;
	}

	public void setParejasActivas(ArrayList<ParejaEntity> parejasActivas) {
		this.parejasActivas = parejasActivas;
	}

	public List<PartidoEntity> getPartidos() {
//		partidos = GrupoDAO.getInstancia().buscarPartidos(this);
		return partidos;
	}

	public void setPartidos(ArrayList<PartidoEntity> partidos) {
		this.partidos = partidos;
	}

	public void armarPareja(ArrayList<JugadorEntity> integrantes) {

		ParejaEntity pareja = new ParejaEntity(0,integrantes.get(0), integrantes.get(1));
		parejasActivas.add(pareja);
	}
	
	public void crearPartida(ArrayList<ParejaEntity> parejas, Timestamp fechaInicio) {
	
		PartidoEntity partido = new PartidoEntity(parejas, fechaInicio , TipoPartido.Grupo);
		partidos.add(partido);	
	}
	
	// desarrollar esta
	public void actualizarRankings() {
	
	}
	
	public void eliminarMiembroGrupo(JugadorEntity jugador) {
	
		for(int i=0; i<miembros.size(); i++){
			if(miembros.get(i).getJugador().getApodo().equals(jugador.getApodo()))
				miembros.get(i).setActivo(false);
		}
	}
	

	public ArrayList<RankingDTO> obtenerRanking() {
		
		ArrayList<RankingDTO> rankings = new ArrayList<RankingDTO>();
		for(int i=0; i<miembros.size();i++)
		{
			rankings.add(miembros.get(i).getRanking().toDTO());
		}
		return rankings;
	}
	
	public boolean esAdministrador(JugadorEntity jugador) {
		
		for(int i=0; i<miembros.size(); i++)
		{
			if(miembros.get(i).getJugador().getApodo().equals(jugador.getApodo()))
			{
				if(miembros.get(i).getTipoMiembro()==TipoMiembro.Administrador)
					return true;
				return false;
			}
		}
		return false;
		
		
	}
	
	public boolean sosGrupo(String nombre) {
		
		if(this.nombre.equals(nombre))
			return true;
		return false;
	}
	
	public void agregarMiembro(JugadorEntity jugador) {
	
		MiembroGrupoEntity miembro = new MiembroGrupoEntity(jugador, TipoMiembro.Estandar);
		miembros.add(miembro);
		GrupoDAO.getInstancia().guardarGrupo(this);
	}
	
	public MiembroGrupoEntity obtenerAdministrador (){
		
		for(int i=0; i<miembros.size(); i++)
		{
			if(miembros.get(i).getTipoMiembro()==TipoMiembro.Administrador)
				return miembros.get(i);
		}
		return null;
	}
	
	public boolean tengoMiembro (JugadorDTO dto){
		
		for(int i=0; i<miembros.size(); i++){
			
			if(miembros.get(i).getJugador().getId() == dto.getId())
				return true;
		}
		
		return false;
	}
	
	
	public ParejaEntity obtenerPareja (ParejaDTO dto){
		
		
		for(int i=0; i<parejasActivas.size(); i++){
			
			if(parejasActivas.get(i).esPareja(dto))
				return parejasActivas.get(i);
			
		}
		
		return null;
		
		
	}
	
	public boolean tenesPareja (ParejaDTO dto){
		
		if(obtenerPareja(dto)==null)
			return false;
		return true;
	}
	
	public void agregarPartido (PartidoEntity partido){
		
		partidos.add(partido);
		
		for(int i=0; i<partido.getParejas().size(); i++){
			
			eliminarPareja(partido.getParejas().get(i));
		}
		
		
	}
	
	public void eliminarPareja (ParejaEntity pareja){
		
		
		parejasActivas.remove(pareja);
		
	}


	public boolean tenesPartido (PartidoEntity partido){
		
		for(PartidoEntity part: partidos){
			
			if(part.getId() == partido.getId())
				return true;			
		}
		
		return false;
		
	}
	
	
	public void actualizarRanking (JugadorEntity jugador, int puntos, PartidoEntity partido){
		
		
		for(MiembroGrupoEntity miembro: miembros){
			
			if(miembro.tenesMiembro(jugador) == true)
				
				miembro.actualizarRanking(partido, puntos);
		}
		
	}
	
	
}
