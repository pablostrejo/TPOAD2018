package daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dtos.GrupoDTO;
import entities.Grupo;
import entities.Partido;
import hibernate.HibernateUtil;

public class GrupoDAO {
	
	protected static GrupoDAO instancia;
	protected static SessionFactory sf=null;
	protected Session session = null;
	
	public static GrupoDAO getInstancia (){
		
		if(instancia ==null){
			instancia = new GrupoDAO();
			sf= HibernateUtil.getSessionFactory();
		}
		return instancia;
	}
	
	public Session getSession() {
		if(session == null || !session.isOpen())
			session = sf.openSession();

		return session;
	}
	
	
	public Grupo buscarGrupo (GrupoDTO grupo) {
		Session s = this.getSession();
		Grupo devolver;
		try{
			
			devolver = (Grupo) s.createQuery("Select g from Grupo g inner join g.miembros where g.id =:id").setParameter("id", grupo.getId()).uniqueResult();
			s.close();
			return devolver;
		} catch(Exception e) {
			System.out.println("Error al obtener Grupo");
			e.printStackTrace();
			return null;
		}
	}
	
	public Grupo buscarGrupoPorNombre (GrupoDTO grupo){		
		Session s = this.getSession();
		Grupo devolver;
		try {
			devolver = (Grupo) s.createQuery("Select g from Grupo g left join g.miembros where g.nombre =:nombre").setParameter("nombre", grupo.getNombre()).uniqueResult();
			s.close();
			return devolver;
		} catch(Exception e) {
			System.out.println("Error al obtener Grupo");
			e.printStackTrace();
			return null;
		}
	}
	
	public void guardarGrupo (Grupo grupo){
		
		Transaction t =null;
		Session s = this.getSession();
		try {
			t= s.beginTransaction();
			s.saveOrUpdate(grupo);
			System.out.println("Grupo Guardado con Exito");
			s.flush();
			t.commit();
			s.close();
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Error al guardar el Grupo");
		}
	}
	
//	
//	public void agregarMiembroGrupo (Grupo grupo){
//		Transaction t =null;
//		Session s = this.getSession();
//		try{
//			
//			t= s.beginTransaction();
//			s.update(grupo);
//			System.out.println("Grupo Guardado con Exito");
//			s.flush();
//			t.commit();
//			s.close();
//			
//			
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			System.out.println("Error al guardar el Grupo");
//		}
//	}
//	
	
	@SuppressWarnings("unchecked")
	public List<Partido> buscarPartidos (Grupo grupo) {
		Session s = this.getSession();
		List<Partido> devolver;
		try {
			devolver = s.createQuery("select p from Grupo g inner join g.partidos p where g.id = :id")
					.setParameter("id", grupo.getId()).list();

			s.close();
			return devolver;
		} catch(Exception e){
			System.out.println("Error al obtener Partidos Grupo");
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Grupo> obtenerGrupos() {		
		Session s = this.getSession();
		List<Grupo> devolver;
		try {
			devolver = s.createQuery("Select g from Grupo g inner join g.partidos").list();
			s.close();
			return devolver;
		} catch(Exception e) {
			System.out.println("Error al obtener todos los grupos");
			e.printStackTrace();
			return null;
		}
	}

}
