/**
 * 
 */
package net.inferum.khaos.vm.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.inferum.khaos.vm.AssemblyBuilder;
import net.inferum.khaos.vm.Instruction;
import net.inferum.khaos.vm.KhaosVM;
import net.inferum.khaos.vm.exception.BadAlignment;
import net.inferum.khaos.vm.exception.UnresolvedLabel;

import org.junit.Test;

/**
 * @author basty
 *
 */
public class AssemblyBuilderTest {

	private byte[] mkexpected(Instruction inst, long arg) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeByte(inst.getOpCode());
		dos.writeLong(arg);
		return mkexpected(baos.toByteArray());
	}

	private byte[] mkexpected(Instruction inst, int arg) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeByte(inst.getOpCode());
		dos.writeInt(arg);
		return mkexpected(baos.toByteArray());
	}

	private byte[] mkexpected(Instruction inst, short arg) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeByte(inst.getOpCode());
		dos.writeShort(arg);
		return mkexpected(baos.toByteArray());
	}

	private byte[] mkexpected(Instruction inst, byte... args) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeByte(inst.getOpCode());
		if(args != null) dos.write(args);
		return mkexpected(baos.toByteArray());
	}

	private byte[] mkexpected(Instruction inst, byte arg) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeByte(inst.getOpCode());
		dos.writeByte(arg);
		return mkexpected(baos.toByteArray());
	}

	private byte[] mkexpected(byte... code) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);		
		
		dos.write(code);

		return baos.toByteArray();
	}
	
	private byte[] skipHeader(byte[] assembly) throws IOException {
		byte[] buf = new byte[4096];
		int read;
		ByteArrayInputStream in = new ByteArrayInputStream(assembly);
		//DataInputStream dis = new DataInputStream(in);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		in.skip(16 + 3);
		int regs = in.read();
		in.skip(8 * regs);
		in.skip(4);
		
		while((read = in.read(buf)) > 0)
			out.write(buf, 0, read);
		
		return out.toByteArray();
	}
	
	@Test
	public void testHeader() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);		
		
		try {
			dos.writeLong(AssemblyBuilder.MAGIC_NUMBER);
			dos.writeInt(AssemblyBuilder.VERSION);
			dos.writeInt(AssemblyBuilder.FLAG_EXTENDED_INSTRUCTIONS);
			dos.writeInt(KhaosVM.DEFAULT_REGISTERS);
			for(int i = 0; i < KhaosVM.DEFAULT_REGISTERS; i++)
				dos.writeLong(0);
			dos.writeInt(0);

			AssemblyBuilder ab = new AssemblyBuilder();
			assertArrayEquals(baos.toByteArray(), ab.generate());
		
		} catch (IOException e) {
			fail(e.getMessage());
		}				
	}
	
	@Test
	public void testLdc() {
		try {
			byte[] bs = mkexpected(Instruction.ldc8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.ldc(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldc16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.ldc(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldc32, 0x31337);
			ab = new AssemblyBuilder();
			ab.ldc(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldc, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.ldc(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
			
			bs = mkexpected(Instruction.ldc8, (byte) -1);
			ab = new AssemblyBuilder();
			ab.ldc(-1);
			assertArrayEquals(bs, skipHeader(ab.generate()));			

			bs = mkexpected(Instruction.ldc16, (short) -0x1300);
			ab = new AssemblyBuilder();
			ab.ldc(-0x1300);
			assertArrayEquals(bs, skipHeader(ab.generate()));			

			bs = mkexpected(Instruction.ldc32, (int) -0x13000000);
			ab = new AssemblyBuilder();
			ab.ldc(-0x13000000);
			assertArrayEquals(bs, skipHeader(ab.generate()));			

			bs = mkexpected(Instruction.ldc, (long) -0x1300000000000000L);
			ab = new AssemblyBuilder();
			ab.ldc(-0x1300000000000000L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLda() {
		try {
			byte[] bs = mkexpected(Instruction.lda8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.lda(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.lda16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.lda(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.lda32, 0x31337);
			ab = new AssemblyBuilder();
			ab.lda(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.lda, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.lda(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLdaa() {
		try {
			byte[] bs = mkexpected(Instruction.ldaa8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.ldaa(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldaa16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.ldaa(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldaa32, 0x31337);
			ab = new AssemblyBuilder();
			ab.ldaa(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldaa, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.ldaa(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLds() {
		try {
			byte[] bs = mkexpected(Instruction.lds8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.lds(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.lds16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.lds(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.lds32, 0x31337);
			ab = new AssemblyBuilder();
			ab.lds(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.lds, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.lds(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLdsa() {
		try {
			byte[] bs = mkexpected(Instruction.ldsa8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.ldsa(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldsa16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.ldsa(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldsa32, 0x31337);
			ab = new AssemblyBuilder();
			ab.ldsa(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldsa, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.ldsa(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLdh() {
		try {
			byte[] bs = mkexpected(Instruction.ldh8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.ldh(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldh16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.ldh(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldh32, 0x31337);
			ab = new AssemblyBuilder();
			ab.ldh(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldh, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.ldh(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLdl() {
		try {
			byte[] bs = mkexpected(Instruction.ldl8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.ldl(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldl16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.ldl(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldl32, 0x31337);
			ab = new AssemblyBuilder();
			ab.ldl(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldl, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.ldl(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLdla() {
		try {
			byte[] bs = mkexpected(Instruction.ldla8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.ldla(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldla16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.ldla(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldla32, 0x31337);
			ab = new AssemblyBuilder();
			ab.ldla(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ldla, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.ldla(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBra() {
		try {
			byte[] bs = mkexpected(Instruction.bra8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.bra(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bra16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.bra(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bra32, 0x31337);
			ab = new AssemblyBuilder();
			ab.bra(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bra, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.bra(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBsr() {
		try {
			byte[] bs = mkexpected(Instruction.bsr8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.bsr(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bsr16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.bsr(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bsr32, 0x31337);
			ab = new AssemblyBuilder();
			ab.bsr(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bsr, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.bsr(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBeq() {
		try {
			byte[] bs = mkexpected(Instruction.beq8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.beq(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.beq16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.beq(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.beq32, 0x31337);
			ab = new AssemblyBuilder();
			ab.beq(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.beq, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.beq(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBne() {
		try {
			byte[] bs = mkexpected(Instruction.bne8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.bne(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bne16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.bne(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bne32, 0x31337);
			ab = new AssemblyBuilder();
			ab.bne(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bne, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.bne(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBle() {
		try {
			byte[] bs = mkexpected(Instruction.ble8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.ble(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ble16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.ble(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ble32, 0x31337);
			ab = new AssemblyBuilder();
			ab.ble(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.ble, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.ble(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBlt() {
		try {
			byte[] bs = mkexpected(Instruction.blt8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.blt(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.blt16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.blt(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.blt32, 0x31337);
			ab = new AssemblyBuilder();
			ab.blt(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.blt, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.blt(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBge() {
		try {
			byte[] bs = mkexpected(Instruction.bge8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.bge(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bge16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.bge(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bge32, 0x31337);
			ab = new AssemblyBuilder();
			ab.bge(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bge, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.bge(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testBgt() {
		try {
			byte[] bs = mkexpected(Instruction.bgt8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.bgt(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bgt16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.bgt(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bgt32, 0x31337);
			ab = new AssemblyBuilder();
			ab.bgt(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.bgt, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.bgt(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testTrap() {
		try {
			byte[] bs = mkexpected(Instruction.trap8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.trap(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.trap16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.trap(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.trap32, 0x31337);
			ab = new AssemblyBuilder();
			ab.trap(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.trap, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.trap(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSta() {
		try {
			byte[] bs = mkexpected(Instruction.sta8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.sta(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.sta16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.sta(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.sta32, 0x31337);
			ab = new AssemblyBuilder();
			ab.sta(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.sta, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.sta(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSts() {
		try {
			byte[] bs = mkexpected(Instruction.sts8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.sts(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.sts16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.sts(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.sts32, 0x31337);
			ab = new AssemblyBuilder();
			ab.sts(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.sts, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.sts(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testStl() {
		try {
			byte[] bs = mkexpected(Instruction.stl8, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.stl(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.stl16, (short) 0x1337);
			ab = new AssemblyBuilder();
			ab.stl(0x1337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.stl32, 0x31337);
			ab = new AssemblyBuilder();
			ab.stl(0x31337);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.stl, 0x100031337L);
			ab = new AssemblyBuilder();
			ab.stl(0x100031337L);
			assertArrayEquals(bs, skipHeader(ab.generate()));			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testTwoArgs() {
		try {
			byte[] bs = mkexpected(Instruction.ldrr, (byte) 0x13, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.ldrr(0x13, 0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testOneArgs() {
		try {
			byte[] bs = mkexpected(Instruction.str, (byte) 0x37);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.str(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.ldr, (byte) 0x37);			
			ab = new AssemblyBuilder();
			ab.ldr(0x37);			
			assertArrayEquals(bs, skipHeader(ab.generate()));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testZeroArgs() {
		try {
			byte[] bs = mkexpected(Instruction.sth, null);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.sth();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.add, null);
			ab = new AssemblyBuilder();
			ab.add();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.sub, null);
			ab = new AssemblyBuilder();
			ab.sub();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.cmp, null);
			ab = new AssemblyBuilder();
			ab.cmp();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.mul, null);
			ab = new AssemblyBuilder();
			ab.mul();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.div, null);
			ab = new AssemblyBuilder();
			ab.div();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.mod, null);
			ab = new AssemblyBuilder();
			ab.mod();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.and, null);
			ab = new AssemblyBuilder();
			ab.and();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.or, null);
			ab = new AssemblyBuilder();
			ab.or();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.xor, null);
			ab = new AssemblyBuilder();
			ab.xor();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.neg, null);
			ab = new AssemblyBuilder();
			ab.neg();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.not, null);
			ab = new AssemblyBuilder();
			ab.not();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.jsr, null);
			ab = new AssemblyBuilder();
			ab.jsr();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.halt, null);
			ab = new AssemblyBuilder();
			ab.halt();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.noop, null);
			ab = new AssemblyBuilder();
			ab.noop();			
			assertArrayEquals(bs, skipHeader(ab.generate()));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testData() {
		try {
			byte[] bs = mkexpected(Instruction.sdata, (byte) 1, (byte) '!');			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.data((byte) '!');			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.sdata, (byte) 2, (byte) 0, (byte) '!');			
			ab = new AssemblyBuilder();
			ab.data((short) '!');			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.sdata, (byte) 4, (byte) 0, (byte) 0, (byte) 0, (byte) '!');			
			ab = new AssemblyBuilder();
			ab.data((int) '!');			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.sdata, (byte) 8, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) '!');			
			ab = new AssemblyBuilder();
			ab.data((long) '!');
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.mdata, (byte) 4, (byte) 1 , (byte) 'T', (byte) 'e', (byte) 's', (byte) 't');			
			ab = new AssemblyBuilder();
			ab.data("Test");
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.rdata, (byte) 0, (byte) 0,(byte) 0, (byte) 0x10, (byte) 0x13);			
			ab = new AssemblyBuilder();
			ab.repeat(0x10, (byte) 0x13);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.rdata, (byte) 0, (byte) 0,(byte) 0, (byte) 0x20, (byte) 0x0);			
			ab = new AssemblyBuilder();
			ab.offset(0x20);
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			
		} catch (IOException | BadAlignment e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLinking() {
		try {
			byte[] bs = mkexpected(Instruction.ldc8, (byte) -2);			
			AssemblyBuilder ab = new AssemblyBuilder();
			ab.label("test").ldc("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.rdata, (byte) 0, (byte) 0, (byte) (0x1), (byte)(0x00), (byte) 0, (byte) Instruction.ldc16.getOpCode(), (byte) 0xfe, (byte) 0xfe);			
			ab = new AssemblyBuilder();
			ab.label("test").offset(0x100).ldc("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));
			
			bs = mkexpected(Instruction.rdata, (byte) 0, (byte) 0x01, (byte) (0x00), (byte)(0x00), (byte) 0, (byte) Instruction.ldc32.getOpCode(),(byte) 0xff, (byte) 0xfe, (byte) 0xff, (byte) 0xfe);			
			ab = new AssemblyBuilder();
			ab.label("test").offset(0x10000).ldc("test").link();
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.ldh8, (byte) -2);			
			ab = new AssemblyBuilder();
			ab.label("test").ldh("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.lda8, (byte) -2);			
			ab = new AssemblyBuilder();
			ab.label("test").lda("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.ldaa8, (byte) -2);			
			ab = new AssemblyBuilder();
			ab.label("test").ldaa("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.lds8, (byte) -2);			
			ab = new AssemblyBuilder();
			ab.label("test").lds("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.ldsa8, (byte) -2);			
			ab = new AssemblyBuilder();
			ab.label("test").ldsa("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.ldl8, (byte) -2);			
			ab = new AssemblyBuilder();
			ab.label("test").ldl("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));

			bs = mkexpected(Instruction.ldla8, (byte) -2);			
			ab = new AssemblyBuilder();
			ab.label("test").ldla("test").link();			
			assertArrayEquals(bs, skipHeader(ab.generate()));
		} catch (IOException | UnresolvedLabel | BadAlignment e) {
			fail(e.getMessage());
		}
	}

	
}
