package BancoDos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;



public class Banco {
	
	private TreeMap <String,Cuenta> cuentas; //cuentas genéricas; CONTENEDORES SIEMPRE GENÉRICOS
	private TreeMap <String,Cliente> clientes;
	
	public Banco() 
	{
		cuentas = new TreeMap<String,Cuenta>();
		clientes = new TreeMap<String,Cliente>();
	}
	
	//***********************************************
	
	public Cuenta getCuenta (String ncc)
	{
		return cuentas.get(ncc);
	}
	
	public Cliente getCliente (String nif)
	{
		return clientes.get(nif);
	}
	
	public Collection <Cuenta> getCuentas() {
		return cuentas.values();
	}

	public Collection <Cliente> getClientes() {
		return clientes.values();
	}
	
	//***********************************************

	public boolean AddCuenta (Cuenta cuenta)
	{
		boolean exito = true;
		
		if (!cuentas.containsKey(cuenta.getNcc()))
			cuentas.put(cuenta.getNcc(),cuenta);
		else
			exito = false;
		
		return exito;
		
	}
	
	public boolean AddCliente (Cliente cliente)
	{
		boolean exito = true;
		
		if (!clientes.containsKey(cliente.getNif()))
			clientes.put(cliente.getNif(),cliente);
		else
			exito = false;
		
		return exito;
	}
	
	public boolean eliminarCuenta (Cuenta cuenta) //puede hacerse con un if y containsKey()
	{
		
		return (cuentas.remove (cuenta.getNcc()) != null); //OJO
		
	}
	
	public boolean eliminarCliente (Cliente cliente)
	{
		boolean exito = (clientes.remove (cliente.getNif()) != null);
		
		if (exito)
		{
			// Borrar clientes de las cuentas; CASCADA
			for (Cuenta cuenta: cuentas.values())
				cuenta.getClientes().remove(cliente.getNif());
					
		}
		
		return exito;
	}


	
	public float getSaldoTotal() //LEEMOS EL ENUNCIADO, Y SABEMOS QUE TIENE QUE HABER ESTA FUNCIÓN
	{
		float saldo = 0;
		
		for (Cuenta cuenta: cuentas.values())
			saldo += cuenta.getSaldo();
		
		return saldo;
	}
	
	
	// Escribir y leer fichero.
	public void escribirFichero (DataOutputStream fichero) throws IOException
	{
		
		// Escribir cuantos hay en el banco
		fichero.writeInt(clientes.size());
		
		// Escribir los clientes del banco
		for (Cliente c : clientes.values())
			c.escribirFichero(fichero);
		
		// Escribir cuantas cuentas hay en el banco
		fichero.writeInt(cuentas.size());
		
		// Escribir las cuentas
		for (Cuenta cuenta: cuentas.values())
		{
			//miguitas de pan, para saber crear un tipo de cuenta u otra a la hora de leer
			fichero.writeBytes(cuenta.getClass().getName()+"\n"); 
			cuenta.escribirFichero(fichero);

		}
		
	}
	
	public void leerFichero (DataInputStream fichero) throws IOException
	{
		int cuantosClientes, cuantasCuentas;
		String tipo;
		
		// vaciar contenedor de clientes
		clientes.clear();
		
		// Leer nÂº de clientes
		cuantosClientes = fichero.readInt();
		
		// Leer clientes
		for (int i = 0; i < cuantosClientes; i++)
		{
			Cliente c = new Cliente();
			c.leerFichero(fichero);
			clientes.put(c.getNif(), c);
		}
		
		
		cuentas.clear();
		// Leer nÂº de cuentas
		cuantasCuentas = fichero.readInt();
		
		// Leer cuentas
		for (int i = 0; i < cuantasCuentas; i++)
		{
			tipo = fichero.readLine();
			Cuenta cuenta;
			try {
				cuenta = (Cuenta)Class.forName(tipo).newInstance();
				cuenta.leerFichero(fichero); //aquí rellenamos cuentas, pero sus TreeMaps de clientes están vacíos, solo tienen la clave
				//por ello, tenemos que orientar la referencia a clientes de nuevo, para que tenga acceso a todos sus datos
				
				// Poner referencia clientes en cuenta
				for (Cliente cliente:cuenta.getClientes().values())
				{
					cliente = clientes.get(cliente.getNif());
					cuenta.getClientes().replace(cliente.getNif(), cliente);
				}
				
				cuentas.put(cuenta.getNcc(), cuenta);
				
				 
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//AQUÍ ACABA EL FOR; el try/catch están dentro del FOR
	}

}
