package dtos;

import java.io.Serializable;
import java.util.List;

import enums.TipoMiembro;

public class GrupoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String nombre;
	private List<MiembroGrupoDTO> miembros;
	private List<ParejaDTO> parejasActivas;
	private List<PartidoDTO> partidos;

	public GrupoDTO() {
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

	public List<MiembroGrupoDTO> getMiembros() {
		return miembros;
	}

	public void setMiembros(List<MiembroGrupoDTO> miembros) {
		this.miembros = miembros;
	}

	public List<ParejaDTO> getParejasActivas() {
		return parejasActivas;
	}

	public void setParejasActivas(List<ParejaDTO> parejasActivas) {
		this.parejasActivas = parejasActivas;
	}

	public List<PartidoDTO> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<PartidoDTO> partidos) {
		this.partidos = partidos;
	}
	
	public boolean tenesAdministrador(JugadorDTO admin){
		for (MiembroGrupoDTO miembro : miembros){
			if (miembro.getJugador().equalsIgnoreCase(admin.getApodo()) && miembro.getTipoMiembro().equals(TipoMiembro.Administrador) && miembro.isActivo()){
				return true;
			}
		}
		return false;
	}

}
