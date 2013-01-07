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
public class Data extends Instruction {
	private long[] data;
	private int width;
	
	/**
	 * @param instruction
	 * @param arguments
	 */
	public Data(long[] data) {
		this(8, data);
	}

	/**
	 * @param i
	 * @param data
	 */
	public Data(int width, long[] data) {
		super(null, null);
		this.width = width;
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
			if(data.length == 1) {
				dos.writeByte(net.inferum.khaos.vm.Instruction.sdata.getOpCode());
				dos.writeByte(width);
				switch (width) {
				case 1: dos.writeByte((byte) data[0]); break;
				case 2: dos.writeShort((short) data[0]); break;
				case 4: dos.writeInt((int) data[0]); break;
				case 8: dos.writeLong((long) data[0]); break;
				}
			}else{
				int len = data.length;
				int offset = 0;
				while(len > 0){
					dos.writeByte(net.inferum.khaos.vm.Instruction.mdata.getOpCode());
					dos.writeByte(len < 255?len:255);
					dos.writeByte(width);
					for(int i = 0; i < len && i < 255; i++){
						switch (width) {
						case 1: dos.writeByte((byte) data[i + offset]); break;
						case 2: dos.writeShort((short) data[i + offset]); break;
						case 4: dos.writeInt((int) data[i + offset]); break;
						case 8: dos.writeLong((long) data[i + offset]); break;
						}
					}
					len -= 255;
					offset += 255;
				}
			}
		}else{
			for (long d : data) {
				dos.writeLong(d);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.inferum.khaos.vm.assembly.Instruction#getWords()
	 */
	@Override
	public int getWords() {
		return data.length; 
	}
	
	/**
	 * @return the data
	 */
	public long[] getData() {
		return data;
	}
}
