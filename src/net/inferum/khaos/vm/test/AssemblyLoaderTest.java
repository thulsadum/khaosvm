/**
 * 
 */
package net.inferum.khaos.vm.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

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
				.sth()
				.jsr().generate();
			
			long[] expected = L(Instruction.add, Instruction.sub, Instruction.mul, Instruction.div, Instruction.mod,
					Instruction.and, Instruction.or, Instruction.xor, Instruction.cmp,
					Instruction.neg, Instruction.not,
					Instruction.noop, Instruction.halt, Instruction.sth,
					Instruction.jsr);
			
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
	
	@Test
	public void testData() {
		AssemblyBuilder ab = new AssemblyBuilder();
		try {
			byte[] t = ab.data((byte)0x37).generate();			
			long[] expected = L(0x37);			
			AssemblyLoader al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(al.load(0), expected);

			ab.clear();
			t = ab.data((short)0x1337).generate();			
			expected = L(0x1337);			
			al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(expected, al.load(0));

			ab.clear();
			t = ab.data((int)0x31337).generate();			
			expected = L(0x31337);			
			al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(expected, al.load(0));

			ab.clear();
			t = ab.data(0x133700031337L).generate();			
			expected = L(0x133700031337L);			
			al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(expected, al.load(0));

			ab.clear();
			t = ab.data("Hello world!".getBytes()).generate();			
			expected = L('H', 'e', 'l','l','o',' ','w','o','r','l','d','!');			
			al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(expected, al.load(0));

			ab.clear();
			t = ab.repeat(0x1000, (byte) 0x13).generate();			
			expected = new long[0x1000];
			Arrays.fill(expected, 0x13);
			al = new AssemblyLoader(new ByteArrayInputStream(t));
			assertArrayEquals(expected, al.load(0));
		} catch (IOException | AssemblyException e) {
			fail(e.getMessage());
		}
	}
}
