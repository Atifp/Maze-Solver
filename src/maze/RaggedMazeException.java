package maze;

/**RaggedMazeException class consisting of a method for an exception extending InvalidMazeException
* a checked exception
* @author Atif
*/
public class RaggedMazeException extends InvalidMazeException{

	/**Constructor for RaggedMazeException passing the error message provided in the parameter
	* @param errorMessage this is a string to tell the user the error
	*/
	public RaggedMazeException(String errorMessage){
		super(errorMessage);
	}
	
}