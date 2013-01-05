/**
 * 
 */
package net.inferum.khaos.vm.exception;

/**
 * @author basty
 *
 */
public class MachineHalted extends KVMException {
	private static final long serialVersionUID = 5492296127751351174L;

	public MachineHalted() {
		super("no execution allowed, the machine halted.");
	}

	
}
