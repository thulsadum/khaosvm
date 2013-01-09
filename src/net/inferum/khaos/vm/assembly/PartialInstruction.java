/**
 * 
 */
package net.inferum.khaos.vm.assembly;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import net.inferum.khaos.vm.AssemblyBuilder;
import net.inferum.khaos.vm.exception.UnresolvedLabel;

/**
 * a partial instruction is used to resolve link references
 * @author basty
 *
 */
public class PartialInstruction extends Instruction {
	/**
	 * @param instruction
	 * @param alignable
	 * @param unalignableWidth
	 * @param arguments
	 */
	public PartialInstruction(int offset, net.inferum.khaos.vm.Instruction instruction,
			boolean alignable, int unalignableWidth, String... arguments) {
		super(instruction, alignable, unalignableWidth, null);
		
		references = new HashMap<>();
		this.offset = offset;
		
		long[] actualArguments = new long[arguments.length];
		for (int i = 0; i < actualArguments.length; i++) {
			try{
				actualArguments[i] = Long.parseLong(arguments[i]);
			}catch(NumberFormatException e){
				references.put(arguments[i], i);
				actualArguments[i] = -1;
			}
		}
		
		setArguments(actualArguments);
	}

	private HashMap<String, Integer> references;
	private int offset;
	
	public Instruction resolve(AssemblyBuilder ab) throws UnresolvedLabel {
		Integer address;
		String label;
		boolean absolute;
		for (Entry<String, Integer> e : references.entrySet()) {
			label = e.getKey();
			absolute = false;
			if(label.charAt(0) == '@'){
				label = label.substring(1);
				absolute = true;
			}
			address = ab.getLabels().get(label);
			
			if(address == null) throw new UnresolvedLabel(label);
			
			getArguments()[e.getValue()] = address.intValue() - (absolute?0:(offset + getArguments().length + 1)); 
		}
		return new Instruction(this);
	}
	
	/* (non-Javadoc)
	 * @see net.inferum.khaos.vm.assembly.Instruction#write(java.io.OutputStream)
	 */
	@Override
	public void write(OutputStream out) throws IOException {
		throw new RuntimeException("This is just a placeholder for linking and should be resolved!");
	}

}
