package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import daos.CartaDAO;
import dtos.CartaDTO;
import dtos.MazoDTO;


/* No se persiste el mazo */
public class Mazo {

	private List<Carta> cartas;

	public Mazo() {
		this.cartas = this.iniciarMazo();
	}

	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	public Carta obtenerCarta() {
		Random rand = new Random();

		int posicion = rand.nextInt(cartas.size());

		Carta carta = cartas.get(posicion);

		cartas.remove(posicion);

		return carta;
	}
	
	public List<Carta> iniciarMazo() {
		List<Carta> mazo = new ArrayList<Carta>();

		mazo = CartaDAO.getInstancia().obtenerCartas();

		return mazo;

/*
		Carta carta;

		carta = new Carta(Palo.Espada, 1, 0);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 1, 1);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 7, 2);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 7, 3);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 3, 4);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 3, 4);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 3, 4);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 3, 4);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 2, 5);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 2, 5);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 2, 5);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 2, 5);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 1, 6);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 1, 6);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 12, 7);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 12, 7);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 12, 7);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 12, 7);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 11, 8);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 11, 8);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 11, 8);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 11, 8);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 10, 9);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 10, 9);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 10, 9);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 10, 9);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 7, 10);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 7, 10);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 6, 11);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 6, 11);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 6, 11);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 6, 11);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 5, 12);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 5, 12);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 5, 12);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 5, 12);
		mazo.add(carta);
		carta = new Carta(Palo.Espada, 4, 13);
		mazo.add(carta);
		carta = new Carta(Palo.Oro, 4, 13);
		mazo.add(carta);
		carta = new Carta(Palo.Basto, 4, 13);
		mazo.add(carta);
		carta = new Carta(Palo.Copa, 4, 13);
		mazo.add(carta);

*/		

	}
	
	public MazoDTO toDto() {
		MazoDTO dto = new MazoDTO();
		List<CartaDTO> cartasDto = new ArrayList<CartaDTO>();

		for(int i=0; i<cartas.size();i++){
			cartasDto.add(cartas.get(i).toDTO());
		}
		
		return dto;
	}
}
