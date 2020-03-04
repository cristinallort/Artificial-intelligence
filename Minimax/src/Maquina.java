import java.util.LinkedList;

/**
 * Classe per gestionar el joc de la màquina
 * 
 * @author Cristina Llort
 *
 */

public class Maquina implements Jugador {


	private int heuristica, algorisme;
	private static final int NIVELL_MAX_MINIMAX = 7;
	private static final int NIVELL_MAX_ALFABETA = 11;

	

	//CONSTRUCTOR
	public Maquina(int heuristica, int algorisme) {
		this.heuristica = heuristica;
		this.algorisme = algorisme;
	}

	/**
	 * Mètode que permet realitzar una nova jugada a la màquina
	 * @param fitxes1 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador 
	 * @param fitxes2 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador rival 
	 * @param fitxes_usades - del tipus LinkedList<Fitxa> que conté les fitxes ja col·locades al taulell
	 * @param extrem_esquerra - del tipus int que és l'extrem esquerra actual
	 * @param extrem_dreta - del tipus int que és l'extrem dret actual
	 * @return fitxa - del tipus Fitxa que és la fitxa que vol tirar el jugador al taulell
	 */
	public Fitxa realitzarJugada(LinkedList<Fitxa> fitxes1, LinkedList<Fitxa> fitxes2, LinkedList<Fitxa> fitxes_usades, int extrem_esquerra, int extrem_dreta) {
		Object[] obj = new Object[2];
		Fitxa fitxa = null;
		Taulell t = new Taulell(fitxes1, fitxes2, fitxes_usades, extrem_esquerra, extrem_dreta, fitxa);
		if(algorisme == 1) {
			obj = Minimax(t, 0);
		}else {
			int alfa = Integer.MIN_VALUE;
			int beta = Integer.MAX_VALUE;
			obj = podaAlfaBeta(t, 0, alfa, beta);
		}
		if(obj[1] != null)
			fitxa = ((Taulell) obj[1]).getFitxa();
		return fitxa;
	}

