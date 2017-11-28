package BancoDos;


public class MenuItem {
	
	private String mensaje;
	private int opcion;
	private MenuAction action;
	
	public MenuItem ()
	{
		this ("",0,null);
		//this ("",0,()->{});
	}
	
	public MenuItem(String mensaje, int opcion, MenuAction action) 
	{
		this.mensaje = mensaje;
		this.opcion = opcion;
		this.action = action;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public MenuAction getAction() {
		return action;
	}

	public void setAction(MenuAction action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "MenuItem [mensaje=" + mensaje + ", opcion=" + opcion + ", action=" + action.getClass().getSimpleName() + "]";
	}


	@Override
	public boolean equals(Object obj) {
		
		MenuItem menuItem = (MenuItem)obj;
		
		return menuItem.opcion == this.opcion;
	}
	
	
}
