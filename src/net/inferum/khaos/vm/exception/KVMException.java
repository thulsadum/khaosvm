/**
 * 
 */
package net.inferum.khaos.vm.exception;

/**
 * @author basty
 *
 */
public abstract class KVMException extends Exception {
	private static final long serialVersionUID = 723446778186015080L;

	/**
	 * 
	 */
	public KVMException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public KVMException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public KVMException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public KVMException(Throwable cause) {
		super(cause);
	}

}
