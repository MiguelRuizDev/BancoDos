package BancoDos;


import daw.com.Pantalla;
import daw.com.Teclado;

public class Transferir implements MenuAction {
	private Banco banco;
	
	
	public Transferir (Banco banco)
	{
		this.banco = banco;
	}
	
	@Override
	public void doMenuAction() {
		// TODO Auto-generated method stub
		
		/*
		Cuenta origen,destino;
		float cantidad;
		String nccOrigen, nccDestino;
		
		nccOrigen = Teclado.leerString("NCC Origen");
		
		nccDestino = Teclado.leerString("NCC Destino");
		
		cantidad = Teclado.leerFloat("Cantidad a transferir");
		
		origen = banco.getCuenta(nccOrigen);
		
		destino = banco.getCuenta(nccDestino);
		
		if (origen != null && destino != null)
		{
		
			if (origen.transferir(cantidad, destino))
			{
				Pantalla.escribirString("\nTranferencia realizada con exito");
				Pantalla.escribirFloat("\nNuevo saldo Origen:", origen.getSaldo());
				Pantalla.escribirFloat("\nComisi√≥n cobrada:", origen.calcularComision(cantidad));
				Pantalla.escribirFloat("\nNuevo saldo Destino:", destino.getSaldo());
			}
			else
			{
				Pantalla.escribirString("\nTranferencia no realizada");
				Pantalla.escribirFloat("\nMaximo a tranferir", -origen.maximoNegativo()+origen.getSaldo());
			}
		}
		else
			Pantalla.escribirString("\nError en los NCC de origen o destino");
		*/
		
		//ALTERNATIVA "EN CASCADA"
		
		String ncc_origen;
		float cantidad;
		
		
		ncc_origen = Teclado.leerString("Cuenta de Origen");
		
		if (banco.getCuenta(ncc_origen) == null)
			
			Pantalla.escribirString("Cuenta de origen inexistente");
		
		else { 
			
			Cuenta origen = banco.getCuenta(ncc_origen);
			cantidad = Teclado.leerFloat("Cantidad a transferir");
			
			if (origen.getSaldo() - cantidad < origen.maximoNegativo())
				
				Pantalla.escribirString("Transferencia no permitida");
			
			else{
				
				String ncc_destino = Teclado.leerString("Cuenta de destino");
				
				if (banco.getCuenta(ncc_destino) == null)
					
					Pantalla.escribirString("Cuenta de destino inexistente");
				
				else{
					
					Cuenta destino = banco.getCuenta(ncc_destino);
					
					origen.transferir(cantidad, destino);
					Pantalla.escribirString("\nTranferencia realizada con exito");
					Pantalla.escribirFloat("\nNuevo saldo Origen:", origen.getSaldo());
					Pantalla.escribirFloat("\nComisiÛn cobrada:", origen.calcularComision(cantidad));
					Pantalla.escribirFloat("\nNuevo saldo Destino:", destino.getSaldo());
					
				}

					
				
				
			}


		}
		
		
	}
	

		

}
