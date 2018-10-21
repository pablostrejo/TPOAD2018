package dtos; 

import java.io.Serializable;
import java.util.List;

public class RankingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private List<PartidoDTO> partidos;
	private int puntos;
	private int cantidadGanadas;

	public RankingDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<PartidoDTO> getPartidos() {
		return partidos;
	}

	public void setPartidos(List<PartidoDTO> partidos) {
		this.partidos = partidos;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getCantidadGanadas() {
		return cantidadGanadas;
	}

	public void setCantidadGanadas(int cantidadGanadas) {
		this.cantidadGanadas = cantidadGanadas;
	}

}
