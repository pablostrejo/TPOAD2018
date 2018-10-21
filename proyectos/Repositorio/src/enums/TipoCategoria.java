package enums; 

public enum TipoCategoria {

	Novato ("Novato"),
	Calificado ("Calificado"),
	Experto ("Experto"),
	Master ("Master");

	private String tipoCat;

	private TipoCategoria(){
		
	}
	
	private TipoCategoria(String tipo){
		this.tipoCat = tipo;
	}

	public String getTipoCategoria() {
		return tipoCat;
	}	
	
	public static TipoCategoria obtenerPorTipo(String tipoCat) {
		for (TipoCategoria e : values()) {
			if (e.tipoCat.equalsIgnoreCase(tipoCat))
				return e;
		}
		return null;
	}

	public static String obtenerTipoMayor(String categoriaMedia) {
		// TODO Auto-generated method stub
				
		if (categoriaMedia.equalsIgnoreCase("Novato")){
			return TipoCategoria.Calificado.toString();
		}else if (categoriaMedia.equalsIgnoreCase("Calificado")){
			return TipoCategoria.Experto.toString();
		}else if (categoriaMedia.equalsIgnoreCase("Experto")){
			return TipoCategoria.Master.toString();
		}
		return null;
	}

	public static String obtenerTipoMenor(String categoriaMedia) {
		// TODO Auto-generated method stub
		if (categoriaMedia.equalsIgnoreCase("Calificado")){
			return TipoCategoria.Novato.toString();
		}else if (categoriaMedia.equalsIgnoreCase("Experto")){
			return TipoCategoria.Calificado.toString();
		}else if (categoriaMedia.equalsIgnoreCase("Master")){
			return TipoCategoria.Experto.toString();
		}
		return null;
	}
}
