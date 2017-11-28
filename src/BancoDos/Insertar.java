package BancoDos;


import daw.com.Teclado;

public class Insertar implements MenuAction {
	
	private Banco banco;
	
	public Insertar (Banco banco)
	{
		this.banco = banco;
	}

	@Override
	public void doMenuAction() {
		// TODO Auto-generated method stub
		String tipo;
		Cuenta cuenta;
		Cliente cliente;
		String continuar,nif;
		
		tipo = Teclado.leerString("tipo de Cuenta: (P o E)");
		
		if (tipo.equals("P"))
			cuenta = new Personal();
		else
			cuenta = new Empresa ();
		
		cuenta.leerDatos(); //POLIMORFISMO!!
		
		// Leer clientes de la cuenta
		do
		{
			nif = Teclado.leerString("nif"); 
			
			cliente = banco.getCliente(nif);
			
			if (cliente == null)
			{
				cliente = new Cliente ();
				cliente.setNif(nif);
				cliente.leerRestoDatos();
				banco.AddCliente(cliente);
			}
			
			if (!cuenta.getClientes().containsKey(nif))
				cuenta.getClientes().put(nif, cliente);
		
			continuar = Teclado.leerString("Otro Cliente?");
		}while (continuar.equals("S"));
		
		banco.AddCuenta(cuenta);

	}

}
