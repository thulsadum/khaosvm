package net.inferum.khaos.vm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.inferum.khaos.vm.exception.AssemblyException;
import net.inferum.khaos.vm.exception.KVMException;

public class AssemblyRunner {
	public static void main(String[] args) throws FileNotFoundException {
		AssemblyLoader al;
		int additionalMemory = Integer.parseInt(System.getProperty("runner.extra_memory", "4096"));
		
		boolean trace = System.getProperty("runner.trace") != null;
		
		if(args.length > 0) {
			al = new AssemblyLoader(new FileInputStream(args[0]));
		}else{
			al = new AssemblyLoader(System.in);
		}
		
		long[] mem = null;
		KhaosVM vm;
		
		try {
			mem = al.load(additionalMemory);
		} catch (AssemblyException | IOException e) {
			System.err.println("error loading the assembly: " + e.getMessage());
			System.exit(-1);
		}
		vm = new KhaosVM(mem);
		vm.setSP(mem.length - additionalMemory);
		vm.setMP(mem.length - additionalMemory);
		vm.setHP(mem.length - 1);
		vm.setTrace(trace);
		try {
			while(!vm.isHalted()) {
					vm.executeStep();
			}
		} catch (KVMException e) {
			System.err.println("execution exception: " + e.getMessage());
			System.exit(-1);
		}
		int r = (int) vm.getRR();
		if(r != 0) System.exit(r);
	}
}
