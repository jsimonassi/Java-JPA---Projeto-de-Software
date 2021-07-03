package locacaocarros.exceptions;

public class DeprecatedObjectException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public DeprecatedObjectException()
	{	super();
	}

	public DeprecatedObjectException(String msg)
	{	super(msg);
	}
}	