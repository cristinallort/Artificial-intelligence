import java.util.LinkedList;

/**
 * Classe per gestionar els operadors
 * 
 * @author Cristina Llort
 *
 */

public class Operador {

	private int algorisme, x_dim, y_dim, heuristica;
	private int[][] taulell;
	
	//CONSTRUCTOR
	public Operador(int algorisme, int x_dim, int y_dim, int[][] taulell, int heuristica) {
		this.algorisme = algorisme;
		this.x_dim = x_dim;
		this.y_dim = y_dim;
		this.taulell = taulell;
		this.heuristica = heuristica;
	}

	public void dreta(Node node_actual, Node node_final, LinkedList successors) {
		int x = node_actual.getX();
		int y = node_actual.getY();
		Node node;
		float valor_acumulat = node_actual.getValorAcumulat();
			
		if(((y+1)<y_dim) && (taulell[x][y+1]!=0)) {
			int[] casella = {x, y+1};
			LinkedList<int[]> cami = new LinkedList<int[]>();
			cami = (LinkedList<int[]>) node_actual.getCami().clone();
			cami.add(casella);
			int valor_successor= taulell[x][y+1];
			if (algorisme == 2) {
				valor_acumulat = valor_acumulat+valor_successor;
			}
			node = new Node(taulell[x][y+1], x, y+1, cami, 0, valor_acumulat);
		
			Heuristica h = new Heuristica(heuristica, node, node_final, algorisme);
        	float valor_heuristic = h.heuristica(node, node_final);
        	node.setValorHeuristic(valor_heuristic);
			successors.add(node);
		}
	}

	public void esquerra(Node node_actual, Node node_final, LinkedList successors) {
		int x = node_actual.getX();
		int y = node_actual.getY();
		Node node;
		float valor_acumulat = node_actual.getValorAcumulat();
			
		if(((y-1)>0) && (taulell[x][y-1]!=0)) {
			int[] casella = {x, y-1};
			LinkedList<int[]> cami = new LinkedList<int[]>();
			cami = (LinkedList<int[]>) node_actual.getCami().clone();
			cami.add(casella);
			int valor_successor= taulell[x][y-1];
			if (algorisme == 2) {
				valor_acumulat = valor_acumulat+valor_successor;
			}
			node = new Node(taulell[x][y-1], x, y-1, cami, 0, valor_acumulat);
		
			Heuristica h = new Heuristica(heuristica, node, node_final, algorisme);
			float valor_heuristic = h.heuristica(node, node_final);
        	node.setValorHeuristic(valor_heuristic);
			successors.add(node);
		}
	}

	public void abaix(Node node_actual, Node node_final, LinkedList successors) {
		int x = node_actual.getX();
		int y = node_actual.getY();
		Node node;
		float valor_acumulat = node_actual.getValorAcumulat();
			
		if(((x+1)<x_dim) && (taulell[x+1][y]!=0)) {
			int[] casella = {x+1, y};
			LinkedList<int[]> cami = new LinkedList<int[]>();
			cami = (LinkedList<int[]>) node_actual.getCami().clone();
			cami.add(casella);
			int valor_successor= taulell[x+1][y];
			if (algorisme == 2) {
				valor_acumulat = valor_acumulat+valor_successor;
			}
			node = new Node(taulell[x+1][y], x+1, y, cami, 0, valor_acumulat);
		
			Heuristica h = new Heuristica(heuristica, node, node_final, algorisme);
			float valor_heuristic = h.heuristica(node, node_final);
        	node.setValorHeuristic(valor_heuristic);
			successors.add(node);
		}
	}

	public void amunt(Node node_actual, Node node_final, LinkedList successors) {
		int x = node_actual.getX();
		int y = node_actual.getY();
		Node node;
		float valor_acumulat = node_actual.getValorAcumulat();
			
		if(((x-1)>=0) && (taulell[x-1][y]!=0)) {
			int[] casella = {x-1, y};
			LinkedList<int[]> cami = new LinkedList<int[]>();
			cami = (LinkedList<int[]>) node_actual.getCami().clone();
			cami.add(casella);
			int valor_successor= taulell[x-1][y];
			if (algorisme == 2) {
				valor_acumulat = valor_acumulat+valor_successor;
			}
			node = new Node(taulell[x-1][y], x-1, y, cami, 0, valor_acumulat);
		
			Heuristica h = new Heuristica(heuristica, node, node_final, algorisme);
			float valor_heuristic = h.heuristica(node, node_final);
        	node.setValorHeuristic(valor_heuristic);
			successors.add(node);
		}
	}

}
