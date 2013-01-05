/**
 * 
 */
package net.inferum.khaos.vm;

/**
 * @author basty
 *
 */
public interface TrapHandler {
	/**
	 * handles the trap request
	 * 
	 * @param number
	 * @param kvm
	 */
	public void trap(long number, KhaosVM kvm);
}
