package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import daos.RankingDAO;
import dtos.PartidoDTO;
import dtos.RankingDTO;
import enums.TipoCategoria;

@Entity
@Table (name = "Rankings")
public class Ranking {
	@Id
	@Column (name = "id_ranking")
	@GeneratedValue
	private int id;

	@ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable (name = "Ranking_Partido",
				joinColumns = {@JoinColumn (name = "id_ranking")},
				inverseJoinColumns = {@JoinColumn (name = "id_partido")})
	private List<Partido> partidos;

	@Column
	private int puntos;
	@Column (name = "cant_ganadas")
	private int cantidadGanadas;
	
	public Ranking() {
		this.partidos = new ArrayList<Partido>();
		this.puntos = 0;
		this.cantidadGanadas = 0;
	}
	
	public RankingDTO toDTO() {

		RankingDTO dto = new RankingDTO();
		dto.setCantidadGanadas(this.cantidadGanadas);
		dto.setId(this.id);
		dto.setPuntos(this.puntos);
		ArrayList<PartidoDTO>partidosDto = new ArrayList<PartidoDTO>();
		
		getPartidos();
		for(int i=0; i < partidos.size();i++)
		{
			partidosDto.add(partidos.get(i).toDTO());
		}
		dto.setPartidos(partidosDto);
		return dto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Partido> getPartidos() {
		partidos = RankingDAO.getInstance().obtenerPartidosRanking(this);
		return partidos;
	}

	public void setPartidos(ArrayList<Partido> partidos) {
		this.partidos = partidos;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getCantidadGanadas() {
		return cantidadGanadas;
	}

	public void setCantidadGanadas(int cantidadGanadas) {
		this.cantidadGanadas = cantidadGanadas;
	}

	public float getPromedio() {
		
		return puntos/partidos.size();
	}
	
	public void actualizar(Partido part, int punt){
		
		partidos.add(part);
		
		if(punt > 0)
			
			cantidadGanadas++;
		
		puntos = puntos + punt;
		
	}

	public TipoCategoria calculoCategoria() {
		
		//Empiezo de Mayor a menor Categoria
		if((partidos.size()>1000) && (puntos>8000) && (getPromedio()>=8))
		{
			return TipoCategoria.Master;
		}
		else{
			if((partidos.size()>500) && (puntos>3000) && (getPromedio()>=6))
				return TipoCategoria.Experto;
			else{
				if((partidos.size()>100) && (puntos>500) && (getPromedio()>=5))
					return TipoCategoria.Calificado;
				else
					return TipoCategoria.Novato;
			}
		}
	}
	
	
}
