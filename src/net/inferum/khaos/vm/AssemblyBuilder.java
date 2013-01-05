/**
 * 
 */
package net.inferum.khaos.vm;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author basty
 *
 */
public class AssemblyBuilder {
	public static final long MAGIC_NUMBER = 4779199072498109771L;
	public static final int VERSION = KhaosVM.VERSION;
	
	public static final int FLAG_EXTENDED_INSTRUCTIONS = 1 << 0;

	private int instructionOffset(long arg) {
		if(!hasFlag(FLAG_EXTENDED_INSTRUCTIONS)) return 0;
		if(arg < 0) return instructionOffset(-arg);
		if((arg & 0xffL) == arg) return 3;
		if((arg & 0xffffL) == arg) return 2;
		if((arg & 0xffffffffL) == arg) return 1;
		return 0;
	}
	
	private void writeArgument(long arg) throws IOException {
		if(!hasFlag(FLAG_EXTENDED_INSTRUCTIONS)){
			dosBuffer.writeLong(arg);
			return;
		}
		switch(instructionOffset(arg)) {
		case 0: dosBuffer.writeLong(arg); break;
		case 1: dosBuffer.writeInt((int) (arg & 0xffffffffL)); break;
		case 2: dosBuffer.writeShort((short) (arg & 0xffffL)); break;
		case 3: dosBuffer.writeByte((byte) (arg & 0xffL)); break;
		}
	}
	
	private void writeInstruction(Instruction instr, long arg) throws IOException {
		if(!hasFlag(FLAG_EXTENDED_INSTRUCTIONS)) {
			dosBuffer.writeLong(instr.getOpCode());
		}else {
			dosBuffer.writeByte(instr.getOpCode() + instructionOffset(arg));
		}
	}
	
	private long[] register;
	private ByteArrayOutputStream buffer;
	private DataOutputStream dosBuffer;
	
	private int flags;
	
	public AssemblyBuilder() {
		this(new long[KhaosVM.DEFAULT_REGISTERS]);
	}
	
	public AssemblyBuilder(long[] register) {
		buffer = new ByteArrayOutputStream();
		dosBuffer = new DataOutputStream(buffer);
		this.register = register;
		flags = FLAG_EXTENDED_INSTRUCTIONS; 
	}
	
	public boolean hasFlag(int flag) {
		return (flags & flag) != 0;
	}

	public void setFlag(int flag, boolean value) {
		if(value)
			flags |= flag;
		else
			flags &= ~flag;
	}

	/**
	 * @return the register
	 */
	public long[] getRegister() {
		return register;
	}

	/**
	 * @param register the register to set
	 */
	public void setRegister(long[] register) {
		this.register = register;
	}

	public byte[] generate() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(buffer.size() + 256);
		DataOutputStream dos = new DataOutputStream(out);
		
		dos.writeLong(MAGIC_NUMBER);
		dos.writeInt(VERSION);
		
		dos.writeInt(flags);
		
		dos.writeByte(0);
		dos.writeByte(0);
		dos.writeByte(0);
		dos.writeByte(register.length);
		
		for(int i = 0; i < register.length; i++) {
			dos.writeLong(register[i]);
		}
		
		dos.writeInt(buffer.size());
		dos.write(buffer.toByteArray());
		
