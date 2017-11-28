package BancoDos;

public class Cronometro implements Runnable { //para ser Thread, implementa Runnable
	
	private AppBanco app; //tiene la app completa, para tener acceso a todos los datos
	private int segundos;
	private boolean fin;
	
	public Cronometro (AppBanco app)
	{
		this.app = app;
		segundos = 0;
		fin = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (!fin) //CONTAR, GUARDAR (si eso), DORMIR
		{
			segundos++; //contamos
		
			if (segundos % 300 == 0) // Salvar cada 5 minutos
				app.escribirFichero();
			
			try {
				Thread.sleep(1000); //dejamos que pase 1 segundo
			} catch (InterruptedException e) { //los sleep() siempre están rodeados de try/catch
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void parar () //levantamos la bandera
	{
		fin = true;
	}

	@Override
	public String toString() { //convertimos los segundos a  Horas, Minutos y Seg
		String resultado ="";
		
		int horas, minutos, segs;
		
		horas = segundos / 3600;
		minutos = (segundos % 3600) / 60;
		segs = segundos % 60; 
		
		resultado = horas + ":" + minutos + ":" + segundos;
		
		return  resultado;
	}
	
	

}
