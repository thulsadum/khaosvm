/**
 * 
 */
package net.inferum.khaos.vm.exception;

/**
 * @author basty
 *
 */
public class BadOpCode extends KVMException {
	private static final long serialVersionUID = 2059386549591128832L;

	public BadOpCode(int opCode) {
		super(String.format("bad opcode 0x%02x", opCode));
	}
}
