package bean;

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
public class Grupo {
	
	@Id
	@Column (name = "id_grupo")
	@GeneratedValue
	private int id;
	@Column
	private String nombre;

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "id_grupo")
	private List<MiembroGrupo> miembros;
	
	
	/* Las Parejas Activas no se persisten */
	@Transient
	private List<Pareja> parejasActivas;

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn (name = "id_grupo")
	private List<Partido> partidos;
	
	
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
	
	public Grupo() {
		parejasActivas = new ArrayList<Pareja>();
	}

	public Grupo(String nombre, Jugador administrador) {
		this.nombre = nombre;
		this.miembros = new ArrayList<MiembroGrupo>();
		this.parejasActivas = new ArrayList<Pareja>();
		this.partidos = new ArrayList<Partido>();
		miembros.add(new MiembroGrupo(administrador, TipoMiembro.Administrador));
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

	public List<MiembroGrupo> getMiembros() {
		return miembros;
	}

	public void setMiembros(ArrayList<MiembroGrupo> miembros) {
		this.miembros = miembros;
	}

	public List<Pareja> getParejasActivas() {
		return parejasActivas;
	}

	public void setParejasActivas(ArrayList<Pareja> parejasActivas) {
		this.parejasActivas = parejasActivas;
	}

	public List<Partido> getPartidos() {
//		partidos = GrupoDAO.getInstancia().buscarPartidos(this);
		return partidos;
	}

	public void setPartidos(ArrayList<Partido> partidos) {
		this.partidos = partidos;
	}

	public void armarPareja(ArrayList<Jugador> integrantes) {

		Pareja pareja = new Pareja(0,integrantes.get(0), integrantes.get(1));
		parejasActivas.add(pareja);
	}
	
	public void crearPartida(ArrayList<Pareja> parejas, Timestamp fechaInicio) {
	
		Partido partido = new Partido(parejas, fechaInicio , TipoPartido.Grupo);
		partidos.add(partido);	
	}
	
	// desarrollar esta
	public void actualizarRankings() {
	
	}
	
	public void eliminarMiembroGrupo(Jugador jugador) {
	
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
	
	public boolean esAdministrador(Jugador jugador) {
		
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
	
	public void agregarMiembro(Jugador jugador) {
	
		MiembroGrupo miembro = new MiembroGrupo(jugador, TipoMiembro.Estandar);
		miembros.add(miembro);
		GrupoDAO.getInstancia().guardarGrupo(this);
	}
	
	public MiembroGrupo obtenerAdministrador (){
		
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
	
	
	public Pareja obtenerPareja (ParejaDTO dto){
		
		
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
	
	public void agregarPartido (Partido partido){
		
		partidos.add(partido);
		
		for(int i=0; i<partido.getParejas().size(); i++){
			
			eliminarPareja(partido.getParejas().get(i));
		}
		
		
	}
	
	public void eliminarPareja (Pareja pareja){
		
		
		parejasActivas.remove(pareja);
		
	}


	public boolean tenesPartido (Partido partido){
		
		for(Partido part: partidos){
			
			if(part.getId() == partido.getId())
				return true;			
		}
		
		return false;
		
	}
	
	
	public void actualizarRanking (Jugador jugador, int puntos, Partido partido){
		
		
		for(MiembroGrupo miembro: miembros){
			
			if(miembro.tenesMiembro(jugador) == true)
				
				miembro.actualizarRanking(partido, puntos);
		}
		
	}
	
	
}