	/**
	 * Mètode que implementa l'algorisme Minimax per decidir quina fitxa tirar
	 * @param node - del tipus Taulell que conté la informació del node actual (la situació actual del taulell)
	 * @param nivell - del tipus int que conté el nombre del nivell actual
	 * @return obj - del tipus Object[] que conté el valor i el node a retornar
	 */
	public Object[] Minimax(Taulell node, int nivell) {
		Object[] obj = new Object[2];
		Heuristica h = new Heuristica(heuristica);
		LinkedList<Taulell> nodes = null;
		
		int mes_infinit = Integer.MAX_VALUE;
		int menys_infinit = Integer.MIN_VALUE;
		int valor_retornar = 0;
		Taulell node_retornar = null;
		
		if(finalPartida(node.getFitxes1(), node.getFitxes2(), node.getExtremEsquerra(), node.getExtremDreta())) {
			if(node.getFitxes1().size() == 0) {
				valor_retornar = menys_infinit;
			}else if(node.getFitxes2().size() == 0){
				valor_retornar = mes_infinit;
			}
		}else if(nivell == NIVELL_MAX_MINIMAX){
			valor_retornar = h.heuristica(node);
        }else {
        	if(nivell % 2 == 0) {
        		valor_retornar = menys_infinit;
        		nodes = generaFills(node, node.getFitxes1(), nivell);
        	}else {
        		valor_retornar = mes_infinit;
        		nodes = generaFills(node, node.getFitxes2(), nivell);
        	}
    		int i = 0;
        	while(i < nodes.size()) {
        		Taulell F = nodes.get(i);
                obj = Minimax(F.clone(), nivell+1);
                if(nivell % 2 == 0) {
                	if((int) obj[0] > valor_retornar){
                		valor_retornar = (int) obj[0];
                	}
            	}else {
            		if((int) obj[0] < valor_retornar){
            			valor_retornar = (int) obj[0];
                	}
            	}
                node_retornar = (Taulell) F.clone();
                i++;
        	}	
        }
		obj[0] = valor_retornar;
		obj[1] = node_retornar;
		return obj;
	}
	
	
	/**
	 * Mètode que permet generar les possibles tirades següents
	 * @param node - del tipus Taulell que conté la informació del node actual (la situació actual del taulell)
	 * @param fitxes - del tipus LinkedList<Fitxa> que conté les fitxes del jugador del que s'està anal·litzant el torn
	 * @param nivell - del tipus int que conté el número del nivell que s'anal·litza
	 * @return nodes - del tipus LinkedList<Taulell> que conté els diversos nodes amb les possibles tirades
	 */
	public LinkedList<Taulell> generaFills(Taulell node, LinkedList<Fitxa> fitxes, int nivell) {
		LinkedList<Taulell> nodes = new LinkedList<Taulell>();

		if (!jugadorBloquejat(fitxes, node.getExtremEsquerra(), node.getExtremDreta())) {
			
			for (int i = 0; i < fitxes.size(); i++) {
				Taulell nou_node, nou_node2;
				LinkedList<Fitxa> f = (LinkedList<Fitxa>) fitxes.clone();
				LinkedList<Fitxa> f2 = (LinkedList<Fitxa>) fitxes.clone();
				LinkedList<Fitxa> fu1 = node.cloneFitxes(node.getFitxesUsades());
				LinkedList<Fitxa> fu2 = node.cloneFitxes(node.getFitxesUsades());
				Fitxa fitxa = new Fitxa(fitxes.get(i).getNum1(), fitxes.get(i).getNum2());
				Fitxa fitxa2 = new Fitxa(fitxes.get(i).getNum1(), fitxes.get(i).getNum2());
				
				if (fitxaCorrecta(f.get(i), node.getExtremEsquerra(), node.getExtremDreta())) {
					

					if (f.get(i).getNum1() == node.getExtremEsquerra()
							|| f.get(i).getNum2() == node.getExtremEsquerra()) {
						
						//DOS EXTREMS
						if (f.get(i).getNum1() == node.getExtremDreta()
								|| f.get(i).getNum2() == node.getExtremDreta()) {
							
							//EXTREM 1
							int num1 = fu1.get(0).getNum1();
							if (f.get(i).getNum2() != num1) {
								num1 = fitxa.getNum1();
								int num2 = fitxa.getNum2();
								fitxa.setNum1(num2);
								fitxa.setNum2(num1);
							}
							fitxa.setExtrem(1);
							fu1.addFirst(fitxa);
							int extrem1 = fu1.get(0).getNum1();
							int extrem2 = fu1.get(fu1.size()-1).getNum2();
							f.remove(f.get(i));
							if(nivell % 2 == 0) {
								nou_node = new Taulell(f, node.getFitxes2(), fu1, extrem1, extrem2, fitxa);
							}else {
								nou_node = new Taulell(node.getFitxes1(), f, fu1, extrem1, extrem2, fitxa);
							}
							nodes.add(nou_node);
							
							
							//EXTREM 2
							int n2 = fu2.get(fu2.size()-1).getNum2();
							if (f2.get(i).getNum1() != n2) {
								int n1 = fitxa2.getNum1();
								n2 = fitxa2.getNum2();
								fitxa2.setNum1(n2);
								fitxa2.setNum2(n1);
							}
							fitxa2.setExtrem(2);
							fu2.addLast(fitxa2);
							extrem1 = fu2.get(0).getNum1();
							extrem2 = fu2.get(fu2.size()-1).getNum2();
							f2.remove(fitxa2);
							if(nivell % 2 == 0) {
								nou_node2 = new Taulell(f2, node.getFitxes2(), fu2, extrem1, extrem2, fitxa);
							}else {
								nou_node2 = new Taulell(node.getFitxes1(), f2, fu2, extrem1, extrem2, fitxa);
							}
							nodes.add(nou_node2);
							
							
						//NOMÉS EXTREM ESQUERRA
						} else {
							int num1 = fu1.get(0).getNum1();
							if (f.get(i).getNum2() != num1) {
								num1 = fitxa.getNum1();
								int num2 = fitxa.getNum2();
								fitxa.setNum1(num2);
								fitxa.setNum2(num1);
							}
							fitxa.setExtrem(1);
							fu1.addFirst(fitxa);
							int extrem1 = fu1.get(0).getNum1();
							int extrem2 = fu1.get(fu1.size()-1).getNum2();
							f.remove(fitxa);
							if(nivell % 2 == 0) {
								nou_node = new Taulell(f, node.getFitxes2(), fu1, extrem1, extrem2, fitxa);
							}else {
								nou_node = new Taulell(node.getFitxes1(), f, fu1, extrem1, extrem2, fitxa);
							}
							nodes.add(nou_node);
						}
						
					//NOMÉS EXTREM DRET
					} else if (f.get(i).getNum1() == node.getExtremDreta() || f.get(i).getNum2() ==  node.getExtremDreta()) {
						f.get(i).setExtrem(2);
						int num2 = fu1.get(fu1.size()-1).getNum2();
						if (f.get(i).getNum1() != num2) {
							int num1 = fitxa.getNum1();
							num2 = fitxa.getNum2();
							fitxa.setNum1(num2);
							fitxa.setNum2(num1);
						}
						fitxa.setExtrem(2);
						fu1.addLast(fitxa);
						int extrem1 = fu1.get(0).getNum1();
						int extrem2 = fu1.get(fu1.size()-1).getNum2();
						f.remove(fitxa);
						if(nivell % 2 == 0) {
							nou_node = new Taulell(f, node.getFitxes2(), fu1, extrem1, extrem2, fitxa);
						}else {
							nou_node = new Taulell(node.getFitxes1(), f, fu1, extrem1, extrem2, fitxa);
						}
						nodes.add(nou_node);
					}
				}
			}
		}
		return nodes;
	}
	
	
	/**
	 * Mètode que implementa l'algorisme Poda Alfa-Beta per decidir quina fitxa tirar
	 * @param node - del tipus Taulell que conté la informació del node actual (la situació actual del taulell)
	 * @param nivell - del tipus int que conté el nombre del nivell actual
	 * @param alfa - del tipus int que representa el valor mínim que té garantitzat un node MAX
	 * @param beta - del tipus int que representa el valor màxim que té garantitzat un node MIN
	 * @return obj - del tipus Object[] que conté el valor i el node a retornar
	 */
	public Object[] podaAlfaBeta(Taulell node, int nivell, int alfa, int beta) {
		Object[] obj = new Object[2];
		Heuristica h = new Heuristica(heuristica);
		LinkedList<Taulell> nodes = null;
		
		int mes_infinit = Integer.MAX_VALUE;
		int menys_infinit = Integer.MIN_VALUE;
		int valor_retornar = 0;
		Taulell node_retornar = null;
		
		if(finalPartida(node.getFitxes1(), node.getFitxes2(), node.getExtremEsquerra(), node.getExtremDreta())) {
			if(node.getFitxes1().size() == 0) {
				valor_retornar = menys_infinit;
			}else if(node.getFitxes2().size() == 0){
				valor_retornar = mes_infinit;
			}
		}else if(nivell == NIVELL_MAX_ALFABETA){
			valor_retornar = h.heuristica(node);
        }else {
        	if(nivell % 2 == 0) {
        		valor_retornar = menys_infinit;
        		nodes = generaFills(node, node.getFitxes1(), nivell);
        	}else {
        		valor_retornar = mes_infinit;
        		nodes = generaFills(node, node.getFitxes2(), nivell);
        	}
    		int i = 0;
        	while(i < nodes.size() && alfa<beta) {
        		Taulell F = nodes.get(i);
                obj = podaAlfaBeta(F.clone(), nivell+1, alfa, beta);
                if(nivell % 2 == 0) {
                	if((int) obj[0] > alfa){
                		alfa = (int) obj[0];
                	}
            	}else {
            		if((int) obj[0] < beta){
            			beta = (int) obj[0];
                	}
            	}
                node_retornar = (Taulell) F.clone();
                i++;
        	}
        	if(nivell % 2 == 0) {
        		valor_retornar = alfa;
    		}else {
    			valor_retornar = beta;
    		}
        }
		obj[0] = valor_retornar;
		obj[1] = node_retornar;
		return obj;
	}
	
	
	/**
	 * Mètode que permet comprovar si el jugador està bloquejat, és a dir, que no pot realitzar cap jugada
	 * @param fitxes - del tipus LinkedList<Fitxa> que conté les fitxes del jugador 
	 * @param extrem_esquerra - del tipus int que és l'extrem esquerra actual
	 * @param extrem_dreta - del tipus int que és l'extrem dret actual
	 * @return bloquejat - del tipus booleà que indica si el jugador està bloquejat o no
	 * */
	public boolean jugadorBloquejat(LinkedList<Fitxa> fitxes, int extrem_esquerra, int extrem_dreta) {
		boolean bloquejat = true;
		int i=0;
		while(bloquejat && i<fitxes.size()) {
			if(fitxes.get(i).getNum1() == extrem_esquerra || 
			fitxes.get(i).getNum1() == extrem_dreta ||
			fitxes.get(i).getNum2() == extrem_esquerra || 
			fitxes.get(i).getNum2() == extrem_dreta) {
				bloquejat = false;
			}
			i++;
		}
		return bloquejat;
	}
	
