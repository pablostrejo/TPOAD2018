package dtos; 

import java.io.Serializable;

public class PuntajeParejaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private ParejaDTO pareja;
	private int puntaje;

	public PuntajeParejaDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParejaDTO getPareja() {
		return pareja;
	}

	public void setPareja(ParejaDTO pareja) {
		this.pareja = pareja;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

}
