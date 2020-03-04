import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Programa principal per controlar el joc
 * 
 * @author Cristina Llort
 *
 */

public class Main {

	int index;
	static int torn;
	static int extrem_esquerra;
	static int extrem_dreta;
	static int algorisme1, algorisme2;
	static int heuristica1, heuristica2;
	static int mode;
	static LinkedList<Fitxa> fitxes = new LinkedList<Fitxa>();
	static LinkedList<Fitxa> fitxes1 = new LinkedList<Fitxa>();
	static LinkedList<Fitxa> fitxes2 = new LinkedList<Fitxa>();
	static LinkedList<Fitxa> fitxes_usades = new LinkedList<Fitxa>();
	//static int guanyador1, guanyador2, empat;
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner (System.in);
		
		//MODE DE JOC
		System.out.println("Selecciona el mode de joc: "
						+ "\n1: Màquina - màquina"
						+ "\n2: Màquina - humà");
		mode = scanner.nextInt();
		if(mode == 1) {
			System.out.println("JUGADOR 1 = MÀQUINA 1"
					+ "\nJUGADOR 2 = MÀQUINA 2\n");
		}else {
			System.out.println("JUGADOR 1 = MÀQUINA"
					+ "\nJUGADOR 2 = HUMÀ\n");
		}
		
		//ALGORISME
		System.out.println("Selecciona algorisme:"
							+ "\n1: Minimax"
							+ "\n2: Poda alfa-beta");
		algorisme1 = scanner.nextInt();
		if(mode == 1) {
			System.out.println("Selecciona algorisme per a la segona màquina: "
							+ "\n1: Minimax"
							+ "\n2: Poda alfa-beta");
			algorisme2 = scanner.nextInt();
		}
				
		//HEURÍSTICA
		System.out.println("Selecciona heurística: "
							+ "\n1: Diferència puntuació"
							+ "\n2: Fitxa més repetida al taulell"
							+ "\n3: Diferència nombre fitxes");
		heuristica1 = scanner.nextInt();
		if(mode == 1) {
			System.out.println("Selecciona heurística per a la segona màquina: "
					+ "\n1: Diferència puntuació"
					+ "\n2: Fitxa més repetida al taulell"
					+ "\n3: Diferència nombre fitxes");
			heuristica2 = scanner.nextInt();
		}
		
		//for(int z = 0; z < 10; z++) {
		
		//CREAR I REPARTIR FITXES
		for(int i=0; i<=6; i++) {
			for(int j=i; j<=6; j++) {
				Fitxa fitxa = new Fitxa(i, j);
				fitxes.add(fitxa);
			}
		}
		repartirFitxes();
					
		String guanyador;
		Maquina maquina1 = new Maquina(heuristica1, algorisme1);
		if(mode == 1) {
			Maquina maquina2 = new Maquina(heuristica2, algorisme2);
			guanyador = jugarDomino(maquina1, maquina2);
		}else {
			Huma huma = new Huma();
			guanyador = jugarDomino(maquina1, huma);
		}
		System.out.println("GUANYADOR: " +guanyador);
		//System.out.println("PARTIDA "+z+ " GUANYADOR: " +guanyador);
		
