/**
 * 
 */
package net.inferum.khaos.vm.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.inferum.khaos.vm.AssemblyBuilder;
import net.inferum.khaos.vm.AssemblyLoader;
import net.inferum.khaos.vm.Instruction;
import net.inferum.khaos.vm.exception.AssemblyException;

import org.junit.Test;

/**
 * @author basty
 *
 */
public class AssemblyLoaderTest {
	private long[] L(long... a) {
		return a;
	}
	
	private long[] L(Instruction... a) {
		long[] A = new long[a.length];
		for (int i = 0; i < A.length; i++) {
			A[i] = a[i].getOpCode();
		}
		return A;
	}
	
	@Test
	public void testZeroArgs() {
		AssemblyBuilder ab = new AssemblyBuilder();
		try {
			byte[] t = ab.add().sub().mul().div().mod()
				.and().or().xor().cmp()
				.neg().not()
				.noop().halt()
				.sth().generate();
			
			long[] expected = L(Instruction.add, Instruction.sub, Instruction.mul, Instruction.div, Instruction.mod,
					Instruction.and, Instruction.or, Instruction.xor, Instruction.cmp,
					Instruction.neg, Instruction.not,
					Instruction.noop, Instruction.halt, Instruction.sth);
			
			AssemblyLoader al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(al.load(0), expected);
		} catch (IOException | AssemblyException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testOneArgs() {
		AssemblyBuilder ab = new AssemblyBuilder();
		try {
			byte[] t = ab.ldc(0x13).lds(0x13).ldaa(0x13).ldr(0x13).generate();			
			long[] expected = L(Instruction.ldc.getOpCode(), 0x13, Instruction.lds.getOpCode(), 0x13, Instruction.ldaa.getOpCode(), 0x13, Instruction.ldr.getOpCode(), 0x13, 0,0);			
			AssemblyLoader al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(al.load(2), expected);
		} catch (IOException | AssemblyException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testTwoArgs() {
		AssemblyBuilder ab = new AssemblyBuilder();
		try {
			byte[] t = ab.ldrr(0x13, 0x37).generate();			
			long[] expected = L(Instruction.ldrr.getOpCode(), 0x13, 0x37, 0,0);			
			AssemblyLoader al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(al.load(2), expected);
		} catch (IOException | AssemblyException e) {
			fail(e.getMessage());
		}
	}
}
