package bean;

import java.sql.Timestamp;

import javax.persistence.*;

import dtos.MovimientoDTO;



@Entity
@Table(name ="Movimientos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo",discriminatorType=DiscriminatorType.STRING)
public class Movimiento {

	@Id
	@Column (name = "id_movimiento", nullable = false)
	@GeneratedValue
	protected int id;

	@Column (name = "nro_turno")
	protected int numeroTurno;

	@Column (name = "fecha_hora")
	protected Timestamp fechaHora;

	public MovimientoDTO toDTO() {
		MovimientoDTO dto = new MovimientoDTO();
		
		dto.setFechaHora(this.fechaHora);
		dto.setId(this.id);
		dto.setNumeroTurno(this.numeroTurno);

		return dto;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumeroTurno() {
		return numeroTurno;
	}
	public void setNumeroTurno(int numeroTurno) {
		this.numeroTurno = numeroTurno;
	}
	public Timestamp getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

}
