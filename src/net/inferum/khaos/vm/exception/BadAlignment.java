/**
 * 
 */
package net.inferum.khaos.vm.exception;

/**
 * @author basty
 *
 */
public class BadAlignment extends AssemblyException {
	private static final long serialVersionUID = 8559740386626930605L;

	public BadAlignment(int oldOffset, int newOffset) {
		super(String.format("bad offset alignment: %d < %d", newOffset, oldOffset));
	}
	
}
