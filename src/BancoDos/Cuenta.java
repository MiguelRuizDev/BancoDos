package BancoDos;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import daw.com.Pantalla;
import daw.com.Teclado;

public abstract class Cuenta {
	
	//DEFINIMOS CONTENEDORES AL PPIO, NUMÉRICOS AL FINAL, PARA FACILITAR LECTURA
	private TreeMap <String,Cliente> clientes; //definimos contenedor
	private String ncc;
	private float saldo;
	
	public Cuenta ()
	{
		this (null,"",0); //si usamos el constructor por parámetros, inicializamos a null el contenedor
		clientes = new TreeMap(); //inicializamos contenedor correctamente
	}
	
	public Cuenta(TreeMap<String, Cliente> clientes, String ncc, float saldo) 
	{
		this.clientes = clientes;
		this.ncc = ncc;
		this.saldo = saldo;
	}

	public TreeMap<String, Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(TreeMap<String, Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getNcc() {
		return ncc;
	}

	public void setNcc(String ncc) {
		this.ncc = ncc;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	
	public float totalAvales()//leyendo el enunciado concluimos que puede ser util (hacer el UML con métodos!!!)
	{
		float total = 0;
		
		//Collection<Cliente> cls = clientes.values(); 
		
		//recorremos la coleccion de clientes y sumamos sus avales
		
		for (Cliente c : clientes.values()) //OJO AL .values() para convertirlo en "recorrible"
			total += c.getAval();

		return total;
	}
	
	public void ingresar (float cantidad)
	{
		if (cantidad < 0 )
			cantidad = 0;
		
		saldo += cantidad;
	}
	
	public  boolean retirar (float cantidad) //salida booleana cuando hay posibilidad de fallo por inexistencia o reglas del juego
	{
		boolean exito = true;
		
		if (cantidad < 0)
			cantidad = 0;
		
		if (saldo - cantidad >= maximoNegativo())//en este caso, posibilidad de fallo por reglas de juego
			saldo -= cantidad;
		else
			exito = false;
		
		return exito;
	}
	
	public  boolean transferir (float cantidad, Cuenta cuentaDestino)
	{
		boolean exito = true;
		float comision ;
		

		if (retirar (cantidad))
		{
			comision = calcularComision(cantidad);
			saldo -= comision;
			cuentaDestino.ingresar(cantidad);
		}
	
		else
			exito = false;
		
		return exito;
	}
	
	public abstract float maximoNegativo();
	public abstract float calcularComision(float cantidad);
	
	// leer y escribir en fichero
	public void escribirFichero (DataOutputStream fichero) throws IOException
	{
		
		// Escribir cuantos clientes por cuenta OBLIGATORIO SI TENEMOS VARIABLES CONTENEDORES
		fichero.writeInt(clientes.size());
		
		// Escribir nif de los clientes de la cuenta
		for (Cliente c : clientes.values())
			fichero.writeBytes(c.getNif()+"\n"); //solo almacenamos la clave
		
		fichero.writeBytes(ncc+"\n");
		
		fichero.writeFloat(saldo);
			
	}
	
	public void leerFichero (DataInputStream fichero) throws IOException
	{
		int cuantos;
		
		// vaciar contendor de clientes SIEMPRE VACIAMOS PARA VOLVER A RELLENAR
		clientes.clear();
		
		// Leer numero de clientes
		cuantos = fichero.readInt();
		
		// Leer clientes
		for (int i = 0; i < cuantos; i++) //ya sabemos cuantas veces se tiene que ejecutar el bucle for
		{
			Cliente c = new Cliente(); //creamos
			c.setNif(fichero.readLine()); //rellenamos (solo con la clave, pues es lo único que hemos escrito anteriormente)
			clientes.put(c.getNif(), c); //añadimos al contenedor que estaba vacío tras el .clear()
		}
		
		ncc = fichero.readLine();
		
		saldo = fichero.readFloat();
		
	}

	public void leerDatos () //no incluye los del contenedor de clientes, pues clientes tiene su propio leerDatos
	{
		ncc = Teclado.leerString("ncc");
		
		saldo = Teclado.leerFloat("saldo");
		
	}
	
	public void verDatos()
	{
		Pantalla.escribirString(toString());
		Pantalla.escribirSaltoLinea();
		
		for (Cliente c : clientes.values())
			Pantalla.escribirString(c.toString()+"\n");
		
	}

	@Override
	public String toString() {
		return "\nCuenta [ncc=" + ncc + ", saldo=" + saldo + "]";
	}
	

}
