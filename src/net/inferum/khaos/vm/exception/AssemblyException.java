/**
 * 
 */
package net.inferum.khaos.vm.exception;

/**
 * @author basty
 *
 */
public class AssemblyException extends KVMException {
	private static final long serialVersionUID = -6418235747853794727L;

	/**
	 * 
	 */
	public AssemblyException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AssemblyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public AssemblyException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AssemblyException(Throwable cause) {
		super(cause);
	}

	
	
}
