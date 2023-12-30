package maze.routing;

/**NoRouteFoundException class consisting of a method for an exception which extends the exception class
* A checked exception
* @author Atif
* @version 28th April 2021
*/
public class NoRouteFoundException extends Exception{

	/**Constructor for NoRouteFoundException passing the error message provided in the parameter
	* @param errorMessage this is a string to tell the user the error
	*/
	public NoRouteFoundException(String errorMessage){
		super(errorMessage);
	}
	
}