	/**
	 * Mètode que comprova si la fitxa que s'ha seleccionat es pot tirar
	 * @param fitxa - del tipus Fitxa que és la fitxa que es vol tirar
	 * @param extrem_esquerra - del tipus int que és l'extrem esquerra actual
	 * @param extrem_dreta - del tipus int que és l'extrem dret actual
	 * @return correcte - del tipus booleà que és cert si la fitxa que es vol tirar es pot tirar
	 * */
	public boolean fitxaCorrecta(Fitxa fitxa, int extrem_esquerra, int extrem_dreta) {
		boolean correcte = false;
		if(fitxa.getNum1() == extrem_esquerra || 
		fitxa.getNum1() == extrem_dreta ||
		fitxa.getNum2() == extrem_esquerra || 
		fitxa.getNum2() == extrem_dreta) {
				correcte = true;
		}
		return correcte;
	}
		
	/**
	 * Mètode que comprova si la partida s'ha acabat
	 * @param fitxes1 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador 
	 * @param fitxes2 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador rival 
	 * @param extrem_esquerra - del tipus int que és l'extrem esquerra actual
	 * @param extrem_dreta - del tipus int que és l'extrem dret actual
	 * @return fi - del tipus booleà que és cert si s'ha acabat la partida
	 */
	public boolean finalPartida(LinkedList<Fitxa> fitxes1, LinkedList<Fitxa> fitxes2, int extrem_esquerra, int extrem_dreta) {
		boolean fi = true;
		int i=0;
		if(fitxes1.size() != 0 && fitxes2.size() != 0) {
			while(fi && i<fitxes1.size()) {
				if(fitxes1.get(i).getNum1() == extrem_esquerra || 
				fitxes1.get(i).getNum1() == extrem_dreta ||
				fitxes1.get(i).getNum2() == extrem_esquerra || 
				fitxes1.get(i).getNum2() == extrem_dreta) {
					fi = false;
				}
				i++;
			}
			if(fi == true) {
				i=0;
				while(fi && i<fitxes2.size()) {
					if(fitxes2.get(i).getNum1() == extrem_esquerra || 
					fitxes2.get(i).getNum1() == extrem_dreta ||
					fitxes2.get(i).getNum2() == extrem_esquerra || 
					fitxes2.get(i).getNum2() == extrem_dreta) {
						fi = false;
					}
					i++;
				}
			}
		}
		return fi;
	}

}
