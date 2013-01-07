/**
 * 
 */
package net.inferum.khaos.vm.exception;

/**
 * @author basty
 *
 */
public class UnresolvedLabel extends AssemblyException {
	private static final long serialVersionUID = 8559740386626930605L;

	public UnresolvedLabel(String label) {
		super(String.format("label '%s' is not defined", label));
	}
	
}
