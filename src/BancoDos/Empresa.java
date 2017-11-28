package BancoDos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.TreeMap;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Empresa extends Cuenta {
	
	public static final float COMISION = 0.001f;
	public static final float MAXIMOCOMISION = 6f;
	private String nombre;
	private String cif;
	private boolean local;
	
	public Empresa() {
		super();
		nombre = "";
		cif = "";
		local = false;
	}

	public Empresa(TreeMap<String, Cliente> clientes, String ncc, float saldo,
					String nombre, String cif, boolean local) 
	{
		super(clientes, ncc, saldo);
		this.nombre = nombre;
		this.cif = cif;
		this.local = local;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	@Override
	public float maximoNegativo() {
		float maximo = 0;
		
		maximo = totalAvales() * 2;
		
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
		nombre = Teclado.leerString("nombre ");
		cif = Teclado.leerString("cif ");
		local = Teclado.leerString("Local (S/N)").equals("S");
		
	}
	
	public void verDatos()
	{
		super.verDatos();
		Pantalla.escribirString(nombre);
		Pantalla.escribirSaltoLinea();
		Pantalla.escribirString(cif);
		Pantalla.escribirSaltoLinea();
		Pantalla.escribirString(local ? "Con local":"Sin local");
		Pantalla.escribirSaltoLinea();
	}
	
	
	// leer y escribir en fichero
	public void escribirFichero (DataOutputStream fichero) throws IOException
	{
		super.escribirFichero(fichero);
		fichero.writeBytes(nombre+"\n");
		fichero.writeBytes(cif+"\n");
		fichero.writeBoolean(local);
		
	}
	
	public void leerFichero (DataInputStream fichero) throws IOException
	{
		super.leerFichero(fichero);
		nombre = fichero.readLine();
		cif = fichero.readLine();
		local = fichero.readBoolean();
	}

}
