import java.util.LinkedList;

/**
 * Classe per gestionar les heurístiques
 * 
 * @author Cristina Llort
 *
 */
public class Heuristica {
	private int heuristica, algorisme;
	private Node node_actual, node_final;
	private float valor_acumulat;
	
	//CONSTRUCTORS
	public Heuristica(int heuristica, Node node_actual, Node node_final, int algorisme) {
		this.heuristica = heuristica;
		this. node_actual = node_actual;
		this.node_final = node_final;
		this.algorisme = algorisme;
	}

	
	/**
	 * Mètode que implementa les tres heurístiques
	 * @param node_inicial - del tipus Node que indica el node des del que partim
	 * @param node_final - del tipus Node que indica el node al que volem arribar
	 * @return valor_heuristic - del tipus float que indica el valor heurístic calculat
	 */
	public float heuristica(Node node_actual, Node node_final) {
		float valor_heuristic = 0;
		
		//HEURÍSTICA 1
		if (heuristica == 1) {
			int x = node_final.getX() - node_actual.getX();
			int y = node_final.getY() - node_actual.getY();
			valor_heuristic = (float) Math.sqrt((x*x) + (y*y));
		
		//HEURÍSTICA 2
		} else if (heuristica == 2) {
			int valor = node_actual.getValor();
			int x = Math.abs(node_final.getX() - node_actual.getX());
			int y = Math.abs(node_final.getY() - node_actual.getY());
			if(valor == 1) {
				valor = 0;
			}else if(valor == 2) {
				valor = 5;
			}else {
				valor = 10;
			}
			valor_heuristic = valor + x + y;
		
		//HEURÍSTICA 3
		} else {
			int valor = Math.abs(node_final.getValor() - node_actual.getValor());
			if(valor == 1) {
				valor = 5;
			}else if(valor == 2) {
				valor = 10;
			}
			valor_heuristic = valor;
		}

		return valor_heuristic;
	}

}
