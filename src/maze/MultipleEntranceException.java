package maze;

/**MultipleEntranceException class consisting of a method for an exception extending InvalidMazeException
* a checked exception
* @author Atif
* @version 28th April 2021
*/
public class MultipleEntranceException extends InvalidMazeException{

	/**Constructor for MultipleEntranceException passing the error message provided in the parameter
	* @param errorMessage this is a string to tell the user the error
	*/
	public MultipleEntranceException(String errorMessage){
		super(errorMessage);
	}
	
}