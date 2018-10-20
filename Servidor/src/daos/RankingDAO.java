package daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import bean.Partido;
import bean.Ranking;
import hibernate.HibernateUtil;

public class RankingDAO {
	
	private static RankingDAO instance;
	private static SessionFactory sf;
	

	public static RankingDAO getInstance() {
		if(instance == null){
			instance = new RankingDAO();
			sf = HibernateUtil.getSessionFactory();
		}
		return instance;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Partido> obtenerPartidosRanking (Ranking ranking){
		Session s = sf.openSession();
		List<Partido> devolver;
		try{
			devolver = s.createQuery("select part from Ranking rank inner join rank.partidos part "
					+ "where rank.id = :id").setParameter("id", ranking.getId()).list();
			
			s.close();
			return devolver;
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Error al levantar Partidos de Ranking");
			return null;
		}
	}

}
