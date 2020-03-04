import java.util.LinkedList;

/**
 * Classe per gestionar els nodes
 * 
 * @author Cristina Llort
 *
 */

public class Node implements Comparable<Node>{

	private int valor, x, y;
	private LinkedList<int[]> cami;
	private float valor_heuristic, valor_acumulat;
	
	//CONSTRUCTOR
	public Node(int valor, int x, int y, LinkedList<int[]> cami, float valor_heuristic, float valor_acumulat) {
		this.valor=valor;
		this.x=x;
		this.y=y;
		this.cami=cami;
		this.valor_heuristic = valor_heuristic;
		this.valor_acumulat = valor_acumulat;
	}
	
	//GETTERS I SETTERS
	public int getValor() {
		return valor;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public LinkedList<int[]> getCami() {
		return cami;
	}

	public float getValorHeuristic() {
		return valor_heuristic;
	}

	public void setValorHeuristic(float valor_heuristic) {
		this.valor_heuristic = valor_heuristic;
	}

	public float getValorAcumulat() {
		return valor_acumulat;
	}

	public void setValorAcumulat(float valorAcumulat) {
		this.valor_acumulat = valor_acumulat;
	}
	
	public String toString() {
        return x + " " + y;
    }
	
	public int compareTo(Node node) {
		int valor, valor1, valor2;
		valor1 = (int) ((int) this.getValorHeuristic() + this.getValorAcumulat());
		valor2 = (int) ((int) node.getValorHeuristic() + node.getValorAcumulat());
		valor = valor1 - valor2;
		return valor;
	}	
}


