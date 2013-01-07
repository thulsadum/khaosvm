/**
 * 
 */
package net.inferum.khaos.vm;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.inferum.khaos.vm.exception.AssemblyException;
import net.inferum.khaos.vm.exception.BadFormat;
import net.inferum.khaos.vm.exception.BadVersion;

/**
 * Assembly format is:
 * 
 * Magic-Number = "BS <3 MK" (8 byte)
 * Version (4 byte)
 * Flags (4 byte)
 * 0 (3 byte)
 * Registers (1 byte)
 * Initial Register States (40 byte <- PC + SP + MP + RR + HP)
 * CodeLength (4 byte)
 * Code ...
 * 
 * @author basty
 *
 */
public class AssemblyLoader {	
	private InputStream stream;
	private long[] register;
	private int flags;

	/**
	 * @param stream
	 */
	public AssemblyLoader(InputStream stream) {
		this.stream = stream;
	}

	/**
	 */
	public AssemblyLoader() {
	}

	/**
	 * @return the stream
	 */
	public InputStream getStream() {
		return stream;
	}

	public int getRegisterCount() {
		return register.length;
	}
	
	public long getRegister(int register) {
		return this.register[register];
	}
	
	/**
	 * @param stream the stream to set
	 */
	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	

	public long[] load(int additionalMemory) throws IOException, AssemblyException {
		long[] code;
		/* TODO add unit tests for this */
		DataInputStream dis = new DataInputStream(stream);
		
		int version, length, noRegister;

		dis.skip(8); // skip magic number ;)
		
		version = dis.readInt();		
		if(version != KhaosVM.VERSION) throw new BadVersion(KhaosVM.VERSION, version);
		
		flags = dis.readInt();
		
		dis.skip(3);		
		noRegister = dis.readByte() & 0xff;
		register = new long[noRegister];
		for(int i = 0; i < noRegister; i++)
			register[i] = dis.readLong();
		
		length = dis.readInt();
		code = new long[length + additionalMemory];
		
		int pinstr, pos = 0, dlen, dcount;
		
		while((pinstr = dis.read()) != -1) {
			dcount = 1;
			switch (Instruction.valueOf(pinstr)) {
			case add:
			case and:
			case cmp:
			case div:
			case halt:
			case jsr:
			case mod:
			case mul:
			case neg:
			case noop:
			case not:
			case or:
			case sub:
			case xor:
			case sth:
				code[pos++] = pinstr;
				break;
			case beq:
			case bge:
			case bgt:
			case ble:
			case blt:
			case bne:
			case bra:
			case bsr:
			case lda:
			case ldaa:
			case ldc:
			case ldh:
			case ldl:
			case ldla:
			case lds:
			case ldsa:
			case sta:
			case stl:
			case sts:
			case trap:
				code[pos++] = pinstr;
				code[pos++] = dis.readLong();
				break;
			case beq16:
				code[pos++] = Instruction.beq.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case beq32:
				code[pos++] = Instruction.beq.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case beq8:
				code[pos++] = Instruction.beq.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case bge16:
				code[pos++] = Instruction.bge.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case bge32:
				code[pos++] = Instruction.bge.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case bge8:
				code[pos++] = Instruction.bge.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case bgt16:
				code[pos++] = Instruction.bgt.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case bgt32:
				code[pos++] = Instruction.bgt.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case bgt8:
				code[pos++] = Instruction.bgt.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case ble16:
				code[pos++] = Instruction.ble.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case ble32:
				code[pos++] = Instruction.ble.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case ble8:
				code[pos++] = Instruction.ble.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case blt16:
				code[pos++] = Instruction.blt.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case blt32:
				code[pos++] = Instruction.blt.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case blt8:
				code[pos++] = Instruction.blt.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case bne16:
				code[pos++] = Instruction.bne.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case bne32:
				code[pos++] = Instruction.bne.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case bne8:
				code[pos++] = Instruction.bne.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case bra16:
				code[pos++] = Instruction.bra.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case bra32:
				code[pos++] = Instruction.bra.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case bra8:
				code[pos++] = Instruction.bra.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case bsr16:
				code[pos++] = Instruction.bsr.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case bsr32:
				code[pos++] = Instruction.bsr.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case bsr8:
				code[pos++] = Instruction.bsr.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case lda16:
				code[pos++] = Instruction.lda.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case lda32:
				code[pos++] = Instruction.lda.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case lda8:
				code[pos++] = Instruction.lda.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case ldaa16:
				code[pos++] = Instruction.ldaa.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case ldaa32:
				code[pos++] = Instruction.ldaa.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case ldaa8:
				code[pos++] = Instruction.ldaa.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case ldc16:
				code[pos++] = Instruction.ldc.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case ldc32:
				code[pos++] = Instruction.ldc.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case ldc8:
				code[pos++] = Instruction.ldc.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case ldh16:
				code[pos++] = Instruction.ldh.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case ldh32:
				code[pos++] = Instruction.ldh.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case ldh8:
				code[pos++] = Instruction.ldh.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case ldl16:
				code[pos++] = Instruction.ldl.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case ldl32:
				code[pos++] = Instruction.ldl.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case ldl8:
				code[pos++] = Instruction.ldl.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case ldla16:
				code[pos++] = Instruction.ldla.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case ldla32:
				code[pos++] = Instruction.ldla.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case ldla8:
				code[pos++] = Instruction.ldla.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case lds16:
				code[pos++] = Instruction.lds.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case lds32:
				code[pos++] = Instruction.lds.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case lds8:
				code[pos++] = Instruction.lds.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case ldsa16:
				code[pos++] = Instruction.ldsa.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case ldsa32:
				code[pos++] = Instruction.ldsa.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case ldsa8:
				code[pos++] = Instruction.ldsa.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case sta16:
				code[pos++] = Instruction.sta.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case sta32:
				code[pos++] = Instruction.sta.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case sta8:
				code[pos++] = Instruction.sta.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case stl16:
				code[pos++] = Instruction.stl.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case stl32:
				code[pos++] = Instruction.stl.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case stl8:
				code[pos++] = Instruction.stl.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case sts16:
				code[pos++] = Instruction.sts.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case sts32:
				code[pos++] = Instruction.sts.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case sts8:
				code[pos++] = Instruction.sts.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case trap16:
				code[pos++] = Instruction.trap.getOpCode();
				code[pos++] = dis.readShort();
				break;
			case trap32:
				code[pos++] = Instruction.trap.getOpCode();
				code[pos++] = dis.readInt();
				break;
			case trap8:
				code[pos++] = Instruction.trap.getOpCode();
				code[pos++] = dis.readByte();
				break;
			case str:
			case ldr:
				code[pos++] = pinstr;
				code[pos++] = dis.readByte() & 0xff;
				break;
			case ldrr:
				code[pos++] = pinstr;
				code[pos++] = dis.readByte() & 0xff;
				code[pos++] = dis.readByte() & 0xff;
				break;
			case rdata:
				dcount = dis.readInt();
				byte data = dis.readByte();
				for(int i = 0; i < dcount; i++)
					code[pos++] = data;
				break;
			case mdata:
				dcount = dis.readByte() & 0xff;
			case sdata:
				dlen = dis.readByte() & 0xff;
				for(int i = 0; i < dcount; i++) {
					switch(dlen) {
					case 1:
						code[pos++] = dis.readByte();
						break;
					case 2:
						code[pos++] = dis.readShort();
						break;
					case 4:
						code[pos++] = dis.readInt();
						break;
					case 8:
						code[pos++] = dis.readLong();
						break;
					default:
						throw new BadFormat("Data length must be one of 1, 2, 4, 8 byte!");
					}
				}
				break;
			}
		}

		return code;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public boolean hasFlag(int f) {
		return (flags & f) != 0;
	}
}
