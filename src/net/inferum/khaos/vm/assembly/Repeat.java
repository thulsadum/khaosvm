/**
 * 
 */
package net.inferum.khaos.vm.assembly;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.inferum.khaos.vm.Instruction;

/**
 * @author basty
 *
 */
public class Repeat extends Data {
	private byte data;
	private int repeats;
	
	/**
	 * @param i
	 * @param data
	 */
	public Repeat(int repeats, byte data) {
		super(null);
		if(repeats < 0) throw new RuntimeException("expect postive number of repeats: " + repeats);
		this.repeats = repeats;
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see net.inferum.khaos.vm.assembly.Instruction#write(java.io.OutputStream, boolean)
	 */
	@Override
	public void write(OutputStream out, boolean align) throws IOException {
		DataOutputStream dos;
		if(out instanceof DataOutputStream) 
			dos = (DataOutputStream) out;
		else
			dos = new DataOutputStream(out);
		
		if(align){
			dos.writeByte(Instruction.rdata.getOpCode());
			dos.writeInt(repeats);
			dos.writeByte(data);
		}else{
			for (int i = 0; i < repeats; i++) {
				dos.writeLong(data);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.inferum.khaos.vm.assembly.Instruction#getWords()
	 */
	@Override
	public int getWords() {
		return repeats; 
	}
}
