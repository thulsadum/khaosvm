/**
 * 
 */
package net.inferum.khaos.vm;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.inferum.khaos.vm.assembly.Data;
import net.inferum.khaos.vm.assembly.PartialInstruction;
import net.inferum.khaos.vm.assembly.Repeat;
import net.inferum.khaos.vm.exception.BadAlignment;
import net.inferum.khaos.vm.exception.UnresolvedLabel;

/**
 * @author basty
 *
 */
public class AssemblyBuilder {
	public static final long MAGIC_NUMBER = 4779199072498109771L;
	public static final int VERSION = KhaosVM.VERSION;
	
	public static final int FLAG_EXTENDED_INSTRUCTIONS = 1 << 0;

	private long[] register;
	private int flags;
	private ArrayList<net.inferum.khaos.vm.assembly.Instruction> program;
	private HashMap<String, Integer> labels;
	private ArrayList<Integer> references;
	
	private int numberOfDataWords;
	private int oldSize;
	
	public AssemblyBuilder() {
		this(new long[KhaosVM.DEFAULT_REGISTERS]);
	}
	
	public AssemblyBuilder(long[] register) {
		program = new ArrayList<>();
		this.register = register;
		flags = FLAG_EXTENDED_INSTRUCTIONS;
		labels = new HashMap<>();
		references = new ArrayList<>();
		numberOfDataWords = 0;
		oldSize = 0;
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

	public byte[] generate() throws IOException  {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		boolean align = hasFlag(FLAG_EXTENDED_INSTRUCTIONS);
		for (net.inferum.khaos.vm.assembly.Instruction instruction : program) {
			instruction.write(buffer, align);
		}
		
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
		
		dos.writeInt(getCurrentOffset());
		dos.write(buffer.toByteArray());
		
		return out.toByteArray();
	}

	public AssemblyBuilder ldc(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.ldc, true, 0, value));
		
