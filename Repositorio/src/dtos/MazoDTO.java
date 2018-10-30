package dtos;

import java.io.Serializable;
import java.util.List;

public class MazoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CartaDTO> cartas;

	public MazoDTO() {

	}

	public List<CartaDTO> getCartas() {
		return cartas;
	}

	public void setCartas(List<CartaDTO> cartas) {
		this.cartas = cartas;
	}

}
