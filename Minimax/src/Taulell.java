import java.util.LinkedList;
/**
 * Classe per gestionar el taulell de joc -> una situació de joc (estat)
 * 
 * @author Cristina Llort
 *
 */

public class Taulell {
	
	private LinkedList<Fitxa> fitxes1;
	private LinkedList<Fitxa> fitxes2;
	private LinkedList<Fitxa> fitxes_usades;
	private int extrem_esquerra;
	private int extrem_dreta;
	private Fitxa fitxa;

	//CONSTRUCTOR
	public Taulell(LinkedList<Fitxa> fitxes1, LinkedList<Fitxa> fitxes2, LinkedList<Fitxa> fitxes_usades,  int extrem_esquerra, int extrem_dreta, Fitxa fitxa) {
		this.fitxes1 = fitxes1;
		this.fitxes2 = fitxes2;
		this.fitxes_usades = fitxes_usades;
		this.extrem_esquerra = extrem_esquerra;
		this.extrem_dreta = extrem_dreta;
		this.fitxa = fitxa;
	}
	
	//GETTERS I SETTERS
	public LinkedList<Fitxa> getFitxes1() {
        return fitxes1;
    }

	public LinkedList<Fitxa> getFitxes2() {
        return fitxes2;
    }
	
	public LinkedList<Fitxa> getFitxesUsades() {
        return fitxes_usades;
    }
	
	public int getExtremEsquerra() {
        return extrem_esquerra;
    }
	
	public int getExtremDreta() {
        return extrem_dreta;
    }
	
	public void setExtremEsquerra(int extrem_esquerra) {
		this.extrem_esquerra = extrem_esquerra;
	}
	
	public void setExtremDreta(int extrem_dreta) {
		this.extrem_dreta = extrem_dreta;
	}
	
	public Fitxa getFitxa() {
        return fitxa;
    }
	
	public void setFitxa(Fitxa fitxa) {
		this.fitxa = fitxa;
    }
    
	/**
	 * Mètode que permet clonar un taulell (una situació de joc)
	 * @return taulell - del tipus Taulell que és el nou taulell
	 */
	public Taulell clone(){
		Taulell taulell = new Taulell(fitxes1, fitxes2, fitxes_usades, extrem_esquerra, extrem_dreta, fitxa);
		return taulell;
	}
	
	/**
	 * Mètode que permet clonar una llista de fitxes
	 * @return fitxes - que són les noves fitxes clonades
	 */
	public LinkedList<Fitxa> cloneFitxes(LinkedList<Fitxa> f){
		LinkedList<Fitxa> fitxes = new LinkedList<Fitxa>(f);
		return fitxes;
	}
	
	public String toString() {
        return "\nFITXA: " + fitxa 
        + "	FITXES USADES: " + fitxes_usades;
    }


}