		return this;
	}
	
	public AssemblyBuilder lda(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.lda, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder ldaa(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.ldaa, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder ldh(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.ldh, true, 0, value));
		
		return this;
	}
	
	public AssemblyBuilder ldl(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.ldl, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder ldla(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.ldla, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder lds(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.lds, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder ldsa(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.ldsa, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder sta(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.sta, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder sts(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.sts, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder stl(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.stl, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder bra(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.bra, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder bsr(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.bsr, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder beq(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.beq, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder bne(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.bne, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder bge(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.bge, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder bgt(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.bgt, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder ble(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.ble, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder blt(String value)  {
		references.add(program.size());
		program.add(new PartialInstruction(getCurrentOffset(), Instruction.blt, true, 0, value));
		
		return this;
	}

	public AssemblyBuilder ldc(long value)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ldc, value));
		
		return this;
	}
		
	public AssemblyBuilder lda(long value)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.lda, value));
		
		return this;
	}
	
	public AssemblyBuilder ldaa(long value)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ldaa, value));
		
		return this;
	}
	
	public AssemblyBuilder ldh(long value)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ldh, value));
		
		return this;
	}
	
	public AssemblyBuilder ldl(long value)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ldl, value));
		
		return this;
	}
	
	public AssemblyBuilder ldla(long value)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ldla, value));
		
		return this;
	}
	
	public AssemblyBuilder ldr(long r)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ldr, false, 1, r));
		
		return this;
	}
	
	public AssemblyBuilder ldr(String r)  {
		return ldr(KhaosVM.REGISTER_RESOLVE.get(r));
	}

	public AssemblyBuilder ldrr(long rDst, long rSrc)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ldrr, false, 1, rDst, rSrc));
		
		return this;
	}

	public AssemblyBuilder ldrr(String rDst, String rSrc)  {
		return ldrr(KhaosVM.REGISTER_RESOLVE.get(rDst), KhaosVM.REGISTER_RESOLVE.get(rSrc));
	}
	
	public AssemblyBuilder lds(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.lds, pos));
		
		return this;
	}
	
	public AssemblyBuilder ldsa(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ldsa, pos));
		
		return this;
	}
	
	
	public AssemblyBuilder sta(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.sta, pos));
		
		return this;
	}
	
	public AssemblyBuilder sth()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.sth, null));
		
		return this;
	}
	
	public AssemblyBuilder stl(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.stl, pos));
		
		return this;
	}
	
	public AssemblyBuilder str(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.str,false, 1, pos));
		
		return this;
	}

	public AssemblyBuilder str(String register)  {
		return str(KhaosVM.REGISTER_RESOLVE.get(register));
	}

	public AssemblyBuilder sts(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.sts, pos));
		
		return this;
	}
	
	public AssemblyBuilder trap(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.trap, pos));
		
		return this;
	}
	
	public AssemblyBuilder bra(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.bra, pos));
		
		return this;
	}
	
	public AssemblyBuilder bsr(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.bsr, pos));
		
		return this;
	}
	
	public AssemblyBuilder jsr()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.jsr));
		
		return this;
	}
	
	public AssemblyBuilder beq(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.beq, pos));
		
		return this;
	}
	
	public AssemblyBuilder bne(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.bne, pos));
		
		return this;
	}
	
	public AssemblyBuilder bge(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.bge, pos));
		
		return this;
	}

	public AssemblyBuilder bgt(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.bgt, pos));
		
		return this;
	}

	public AssemblyBuilder ble(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ble, pos));
		
		return this;
	}

	public AssemblyBuilder blt(long pos)  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.blt, pos));
		
		return this;
	}
	
	public AssemblyBuilder add()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.add, null));
		return this;
	}
	
	public AssemblyBuilder sub()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.sub, null));
		return this;
	}
	
	public AssemblyBuilder mul()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.mul, null));
		return this;
	}
	
	public AssemblyBuilder div()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.div, null));
		return this;
	}
	
	public AssemblyBuilder mod()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.mod, null));
		return this;
	}
	
	public AssemblyBuilder and()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.and, null));
		return this;
	}
	
	public AssemblyBuilder or()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.or, null));
		return this;
	}
	
	public AssemblyBuilder xor()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.xor, null));
		return this;
	}
	
	public AssemblyBuilder neg()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.neg, null));
		return this;
	}
	
	public AssemblyBuilder not()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.not, null));
		return this;
	}
	
	public AssemblyBuilder halt()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.halt, null));
		return this;
	}
	
	public AssemblyBuilder noop()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.noop, null));
		return this;
	}

	public AssemblyBuilder cmp()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.cmp, null));
		return this;
	}
	
	public AssemblyBuilder ret()  {
		program.add(new net.inferum.khaos.vm.assembly.Instruction(Instruction.ret, null));
		return this;
	}
	
	public AssemblyBuilder data(byte... data) {
		long[] d = new long[data.length];
		for(int i = 0; i < data.length; i++)
			d[i] = data[i];
		this.data(1, d);
		return this;
	}
	
	public AssemblyBuilder data(short... data) {
		long[] d = new long[data.length];
		for(int i = 0; i < data.length; i++)
			d[i] = data[i];
		this.data(2, d);
		return this;
	}
	
	public AssemblyBuilder data(int... data) {
		long[] d = new long[data.length];
		for(int i = 0; i < data.length; i++)
			d[i] = data[i];
		this.data(4, d);
		return this;
	}

	
	public AssemblyBuilder data(long... data) {
		long[] d = new long[data.length];
		for(int i = 0; i < data.length; i++)
			d[i] = data[i];
		this.data(8, d);
		return this;
	}
	
	public AssemblyBuilder data(String data) {
		byte[] bs = data.getBytes();
		long[] d = new long[bs.length];
		for(int i = 0; i < bs.length; i++)
			d[i] = bs[i];
		this.data(1, d);
		return this;
	}
	
	public AssemblyBuilder data(int width, long[] data) {
		program.add(new Data(width, data));
		return this;
	}

	public AssemblyBuilder label(String label) {
		labels.put(label, getCurrentOffset());
		return this;
	}
	

	/**
	 * @return the numberOfDataWords
	 */
	public int getCurrentOffset() {
		if(oldSize == program.size()) return numberOfDataWords;
		int count = 0;
		for (net.inferum.khaos.vm.assembly.Instruction instruction : program) {
			count += instruction.getWords();
		}
		oldSize = program.size();
		numberOfDataWords = count;
		return numberOfDataWords;
	}
	
	public AssemblyBuilder link() throws UnresolvedLabel {
		for (Integer i : references) {
			program.set(i, ((PartialInstruction) program.get(i)).resolve(this));
		}
		references.clear();
		return this;
	}
	
	public AssemblyBuilder repeat(int amount, byte data) {
		program.add(new Repeat(amount, data));
		return this;
	}
	
	public AssemblyBuilder offset(int newOffset) throws BadAlignment {
		int diff = newOffset - getCurrentOffset();
		if(diff < 0) throw new BadAlignment(getCurrentOffset(), newOffset);
		return repeat(diff, (byte) 0);
	}

	public AssemblyBuilder append(AssemblyBuilder other) {
		for (Entry<String, Integer> e : other.labels.entrySet()) {
			labels.put(e.getKey(), getCurrentOffset() + e.getValue());
		}
		for(Integer ref :other.references) {
			this.references.add(ref.intValue() + program.size());
		}
		program.addAll(other.program);
		return this;
	}
	
	/**
	 * @return the labels
	 */
	public HashMap<String, Integer> getLabels() {
		return labels;
	}
	
	public void clear() {
		labels.clear();
		references.clear();
		program.clear();
		numberOfDataWords = 0;
		oldSize = 0;
	}
}
