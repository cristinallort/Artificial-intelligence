import java.util.LinkedList;
import java.util.Scanner;

/**
 * Programa principal per seleccionar i controlar les heurístiques i els algorismes
 * 
 * @author Cristina Llort
 *
 */
public class Main {

	public static void main(String[] args) {
		
		//VARIABLES
		Scanner scanner = new Scanner (System.in);
		int x_dim, y_dim, x_inicial, y_inicial, x_final, y_final, valor_inicial, valor_final, algorisme, heuristica; 
		LinkedList<int[]> solucio = new LinkedList<int[]>();
		Node node_inicial, node_final;
		int[][] taulell = {{ 2, 2, 2, 2, 3, 0, 1, 0, 0, 2},
		                   { 3, 0, 0, 0, 3, 0, 1, 0, 0, 2},
		                   { 3, 0, 0, 0, 3, 0, 1, 3, 3, 2},
		                   { 3, 3, 3, 3, 3, 0, 1, 0, 0, 2},
		                   { 3, 0, 2, 0, 3, 0, 1, 2, 2, 2},
		                   { 3, 0, 1, 0, 3, 3, 1, 3, 3, 2},
		                   { 3, 0, 1, 0, 0, 0, 1, 0, 0, 2},
		                   { 1, 1, 1, 1, 1, 1, 1, 0, 0, 2},
		                   { 3, 0, 0, 0, 0, 0, 1, 0, 0, 2},
		                   { 3, 2, 2, 2, 2, 0, 1, 2, 0, 2}};
		
		/*int[][] taulell = {{ 2, 3, 1, 3, 3 },
						   { 2, 0, 0, 0, 2 },
                		   { 2, 1, 2, 2, 2 },
                		   { 3, 1, 2, 3, 0 },
                		   { 3, 0, 2, 0, 0 }};*/
		
		x_dim = taulell.length;
		y_dim = taulell[0].length;
		
		
		
		//CASELLA INICIAL
		System.out.println("Escriu el valor inicial de X: ");
		x_inicial = scanner.nextInt();
		System.out.println("Escriu el valor inicial de Y: ");
		y_inicial = scanner.nextInt();
		while (taulell[x_inicial][y_inicial] == 0) {
			System.out.println("A la casella inicial no existeix carretera, tria una altra casella inicial: ");
			System.out.println("Escriu el valor inicial de X: ");
			x_inicial = scanner.nextInt();
			System.out.println("Escriu el valor inicial de Y: ");
			y_inicial = scanner.nextInt();
		}
		int[] casella_inicial = {x_inicial, y_inicial};
		solucio.add(casella_inicial);
		valor_inicial = taulell[x_inicial][y_inicial];
		node_inicial = new Node(valor_inicial, x_inicial, y_inicial, solucio, 0, 0);
		
		
		
		//CASELLA FINAL
		System.out.println("Escriu el valor final de X: ");
		x_final = scanner.nextInt();
		System.out.println("Escriu el valor final de Y: ");
		y_final = scanner.nextInt();
		while (taulell[x_final][y_final] == 0) {
			System.out.println("A la casella final no existeix carretera, tria una altra casella final: ");
			System.out.println("Escriu el valor final de X: ");
			x_final = scanner.nextInt();
			System.out.println("Escriu el valor final de Y: ");
			y_final = scanner.nextInt();
		}
		int[] casella_final = {x_final, y_final};
		valor_final = taulell[x_final][y_final];
		node_final = new Node(valor_final, x_final, y_final, solucio, 0, 0);
		
		
		
		//HEURÍSTICA
		System.out.println("Selecciona heurística: "
				+ "\n1: Línia recta"
				+ "\n2: Carretera més ràpida"
				+ "\n3: Carretera similar");
		heuristica = scanner.nextInt();
		
		
		
		//ALGORISME
		System.out.println("Selecciona algorisme:"
				+ "\n1: Best first"
				+ "\n2: A*");
		algorisme = scanner.nextInt();
		switch (algorisme) {
			case 1: 
				Algorisme bestFirst = new Algorisme(algorisme, x_dim, y_dim, taulell, heuristica);
				solucio = bestFirst.algorisme(node_inicial, node_final);
				bestFirst.solucio(solucio);
				break;
			case 2: 
				Algorisme aEstrella = new Algorisme(algorisme, x_dim, y_dim, taulell, heuristica);
				solucio = aEstrella.algorisme(node_inicial, node_final);
				aEstrella.solucio(solucio);
				break;
		}
		
		
		scanner.close();
	}

}
