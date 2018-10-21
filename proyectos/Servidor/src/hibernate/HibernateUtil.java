package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import bean.*;


public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static { 
		try {
			AnnotationConfiguration config = new AnnotationConfiguration();

			config.addAnnotatedClass(Ranking.class);
			config.addAnnotatedClass(Baza.class);
			config.addAnnotatedClass(Carta.class);
			config.addAnnotatedClass(CartaJugador.class);
			config.addAnnotatedClass(CartaTirada.class);
			config.addAnnotatedClass(Chico.class);
			config.addAnnotatedClass(Grupo.class);
			config.addAnnotatedClass(Jugador.class);
			config.addAnnotatedClass(Mano.class);
			config.addAnnotatedClass(MiembroGrupo.class);
			config.addAnnotatedClass(Movimiento.class);
			config.addAnnotatedClass(Pareja.class);
			config.addAnnotatedClass(Partido.class);
			config.addAnnotatedClass(PuntajePareja.class);
			config.addAnnotatedClass(Envite.class);

			sessionFactory = config.buildSessionFactory(); 
			
		} catch(Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static Session getSession() throws HibernateException {
	    	return sessionFactory.openSession();
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}