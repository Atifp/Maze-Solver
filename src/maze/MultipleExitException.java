package maze;

/**MultipleExitException class consisting of a method for an exception extending InvalidMazeException
* a checked exception
* @author Atif
* @version 28th April 2021
*/
public class MultipleExitException extends InvalidMazeException{

	/**Constructor for MultiplExitException passing the error message provided in the parameter
	* @param errorMessage this is a string to tell the user the error
	*/
	public MultipleExitException(String errorMessage){
		super(errorMessage);
	}
	
}