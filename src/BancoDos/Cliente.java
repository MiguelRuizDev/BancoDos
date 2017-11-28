package BancoDos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import daw.com.Pantalla;
import daw.com.Teclado;

public class Cliente {
	
	private String nif;
	private String nombre;
	private String telefono;
	private float aval; //variables distintas a String al final, para facilitar lectura/escritura en fichero
	
	public Cliente ()
	{
		this ("","","",0);
	}
	
	public Cliente(String nif, String nombre, String telefono, float aval) 
	{
		this.nif = nif;
		this.nombre = nombre;
		this.telefono = telefono;
		this.aval = aval;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public float getAval() {
		return aval;
	}

	public void setAval(float aval) {
		
		if (aval < 0) //comprobaciones en datos numéricos
			aval = 0;
		
		this.aval = aval;
	}
	
	public void leerDni() //función específica para leer la clave
	{
		this.nif = Teclado.leerString("nif");
	}
	
	
	public void leerRestoDatos()
	{
		
		this.nombre = Teclado.leerString("nombre");
		this.telefono = Teclado.leerString("telefono");
		this.aval = Teclado.leerFloat("aval");
		
	}
	
	public void leerDatos()
	{
		leerDni();
		leerRestoDatos();
	}
	
	
	public void verDatos()
	
	{
		Pantalla.escribirString(toString());
	}

	@Override
	public String toString() {
		return "Cliente [nif=" + nif + ", nombre=" + nombre + ", telefono=" + telefono + ", aval=" + aval + "]";
	}
	
	// Guardar y leer datos del fichero
	public void escribirFichero (DataOutputStream fichero) throws IOException
	{
		fichero.writeBytes(nif + "\n"); //los saltos de línea son necesarios para la posterior lectura
		fichero.writeBytes(nombre + "\n");
		fichero.writeBytes(telefono + "\n");
		fichero.writeFloat(aval);
	}
	
	public void leerFichero (DataInputStream fichero) throws IOException
	{
		nif = fichero.readLine(); //lee caracteres hasta que encuentra un salto de línea
		nombre = fichero.readLine();
		telefono = fichero.readLine();
		aval = fichero.readFloat(); //lee el float y para
	}
	

}
