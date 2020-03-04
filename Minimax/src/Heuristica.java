/**
 * Classe per gestionar les heurístiques
 * 
 * @author Cristina Llort
 *
 */

public class Heuristica {
	private int heuristica;

	
	//CONSTRUCTORS
	public Heuristica(int heuristica) {
		this.heuristica = heuristica;
	}

	
	/**
	 * Mètode que implementa les tres heurístiques
	 * @param node 
	 * @return valor_heuristic - del tipus float que indica el valor heurístic calculat
	 */
	public int heuristica(Taulell node) {
		int valor_heuristic = 0;
		
		//HEURÍSTICA 1
		if (heuristica == 1) {
			int punts = 0, punts_rival = 0;
			for(int i = 0; i < node.getFitxes1().size(); i++) {
				punts = node.getFitxes1().get(i).getNum1() + node.getFitxes1().get(i).getNum2();
			}
			for(int i = 0; i < node.getFitxes2().size(); i++) {
				punts_rival = node.getFitxes2().get(i).getNum1() + node.getFitxes2().get(i).getNum2();
			}
			valor_heuristic = punts_rival - punts;
			
			
		//HEURÍSTICA 2
		} else if (heuristica == 2) {
			for(int i = 0; i < node.getFitxesUsades().size(); i++) {
				if(node.getFitxesUsades().get(i).getNum1() == node.getFitxa().getNum1()) valor_heuristic++;
				if(node.getFitxesUsades().get(i).getNum1() == node.getFitxa().getNum2()) valor_heuristic++;
				if(node.getFitxesUsades().get(i).getNum2() == node.getFitxa().getNum1()) valor_heuristic++;
				if(node.getFitxesUsades().get(i).getNum2() == node.getFitxa().getNum2()) valor_heuristic++;
			}
		
		
		//HEURÍSTICA 3
		} else {
			valor_heuristic = node.getFitxes2().size() - node.getFitxes1().size();
		}

		return valor_heuristic;
	}

}
