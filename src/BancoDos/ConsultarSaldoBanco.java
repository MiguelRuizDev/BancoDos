package BancoDos;


import java.util.Iterator;

import daw.com.Pantalla;

public class ConsultarSaldoBanco implements MenuAction {

	private Banco banco;
	
	public ConsultarSaldoBanco (Banco banco)
	{
		this.banco = banco;
	}
	
	@Override
	public void doMenuAction() {
		// TODO Auto-generated method stub
		/*
		Iterator <Cuenta> iterador = banco.getCuentas(); 
		//Listar cuentas
		while (iterador.hasNext())
			iterador.next().verDatos();
		*/
		
		//MI FORMA
		
		for (Cuenta cuenta: banco.getCuentas()) //esto devuelve --> cuentas.values()
			cuenta.verDatos();
		
		// Mostar saldo total
		Pantalla.escribirFloat("\nSaldo total", banco.getSaldoTotal());
		
		

	}

}
