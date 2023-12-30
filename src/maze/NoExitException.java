package maze;

/**NoExitException class consisting of a method for an exception extending InvalidMazeException
* a checked exception
* @author Atif
* @version 28th April 2021
*/
public class NoExitException extends InvalidMazeException{

	/**Constructor for NoExitException passing the error message provided in the parameter
	* @param errorMessage this is a string to tell the user the error
	*/
	public NoExitException(String errorMessage){
		super(errorMessage);
	}
	
}