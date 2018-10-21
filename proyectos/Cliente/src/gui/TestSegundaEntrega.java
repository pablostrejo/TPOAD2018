package gui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import controlador.*;
import bean.*;
import businessDelegate.BusinessDelegate;
import daos.*;
import dtos.*;
import exceptions.*;

public class TestSegundaEntrega {

	private BusinessDelegate businessDelegate = null;
	
	public static void main(String[] args) throws RemoteException {
		new TestSegundaEntrega();
	}
	
	
	public TestSegundaEntrega() throws RemoteException {
		
		businessDelegate = new BusinessDelegate();

		// ************** INICIO PRUEBAS:  ************** //
		
		// ************** registrarJugador ************** //
		
		// Damos de alta un nuevo Jugador
		JugadorDTO jugador = new JugadorDTO();

		jugador.setApodo("TAM");
		jugador.setMail("TAM@uade.edu.ar");
		jugador.setPassword("TAM");

		try {
			Controlador.getInstance().registrarJugador(jugador);
		} catch (JugadorException e) {
			System.err.println(e.getMessage());
		}
		
		// Tratamos de dar de alta un jugador con el mismo apodo. NO deberia darlo de alta
		jugador.setApodo("TAM");
		jugador.setMail("TAM@gmail.com");
		jugador.setPassword("1234567");

		try {
			Controlador.getInstance().registrarJugador(jugador);
		} catch (JugadorException e) {
			System.err.println(e.getMessage());
		}


		// ************** inicio de sesión ************** //

		jugador.setApodo("TAM");
		jugador.setPassword("TAM");

		try {
			Controlador.getInstance().iniciarSesion(jugador);
		} catch (JugadorException e) {
			System.err.println(e.getMessage());
		}

		
		
		// ************** Creamos un Grupo ************** //

		jugador.setId(4);
		
		GrupoDTO grupo = new GrupoDTO();
		grupo.setNombre("UADE_GRUPO_9");
				
		try {
			Controlador.getInstance().crearGrupo(grupo, jugador);
		} catch (ControladorException e1) {
			System.err.println(e1.getMessage());
			//e1.printStackTrace();
		}

		
		// ************** Agregamos Miembros a Grupos ************** //

		JugadorDTO jugador1 = new JugadorDTO();

		jugador1.setApodo("jugador1");
		jugador1.setMail("jugador1@gmail.com");
		jugador1.setPassword("jugador1");

		try {
			Controlador.getInstance().registrarJugador(jugador1);
		} catch (JugadorException e) {
			System.err.println(e.getMessage());
		}

		JugadorDTO jugador2 = new JugadorDTO();

		jugador2.setApodo("jugador2");
		jugador2.setMail("jugador2@gmail.com");
		jugador2.setPassword("jugador2");

		try {
			Controlador.getInstance().registrarJugador(jugador2);
		} catch (JugadorException e) {
			System.err.println(e.getMessage());
		}

		JugadorDTO jugador3 = new JugadorDTO();

		jugador3.setApodo("jugador3");
		jugador3.setMail("jugador3@gmail.com");
		jugador3.setPassword("jugador3");

		try {
			Controlador.getInstance().registrarJugador(jugador3);
		} catch (JugadorException e) {
			System.err.println(e.getMessage());
		}

		JugadorDTO jugador4 = new JugadorDTO();

		jugador4.setApodo("jugador4");
		jugador4.setMail("jugador4@gmail.com");
		jugador4.setPassword("jugador4");

		try {
			Controlador.getInstance().registrarJugador(jugador4);
		} catch (JugadorException e) {
			System.err.println(e.getMessage());
		}

		ArrayList<JugadorDTO> jugadores = Controlador.getInstance().obtenerJugadores();
		List<JugadorDTO> agregar = new ArrayList<JugadorDTO>();

		System.out.println(" ID Jugador : " + jugadores.get(1));
		System.out.println(" ID Jugador : " + jugadores.get(2));
		System.out.println(" ID Jugador : " + jugadores.get(3));
		System.out.println(" ID Jugador : " + jugadores.get(4));
		
		agregar.add(jugadores.get(1));
		agregar.add(jugadores.get(2));
		agregar.add(jugadores.get(3));
		agregar.add(jugadores.get(4)); 
		
		Controlador.getInstance().agregarJugadorGrupo(agregar, grupo , jugador);



		// ************** Jugar Libre Individual ************** //

		try {
			jugador1 = businessDelegate.login(jugador1);
		} catch (RemoteException e) {
			System.err.println(e.getMessage());
		}
		try {
			jugador2 = businessDelegate.login(jugador2);
		} catch (RemoteException e) {
			System.err.println(e.getMessage());
		}
		try {
			jugador3 = businessDelegate.login(jugador3);
		} catch (RemoteException e) {
			System.err.println(e.getMessage());
		}
		try {
			jugador4 = businessDelegate.login(jugador4);
		} catch (RemoteException e) {
			System.err.println(e.getMessage());
		}	

		// ************** Controlamos si se armo Partido ************** //
		
		// generamos PARTIDO
		System.out.println("");

		PartidoDTO part1 = null;
		PartidoDTO part2 = null;
		PartidoDTO part3 = null;
		PartidoDTO part4 = null;

		try {
			part1 = businessDelegate.jugarLibreIndividual(jugador1);
			part2 = businessDelegate.jugarLibreIndividual(jugador2);
			part3 = businessDelegate.jugarLibreIndividual(jugador3);
			part4 = businessDelegate.jugarLibreIndividual(jugador4);
		}
		catch (RemoteException e) {
			System.err.println("Error al Intentar Jugar Libre Individual: " + e.getMessage());
		}
				
		if (part4 != null) {
			System.out.println("Se armo un partido! Inicio: " + part4.getFechaInicio());
			System.out.println("El partido nuevo es: " + part4.getId());
		}

		if (part3 == null) {
			List<PartidoDTO> partidosPendientes;
			try {
				partidosPendientes = businessDelegate.tengoPartido(jugador3);
				if (!partidosPendientes.isEmpty()){
					part3 = partidosPendientes.get(partidosPendientes.size()-1);
					System.out.println("El partido nuevo es: " + part3.getId());
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		if (part2 == null) {
			List<PartidoDTO> partidosPendientes;
			try {
				partidosPendientes = businessDelegate.tengoPartido(jugador2);
				if (!partidosPendientes.isEmpty()){
					part2 = partidosPendientes.get(partidosPendientes.size()-1);
					System.out.println("El partido nuevo es: " + part2.getId());
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		if (part1 == null) {
			List<PartidoDTO> partidosPendientes;
			try {
				partidosPendientes = businessDelegate.tengoPartido(jugador1);
				if (!partidosPendientes.isEmpty()){
					part1 = partidosPendientes.get(partidosPendientes.size()-1);
					System.out.println("El partido nuevo es: " + part1.getId());
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		// ejecutamos las 4 ventanas de juego
		try {
			VentanaPrueba ventana1 = new VentanaPrueba(part1, jugador1);
			VentanaPrueba ventana2 = new VentanaPrueba(part2, jugador2);
			VentanaPrueba ventana3 = new VentanaPrueba(part3, jugador3);
			VentanaPrueba ventana4 = new VentanaPrueba(part4, jugador4);
		}
		catch(RemoteException e){
			System.out.println("Error al crear las Ventanas de Prueba: " + e.getMessage());
		}

// DEBERIA HABER GENERADO EL PARTIDO
		
	
	}
	
	
}


