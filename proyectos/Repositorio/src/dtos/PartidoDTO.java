package dtos; 

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import enums.EstadoPartido;
import enums.TipoPartido;

public class PartidoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private List<ParejaDTO> parejas;
	private ParejaDTO parejaGanadora;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private TipoPartido tipoPartido;
	private EstadoPartido estadoPartido;
	private List<ChicoDTO> chicos;

	public PartidoDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ParejaDTO> getParejas() {
		return parejas;
	}

	public void setParejas(List<ParejaDTO> parejas) {
		this.parejas = parejas;
	}

	public ParejaDTO getParejaGanadora() {
		return parejaGanadora;
	}

	public void setParejaGanadora(ParejaDTO parejaGanadora) {
		this.parejaGanadora = parejaGanadora;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public TipoPartido getTipoPartido() {
		return tipoPartido;
	}

	public void setTipoPartido(TipoPartido tipoPartido) {
		this.tipoPartido = tipoPartido;
	}

	public EstadoPartido getEstadoPartido() {
		return estadoPartido;
	}

	public void setEstadoPartido(EstadoPartido estadoPartido) {
		this.estadoPartido = estadoPartido;
	}

	public List<ChicoDTO> getChicos() {
		return chicos;
	}

	public void setChicos(List<ChicoDTO> chicos) {
		this.chicos = chicos;
	}

}
