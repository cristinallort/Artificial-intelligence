import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe per gestionar el joc de l'humà
 * 
 * @author Cristina Llort
 *
 */

public class Huma implements Jugador{
	

	//CONSTRUCTOR
	public Huma() {}

	/**
	 * Mètode que permet realitzar una nova jugada a l'humà
	 * @param fitxes1 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador rival
	 * @param fitxes2 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador 
	 * @param fitxes_usades - del tipus LinkedList<Fitxa> que conté les fitxes ja col·locades al taulell
	 * @param extrem_esquerra - del tipus int que és l'extrem esquerra actual
	 * @param extrem_dreta - del tipus int que és l'extrem dret actual
	 * @return fitxa - del tipus Fitxa que és la fitxa que vol tirar el jugador al taulell
	 */
	public Fitxa realitzarJugada(LinkedList<Fitxa> fitxes1, LinkedList<Fitxa> fitxes2, LinkedList<Fitxa> fitxes_usades, int extrem_esquerra, int extrem_dreta) {
		Fitxa fitxa = null;
		Scanner scanner = new Scanner (System.in);
		boolean fitxa_incorrecta = true;
		
		System.out.println("Aquest és el taulell de joc actual:");
		System.out.println(fitxes_usades);
		
		if(!finalPartida(fitxes1, fitxes2, extrem_dreta, extrem_dreta) && !jugadorBloquejat(fitxes2, extrem_esquerra, extrem_dreta)) {
			System.out.println("Escolleix quina de les teves fitxes vols tirar:");
			while(fitxa_incorrecta) {
				for(int i=1; i<fitxes2.size(); i++) {
					System.out.println(i+ "- " +fitxes2.get(i));
				}
				int index = scanner.nextInt();
				if(fitxaCorrecta(fitxes2.get(index), extrem_esquerra, extrem_dreta)) {
					fitxa = fitxes2.get(index);
					fitxa_incorrecta = false;
				}else {
					System.out.println("Fitxa escollida incorrecta. Torna a intentar-ho.");
				}
			}
			escollirExtrem(fitxa, extrem_esquerra, extrem_dreta);
		}
		return fitxa;
	}

	/**
	 * Mètode que permet comprovar si el jugador està bloquejat, és a dir, que no pot realitzar cap jugada
	 * @param fitxes2 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador 
	 * @param extrem_esquerra - del tipus int que és l'extrem esquerra actual
	 * @param extrem_dreta - del tipus int que és l'extrem dret actual
	 * @return bloquejat - del tipus booleà que indica si el jugador està bloquejat o no
	 * */
	public boolean jugadorBloquejat(LinkedList<Fitxa> fitxes2, int extrem_esquerra, int extrem_dreta) {
		boolean bloquejat = true;
		int i=0;
		while(bloquejat && i<fitxes2.size()) {
			if(fitxes2.get(i).getNum1() == extrem_esquerra || 
			fitxes2.get(i).getNum1() == extrem_dreta ||
			fitxes2.get(i).getNum2() == extrem_esquerra || 
			fitxes2.get(i).getNum2() == extrem_dreta) {
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
	 * Mètode que indica en quin extrem es tira la fitxa i que permet escollir en cas que es pugui tirar en els dos extrems
	 * @param fitxa - del tipus Fitxa que és la fitxa que es vol col·locar
	 * @param extrem_esquerra - del tipus int que és l'extrem esquerra actual
	 * @param extrem_dreta - del tipus int que és l'extrem dret actual
	 * */
	public void escollirExtrem(Fitxa fitxa, int extrem_esquerra, int extrem_dreta) {
		Scanner scanner = new Scanner (System.in);
		
		if(fitxa.getNum1() == extrem_esquerra || 
		fitxa.getNum2() == extrem_esquerra) {
			if(fitxa.getNum1() == extrem_dreta || 
			fitxa.getNum2() == extrem_dreta) {
				System.out.println("Tria en quin extrem vols col·locar la fitxa: "
						+ "\n1. Extrem esquerra"
						+ "\n2. Extrem dret");
				int extrem = scanner.nextInt();	
				fitxa.setExtrem(extrem);
			}else {
				fitxa.setExtrem(1);
			}
		}else if(fitxa.getNum1() == extrem_dreta || 
		fitxa.getNum2() == extrem_dreta) {
			fitxa.setExtrem(2);
		}
	}

	
	/**
	 * Mètode que comprova si la partida s'ha acabat
	 * @param fitxes1 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador rival
	 * @param fitxes2 - del tipus LinkedList<Fitxa> que conté les fitxes del jugador  
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
