import java.util.LinkedList;

/**
 * Interface per a definir el contenidor de qualsevol tipus de jugador
 * 
 * @author Cristina Llort
 *
 */
public interface Jugador{

	Fitxa realitzarJugada(LinkedList<Fitxa> fitxes1, LinkedList<Fitxa> fitxes2, LinkedList<Fitxa> fitxes_usades, int extrem_esquerra, int extrem_dreta);
	
	boolean jugadorBloquejat(LinkedList<Fitxa> fitxes, int extrem_esquerra, int extrem_dreta);
	
	boolean fitxaCorrecta(Fitxa fitxa, int extrem_esquerra, int extrem_dreta);
	
	boolean finalPartida(LinkedList<Fitxa> fitxes1, LinkedList<Fitxa> fitxes2, int extrem_esquerra, int extrem_dreta);
	
}