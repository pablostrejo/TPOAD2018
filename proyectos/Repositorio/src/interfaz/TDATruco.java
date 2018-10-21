package interfaz; 

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
import exceptions.JugadorException;


public interface TDATruco extends Remote {

	public PartidoDTO jugarLibreIndividual(JugadorDTO jugador) throws RemoteException;

	public List<CartaJugadorDTO> obtenerCartasJugador (PartidoDTO partido, JugadorDTO jugador) throws RemoteException;

	public List<PuntajeParejaDTO> obtenerPuntajeChico (PartidoDTO partido, JugadorDTO jugador) throws RemoteException;

	public List<MovimientoDTO> obtenerMovimientosUltimaBaza (PartidoDTO partido, JugadorDTO jugador) throws RemoteException;

	public List<TipoEnvite> obtenerEnvitesDisponibles(PartidoDTO partido, JugadorDTO jugador) throws RemoteException;

	public List<PartidoDTO> tengoPartido(JugadorDTO jugador) throws RemoteException;

	public JugadorDTO obtenerJugadorActual(PartidoDTO partido, JugadorDTO jugador) throws RemoteException;

	public void nuevoMovimientoPartido(PartidoDTO partido, JugadorDTO turnoJugador, MovimientoDTO movimiento) throws RemoteException;

	public JugadorDTO login(JugadorDTO jg) throws RemoteException;

	public void registrarJugador(JugadorDTO jg) throws RemoteException;
	
	public void crearGrupo(GrupoDTO dto, JugadorDTO administrador) throws RemoteException;
	
	public PartidoDTO obtenerUltimoPartidoPendienteModalidad (TipoPartido tipoPartido, JugadorDTO jugadorDTO) throws RemoteException;

	public List<ParejaDTO> obtenerParejasPartido(PartidoDTO partido) throws RemoteException;

	public boolean partidoEstaTerminado (PartidoDTO partido, JugadorDTO jugador) throws RemoteException;
	
	public List<JugadorDTO> obtenerGanadoresBazas (PartidoDTO partido, JugadorDTO jugador) throws RemoteException;

	public ManoDTO obtenerUltimaManoActiva (PartidoDTO partido, JugadorDTO jugador) throws RemoteException;

	public void cerrarSesion(JugadorDTO jg) throws RemoteException, JugadorException;

	public List<MovimientoDTO> obtenerProximoMovimientoPartido (JugadorDTO jugador, PartidoDTO partido, MovimientoDTO ultimoMovimiento) throws RemoteException;
	
	public List<PartidoDTO>  levantarPartidosTerminadosJugador (JugadorDTO jugador) throws RemoteException;
	
	public JugadorDTO obtenerJugadorCompleto (JugadorDTO jugador) throws RemoteException;
	
	public List<ChicoDTO> obtenerResultadoFinalPartido (JugadorDTO jugador, PartidoDTO partido) throws RemoteException;
	
	public ParejaDTO obtenerParejaGanadoraPartido (JugadorDTO jugador, PartidoDTO partido) throws RemoteException;
	
	public List<CartaJugadorDTO> obtenerCartasJugadorMano(JugadorDTO jugador, PartidoDTO partido, MovimientoDTO movimiento) throws RemoteException;

	public PartidoDTO jugarLibreParejas(ParejaDTO pareja) throws RemoteException, ControladorException;
	
	public List<MiembroGrupoDTO> obtenerMiembrosGrupo (GrupoDTO grupo) throws RemoteException;
	
	public void agregarJugadorGrupo(List<JugadorDTO> agregar, GrupoDTO dto, JugadorDTO administrador) throws RemoteException;
	
	public void eliminarMiembroGrupo(JugadorDTO jugador, GrupoDTO grupo, JugadorDTO administrador) throws RemoteException;

	public void armarParejaGrupo(ParejaDTO pareja, GrupoDTO grupo, JugadorDTO admin) throws RemoteException;

	public PartidoDTO armarPartidoGrupo(List<ParejaDTO> parejas, GrupoDTO grupo, JugadorDTO admin) throws RemoteException;

	public List<PartidoDTO> obtenerPartidosGrupo(GrupoDTO grupoSeleccionado, JugadorDTO jugador) throws RemoteException;

}
