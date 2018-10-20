package businessDelegate;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

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
import exceptions.ControladorException;
import interfaz.TDATruco;

public class BusinessDelegate {

	public TDATruco objetoRemoto;

	public BusinessDelegate() throws RemoteException {
		
		
		/************************************************************************************************************/
		/************************************************************************************************************/
		/************************************************************************************************************/

		/**********************     VERIFICAR EL TEMA DE 'throws RemoteException'            ************************/
		/**********************		EL BusinessDelegate, DEBE LANZAR 'RemoteException'       ************************/
		/************************************************************************************************************/

		/************************************************************************************************************/
		/************************************************************************************************************/
		/************************************************************************************************************/

		super();

		try {
			objetoRemoto = (TDATruco) Naming.lookup("//localhost/ServicioCentral");
//			objetoRemoto = (TDATruco) Naming.lookup("//192.168.0.12/ServicioCentral");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new RemoteException("No pudo encontrarse el Servicio Central. " + e.getMessage());
		}
	}

	public JugadorDTO login(JugadorDTO jg) throws RemoteException {
		return objetoRemoto.login(jg);
	}
	
	public List<CartaJugadorDTO> obtenerCartasJugador (PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		try {
			return objetoRemoto.obtenerCartasJugador(partido, jugador);
		} catch (RemoteException e) {

			throw new RemoteException("Se produjo un error al intentar obtener las cartas: " + e.getMessage());
		}
	}
	
	public List<MovimientoDTO> obtenerMovimientosUltimaBaza (PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		try {
			return objetoRemoto.obtenerMovimientosUltimaBaza(partido, jugador);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al intentar obtener las Ultimos Movimientos: " + e.getMessage());
		}
	}	
	
	public List<TipoEnvite> obtenerEnvitesDisponibles(PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		try {
			return objetoRemoto.obtenerEnvitesDisponibles(partido, jugador);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al intentar obtener los Envites Disponibles: " + e.getMessage());
		}
	}	
	
	public PartidoDTO jugarLibreIndividual(JugadorDTO jugador) throws RemoteException {
		return objetoRemoto.jugarLibreIndividual(jugador);
	}

	public List<PartidoDTO> tengoPartido(JugadorDTO jugador) throws RemoteException {
		return objetoRemoto.tengoPartido(jugador);
	}

	public JugadorDTO obtenerJugadorActual(PartidoDTO part, JugadorDTO jugador) throws RemoteException {
		try {
			return objetoRemoto.obtenerJugadorActual(part,jugador);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al obtener el Jugador Actual: " + e.getMessage());
		}
	}

	public void nuevoMovimientoPartido(PartidoDTO partido, JugadorDTO turnoJugador, MovimientoDTO movimiento) throws RemoteException {
		try {
			objetoRemoto.nuevoMovimientoPartido(partido, turnoJugador, movimiento);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al hacer el nuevo Movimiento: " + e.getMessage());
		}
	}
	
	public List<PuntajeParejaDTO> obtenerPuntajeChico (PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		try {
			return objetoRemoto.obtenerPuntajeChico(partido, jugador);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al obtener el Puntaje del Chico: " + e.getMessage());
		}
	}

	public void registrarJugador(JugadorDTO jg) throws RemoteException {
		try {
			objetoRemoto.registrarJugador(jg);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al querer registrar el jugador.");
		}	
	}
	
	public void crearGrupo(GrupoDTO dto, JugadorDTO administrador) throws RemoteException {
		
		try {
			objetoRemoto.crearGrupo(dto, administrador);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al querer crear el grupo. " + e.getMessage());
		}	
	}
	
	public PartidoDTO obtenerUltimoPartidoPendienteModalidad (TipoPartido tipoPartido, JugadorDTO jugadorDTO) throws RemoteException {
		
		try {
			return objetoRemoto.obtenerUltimoPartidoPendienteModalidad(tipoPartido, jugadorDTO);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al verificar partidos pendientes. " + e.getMessage());
		}
	}

	public List<ParejaDTO> obtenerParejasPartido(PartidoDTO partido) throws RemoteException {
		
		try {
			return objetoRemoto.obtenerParejasPartido(partido);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al Obtener Parejas del Partido. " + e.getMessage());

		}
	}
	
	public boolean partidoEstaTerminado(PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		
		try {
			return objetoRemoto.partidoEstaTerminado(partido, jugador);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al conocer si el partido esta Terminado. " + e.getMessage());
		}
	}
	

	public List<JugadorDTO> obtenerGanadoresBazas(PartidoDTO partido, JugadorDTO jugador) throws RemoteException {
		
		try {
			return objetoRemoto.obtenerGanadoresBazas(partido, jugador);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al Obtener los Ganadores de las Bazas. " + e.getMessage());

		}
	}
	
