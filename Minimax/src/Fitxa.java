import java.util.LinkedList;

/**
 * Classe per gestionar les fitxes
 * 
 * @author Cristina Llort
 *
 */

public class Fitxa {
	private int num1;
	private int num2;
	private int extrem;
	
	//CONSTRUCTOR
		public Fitxa(int num1, int num2) {
			this.num1 = num1;
			this.num2 = num2;
		}
		
	// GETTERS I SETTERS
	public int getNum1() {
		return num1;
	}

	public int getNum2() {
		return num2;
	}
	
	public int getExtrem() {
		return extrem;
	}
	
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	
	public void setExtrem(int extrem) {
		this.extrem = extrem;
	}
	
	public String toString() {
        return num1 + " " + num2;
    }
	

}
