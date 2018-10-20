package daos;

import hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dtos.ParejaDTO;
import entities.ParejaEntity;


public class ParejaDAO {
	
	protected static SessionFactory sf =null;
	protected static ParejaDAO instancia;
	protected Session s = null;
	
	public static ParejaDAO getinstance() {
		if(instancia ==null){
			sf = HibernateUtil.getSessionFactory();
			instancia = new ParejaDAO();
		}
		return instancia;
	}
	
	public Session getSession() {
		if(s == null || !s.isOpen())
			s = sf.openSession();

		return s;
	}
	
	public Integer guardarPareja(ParejaEntity pareja) {
		Transaction t = null;
		Session s = sf.openSession();
		
		try {
			t = s.beginTransaction();
			Integer id = (Integer) s.save(pareja);
			s.flush();
			t.commit();
			s.close();

			return id;
		} catch (Exception e) {
			t.rollback();
			s.close();
			e.printStackTrace();
			System.out.println("Error al guardar la Pareja");
		}
		return null;
	}
	
	public ParejaEntity buscarPareja (ParejaDTO pareja){
		Session s = this.getSession();
		ParejaEntity devolver;
		try {
			devolver = (ParejaEntity) s.createQuery("select p from Pareja p inner join p.jugador1 p1 inner join p.jugador2 p2 where p.id=:id")
					.setParameter("id",pareja.getId()).uniqueResult();

			s.close();
			return devolver;
		} catch(Exception e) {
			System.out.println("Error al obtener Pareja");
			e.printStackTrace();
			return null;
		}
	}

}