		/*}
		System.out.println("JUGADOR 1: " +guanyador1+
				"\nJUGADOR 2: " +guanyador2+
				"\nEMPAT: " +empat);*/

	}
	
	
	
	/**
	 * Mètode que gestiona el joc del domino
	 * @param jugador1 - del tipus Jugador que implementa el primer jugador
	 * @param jugador2 - del tipus Jugador que implementa el primer jugador
	 * @return guanyador - del tipus String que indica qui ha guanyat el joc
	 */
	public static String jugarDomino(Jugador jugador1, Jugador jugador2) {
		String guanyador = null;
		primerTorn();
		
		int n1 = 0, n2 = 0;
		long inici, fi, temps_partida, temps_total1 = 0, temps_total2 = 0, temps1, temps2, inici1, inici2, fi1, fi2, primera_jugada = 0;
		double mitjana1 = 0, mitjana2 = 0;
		inici = System.nanoTime();

		while(!partidaBloquejada() && fitxes1.size() != 0 && fitxes2.size() != 0) {
			Fitxa fitxa;
			int extrem;
			if(mode == 2) {
				if(torn == 1) {
					inici1 = System.nanoTime();
					System.out.println("\nTORN JUGADOR 1");
					System.out.println("MÀQUINA: " +fitxes1);
					System.out.println("HUMÀ: " +fitxes2);
					fitxa = jugador1.realitzarJugada(fitxes1, fitxes2, fitxes_usades, extrem_esquerra, extrem_dreta);
					if(fitxa != null)
						eliminaFitxa(fitxa, fitxes1);
					torn = 2;
					n1++;
					fi1 = System.nanoTime();
					temps1 = (fi1 - inici1)/1000000;
					temps_total1 = temps_total1 + temps1;
					System.out.println("TEMPS ITERACIÓ: " +temps1+ " ms");
				}else {
					inici2 = System.nanoTime();
					System.out.println("\nTORN JUGADOR 2");
					System.out.println("MÀQUINA: " +fitxes1);
					System.out.println("HUMÀ: " +fitxes2);
					fitxa = jugador2.realitzarJugada(fitxes1, fitxes2, fitxes_usades, extrem_esquerra, extrem_dreta);
					if(fitxa != null)
						eliminaFitxa(fitxa, fitxes2);
					torn = 1;
					n2++;
					fi2 = System.nanoTime();
					temps2 = (fi2 - inici2)/1000000;
					temps_total2 = temps_total2 + temps2;
					System.out.println("TEMPS ITERACIÓ: " +temps2+ " ms");
				}
			}else {
				if(torn == 1) {
					inici1 = System.nanoTime();
					System.out.println("\nTORN JUGADOR 1");
					System.out.println("MÀQUINA 1: " +fitxes1);
					System.out.println("MÀQUINA 2: " +fitxes2);
					fitxa = jugador1.realitzarJugada(fitxes1, fitxes2, fitxes_usades, extrem_esquerra, extrem_dreta);
					if(fitxa != null)
						eliminaFitxa(fitxa, fitxes1);
					torn = 2;
					n1++;
					fi1 = System.nanoTime();
					temps1 = (fi1 - inici1)/1000000;
					temps_total1 = temps_total1 + temps1;
					System.out.println("TEMPS ITERACIÓ: " +temps1+ " ms");
					if(fitxes_usades.size() == 1) {
						primera_jugada = temps1;
					}
				}else {
					inici2 = System.nanoTime();
					System.out.println("\nTORN JUGADOR 2");
					System.out.println("MÀQUINA 1: " +fitxes1);
					System.out.println("MÀQUINA 2: " +fitxes2);
					fitxa = jugador2.realitzarJugada(fitxes2, fitxes1, fitxes_usades, extrem_esquerra, extrem_dreta);
					if(fitxa != null)
						eliminaFitxa(fitxa, fitxes2);
					torn = 1;
					n2++;
					fi2 = System.nanoTime();
					temps2 = (fi2 - inici2)/1000000;
					temps_total2 = temps_total2 + temps2;
					System.out.println("TEMPS ITERACIÓ: " +temps2+ " ms");
					if(fitxes_usades.size() == 1) {
						primera_jugada = temps2;
					}
				}
			}
			if(fitxa != null) {
				System.out.println("FITXA TIRADA: " +fitxa);
				extrem = fitxa.getExtrem();
				afegirFitxaTaulell(extrem, fitxa);
			}else {
				System.out.println("El jugador no ha pogut realitzar cap jugada");
			}
			System.out.println("TAULELL " + fitxes_usades);
		}
		
		fi = System.nanoTime();
		temps_partida = (fi - inici)/1000000;
		
		
		mitjana1 = temps_total1 / n1;
		mitjana2 = temps_total2 / n2;
		
		System.out.println("\nTEMPS TOTAL PARTIDA: "+temps_partida+" ms");
		System.out.println("\nTEMPS PRIMERA ITERACIÓ: "+primera_jugada+" ms");
		System.out.println("\nTEMPS TOTAL JUGADOR 1: "+temps_total1+" ms "
				+ " MITJANA TEMPS PER ITERACIÓ: "+mitjana1+ " ms/iteració");
		System.out.println("\nTEMPS TOTAL JUGADOR 2: "+temps_total2+" ms "
				+ "  MITJANA TEMPS PER ITERACIÓ: "+mitjana2+ " ms/iteració");
	
		if(fitxes1.size() == 0) {
			if(mode == 1) {
				guanyador = "MÀQUINA 1";
				//guanyador1++;
			}
			else {
				guanyador = "MÀQUINA";
			}
		}else if(fitxes2.size() == 0){
			if(mode == 1) {
				guanyador = "MÀQUINA 2";
				//guanyador2++;
			}
			else {
				guanyador = "HUMÀ";
			}
		}else {
			guanyador = recomptePuntuacio();
		}
		return guanyador;
	}
	
	
	/**
	 * Mètode que reparteix les fitxes entre els jugadors aleatòriament
	 */
	public static void repartirFitxes() {
		Random rand = new Random();
		int index;
		while(fitxes.size() != 0){
			index = rand.nextInt(fitxes.size());
			fitxes1.add(fitxes.get(index));
			fitxes.remove(fitxes.get(index));
			index = rand.nextInt(fitxes.size());
			fitxes2.add(fitxes.get(index));
			fitxes.remove(fitxes.get(index));
		}
		System.out.println("REPARTIMENT DE FITXES");
		System.out.println("JUGADOR 1 " +fitxes1);
		System.out.println("JUGADOR 2 " +fitxes2);
		System.out.println("\n");
	}
		
	
	 /**
	 * Mètode que realitza el primer torn, la primera jugada
	 */
	public static void primerTorn() {
		int i=0;
		boolean trobat = false;
		while(trobat == false && i<14) {
			if(fitxes1.get(i).getNum1() == 6 && fitxes1.get(i).getNum2() == 6) {
				trobat = true;
			}
			i++;
		}
		if(trobat == true) {
			fitxes_usades.add(fitxes1.get(i-1));
			fitxes1.remove(i-1);
			torn = 2;
			System.out.println("PRIMER TORN -> JUGADOR 1");
		}else {
			i=0;
			while(trobat == false && i<14) {
				if(fitxes2.get(i).getNum1() == 6 && fitxes2.get(i).getNum2() == 6) {
					trobat = true;
				}
				i++;
			}
			fitxes_usades.add(fitxes2.get(i-1));
			fitxes2.remove(i-1);
			torn = 1;
			System.out.println("PRIMER TORN -> JUGADOR 2");
		}
		extrem_esquerra = 6;
		extrem_dreta = 6;
		System.out.println("TAULELL " +fitxes_usades);
	}
	
	
	/**
	 * Mètode per eliminar una fitxa d'una llista de fitxes
	 * @param fitxa - del tipus Fitxa que és la fitxa que es vol afegir
	 * @param fitxes - del tipus LinkedList<Fitxa> que és la llista de fitxes de la que es vol eliminar la fitxa
	 */
	public static void eliminaFitxa(Fitxa fitxa, LinkedList<Fitxa> fitxes) {
		int i = 0;
		boolean trobat = false;
		while (trobat == false && i < fitxes.size()) {
			if (fitxes.get(i).getNum1() == fitxa.getNum1() && fitxes.get(i).getNum2() == fitxa.getNum2() ||
				fitxes.get(i).getNum2() == fitxa.getNum1() && fitxes.get(i).getNum1() == fitxa.getNum2()) {
				fitxes.remove(i);
				trobat = true;
			}
			i++;
		}
	}
	
	/**
	 * Mètode que comprova si la partida s'ha quedat bloquejada -> final
	 * @return bloquejat - del tipus booleà que indica cert si la partida s'ha quedat bloquejada
	 */
	public static boolean partidaBloquejada() {
		boolean bloquejat = true;
		int i=0;
		while(bloquejat && i<fitxes1.size()) {
			if(fitxes1.get(i).getNum1() == extrem_esquerra || 
			fitxes1.get(i).getNum1() == extrem_dreta ||
			fitxes1.get(i).getNum2() == extrem_esquerra || 
			fitxes1.get(i).getNum2() == extrem_dreta) {
				bloquejat = false;
			}
			i++;
		}
		if(bloquejat == true) {
			i=0;
			while(bloquejat && i<fitxes2.size()) {
				if(fitxes2.get(i).getNum1() == extrem_esquerra || 
				fitxes2.get(i).getNum1() == extrem_dreta ||
				fitxes2.get(i).getNum2() == extrem_esquerra || 
				fitxes2.get(i).getNum2() == extrem_dreta) {
					bloquejat = false;
				}
				i++;
			}
		}
		return bloquejat;
	}
	
	
	/**
	 * Mètode que afegeix una nova fitxa al taulell
	 * @param extrem - del tipus int que indica en quin dels dos extrems s'afegirà la nova fitxa
	 * @param fitxa - del tipus Fitxa que és la fitxa que afegirm
	 */
	public static void afegirFitxaTaulell(int extrem, Fitxa fitxa) {

		int num1, num2;
		//EXTREM ESQUERRA
		if (extrem == 1) {
			num1 = fitxes_usades.get(0).getNum1();
			if (fitxa.getNum2() != num1) {
				num1 = fitxa.getNum1();
				num2 = fitxa.getNum2();
				fitxa.setNum1(num2);
				fitxa.setNum2(num1);
			}
			fitxes_usades.addFirst(fitxa);
			extrem_esquerra = fitxes_usades.get(0).getNum1();
		//EXTREM DRETA
		} else {
			num2 = fitxes_usades.get(fitxes_usades.size()-1).getNum2();
			if (fitxa.getNum1() != num2) {
				num1 = fitxa.getNum1();
				num2 = fitxa.getNum2();
				fitxa.setNum1(num2);
				fitxa.setNum2(num1);
			}
			fitxes_usades.addLast(fitxa);
			extrem_dreta = fitxes_usades.get(fitxes_usades.size()-1).getNum2();
		}
	}
	
	/**
	 * Mètode que en cas de que la partida s'hagi quedat bloquejada fa un recompte 
	 * dels punts de les fitxes que queden a cada jugador per a conéixer el guanyador
	 * @return guanyador - del tipus String que indica qui ha estat el guanyador
	 */
	private static String recomptePuntuacio() {
		String guanyador = null;
		int punts_fitxes1 = 0, punts_fitxes2 = 0;
		System.out.println("\nPARTIDA BLOQUEJADA -> RECOMPTE PUNTUACIÓ");
		
		
		for(int i=0; i<fitxes1.size(); i++) {
			punts_fitxes1 = punts_fitxes1 + fitxes1.get(i).getNum1() + fitxes1.get(i).getNum2();
		}
		for(int i=0; i<fitxes2.size(); i++) {
			punts_fitxes2 = punts_fitxes2 + fitxes2.get(i).getNum1() + fitxes2.get(i).getNum2();
		}
		System.out.println("JUGADOR 1: " +fitxes1+ " TOTAL -> " +punts_fitxes1);
		System.out.println("JUGADOR 2: " +fitxes2+ " TOTAL -> " +punts_fitxes2);
		
		
		if(punts_fitxes1 < punts_fitxes2) {
			if(mode == 1) {
				guanyador = "MÀQUINA 1";
				//guanyador1++;
			}
			else {
				guanyador = "MÀQUINA";
			}
		}else if(punts_fitxes1 > punts_fitxes2){
			if(mode == 1) {
				guanyador = "MÀQUINA 2";
				//guanyador2++;
			}
			else {
				guanyador = "HUMÀ";
			}
		}else {
			guanyador = "EMPAT";
			//empat++;
		}
		return guanyador;
	}

}
