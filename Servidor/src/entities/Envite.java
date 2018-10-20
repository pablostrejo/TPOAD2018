package entities;

import javax.persistence.*;

import dtos.EnviteDTO;
import enums.TipoEnvite;


@Entity
@DiscriminatorValue("env")
public class Envite extends Movimiento {

	@Column (name = "tipo_envite", columnDefinition = "tinyint")
	private TipoEnvite tipoEnvite;

//	@ManyToOne(cascade = CascadeType.ALL)
	@ManyToOne
	@JoinColumn(name = "id_jugador")
	private Jugador jugador;

	public Envite() {
	}

	public TipoEnvite getTipoEnvite() {
		return tipoEnvite;
	}

	public void setTipoEnvite(TipoEnvite tipoEnvite) {
		this.tipoEnvite = tipoEnvite;
	}

	public Envite(TipoEnvite tipoEnvite) {
		this.tipoEnvite = tipoEnvite;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public boolean sosAlgunEnvido() {
		return	(this.tipoEnvite.equals(TipoEnvite.Envido))			||
				(this.tipoEnvite.equals(TipoEnvite.EnvidoEnvido))	||
				(this.tipoEnvite.equals(TipoEnvite.RealEnvido))		||
				(this.tipoEnvite.equals(TipoEnvite.FaltaEnvido));
	}

	public boolean sosAlgunTruco() {
		return	(this.tipoEnvite.equals(TipoEnvite.Truco))		||
				(this.tipoEnvite.equals(TipoEnvite.ReTruco))	||
				(this.tipoEnvite.equals(TipoEnvite.ValeCuatro));
	}

	public static byte obtenerPuntajeEnvido(String cadenaDeEnvidos) {

		// agrupamos primero los Envidos que dicen 'Quiero'
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.Quiero.name()))
			return 2;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.RealEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 3;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 100; // es un aviso para que lo manejen de afuera
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.EnvidoEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 4;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 5;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 100; // es un aviso para que lo manejen de afuera
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.RealEnvido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 100; // es un aviso para que lo manejen de afuera
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.RealEnvido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 6;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.EnvidoEnvido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 100; // es un aviso para que lo manejen de afuera
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.EnvidoEnvido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 7;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 100; // es un aviso para que lo manejen de afuera
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.EnvidoEnvido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.Quiero.name()))
			return 100; // es un aviso para que lo manejen de afuera

		else

		// agrupamos ahora los Envidos que dicen 'NoQuiero'
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.NoQuiero.name()))
			return 1;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.RealEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 1;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 1;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.EnvidoEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 2;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 2;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 2;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.RealEnvido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 3;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.RealEnvido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 3;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.EnvidoEnvido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 4;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.EnvidoEnvido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 4;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 5;
		else
		if (cadenaDeEnvidos.equals(	TipoEnvite.Envido.name() +
									TipoEnvite.EnvidoEnvido.name() +
									TipoEnvite.RealEnvido.name() +
									TipoEnvite.FaltaEnvido.name() +
									TipoEnvite.NoQuiero.name()))
			return 7;

		return 0;
	}

	@Override
	public EnviteDTO toDTO() {
		EnviteDTO envite = new EnviteDTO();

		envite.setId(this.id);
		envite.setNumeroTurno(numeroTurno);
		envite.setFechaHora(this.fechaHora);

		envite.setJugador(this.jugador.toDTO());
		envite.setTipoEnvite(this.tipoEnvite);

		return envite;
	}

}
