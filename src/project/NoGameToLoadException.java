package project;

/**
 * @author Kacper Pietkun
 */
public class NoGameToLoadException extends Exception
{
    public NoGameToLoadException()
    {
    }
    public NoGameToLoadException(String message)
    {
        super(message);
    }
    public NoGameToLoadException(Throwable cause)
    {
        super(cause);
    }
    public NoGameToLoadException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
