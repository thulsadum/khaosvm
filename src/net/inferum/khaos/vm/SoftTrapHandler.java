/**
 * 
 */
package net.inferum.khaos.vm;

/**
 * @author basty
 *
 */
public class SoftTrapHandler implements TrapHandler {
	private KhaosVM vm;
	private long trap;
	private long location;
	
	/**
	 * @param vm
	 * @param location
	 */
	public SoftTrapHandler(KhaosVM vm, long trap, long location) {
		this.vm = vm;
		this.trap = trap;
		this.location = location;
	}

	/**
	 * @return the vm
	 */
	public KhaosVM getVm() {
		return vm;
	}

	/**
	 * @return the location
	 */
	public long getLocation() {
		return location;
	}

	/**
	 * @return the trap
	 */
	public long getTrap() {
		return trap;
	}

	/* (non-Javadoc)
	 * @see net.inferum.khaos.vm.TrapHandler#trap(long, net.inferum.khaos.vm.KhaosVM)
	 */
	@Override
	public void trap(long number, KhaosVM kvm) {
		if(trap != number || kvm != vm) {
			kvm.leaveTrap();
		}
		
		/* the called function must return */
		kvm.setPC(location);
	}

}
