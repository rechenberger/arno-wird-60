package game.content.statics;

/**
 * Wichtige Punkte auf der Karte.
 * @author Marius
 *
 */
public enum WayPoints {
	
	/**
	 * Eintrittspunkt von der Basis A auf den oberen Weg.
	 */
	TOPPATH_ENTREE_TEAM_A,
	
	/**
	 * Eintrittspunkt von der Basis B auf den oberen Weg.
	 */
	TOPPATH_ENTREE_TEAM_B,
	
	/**
	 * Mittelpunkt des oberen Weges.
	 */
	TOPPATH_CENTER_POINT,
	
	
	

	/**
	 * Eine Menge an Punkten die sich um TOPPATH_ENTREE_TEAM_A befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	TOPPATH_ENTREE_TEAM_A_AREA,
	
	/**
	 * Eine Menge an Punkten die sich um TOPPATH_ENTREE_TEAM_B befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	TOPPATH_ENTREE_TEAM_B_AREA,
	
	/**
	 * Eine Menge an Punkten die sich um TOPPATH_CENTER_POINT befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	TOPPATH_CENTER_POINT_AREA,
	
	
	
	
	/**
	 * Der Bereich an dem die Vasalen des oberen Pfades aus Team A starten.
	 */
	TOPPATH_SPAWNPOINT_VASAL_TEAM_A_AREA,
	
	/**
	 * Der Bereich an dem die Vasalen des oberen Pfades aus Team B starten.
	 */
	TOPPATH_SPAWNPOINT_VASAL_TEAM_B_AREA,
	
	
	
	
	/**
	 * Eintrittspunkt von der Basis A auf den mittleren Weg.
	 */
	MIDDLEPATH_ENTREE_TEAM_A,
	
	/**
	 * Eintrittspunkt von der Basis B auf den mittleren Weg.
	 */
	MIDDLEPATH_ENTREE_TEAM_B,
	
	/**
	 * Mittelpunkt des mittleren Weges.
	 */
	MIDDLEPATH_CENTER_POINT,
	
	
	

	/**
	 * Eine Menge an Punkten die sich um MIDDLEPATH_ENTREE_TEAM_A befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	MIDDLEPATH_ENTREE_TEAM_A_AREA,
	
	/**
	 * Eine Menge an Punkten die sich um MIDDLEPATH_ENTREE_TEAM_B befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	MIDDLEPATH_ENTREE_TEAM_B_AREA,
	
	/**
	 * Eine Menge an Punkten die sich um MIDDLEPATH_CENTER_POINT befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	MIDDLEPATH_CENTER_POINT_AREA,
	
	
	
	
	/**
	 * Eintrittspunkt von der Basis A auf den unterem Weg.
	 */
	BOTTOMPATH_ENTREE_TEAM_A,
	
	/**
	 * Eintrittspunkt von der Basis B auf den unterem Weg.
	 */
	BOTTOMPATH_ENTREE_TEAM_B,
	
	/**
	 * Mittelpunkt des unteren Weges.
	 */
	BOTTOMPATH_CENTER_POINT,
	
	
	
	
	/**
	 * Eine Menge an Punkten die sich um BOTTOMPATH_ENTREE_TEAM_A befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	BOTTOMPATH_ENTREE_TEAM_A_AREA,
	
	/**
	 * Eine Menge an Punkten die sich um BOTTOMPATH_ENTREE_TEAM_B befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	BOTTOMPATH_ENTREE_TEAM_B_AREA,
	
	/**
	 * Eine Menge an Punkten die sich um BOTTOMPATH_CENTER_POINT befinden,
	 * sodass diese Punkte ein Viereck bilden, 
	 * dessen Seitenlaenge der breite des Pfades entspricht.
	 */
	BOTTOMPATH_CENTER_POINT_AREA,
	
	
	
	
	/**
	 * Der Bereich an dem die Vasalen des unteren Pfades aus Team A starten.
	 */
	BOTTOMPATH_SPAWNPOINT_VASAL_TEAM_A_AREA,
	
	/**
	 * Der Bereich an dem die Vasalen des unteren Pfades aus Team B starten.
	 */
	BOTTOMPATH_SPAWNPOINT_VASAL_TEAM_B_AREA,


	
	
	/**
	 * Berechnet den Mittelpunkt des Basisareals von Team A.
	 */
	BASE_TEAM_A_CENTER_POINT,
	
	/**
	 * Berechnet den Mittelpunkt des Basisareals von Team B.
	 */
	BASE_TEAM_B_CENTER_POINT,
	
	
	
	
	/**
	 * Berechnet ein Rechteckiges Gebiet um BASE_TEAM_A_CENTER_POINT.
	 * Die Dimension des Rechtecks entspricht dabei der Dimension des Weges
	 */
	BASE_TEAM_A_CENTER_POINT_AREA,
	
	/**
	 * Berechnet ein Rechteckiges Gebiet um BASE_TEAM_B_CENTER_POINT.
	 * Die Dimension des Rechtecks entspricht dabei der Dimension des Weges
	 */
	BASE_TEAM_B_CENTER_POINT_AREA,
	
}
