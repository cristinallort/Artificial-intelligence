import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe per gestionar els algorismes
 * 
 * @author Cristina Llort
 *
 */

public class Algorisme {
	
	private int algorisme, x_dim , y_dim, heuristica;
	private int[][] taulell;

	//CONSTRUCTOR
	public Algorisme(int algorisme, int x_dim, int y_dim, int[][] taulell, int heuristica) {
		this.algorisme = algorisme;
		this.x_dim = x_dim;
        this.y_dim = y_dim;
        this.taulell = taulell;
        this.heuristica = heuristica;
	}
	
	//GETTERS
	public int getHeuristica() {
		return heuristica;
	}
	
	public int getAlgorisme() {
		return algorisme;
	}
	
	public int getXDim() {
		return x_dim;
	}
	
	public int getYDim() {
		return y_dim;
	}

	
	/**
	 * Mètode que gestiona l'algorisme -> tant el Best First com el A*
	 * @param node_inicial - del tipus Node que indica el node des del que partim
	 * @param node_final - del tipus Node que indica el node al que volem arribar
	 * @param algorisme - del tipus int que ens indica quin dels dos algorismes possibles volem utilitzar
	 * @return solucio - del tipus LinkedList<int[]> que conté els nodes que s'han recorregut
	 */
	public LinkedList<int[]> algorisme(Node node_inicial, Node node_final) {
		LinkedList<int[]> solucio = null;
		LinkedList<Node> pendents = new LinkedList<Node>();
		pendents.add(node_inicial);
		List<Node> tractats = new LinkedList<Node>();
		LinkedList successors;
		boolean trobat = false;
		
		while(!trobat && pendents.size()>0) {
		    Node node_actual = pendents.remove(0);
		    if((node_actual.getX() == node_final.getX()) && (node_actual.getY() == node_final.getY())) {
		    	trobat = true;
		    	solucio = node_actual.getCami();
		    }else {
		    	successors = generaSuccessors(node_actual, node_final);
                while (successors.size()>0){
                    Node node_successor = (Node) successors.remove(0);
                    if(!comprovaNode(tractats,node_successor) && !comprovaNode(pendents,node_successor)){
                    	pendents.add(node_successor);
                    	Collections.sort(pendents);
                    }
                } 
		    }
		    tractats.add(node_actual);
		}
		if(solucio != null) {
			System.out.println("NODES TRACTATS: " +tractats.size());
		}
		return solucio;
	}
	
	
	
	/**
	 * Mètode per generar els successors del node actual
	 * @param node_actual - del tipus Node que és el node que estem recorrent actualment
	 * @param node_final - del tipus Node que és el node de l'estat final
	 * @return successors - del tipus LinkedList<Node> que és la llista amb els nodes successors trobats
	 */
	public LinkedList<Node> generaSuccessors(Node node_actual, Node node_final){
        LinkedList successors = new LinkedList<Node>();
        Operador op = new Operador(this.algorisme, this.x_dim, this.y_dim, this.taulell, this.heuristica);
        
        op.amunt(node_actual, node_final, successors);
        op.abaix(node_actual, node_final, successors);
        op.dreta(node_actual, node_final, successors);
        op.esquerra(node_actual, node_final, successors);
        
        return successors;
    }

	
	/**
	 * Mètode per comprovar si un node es troba dins d'una llista de nodes
	 * @param llista - del tipus List<Node> que és la llista en la que busquem el node
	 * @param node_actual - del tipus Node que és el node que busquem
	 * @return trobat - del tipus booleà que indica cert si s'ha trobat el node a la llista i fals si no s'ha trobat
	 */
	public boolean comprovaNode(List<Node> llista, Node node_actual){
		boolean trobat = false;
        for (Node node: llista) {
        	if(node.getX() == node_actual.getX() && node.getY() == node_actual.getY()) {
        		trobat = true;
        	}
        }
		return trobat;
	}
	
    

	/**
	 * Mètode que mostra la solució per pantalla
	 * @param solucio - del tipus LinkedList<int[]> que conté els nodes que s'han recorregut
	 */
	public void solucio(LinkedList<int[]> solucio) {
		if(solucio!=null) {
			System.out.println("SOLUCIÓ"
					+ "\nNÚMERO DE NODES: " +solucio.size());
			for(int[] node:solucio) {
				System.out.println("NODE: ("+node[0]+","+node[1]+")");
			}
		}
		else {
			System.out.println("No existeix solució.");
		}
	}

}
