package BancoDos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.TreeMap;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Personal extends Cuenta {
	
	public static final float COMISION = 0.002f;
	public static final float MAXIMOCOMISION = 4f;
	private boolean credito;
	
	
	public Personal() {
		super();
		// TODO Auto-generated constructor stub
		credito = false;
	}

	public Personal(TreeMap<String, Cliente> clientes, String ncc, float saldo,boolean credito) 
	{
		super(clientes, ncc, saldo);
		this.credito = credito;
		// TODO Auto-generated constructor stub
	}

	
	public boolean isCredito() {
		return credito;
	}

	public void setCredito(boolean credito) {
		this.credito = credito;
	}

	@Override
	public float maximoNegativo() {
		float maximo = 0;
		
		maximo = totalAvales() / 2; //usamos la función, tal cual, pues es del super
		
		//maximo = super.totalAvales() / 2 --> es equivalente
		
		return -maximo;
	}

	@Override
	public float calcularComision(float cantidad) {
		float comision = 0;
		
		if (cantidad < 0)
			cantidad = 0;
		
		comision = cantidad * COMISION;
		
		if (comision > MAXIMOCOMISION)	
			comision = MAXIMOCOMISION;
		
		return comision;
	}
	
	public void leerDatos()
	{
		super.leerDatos();
		
		credito = Teclado.leerString("Tarjeta (S/N)").equalsIgnoreCase("S");
		
	}
	
	public void verDatos()
	{
		super.verDatos();
		
		Pantalla.escribirString(credito ? "Con tarjeta":"Sin tarjeta"); //if chulo
		Pantalla.escribirSaltoLinea();
	}
	
	
	// leer y escribir en fichero
	public void escribirFichero (DataOutputStream fichero) throws IOException
	{
		super.escribirFichero(fichero);
		
		fichero.writeBoolean(credito);
		
	}
	
	public void leerFichero (DataInputStream fichero) throws IOException
	{
		super.leerFichero(fichero);
		
		credito = fichero.readBoolean();
	}
	
	

}
