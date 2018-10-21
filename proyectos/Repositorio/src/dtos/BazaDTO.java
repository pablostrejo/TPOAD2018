package dtos; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BazaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private List<MovimientoDTO> turnosBaza;
	private int numeroBaza;
	private JugadorDTO ganador;
	private ArrayList<JugadorDTO> ordenJuego;

	public BazaDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MovimientoDTO> getTurnosBaza() {
		return turnosBaza;
	}

	public void setTurnosBaza(List<MovimientoDTO> turnosBaza) {
		this.turnosBaza = turnosBaza;
	}

	public int getNumeroBaza() {
		return numeroBaza;
	}

	public void setNumeroBaza(int numeroBaza) {
		this.numeroBaza = numeroBaza;
	}

	public JugadorDTO getGanador() {
		return ganador;
	}

	public void setGanador(JugadorDTO ganador) {
		this.ganador = ganador;
	}

	public ArrayList<JugadorDTO> getOrdenJuego() {
		return ordenJuego;
	}

	public void setOrdenJuego(ArrayList<JugadorDTO> ordenJuego) {
		this.ordenJuego = ordenJuego;
	}

}
