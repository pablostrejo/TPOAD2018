package server;

import hibernate.HibernateUtil;
import interfaz.TDATruco;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import objetoRemoto.ObjetoRemoto;

public class Servidor {

	TDATruco objetoRemoto;

	public static void main(String[] args) {
		new Servidor();

	//	new TestSegundaEntrega();
	}

	public Servidor() {
		iniciar();
	}

	public void iniciar() {
		try {
			objetoRemoto = new ObjetoRemoto();

			LocateRegistry.createRegistry(1099);
			Naming.rebind("//localhost/ServicioCentral", objetoRemoto);
			System.out.println("Fijado en //localhost/ServicioCentral");

			new HibernateUtil(); // crea la conexion a la BD

			/*
			 * ServicioCentral.getModelo().registrarObserver((Observer) objetoRemoto);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
