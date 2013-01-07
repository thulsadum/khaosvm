/**
 * 
 */
package net.inferum.khaos.vm.assembly;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author basty
 *
 */
public class Instruction {
	private net.inferum.khaos.vm.Instruction instruction;
	private long[] arguments;
	private boolean alignable;
	private int unalignableWidth;

	public Instruction(net.inferum.khaos.vm.Instruction instruction, long... arguments) {
		this(instruction, true, 8, arguments);
	}

		/**
	 * @param opCode
	 * @param arguments
	 */
	public Instruction(net.inferum.khaos.vm.Instruction instruction, boolean alignable, int unalignableWidth, long... arguments) {
		this.instruction = instruction;
		this.arguments = arguments;
		this.alignable = alignable;
		this.unalignableWidth = unalignableWidth;
	}

	/**
		 * @param partialInstruction
		 */
		protected Instruction(Instruction base) {
			instruction = base.instruction;
			if(base.arguments != null) arguments = base.arguments.clone();
			alignable = base.alignable;
			unalignableWidth = base.unalignableWidth;
		}

	/**
	 * @return the instruction
	 */
	public net.inferum.khaos.vm.Instruction getInstruction() {
		return instruction;
	}

	/**
	 * @param instruction the instruction to set
	 */
	public void setInstruction(net.inferum.khaos.vm.Instruction instruction) {
		this.instruction = instruction;
	}

	/**
	 * @return the arguments
	 */
	public long[] getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(long[] arguments) {
		this.arguments = arguments;
	}

	public void write(OutputStream out) throws IOException{
		write(out, true);
	}

	public void write(OutputStream out, boolean align) throws IOException{
		DataOutputStream dos;
		int width = 8;
		int instructionOffset = 0;
		
		if(out instanceof DataOutputStream)
			dos = (DataOutputStream) out;
		else
			dos = new DataOutputStream(out);

		if(arguments != null && arguments.length > 0) {
			if(alignable && align) {
				int maxWidth = 0;
				long t;
				for (int i = 0; i < arguments.length; i++) {
					t = arguments[i] >= 0 ? arguments[i] : -arguments[i];
					if((t & 0xffL) == t && maxWidth < 1){
						maxWidth = 1;
						instructionOffset = 3;
					}else if((t & 0xffffL) == t && maxWidth < 2){
						maxWidth = 2;
						instructionOffset = 2;
					}else if((t & 0xffffffffL) == t && maxWidth < 4){
						maxWidth = 4;
						instructionOffset = 1;
					}else {
						maxWidth = 8;
						instructionOffset = 0;
						break;
					}
				}
				width = maxWidth;
			}else{
				width = unalignableWidth;
			}
		}
		
		if(align) {
			dos.write(instruction.getOpCode() + instructionOffset);
			if(arguments != null && arguments.length > 0){
				for (int i = 0; i < arguments.length; i++) {
					switch (width) {
					case 1: dos.writeByte((byte) (arguments[i] & 0xffL)); break;
					case 2: dos.writeShort((short) (arguments[i] & 0xffffL)); break;
					case 4: dos.writeInt((int) (arguments[i] & 0xffffffffL)); break;
					case 8: dos.writeLong((long) (arguments[i] & 0xffffffffffffffffL)); break;
					}
				}
			}
		}else{
			dos.writeLong(instruction.getOpCode());
			if(arguments != null && arguments.length > 0)
				for (long arg : arguments) {
					dos.writeLong(arg);
				}
		}
	}
	
	public int getWords(){
		if(arguments != null) return 1 + arguments.length;
		return 1;
	}
	
}
