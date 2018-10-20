package entities;

import javax.persistence.*;

import dtos.JugadorDTO;
import dtos.ParejaDTO;

import enums.TipoCategoria;


/**
 * Si no tiene asignado un numero pareja no esta en un partido
**/

@Entity
@Table (name = "Parejas")
public class ParejaEntity {
	@Id
	@Column (name = "id_pareja", nullable = false)
	@GeneratedValue
	private int id;

//	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name = "id_jugador1")
	private JugadorEntity jugador1;

//	@ManyToOne (cascade = CascadeType.ALL) /* fetch = FetchType.EAGER)*/
	@ManyToOne
	@JoinColumn (name = "id_jugador2")
	private JugadorEntity jugador2;

	@Column (name = "nro_Pareja")
	private int numeroPareja;
	
	public TipoCategoria obtenerCategoriaSuperior() {
		if(jugador1.getCategoria()==jugador2.getCategoria())
			return jugador1.getCategoria();
		else{
			if(jugador1.getCategoria().ordinal()<jugador2.getCategoria().ordinal())
				return jugador2.getCategoria();
			else
				return jugador1.getCategoria(); 
		}
	}
	
	public ParejaEntity() {
	}

	public ParejaEntity(int numeroPareja, JugadorEntity jugador1, JugadorEntity jugador2) {
		this.numeroPareja = numeroPareja;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}
	
	public ParejaDTO toDTO() {
		ParejaDTO dto = new ParejaDTO();
		dto.setId(this.id);
		dto.setJugador1(this.jugador1.getApodo());
		dto.setJugador2(this.jugador2.getApodo());
		dto.setNumeroPareja(this.numeroPareja);
		return dto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JugadorEntity getJugador1() {
		return jugador1;
	}

	public void setJugador1(JugadorEntity jugador1) {
		this.jugador1 = jugador1;
	}

	public JugadorEntity getJugador2() {
		return jugador2;
	}

	public void setJugador2(JugadorEntity jugador2) {
		this.jugador2 = jugador2;
	}

	public int getNumeroPareja() {
		return numeroPareja;
	}

	public void setNumeroPareja(int numeroPareja) {
		this.numeroPareja = numeroPareja;
	}

	public boolean tenesJugador(JugadorEntity jugador) {
		if(jugador1.getApodo().equals(jugador.getApodo()))
			return true;
		else
			if(jugador2.getApodo().equals(jugador.getApodo()))
				return true;
		return false;
	}
	
	public boolean esPareja (ParejaDTO dto){
		if (jugador1.getApodo().equals(dto.getJugador1()) && (jugador2.getApodo().equals(dto.getJugador2())))
			return true;
		else
			if (jugador1.getApodo().equals(dto.getJugador2()) && (jugador2.getApodo().equals(dto.getJugador1())))
				return true;

		return false;
	}
	
	public boolean esPareja (ParejaEntity pareja) {
		if (this.tenesJugador(pareja.getJugador1()) && this.tenesJugador(pareja.getJugador2()))
			return true;
		else
			return false;
	}

	public boolean tenesJugador(JugadorDTO jugador) {
		if(jugador1.getApodo().equals(jugador.getApodo()))
			return true;
		else
			if(jugador2.getApodo().equals(jugador.getApodo()))
				return true;
		return false;
	}

}
