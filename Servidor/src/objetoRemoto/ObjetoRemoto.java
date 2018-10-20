package objetoRemoto;

import interfaz.TDATruco;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import controlador.ServicioCentral;
import dtos.CartaJugadorDTO;
import dtos.ChicoDTO;
import dtos.GrupoDTO;
import dtos.JugadorDTO;
import dtos.ManoDTO;
import dtos.MiembroGrupoDTO;
import dtos.MovimientoDTO;
import dtos.ParejaDTO;
import dtos.PartidoDTO;
import dtos.PuntajeParejaDTO;
import enums.TipoEnvite;
import enums.TipoPartido;
import exceptions.BazaException;
import exceptions.ChicoException;
import exceptions.ControladorException;
import exceptions.JugadorException;
import exceptions.ManoException;
import exceptions.PartidoException;

public class ObjetoRemoto extends UnicastRemoteObject implements TDATruco {

	private static final long serialVersionUID = 1L;
	private ServicioCentral controlador;

	public ObjetoRemoto() throws RemoteException {
		super();

		controlador = ServicioCentral.getInstance();
	}

	public JugadorDTO login(JugadorDTO jg) throws JugadorException {
		//Intengo hacer login//
		try {
			return controlador.iniciarSesion(jg);
		} catch (JugadorException e) {
			throw new JugadorException(e.getMessage());
		}
	}
	
	@Override
	public void registrarJugador(JugadorDTO jg) throws RemoteException {
		controlador.registrarJugador(jg);
	}

	
	public List<CartaJugadorDTO> obtenerCartasJugador (PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		
		try {
			return controlador.obtenerCartasJugador(partido, jugador);
		} catch (ControladorException | PartidoException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
			
		}
	}
	
	public List<PuntajeParejaDTO> obtenerPuntajeChico (PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		
		try {
			return controlador.obtenerPuntajeChico(partido, jugador);
		} catch (ControladorException | PartidoException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
			
		}
	}

