/**
 * 
 */
package net.inferum.khaos.vm.exception;

/**
 * @author basty
 *
 */
public class BadVersion extends BadFormat {
	private static final long serialVersionUID = 7732425755365638499L;

	public BadVersion(int expected, int actual) {
		super(String.format("Version %d does not match expected version %d", actual, expected));
	}
	
}