	public ManoDTO obtenerUltimaManoActiva(PartidoDTO partido, JugadorDTO jugador) throws RemoteException{
		
		try {
			return objetoRemoto.obtenerUltimaManoActiva(partido, jugador);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al Obtener la Ultima Mano Activa del Partido. " + e.getMessage());
		}
	}

	public void cerrarSesion(JugadorDTO jg) throws RemoteException {
		objetoRemoto.cerrarSesion(jg);
	}
	
	
	public List<MovimientoDTO> obtenerProximoMovimientoPartido(JugadorDTO jugador, PartidoDTO partido,MovimientoDTO ultimoMovimiento) 
	throws RemoteException{
		
		try {
			return objetoRemoto.obtenerProximoMovimientoPartido(jugador, partido, ultimoMovimiento);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al Obtener el proximo Movimiento del Partido " + e.getMessage());

		}
	}
	
	
	public List<PartidoDTO> levantarPartidosTerminadosJugador(JugadorDTO jugador) throws RemoteException {
		
		try {
			return objetoRemoto.levantarPartidosTerminadosJugador(jugador);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al Obtener los Partidos Terminados del Jugador " + e.getMessage());
		}
	}
	
	public JugadorDTO obtenerJugadorCompleto(JugadorDTO jugador) throws RemoteException {
		
		try {
			return objetoRemoto.obtenerJugadorCompleto(jugador);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al Obtener los un Jugador Completo " + e.getMessage());

		}
	}
	
	public List<ChicoDTO> obtenerResultadoFinalPartido(JugadorDTO jugador, PartidoDTO partido) throws RemoteException{
		
		try {
			return objetoRemoto.obtenerResultadoFinalPartido(jugador, partido);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al Obtener el Resultado de un Partido Terminado" + e.getMessage());
		}
	}
	
	
	public List<CartaJugadorDTO> obtenerCartasJugadorMano(JugadorDTO jugador, PartidoDTO partido, MovimientoDTO movimiento) throws RemoteException{
		
		try {
			
				return objetoRemoto.obtenerCartasJugadorMano(jugador, partido, movimiento);
			
		} catch (RemoteException e) {

			throw new RemoteException("Se produjo un error al Obtener las Cartas de un Partido Terminado" + e.getMessage());
		}
	}
	
	public ParejaDTO obtenerParejaGanadoraPartido(JugadorDTO jugador, PartidoDTO partido) throws RemoteException{
		
		try {
			return objetoRemoto.obtenerParejaGanadoraPartido(jugador, partido);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al Obtener la Pareja Ganadora de un Partido" + e.getMessage());
		}
	}

	public PartidoDTO jugarLibreParejas(ParejaDTO pareja) throws ControladorException, RemoteException{
		try {
			return objetoRemoto.jugarLibreParejas(pareja);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al intentar inscribir la pareja." + e.getMessage());
		}
	}
	
	public List<MiembroGrupoDTO> obtenerMiembrosGrupo(GrupoDTO grupo) throws RemoteException {
		
		try {
			return objetoRemoto.obtenerMiembrosGrupo(grupo);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al Obtener los Miembros de un Grupo" + e.getMessage());

		}
		
	}
	
	public void agregarJugadorGrupo(List<JugadorDTO> agregar, GrupoDTO dto, JugadorDTO administrador) throws RemoteException{
		
		try {
			objetoRemoto.agregarJugadorGrupo(agregar, dto, administrador);
		} catch (RemoteException e) {
			throw new RemoteException("Se produjo un error al Agregar Miembros a un Grupo" + e.getMessage());
		}
	}
	
	public void eliminarMiembroGrupo(JugadorDTO jugador, GrupoDTO grupo, JugadorDTO administrador) throws RemoteException {
		
		try {
			objetoRemoto.eliminarMiembroGrupo(jugador, grupo, administrador);
		} catch (RemoteException e) {
			
			throw new RemoteException("Se produjo un error al Eliminar un Miembro de un Grupo" + e.getMessage());
		}
	}

	public void armarParejaGrupo(ParejaDTO pareja, GrupoDTO grupo, JugadorDTO admin) throws RemoteException {
		// TODO Auto-generated method stub
		objetoRemoto.armarParejaGrupo(pareja, grupo, admin);
	}

	public PartidoDTO armarPartidoGrupo(List<ParejaDTO> parejas, GrupoDTO grupo, JugadorDTO admin) throws RemoteException {
		// TODO Auto-generated method stub
		return objetoRemoto.armarPartidoGrupo(parejas, grupo, admin);
	}

	public List<PartidoDTO> obtenerPartidosGrupo(GrupoDTO grupoSeleccionado, JugadorDTO jugador) throws RemoteException{
		// TODO Auto-generated method stub
		return objetoRemoto.obtenerPartidosGrupo(grupoSeleccionado, jugador);
	}



	
}
