package daos;


import hibernate.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dtos.CartaDTO;
import entities.CartaEntity;


public class CartaDAO {

	private static CartaDAO instancia;
	private static SessionFactory sf = null;
	private Session session = null;

	public static CartaDAO getInstancia() {
		if (instancia == null) {
			sf = HibernateUtil.getSessionFactory();
			instancia = new CartaDAO();		
		}
		return instancia;
	}
	
	public Session getSession(){
		if (session == null || !session.isOpen()){
			session = sf.openSession();
		}
		return session;
	}
	
	public void closeSession(){
		if (session.isOpen())
			session.close();
	}
	
	public CartaEntity obtenerCarta(CartaDTO carta) {
		Session s = this.getSession();
		CartaEntity devolver = new CartaEntity();
		try {
			devolver = (CartaEntity) s.createQuery("select c from Carta c where c.id =:idcarta")
					.setParameter("idcarta", carta.getId()).uniqueResult();
			closeSession();

			return devolver;
		} catch(Exception e) {
			System.out.println("Error al obtener carta");
			return devolver;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CartaEntity> obtenerCartas() {
		Session s = this.getSession();
		List<CartaEntity> cartas;
		try {
			Query query = s.createQuery("from Carta");
			cartas = query.list();
			closeSession();

			return cartas;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void guardarCarta(CartaEntity c) {
		Transaction t = null;
		Session s = this.getSession();
		try {
			t = s.beginTransaction();
			s.save(c);
			System.out.println("Carta Guardada");
			t.commit();
			closeSession();
		} catch(Exception e) {
			System.out.println("ERROR AL GUARDAR CARTA");
		}
	}

}