	public List<MovimientoDTO> obtenerMovimientosUltimaBaza(PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		
		try {
			return controlador.obtenerMovimientosUltimaBaza(partido, jugador);
		}
		catch (ControladorException | PartidoException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}

	public List<TipoEnvite> obtenerEnvitesDisponibles(PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
	
		try {
			return controlador.obtenerEnvitesDisponibles(partido, jugador);
		}
		catch (PartidoException | ControladorException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}
	
	public PartidoDTO jugarLibreIndividual(JugadorDTO jugador) {
		
		return controlador.jugarLibreIndividual(jugador);
	}
	
	public List<PartidoDTO> tengoPartido(JugadorDTO jugador) {
		
		return controlador.tengoPartido(jugador);
	}

	public JugadorDTO obtenerJugadorActual(PartidoDTO part, JugadorDTO jugador) throws RemoteException {
		
		try {
			return controlador.obtenerJugadorActual(part, jugador);
		} catch (ControladorException | PartidoException e) {
			
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
		
	}

	public void nuevoMovimientoPartido(PartidoDTO partido, JugadorDTO turnoJugador, MovimientoDTO movimiento) throws RemoteException {
	
		try {
			controlador.nuevoMovimientoPartido(partido, turnoJugador, movimiento);
		} catch (ControladorException | PartidoException | BazaException | JugadorException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}


	public void crearGrupo(GrupoDTO dto, JugadorDTO administrador) throws RemoteException {		
		try {
			controlador.crearGrupo(dto, administrador);
		} catch (ControladorException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}		
	}

	public PartidoDTO obtenerUltimoPartidoPendienteModalidad(TipoPartido tipoPartido, JugadorDTO jugadorDTO) throws RemoteException {

		return controlador.obtenerUltimoPartidoPendienteModalidad(tipoPartido, jugadorDTO);
	}

	
	public List<ParejaDTO> obtenerParejasPartido(PartidoDTO partido) throws RemoteException {
		
		try {
			return controlador.obtenerParejasPartido(partido);
		} catch (ControladorException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}

	public boolean partidoEstaTerminado(PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		
		try {
			return controlador.partidoEstaTerminado(partido, jugador);
		} catch (ControladorException | PartidoException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}
	
	@Override
	public void cerrarSesion(JugadorDTO jg) throws RemoteException, JugadorException {
		controlador.cerrarSesion(jg);
	}


	public List<JugadorDTO> obtenerGanadoresBazas(PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
	
		try {
			return controlador.obtenerGanadoresBazas(partido, jugador);
		} catch (ControladorException | PartidoException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}


	public ManoDTO obtenerUltimaManoActiva(PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		
		try {
			return controlador.obtenerUltimaManoActiva(partido, jugador);
		} catch (ControladorException | PartidoException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}

	public List<MovimientoDTO> obtenerProximoMovimientoPartido(JugadorDTO jugador, PartidoDTO partido,MovimientoDTO ultimoMovimiento) 
	throws RemoteException {
		
		try {
			return controlador.obtenerProximoMovimientoPartido(jugador, partido, ultimoMovimiento);
		} catch (ControladorException | PartidoException | ChicoException | ManoException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}

	
	public List<PartidoDTO> levantarPartidosTerminadosJugador(JugadorDTO jugador) throws RemoteException {
	
		try {
			return controlador.levantarPartidosTerminadosJugador(jugador);
		} catch (ControladorException e) {
			
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}

	public JugadorDTO obtenerJugadorCompleto(JugadorDTO jugador) throws RemoteException {

		return controlador.obtenerJugadorCompleto(jugador);
	}

	public List<ChicoDTO> obtenerResultadoFinalPartido(JugadorDTO jugador, PartidoDTO partido) throws RemoteException {
		
		
		try {
			return controlador.obtenerResultadoFinalPartido(jugador, partido);
		} catch (ControladorException | PartidoException e) {
	
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}

	public List<CartaJugadorDTO> obtenerCartasJugadorMano(JugadorDTO jugador, PartidoDTO partido, MovimientoDTO movimiento) throws RemoteException {
		
		try {
			return controlador.obtenerCartasJugadorMano(jugador, partido, movimiento);
		} catch (ControladorException | PartidoException | ChicoException e) {
			
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}

	public ParejaDTO obtenerParejaGanadoraPartido(JugadorDTO jugador, PartidoDTO partido) throws RemoteException {
		
		try {
			return controlador.obtenerParejaGanadoraPartido(jugador, partido);
			
		} catch (PartidoException | ControladorException e) {
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}
	
	public PartidoDTO jugarLibreParejas(ParejaDTO pareja) throws RemoteException, ControladorException{
		return controlador.jugarLibreParejas(pareja);
	}

	
	public List<MiembroGrupoDTO> obtenerMiembrosGrupo(GrupoDTO grupo) throws RemoteException {

		try {
			return controlador.obtenerMiembrosGrupo(grupo);
			
		} catch (ControladorException e) {
			
			throw new RemoteException(e.getMessage());
			
			/* DISCUTIR SI ESTA ES LA MEJOR MANERA DE RE-LANZAR LA EXCEPTION */
		}
	}

	public void agregarJugadorGrupo(List<JugadorDTO> agregar, GrupoDTO dto, JugadorDTO administrador) throws RemoteException {
	
		controlador.agregarJugadorGrupo(agregar, dto, administrador);
		
	}

	
	public void eliminarMiembroGrupo(JugadorDTO jugador, GrupoDTO grupo, JugadorDTO administrador) throws RemoteException {
		
		controlador.eliminarMiembroGrupo(jugador, grupo, administrador);
	}

	@Override
	public void armarParejaGrupo(ParejaDTO pareja, GrupoDTO grupo, JugadorDTO admin) throws RemoteException {
		// TODO Auto-generated method stub
		controlador.armarParejaGrupo(pareja, grupo, admin);
	}

	@Override
	public PartidoDTO armarPartidoGrupo(List<ParejaDTO> parejas, GrupoDTO grupo, JugadorDTO admin) throws RemoteException {
		// TODO Auto-generated method stub
		return controlador.crearPartidaGrupo(parejas, grupo, admin);
	}

	@Override
	public List<PartidoDTO> obtenerPartidosGrupo(GrupoDTO grupoSeleccionado, JugadorDTO jugador) throws RemoteException {
		// TODO Auto-generated method stub
		return controlador.obtenerPartidosGrupo(grupoSeleccionado, jugador);
	}



}