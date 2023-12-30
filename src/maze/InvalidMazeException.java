package maze;

/**InvalidMazeException class consisting of a method for an exception which extends the exception class
* A checked exception
* @author Atif
* @version 28th April 2021
*/
public class InvalidMazeException extends Exception{

	/**Constructor for InvalidMazeException passing the error message provided in the parameter
	* @param errorMessage this is a string to tell the user the error
	*/
	public InvalidMazeException(String errorMessage){
		super(errorMessage);
	}
	
}
