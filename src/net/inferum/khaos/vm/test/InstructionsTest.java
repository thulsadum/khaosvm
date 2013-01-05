/**
 * 
 */
package net.inferum.khaos.vm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import net.inferum.khaos.vm.Instruction;
import net.inferum.khaos.vm.KhaosVM;
import net.inferum.khaos.vm.exception.KVMException;

import org.junit.Test;

/**
 * @author basty
 *
 */
public class InstructionsTest {
	private static final int memorySize = 64;
	
	private static long[] A(long... args) {
		return args;
	}
	
	private static KhaosVM setup(long[] code) {
		long[] regs = new long[KhaosVM.DEFAULT_REGISTERS];
		regs[0] = 0;
		regs[1] = code.length;
		regs[2] = code.length;
		regs[3] = 0;
		regs[4] = memorySize-1;
		return setup(code, regs);
	}
	
	private static KhaosVM setup(long[] code, long[] registers) {
		
		KhaosVM ret = new KhaosVM(memorySize);
		
		System.arraycopy(code, 0, ret.getMemory(), 0, code.length);
		if(registers!=null)
			System.arraycopy(registers, 0, ret.getRegisters(), 0, registers.length);
		
		return ret;
	}
	
	@Test
	public void testLdc() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[3], 0);
		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[3], 0x1337);
	}

	@Test
	public void testLdc2() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.ldc.getOpCode(), 0x7331));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[5], 0);
		assertEquals(vm.getMemory()[6], 0);
		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[5], 0x1337);
		assertEquals(vm.getMemory()[6], 0x7331);
	}

	@Test
	public void testAdd() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.ldc.getOpCode(), 0x7331, Instruction.add.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0);
		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0x7331 + 0x1337);
	}

	@Test
	public void testSub() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.ldc.getOpCode(), 0x7331, Instruction.sub.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0);
		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], -0x7331 + 0x1337);
	}

	@Test
	public void testMul() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.ldc.getOpCode(), 0x7331, Instruction.mul.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0x7331 * 0x1337);
	}

	@Test
	public void testDiv() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x7331, Instruction.ldc.getOpCode(), 0x1337, Instruction.div.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0x7331 / 0x1337);
	}

	@Test
	public void testMod() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x7331, Instruction.ldc.getOpCode(), 0x1337, Instruction.mod.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0x7331 % 0x1337);
	}

	@Test
	public void testNot() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.not.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[4], 0);
		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 3);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[4], ~0x1337);
	}

	@Test
	public void testNeg() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.neg.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[4], 0);
		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 3);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[4], -0x1337);
	}

	@Test
	public void testAnd() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x7331, Instruction.ldc.getOpCode(), 0x1337, Instruction.and.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0x7331 & 0x1337);
	}

	@Test
	public void testOr() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x7331, Instruction.ldc.getOpCode(), 0x1337, Instruction.or.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0x7331 | 0x1337);
	}

	@Test
	public void testXOr() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x7331, Instruction.ldc.getOpCode(), 0x1337, Instruction.xor.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[6], 0x7331 ^ 0x1337);
	}
	
	@Test
	public void testLda() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x0, Instruction.lda.getOpCode(), 0x5, 0x0, 0x7331));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 6);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 7);
		assertEquals(vm.getMP(), 6);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0x7331);
	}

	@Test
	public void testLdaa() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x0, Instruction.ldaa.getOpCode(), 0x5, 0x0, 0x7331));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 6);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 8);
		assertEquals(vm.getMP(), 6);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0x5);
	}

	@Test
	public void testLdh() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x5, Instruction.sth.getOpCode(), Instruction.ldh.getOpCode(), 0));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 5);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 2);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0x5);
	}

	@Test
	public void testLdl() {
		KhaosVM vm = setup(A(Instruction.ldl.getOpCode(), -1, 0x05));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0x5);
	}

	@Test
	public void testLdla() {
		KhaosVM vm = setup(A(Instruction.ldla.getOpCode(), -1, 0x05));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0x2);
	}

	@Test
	public void testLdrPC() {
		KhaosVM vm = setup(A(Instruction.ldr.getOpCode(), 0));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 1);
	}

	@Test
	public void testLdrSP() {
		KhaosVM vm = setup(A(Instruction.ldr.getOpCode(), 1));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 2);
	}

	@Test
	public void testLdrMP() {
		KhaosVM vm = setup(A(Instruction.ldr.getOpCode(), 2));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 2);
	}

	@Test
	public void testLdrRA() {
		KhaosVM vm = setup(A(Instruction.ldr.getOpCode(), 3));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);
	}

	@Test
	public void testLdrHP() {
		KhaosVM vm = setup(A(Instruction.ldr.getOpCode(), 4));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], memorySize - 1);
	}

	@Test
	public void testLdrr() {
		KhaosVM vm = setup(A(Instruction.ldrr.getOpCode(), 4, 1));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		assertEquals(vm.getPC(), 3);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), 3);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);
	}

	@Test
	public void testLds() {
		KhaosVM vm = setup(A(Instruction.lds.getOpCode(), -1, 5));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 5);
	}

	@Test
	public void testLdsa() {
		KhaosVM vm = setup(A(Instruction.ldsa.getOpCode(), -1, 5));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 2);
	}

	@Test
	public void testSta() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x10, Instruction.ldc.getOpCode(), 0x1337, Instruction.sta.getOpCode(), -2));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 6);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 6);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 6);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[0x10 - 2], 0x1337);
	}

	@Test
	public void testSth() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.sth.getOpCode()));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 3);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 3);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 2);
		assertEquals(vm.getMemory()[(int) vm.getSP()], memorySize-2);
		assertEquals(vm.getMemory()[memorySize - 2], 0x1337);
	}

	@Test
	public void testStl() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.stl.getOpCode(),-1, 0x0));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 5);
		assertEquals(vm.getMP(), 5);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[4], 0x1337);
	}

	@Test
	public void testStrPC() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.str.getOpCode(), 0));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x1337);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
	}
	
	@Test
	public void testStrSP() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.str.getOpCode(), 1));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 0x1337);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
	}
	
	@Test
	public void testStrMP() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.str.getOpCode(), 2));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 0x1337);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
	}
	
	@Test
	public void testStrRA() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.str.getOpCode(), 3));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0x1337);
		assertEquals(vm.getHP(), memorySize - 1);
	}
	
	@Test
	public void testStrHP() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.str.getOpCode(), 4));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), 0x1337);
	}

	@Test
	public void testStrR0() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.str.getOpCode(), 5));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getRegister(0), 0x1337);
	}

	@Test
	public void testStrR1() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.str.getOpCode(), 6));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getRegister(1), 0x1337);
	}

	@Test
	public void testSts() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0x1337, Instruction.sts.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) (vm.getSP() + 0x10 + 1)], 0x1337);
	}

	@Test
	public void testBraA() {
		KhaosVM vm = setup(A(Instruction.bra.getOpCode(), 0x0));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 2);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBraB() {
		KhaosVM vm = setup(A(Instruction.bra.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x12);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBsr() {
		KhaosVM vm = setup(A(Instruction.bsr.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 2);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x12);
		assertEquals(vm.getSP(), 3);
		assertEquals(vm.getMP(), 2);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 3);
	}

	@Test
	public void testBeqTrue() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0, Instruction.beq.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x14);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBeqFalse() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 10, Instruction.beq.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBneTrue() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), -10, Instruction.bne.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x14);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBneFalse() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0, Instruction.bne.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBltTrue() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 10, Instruction.blt.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x14);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBltFalseA() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0, Instruction.blt.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBltFalseB() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), -10, Instruction.blt.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBgtTrue() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), -10, Instruction.bgt.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x14);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBgtFalseA() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0, Instruction.bgt.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBgtFalseB() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 10, Instruction.bgt.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}


	@Test
	public void testBleTrueA() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 10, Instruction.ble.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x14);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBleTrueB() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0, Instruction.ble.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x14);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBleFalse() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), -10, Instruction.ble.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBgeTrueA() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), -10, Instruction.bge.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x14);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBgeTrueB() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0, Instruction.bge.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 0x14);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}

	@Test
	public void testBgeFalse() {
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 10, Instruction.bge.getOpCode(), 0x10));
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 4);
		assertEquals(vm.getSP(), 4);
		assertEquals(vm.getMP(), 4);
		assertEquals(vm.getRR(), 0);
	}
	
	@Test
	public void testTrapRead() {
		byte[] b = {0x13};
		ByteArrayInputStream in = new ByteArrayInputStream(b);
		
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 0, Instruction.str.getOpCode(), KhaosVM.FIXED_REGISTERS, Instruction.trap.getOpCode(), 0));
		vm.setInput(in);
		
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 6);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage());
		}
		
		assertEquals(vm.getPC(), 6);
		assertEquals(vm.getSP(), 6);
		assertEquals(vm.getMP(), 6);
		assertEquals(vm.getRR(), b[0]);
	}
	
	@Test
	public void testTrapWriteOut() {
		ByteArrayOutputStream b = new ByteArrayOutputStream(1);
		
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 1, Instruction.str.getOpCode(), KhaosVM.FIXED_REGISTERS,Instruction.ldc.getOpCode(), '!', Instruction.str.getOpCode(), KhaosVM.FIXED_REGISTERS + 1, Instruction.trap.getOpCode(), 1));
		vm.setOutput(new PrintStream(b));
		
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 10);
		assertEquals(vm.getMP(), 10);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage() + " (pc: " + vm.getPC() + ")");
		}
		
		assertEquals(vm.getPC(), 10);
		assertEquals(vm.getSP(), 10);
		assertEquals(vm.getMP(), 10);
		assertEquals(vm.getRR(), 0);
		assertEquals(b.toByteArray()[0], '!');
	}

	@Test
	public void testTrapWriteErr() {
		ByteArrayOutputStream b = new ByteArrayOutputStream(1);
		
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 2, Instruction.str.getOpCode(), KhaosVM.FIXED_REGISTERS,Instruction.ldc.getOpCode(), '!', Instruction.str.getOpCode(), KhaosVM.FIXED_REGISTERS + 1, Instruction.trap.getOpCode(), 1));
		vm.setError(new PrintStream(b));
		
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 10);
		assertEquals(vm.getMP(), 10);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage() + " (pc: " + vm.getPC() + ")");
		}
		
		assertEquals(vm.getPC(), 10);
		assertEquals(vm.getSP(), 10);
		assertEquals(vm.getMP(), 10);
		assertEquals(vm.getRR(), 0);
		assertEquals(b.toByteArray()[0], '!');
	}

	@Test
	public void testTrapWriteDebug() {
		ByteArrayOutputStream b = new ByteArrayOutputStream(1);
		
		KhaosVM vm = setup(A(Instruction.ldc.getOpCode(), 3, Instruction.str.getOpCode(), KhaosVM.FIXED_REGISTERS,Instruction.ldc.getOpCode(), '!', Instruction.str.getOpCode(), KhaosVM.FIXED_REGISTERS + 1, Instruction.trap.getOpCode(), 1));
		vm.setDebug(new PrintStream(b));
		
		assertEquals(vm.getPC(), 0);
		assertEquals(vm.getSP(), 10);
		assertEquals(vm.getMP(), 10);
		assertEquals(vm.getRR(), 0);
		assertEquals(vm.getHP(), memorySize - 1);
		assertEquals(vm.getMemory()[(int) vm.getSP()], 0);

		try {
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
			vm.executeStep();
		} catch (KVMException e) {
			fail(e.getMessage() + " (pc: " + vm.getPC() + ")");
		}
		
		assertEquals(vm.getPC(), 10);
		assertEquals(vm.getSP(), 10);
		assertEquals(vm.getMP(), 10);
		assertEquals(vm.getRR(), 0);
		assertEquals(b.toByteArray()[0], '!');
	}
}
