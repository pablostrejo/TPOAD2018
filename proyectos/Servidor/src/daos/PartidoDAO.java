package daos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dtos.JugadorDTO;
import dtos.PartidoDTO;
import enums.EstadoPartido;
import enums.TipoPartido;
import exceptions.PartidoException;
import bean.Partido;
import hibernate.HibernateUtil;

public class PartidoDAO {

	private static PartidoDAO instancia = null;
	private static SessionFactory sf = null;

	public static PartidoDAO getInstance() {
		if (instancia == null) {
			sf = HibernateUtil.getSessionFactory();
			instancia = new PartidoDAO();
		}
		return instancia;
	}

	public Integer guardarPartido(Partido partido) {
		Transaction t = null;
		Session s = sf.openSession();
		
		try {
			t = s.beginTransaction();
			Integer id = (Integer) s.save(partido);
			s.flush();
			t.commit();
			s.close();

			return id;
		} catch (Exception e) {
			t.rollback();
			s.close();
			e.printStackTrace();
			System.out.println("Error al guardar Partido");
		}
		return null;
	}

	public Partido buscarPartido(PartidoDTO partido) throws PartidoException {
		Session s = sf.openSession();
		Partido devolver;
		try{
			
			devolver = (Partido) s.createQuery(
						"select p from Partido p " + 
						"inner join p.chicos chicos " +
						"inner join p.parejas parejas " +
						"inner join chicos.manos manos " + 
						"inner join manos.bazas bazas " +
						"where p.id =:id").setParameter("id", partido.getId()).uniqueResult();
			s.close();
			return devolver;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new PartidoException("Error al Obtener el Partido de la Base de Datos");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Partido> obtenerPartidosEntreFechas(Timestamp fechaDesde, Timestamp fechaHasta, 
			TipoPartido modalidad, JugadorDTO jugador) throws PartidoException {

		Session s = sf.openSession();
		List<Partido> partidos = new ArrayList<Partido>();
		try {
			partidos = s.createQuery(
					"select p from Partido p " +
					"inner join p.chicos chicos " + 
					"inner join p.parejas parejas " +
					"inner join chicos.manos manos " +
					"inner join manos.bazas bazas " + 
					"where p.tipoPartido =:modalidad and p.fechaInicio between :desde and :hasta")
					.setParameter("desde", fechaDesde)
					.setParameter("hasta", fechaHasta)
					.setParameter("modalidad", modalidad).list();

			s.close();

			int i = 0;
			while (i<partidos.size()) {
				if(partidos.get(i).getParejas().get(0).tenesJugador(jugador) && partidos.get(i).getParejas().get(1).tenesJugador(jugador))
					//El jugador no esta en ninguna de las parejas
					partidos.remove(i);
				else
					i++;
			}

			return partidos;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new PartidoException("Error al Obtener Partidos Entre Fechas de la Base de Datos");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Partido> obtenerPartidosPendientes() throws PartidoException {
		Session s = sf.openSession();
		List<Partido> devolver = null;
		try{
			//No es necesario los inners por la estrategia de EAGER!!!!
			devolver = s.createQuery(
							"select p from Partido p " +
//							"inner join p.chicos chicos " +
//							"inner join p.parejas parejas " +
//							"inner join chicos.manos manos " +
//							"inner join manos.bazas bazas " +
							"where p.estadoPartido =:estadoPartido")
					.setParameter("estadoPartido", EstadoPartido.Empezado)
					.list();
			s.close();
			return devolver;
			
		}
		catch(Exception e){
			e.printStackTrace();
			throw new PartidoException("Error al Obtener Partidos Pendientes de la Base de Datos");
		}
	}
	
	public void update(Partido partido) throws PartidoException {
		Transaction t = null;
		Session s = sf.openSession();
		try {
			t = s.beginTransaction();
//			s.clear();
			s.saveOrUpdate(partido);
//			s.merge(partido);
			s.flush();
			t.commit();
			s.close();
		} catch(Exception e) {
			t.rollback();
			s.close();
			e.printStackTrace();
			throw new PartidoException("Error al Hacer Update de Partido");
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<Partido> levantarPartidosTerminadosJugador(JugadorDTO jugador) throws PartidoException {
	Session s = sf.openSession();
	List<Partido> devolver = null; 
	try{
		devolver = s.createQuery(
						"select p from Partido p inner join p.parejas par" +
						" where p.estadoPartido =:estadoPartido and (par.jugador1.id =:idJugador or par.jugador2.id =:idJugador)").setParameter("estadoPartido", EstadoPartido.Terminado).setParameter("idJugador", jugador.getId()).list();
				
		s.close();
		return devolver;
		
	}
	catch(Exception e){
		e.printStackTrace();
		throw new PartidoException("Error al Obtener Partidos Pendientes de la Base de Datos");
	}
}

}
