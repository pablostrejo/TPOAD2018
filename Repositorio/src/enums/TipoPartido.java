package enums;

public enum TipoPartido {

//	LibreIndividual("Libre individual"),
//	LibreParejas("Libre parejas"),
//	Grupo("Cerrada"); // 'cerrada' en el uml

	LibreIndividual,
	LibreParejas,
	Grupo; // 'cerrada' en el uml

	public static TipoPartido obtenerPorTipo(String tipoPartido) {
		for (TipoPartido e : values()) {
			if (e.name().equalsIgnoreCase(tipoPartido))
				return e;
		}
		return null;
	}
}
