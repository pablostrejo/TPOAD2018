package enums; 

public enum TipoEnvite {

	Envido,
	EnvidoEnvido,
	RealEnvido,
	FaltaEnvido,
	Truco,
	ReTruco,
	ValeCuatro,
	Quiero,
	NoQuiero,
	IrAlMazo;

	public static TipoEnvite obtenerPorTipo(String nombreEnvite) {
		for (TipoEnvite tipoEnvite : values()) {
			if (tipoEnvite.name().equalsIgnoreCase(nombreEnvite))
				return tipoEnvite;
		}
		return null;
	}

}