		return out.toByteArray();
	}
	
	public AssemblyBuilder ldc(long value) throws IOException {
		writeInstruction(Instruction.ldc , value);
		writeArgument(value);
		
		return this;
	}
		
	public AssemblyBuilder lda(long value) throws IOException {
		writeInstruction(Instruction.lda, value);
		writeArgument(value);
		
		return this;
	}
	
	public AssemblyBuilder ldaa(long value) throws IOException {
		writeInstruction(Instruction.ldaa, value);
		writeArgument(value);
		
		return this;
	}
	
	public AssemblyBuilder ldh(long value) throws IOException {
		writeInstruction(Instruction.ldh, value);
		writeArgument(value);
		
		return this;
	}
	
	public AssemblyBuilder ldl(long value) throws IOException {
		writeInstruction(Instruction.ldl, value);
		writeArgument(value);
		
		return this;
	}
	
	public AssemblyBuilder ldla(long value) throws IOException {
		writeInstruction(Instruction.ldla, value);
		writeArgument(value);
		
		return this;
	}
	
	public AssemblyBuilder ldr(long r) throws IOException {
		writeInstruction(Instruction.ldr, Long.MAX_VALUE);
		writeArgument(r & 0xff);
		
		return this;
	}
	
	public AssemblyBuilder ldrr(long rDst, long rSrc) throws IOException {
		writeInstruction(Instruction.ldrr, Long.MAX_VALUE);
		writeArgument(rDst & 0xff);
		writeArgument(rSrc & 0xff);
		
		return this;
	}
	
	public AssemblyBuilder lds(long pos) throws IOException {
		writeInstruction(Instruction.lds, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder ldsa(long pos) throws IOException {
		writeInstruction(Instruction.ldsa, pos);
		writeArgument(pos);
		
		return this;
	}
	
	
	public AssemblyBuilder sta(long pos) throws IOException {
		writeInstruction(Instruction.sta, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder sth() throws IOException {
		writeInstruction(Instruction.sth, Long.MAX_VALUE);
		
		return this;
	}
	
	public AssemblyBuilder stl(long pos) throws IOException {
		writeInstruction(Instruction.stl, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder str(long pos) throws IOException {
		writeInstruction(Instruction.str, Long.MAX_VALUE);
		writeArgument(pos & 0xff);
		
		return this;
	}

	public AssemblyBuilder sts(long pos) throws IOException {
		writeInstruction(Instruction.sts, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder trap(long pos) throws IOException {
		writeInstruction(Instruction.trap, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder bra(long pos) throws IOException {
		writeInstruction(Instruction.bra, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder bsr(long pos) throws IOException {
		writeInstruction(Instruction.bsr, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder beq(long pos) throws IOException {
		writeInstruction(Instruction.beq, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder bne(long pos) throws IOException {
		writeInstruction(Instruction.bne, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder bge(long pos) throws IOException {
		writeInstruction(Instruction.bge, pos);
		writeArgument(pos);
		
		return this;
	}

	public AssemblyBuilder bgt(long pos) throws IOException {
		writeInstruction(Instruction.bgt, pos);
		writeArgument(pos);
		
		return this;
	}

	public AssemblyBuilder ble(long pos) throws IOException {
		writeInstruction(Instruction.ble, pos);
		writeArgument(pos);
		
		return this;
	}

	public AssemblyBuilder blt(long pos) throws IOException {
		writeInstruction(Instruction.blt, pos);
		writeArgument(pos);
		
		return this;
	}
	
	public AssemblyBuilder add() throws IOException {
		writeInstruction(Instruction.add, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder sub() throws IOException {
		writeInstruction(Instruction.sub, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder mul() throws IOException {
		writeInstruction(Instruction.mul, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder div() throws IOException {
		writeInstruction(Instruction.div, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder mod() throws IOException {
		writeInstruction(Instruction.mod, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder and() throws IOException {
		writeInstruction(Instruction.and, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder or() throws IOException {
		writeInstruction(Instruction.or, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder xor() throws IOException {
		writeInstruction(Instruction.xor, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder neg() throws IOException {
		writeInstruction(Instruction.neg, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder not() throws IOException {
		writeInstruction(Instruction.not, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder halt() throws IOException {
		writeInstruction(Instruction.halt, Long.MAX_VALUE);
		return this;
	}
	
	public AssemblyBuilder noop() throws IOException {
		writeInstruction(Instruction.noop, Long.MAX_VALUE);
		return this;
	}

	public AssemblyBuilder cmp() throws IOException {
		writeInstruction(Instruction.cmp, Long.MAX_VALUE);
		return this;
	}
}
