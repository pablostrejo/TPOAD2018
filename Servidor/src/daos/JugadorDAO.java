package daos;

import hibernate.HibernateUtil;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dtos.JugadorDTO;
import entities.GrupoEntity;
import entities.JugadorEntity;

public class JugadorDAO {

	protected static SessionFactory sf = null;
	protected static JugadorDAO instancia;
	protected Session s = null;

	public static JugadorDAO getinstance() {
		if (instancia == null) {
			sf = HibernateUtil.getSessionFactory();
			instancia = new JugadorDAO();
		}
		return instancia;
	}

	public Session getSession() {
		if (s == null || !s.isOpen())
			s = sf.openSession();

		return s;
	}

	public void closeSession() {
		if (s.isOpen())
			s.close();
	}

	public void guardarJugador(JugadorEntity jugador) {
		Transaction t = null;
		s = this.getSession();

		try {
			t = s.beginTransaction();
			s.saveOrUpdate(jugador);
			s.flush();
			t.commit();
			s.close();
		} catch (Exception e) {
			System.out.println("Error al Guardar Jugador");
		}
	}

	public JugadorEntity buscarJugador(JugadorDTO jugador) {
		Session s = this.getSession();
		try {
			JugadorEntity devolver = (JugadorEntity) s.createQuery(
					"select j from Jugador j inner join j.ranking  left join j.grupos where j.id =:id")
					.setParameter("id", jugador.getId()).uniqueResult();

			s.close();
			return devolver;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al buscar jugador");
			return null;
		}
	}

	public JugadorEntity buscarJugadorPorApodoMail(JugadorDTO jugador) {
		Session s = this.getSession();
		try {
			JugadorEntity jug = (JugadorEntity) s.createQuery(
					"select j " +
					"from Jugador j inner join j.ranking " +
					"where j.apodo = :apodo or j.mail = :mail")
					.setParameter("apodo", jugador.getApodo())
					.setParameter("mail", jugador.getMail())
					.uniqueResult();

			s.close();
			return jug;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public JugadorEntity buscarJugadorPorApodoPassword(JugadorDTO jugador) {
		Session s = this.getSession();
		try {
			JugadorEntity jug = (JugadorEntity) s.createQuery(
					"select j " +
					"from Jugador j inner join j.ranking left join j.grupos " +
					"where j.apodo = :apodo and j.password = :password")
					.setParameter("apodo", jugador.getApodo())
					.setParameter("password", jugador.getPassword())
					.uniqueResult();

			s.close();

			// OJO, la consulta anterior no es case sensitive, por lo tanto
			// no tiene en cuenta las mayusculas y minusculas de la Password,
			// valido entonces que sean exactamente iguales!
			
			if(jug != null)
			{
				if (!jug.getPassword().equals(jugador.getPassword()))
					jug = null;
					
			}
			
			return jug;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<GrupoEntity> obtenerGruposJugador(JugadorEntity jugador) {
		Session s = this.getSession();
		ArrayList<GrupoEntity> devolver = null;
		try {
			devolver = (ArrayList<GrupoEntity>) s.createQuery(
							"select g from Jugador j inner join j.grupos g where j.id =:id")
							.setParameter("id", jugador.getId()).list();
			s.close();
			return devolver;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al buscar jugador");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<JugadorEntity> obtenerJugadores() {
		Session s = this.getSession();
		ArrayList<JugadorEntity> jugadores;
		try {
			jugadores = (ArrayList<JugadorEntity>) s.createQuery(
					"Select j from Jugador j left join j.grupos").list();
			s.close();
			return jugadores;
		} catch (Exception e) {
			System.out.println("Error al obtener todos los Jugadores");
			e.printStackTrace();
			return null;
		}
	}

	public JugadorEntity buscarJugadorPorApodo(JugadorDTO jugador) {
		Session s = this.getSession();
		try {
			JugadorEntity devolver = (JugadorEntity) s.createQuery(
					"select j from Jugador j inner join j.ranking  left join j.grupos where j.apodo =:apodo")
					.setParameter("apodo", jugador.getApodo()).uniqueResult();

			s.close();
			return devolver;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al buscar jugador");
			return null;
		}
	}

}
