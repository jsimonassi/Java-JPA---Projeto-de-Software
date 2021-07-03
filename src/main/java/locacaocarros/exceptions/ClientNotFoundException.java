package locacaocarros.exceptions;

public class ClientNotFoundException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	private int cod;
	
	public ClientNotFoundException(String msg)
	{	super(msg);
	}

	public ClientNotFoundException(int cod, String msg)
	{	super(msg);
		this.cod = cod;
	}
	
	public int getErrorCod()
	{	return cod;
	}
}	