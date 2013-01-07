/**
 * 
 */
package net.inferum.khaos.vm;

/**
 * @author basty
 *
 */
public enum Instruction {
	// misc
	noop(0x00),
	halt(0x80),
	trap8(0xf3, 1),
	trap16(0xf2, 1),
	trap32(0xf1, 1),
	trap(0xf0, 1),
	rdata(0xfd, 2),
	sdata(0xfe, 1),
	mdata(0xff, 2),
	// arithmetic
	add(0x01),
	sub(0x02),
	mul(0x03),
	div(0x04),
	mod(0x05),
	neg(0x06),
	// logic
	or(0x81),
	cmp(0x82),
	and(0x83),
	xor(0x84),
	not(0x86),
	// branching
	bra(0x60, 1),
	bra32(0x61, 1),
	bra16(0x62, 1),
	bra8(0x63, 1),
	beq(0x64, 1),
	beq32(0x65, 1),
	beq16(0x66, 1),
	beq8(0x67, 1),
	bne(0x68, 1),
	bne32(0x69, 1),
	bne16(0x6a, 1),
	bne8(0x6b, 1),
	blt(0x6c, 1),
	blt32(0x6d, 1),
	blt16(0x6e, 1),
	blt8(0x6f, 1),
	bgt(0x70, 1),
	bgt32(0x71, 1),
	bgt16(0x72, 1),
	bgt8(0x73, 1),
	ble(0x74, 1),
	ble32(0x75, 1),
	ble16(0x76, 1),
	ble8(0x77, 1),
	bge(0x78, 1),
	bge32(0x79, 1),
	bge16(0x7a, 1),
	bge8(0x7b, 1),
	bsr(0x7c, 1),
	bsr32(0x7d, 1),
	bsr16(0x7e, 1),
	bsr8(0x7f, 1),
	jsr(0x87),
	// push
	ldc(0x10,1),
	ldc32(0x11,1),
	ldc16(0x12,1),
	ldc8(0x13,1),
	lda(0x14,1),
	lda32(0x15,1),
	lda16(0x16,1),
	lda8(0x17,1),
	lds(0x18,1),
	lds32(0x19,1),
	lds16(0x1a,1),
	lds8(0x1b,1),
	ldh(0x1c,1),
	ldh32(0x1d,1),
	ldh16(0x1e,1),
	ldh8(0x1f,1),
	ldl(0x20,1),
	ldl32(0x21,1),
	ldl16(0x22,1),
	ldl8(0x23,1),
	ldsa(0x24,1),
	ldsa32(0x25,1),
	ldsa16(0x26,1),
	ldsa8(0x27,1),
	ldla(0x28,1),
	ldla32(0x29,1),
	ldla16(0x2a,1),
	ldla8(0x2b,1),
	ldaa(0x2c,1),
	ldaa32(0x2d,1),
	ldaa16(0x2e,1),
	ldaa8(0x2f,1),
	ldr(0x30,1),
	ldrr(0x31,2),
	// pop
	sts(0x34,1),
	sts32(0x35,1),
	sts16(0x36,1),
	sts8(0x37,1),
	stl(0x38,1),	
	stl32(0x39,1),	
	stl16(0x3a,1),	
	stl8(0x3b,1),	
	sta(0x3c,1),
	sta32(0x3d,1),
	sta16(0x3e,1),
	sta8(0x3f,1),
	str(0x40,1), // -> register	
	sth(0x41),
	;

	static{
		Instruction[] instructions = Instruction.values();
		for(int i = 0; i < instructions.length; i++)
			for(int j = i+1; j < instructions.length; j++)
				assert(instructions[i].opCode != instructions[j].opCode);
	}
	
	private int opCode;
	private int arguments;
	
	private Instruction(int op) {
		this(op, 0);
	}

	private Instruction(int op, int args) {
		this.opCode = op;
		this.arguments = args;
	}
	
	public int getArguments(){
		return arguments;
	}
	
	/**
	 * @return the opCode
	 */
	public int getOpCode() {
		return opCode;
	}
	
	public static Instruction valueOf(int opCode) {
		for (Instruction instruction : Instruction.values()) {
			if(instruction.opCode == opCode) return instruction;
		}
		return null;
	}
}
