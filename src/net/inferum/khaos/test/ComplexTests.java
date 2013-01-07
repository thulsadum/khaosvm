/**
 * 
 */
package net.inferum.khaos.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import net.inferum.khaos.vm.AssemblyBuilder;
import net.inferum.khaos.vm.AssemblyLoader;
import net.inferum.khaos.vm.KhaosVM;
import net.inferum.khaos.vm.exception.AssemblyException;
import net.inferum.khaos.vm.exception.KVMException;

import org.junit.Test;

/**
 * @author basty
 *
 */
public class ComplexTests {

	@Test
	public void testHelloWorldB() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		AssemblyBuilder ab = new AssemblyBuilder();
		String msg = "Hello world!";
		ab
			.bra("main")
			.label("msg")
				.data(msg)
			.label("main")
				.ldr("SP")
				.ldc(2)
				.add()
				.str("SP")
				.ldc("msg")
				.ldc(ab.getCurrentOffset())
				.add()
				.stl(0)
				.ldc(0)
				.stl(1)
				
				.label("loop")
					.ldc(msg.length())
					.ldl(1)
					.cmp()
					.beq("end")
					
					.ldl(0)
					.ldl(1)
					.add()
					.lda(0)
					.str("R1")
					.ldc(1)
					.str("R0")
					.trap(1)
					
					.ldl(1)
					.ldc(1)
					.add()
					.stl(1)
					.bra("loop")
			.label("end")
				.halt();
					
				
		
		KhaosVM vm = new KhaosVM();
		vm.setOutput(new PrintStream(baos));
		try {
			AssemblyLoader al = new AssemblyLoader(new ByteArrayInputStream(ab.link().generate()));
			vm.setMemory(al.load(0x20));
			vm.setSP(ab.getCurrentOffset());
			vm.setMP(ab.getCurrentOffset());
			vm.setHP(0);
		} catch (IOException | AssemblyException e) {
			fail(e.getMessage());
		}
		
		while(!vm.isHalted()) {
			try {
				vm.executeStep();
			} catch (KVMException e) {
				fail(e.getMessage() + " PC: " + vm.getPC());
			}
		}
		
		assertArrayEquals(msg.getBytes(), baos.toByteArray());
	}

	@Test
	public void testHelloWorldA() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		AssemblyBuilder ab = new AssemblyBuilder();
		String msg = "Hello world!";
		
		for(int i = 0; i < msg.length(); i++) {
			ab.ldc(msg.charAt(i))
				.str("R1")
				.ldc(1)
				.str("R0")
				.trap(KhaosVM.TRAP_WRITE);
		}
		ab.halt();
		
		KhaosVM vm = new KhaosVM();
		vm.setOutput(new PrintStream(baos));
		try {
			AssemblyLoader al = new AssemblyLoader(new ByteArrayInputStream(ab.link().generate()));
			vm.setMemory(al.load(0x20));
			vm.setSP(ab.getCurrentOffset());
			vm.setMP(ab.getCurrentOffset());
			vm.setHP(0);
		} catch (IOException | AssemblyException e) {
			fail(e.getMessage());
		}
		
		while(!vm.isHalted()) {
			try {
				vm.executeStep();
			} catch (KVMException e) {
				fail(e.getMessage() + " PC: " + vm.getPC());
			}
		}
		
		assertArrayEquals(msg.getBytes(), baos.toByteArray());
	}

}
