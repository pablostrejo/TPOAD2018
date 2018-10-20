package dtos;

import java.io.Serializable;
import java.util.List;

public class ChicoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private List<ManoDTO> manos;
	private List<PuntajeParejaDTO> puntajes;
	private int puntajeMaximo;
	private int numeroChico;
	private boolean terminado;

	public ChicoDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ManoDTO> getManos() {
		return manos;
	}

	public void setManos(List<ManoDTO> manos) {
		this.manos = manos;
	}

	public int getNumeroChico() {
		return numeroChico;
	}

	public void setNumeroChico(int numeroChico) {
		this.numeroChico = numeroChico;
	}

	public List<PuntajeParejaDTO> getPuntajes() {
		return puntajes;
	}

	public void setPuntajes(List<PuntajeParejaDTO> puntajes) {
		this.puntajes = puntajes;
	}

	public int getPuntajeMaximo() {
		return puntajeMaximo;
	}

	public void setPuntajeMaximo(int puntajeMaximo) {
		this.puntajeMaximo = puntajeMaximo;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

}
