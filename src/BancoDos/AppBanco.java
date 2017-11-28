package BancoDos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import daw.com.Pantalla;

public class AppBanco extends AppMenu {
	private Banco banco;
	
	public AppBanco ()
	{
		super ();
		banco = new Banco ();
		
		addOpcion (new MenuItem ("Insertar Cuenta", 1, new Insertar(banco)));
		addOpcion (new MenuItem ("Transferir", 4, new Transferir(banco)));
		addOpcion (new MenuItem ("Consultar Saldo Banco", 6, new ConsultarSaldoBanco(banco)));
		addOpcion (new Salir());
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AppBanco app = new AppBanco();
		
		Cronometro crono = new Cronometro (app);
		Thread hilo = new Thread (crono);
		
		app.leerFichero ();
		
		 
		hilo.start(); // Poner en marcha el cronÃ³metro; DESDE THREAD HILO
		
		app.run();
		
		crono.parar ();//Para el cronómetro; DESDE Objeto Cronometro
		
		app.escribirFichero ();
		
		// Mostrar tiempo
		Pantalla.escribirString ("\nTiempo en uso: "+ crono.toString());
	}
	
	public void leerFichero ()
	{
		FileInputStream bruto = null; //aquí se inicializan los objetos ficheros, en la APP!!
		DataInputStream filtro = null;
				
		
		try //para el "new" de ficheros
		{
				System.err.println("Cargando datos...");
				//String tipo ;
				bruto = new FileInputStream ("banco.dat");
				filtro = new DataInputStream (bruto);
				banco.leerFichero(filtro);
				
		}
		catch (IOException e)
		{
			System.err.println("No hay datos para cargar...");
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			try //para el close() de ficheros
			{
				if (filtro!= null)
					filtro.close();
				if (bruto != null)
					bruto.close();
			}
			catch (IOException e)
			{
				System.err.println("Error; cerrando ficheros...");
				
			}
		}
	}
	
	
	public void escribirFichero ()
	{
		//SHORT MODE; no se necesita cerrar ficheros, y por tanto, no hace falta ningún finally
		try (DataOutputStream filtro = new DataOutputStream (new FileOutputStream ("banco.dat"));)
		{
			System.err.println("Guardando datos...");
			banco.escribirFichero(filtro);
			
		}
		catch (IOException e)
		{
			System.err.println("Error en escritura de fichero...");
		}
	}

}
