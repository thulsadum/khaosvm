/**
 * 
 */
package net.inferum.khaos.vm;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import net.inferum.khaos.vm.exception.BadOpCode;
import net.inferum.khaos.vm.exception.KVMException;
import net.inferum.khaos.vm.exception.MachineHalted;

/**
 * Khaos Virtual Machine a stack machine implementation
 * 
 * @author basty
 * 
 */
public class KhaosVM implements Comparable<KhaosVM> {
	/**
	 * Version of the KhaosVM a change of this number will indicate possible
	 * (i.e. certain) incompatible format/register/w/e changes.
	 */
	public static final int VERSION = 2;

	/**
	 * number of fixed registers (PC, SP, MP, RR, HP)
	 */
	public static final int FIXED_REGISTERS = 5;

	/**
	 * the length of a regular data word in bytes
	 */
	// public static final int WORD_SIZE = 8;

	/**
	 * number of default registers. should not be less than
	 * {@link KhaosVM#FIXED_REGISTERS}
	 */
	public static final int DEFAULT_REGISTERS = 8;

	public static final int DEFAULT_MEMORY = 0x8000;

	/**
	 * index of the program counter register
	 */
	public static final int PCI = 0;

	/**
	 * index of the stack pointer register
	 */
	public static final int SPI = 1;

	/**
	 * index of the mark pointer register
	 */
	public static final int MPI = 2;

	/**
	 * index of the return address register
	 */
	public static final int RRI = 3;

	/**
	 * index of the heap pointer register
	 */
	public static final int HPI = 4;

	/* status flags */
	/**
	 * the bit set when the machine is halted.
	 * 
	 * A program will never be able to check a set halt-bit, it's just used for
	 * external checks.
	 */
	public static final long STATUS_HALT = 1 << 32;

	/**
	 * this bit is set whenever the machine triggered an exception (e.g.
	 * arithmetic exception, i.e. division by 0)
	 */
	public static final long STATUS_EXCEPTION = 1 << 31;

	/**
	 * this flag is set, when traps are disabled
	 */
	public static final long STATUS_NO_TRAPS = 1 << 0;

	/**
	 * this flag is set, when we currently handle a trap
	 */
	public static final long STATUS_TRAPPED = 1 << 1;

	public static final long TRAP_READ = 0x00;
	public static final long TRAP_WRITE = 0x01;
	public static final long TRAP_REG_TRAP = 0x80;
	
	public static final HashMap<String, Integer> REGISTER_RESOLVE;
	
	static {
		REGISTER_RESOLVE = new HashMap<>();
		REGISTER_RESOLVE.put("PC", PCI);
		REGISTER_RESOLVE.put("SP", SPI);
		REGISTER_RESOLVE.put("MP", MPI);
		REGISTER_RESOLVE.put("HP", HPI);
		REGISTER_RESOLVE.put("RR", RRI);
		REGISTER_RESOLVE.put("R0", FIXED_REGISTERS + 0);
		REGISTER_RESOLVE.put("R1", FIXED_REGISTERS + 1);
		REGISTER_RESOLVE.put("R2", FIXED_REGISTERS + 2);
	}
	
	private long[] memory; // memory
	private long[] register; // number of aux registers
	private long status;
	
	private InputStream input;
	private PrintStream output, error, debug;

	private HashMap<Long, Stack<TrapHandler>> traps;
	private boolean trace;

	private TrapHandler thDefaults = new TrapHandler() {
		@Override
		public void trap(long number, KhaosVM kvm) {
			long stream, value;
			long trap, location;
			
			if (number == TRAP_READ) {
				stream = kvm.getRegister(0);
				if(stream == 0) {
					try {
						value = kvm.getInput().read();
					} catch (IOException e) {
						// TODO put machine into an exceptional state?
						value = -1;
					}
				}else{
					value = -1;
				}
				kvm.setRR(value);
			} else if (number == TRAP_WRITE) {
				stream = kvm.getRegister(0);
				value = kvm.getRegister(1);
				if (stream == 1) {
					kvm.getOutput().print((char) value);
					value = 0;
				} else if (stream == 2) {
					kvm.getError().print((char) value);
					value = 0;
				} else if (stream == 3) {
					kvm.getDebug().print((char) value);
					value = 0;
				} else {
					value = -1;
				}
				kvm.setRR(value);
			} else if(number == TRAP_REG_TRAP) {
				trap = kvm.getRegister(0);
				location = kvm.getRegister(1);
				
				kvm.pushTrapHandler(trap, new SoftTrapHandler(kvm, trap, location));				
			}
			
			kvm.leaveTrap();
		}
	};
	
	public KhaosVM() {
		this(new long[DEFAULT_MEMORY], DEFAULT_REGISTERS);
	}

	public KhaosVM(long[] memorySize) {
		this(memorySize, DEFAULT_REGISTERS);
	}

	
	public KhaosVM(long[] mem, int regs) {
		memory = mem;
		register = new long[regs < FIXED_REGISTERS ? FIXED_REGISTERS : regs];
		status = 0;
		traps = new HashMap<>();

		pushTrapHandler(TRAP_READ, thDefaults);
		pushTrapHandler(TRAP_WRITE, thDefaults);
		
		input = System.in;
		output = System.out;
		error = System.err;
		debug = System.err;
	}

	public ArrayList<TrapHandler> getTrapHandler(long trap) {
		return new ArrayList<>(traps.get(trap));
	}

	public TrapHandler getCurrentTrapHandler(long trap) {
		if (!traps.containsKey(trap))
			return null;
		return traps.get(trap).peek();
	}

	public void pushTrapHandler(long trap, TrapHandler handler) {
		if (!traps.containsKey(trap))
			traps.put(trap, new Stack<TrapHandler>());

		traps.get(trap).push(handler);
	}

	public TrapHandler popTrapHandler(long trap) {
		TrapHandler old = getCurrentTrapHandler(trap);

		if (old == null)
			return null; /* TODO worth a exception? */

		traps.get(trap).pop();

		return old;
	}

	private long _getRegister(int r) {
		return register[r];
	}

	private void _setRegister(int r, long v) {
		register[r] = v;
	}

	public long getRegister(int r) {
		return _getRegister(r + FIXED_REGISTERS);
	}

	public void setRegister(int r, long v) {
		_setRegister(r + FIXED_REGISTERS, v);
	}

	public long[] getRegisters() {
		return register;
	}

	public void setRegisters(long[] register) {
		this.register = register;
	}

	/**
	 * @return the pc
	 */
	public long getPC() {
		return _getRegister(PCI);
	}

	/**
	 * @param pc
	 *            the pc to set
	 */
	public void setPC(long pc) {
		_setRegister(PCI, pc);
	}

	/**
	 * @return the sp
	 */
	public long getSP() {
		return _getRegister(SPI);
	}

	/**
	 * @param sp
	 *            the sp to set
	 */
	public void setSP(long sp) {
		_setRegister(SPI, sp);
	}

	/**
	 * @return the hp
	 */
	public long getHP() {
		return _getRegister(HPI);
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHP(long hp) {
		_setRegister(HPI, hp);
	}

	/**
	 * @return the mp
	 */
	public long getMP() {
		return _getRegister(MPI);
	}

	/**
	 * @param mp
	 *            the mp to set
	 */
	public void setMP(long mp) {
		_setRegister(MPI, mp);
	}

	/**
	 * @return the ra
	 */
	public long getRR() {
		return _getRegister(RRI);
	}

	/**
	 * @param ra
	 *            the ra to set
	 */
	public void setRR(long ra) {
		_setRegister(RRI, ra);
	}

	/**
	 * @return the memory
	 */
	public long[] getMemory() {
		return memory;
	}

	/**
	 * @param memory
	 *            the memory to set
	 */
	public void setMemory(long[] memory) {
		this.memory = memory;
	}

	public void executeStep() throws KVMException{
		if(hasFlag(STATUS_HALT))
			throw new MachineHalted();
		
		int r;

		int pc = (int) getPC();
		int sp = (int) getSP();
		int mp = (int) getMP();
		int hp = (int) getHP();
		int rr = (int) getRR();
		
		long trap = -1;
		
		int op = (int) memory[pc++];
		setPC(pc);
		
		Instruction instruction = Instruction.valueOf(op);
		if(instruction == null) throw new BadOpCode(op);
		
		if(trace) debug.println(String.format("Instruction: %s, PC: %d (0x%x), SP: %d (0x%x), MP: %d (0x%x), HP: %d (0x%x)", instruction.name(), pc, memory[pc], sp, memory[sp], mp, memory[mp], hp, memory[hp]));
		
		switch (instruction) {
		case add:
			memory[sp-1] = memory[sp-1] + memory[sp];
			sp--;
			break;
		case and:
			memory[sp-1] = memory[sp-1] & memory[sp];
			sp--;
			break;
		case beq:
			if(memory[sp--] == 0){
				pc = (int) (pc + memory[pc] + 1);				
			}else{
				pc+=1;
			}
			break;
		case bge:
			if(memory[sp--] <= 0){
				pc = (int) (pc + memory[pc] + 1);				
			}else{
				pc+=1;
			}
			break;
		case bgt:
			if(memory[sp--] < 0){
				pc = (int) (pc + memory[pc] + 1);				
			}else{
				pc+=1;
			}
			break;
		case ble:
			if(memory[sp--] >= 0){
				pc = (int) (pc + memory[pc] + 1);				
			}else{
				pc+=1;
			}
			break;
		case blt:
			if(memory[sp--] > 0){
				pc = (int) (pc + memory[pc] + 1);				
			}else{
				pc+=1;
			}
			break;
		case bne:
			if(memory[sp--] != 0){
				pc = (int) (pc + memory[pc] + 1);				
			}else{
				pc+=1;
			}
			break;
		case bra:
			pc = (int) (pc + memory[pc] + 1);
			break;
		case jsr:
			int t = pc;
			pc = (int) (memory[sp]);
			memory[sp] = t + 2;
			break;
		case bsr:
			memory[++sp] = pc + 2;
			pc = (int) (pc + memory[pc] + 1);
			break;
		case ret:
			pc = (int) memory[sp--];
			break;
		case div:
			memory[sp-1] = memory[sp-1] / memory[sp];
			sp--;
			break;
		case halt:
			setFlag(STATUS_HALT, true);
			break;
		case lda:
			memory[sp] = memory[(int) (memory[sp] + memory[pc++])];
			break;
		case ldaa:
			memory[sp+1] = memory[sp] + memory[pc++];
			sp++;
			break;
		case ldc:
			memory[++sp] = memory[pc++];
			break;
		case ldh:
			memory[sp] = memory[(int) (memory[sp] + memory[pc++])];
			//sp++;
			break;
		case ldl:
			memory[++sp] = memory[(int) (mp + memory[pc++])];
			break;
		case ldla:
			memory[++sp] = mp + memory[pc++];
			break;
		case ldr:
			memory[++sp] = register[(int) memory[pc++]];
			break;
		case ldrr:
			_setRegister(r = (int) memory[pc++], register[(int) memory[pc++]]);
			
			/* reload register values */
			switch(r) {
			case 0:
				pc = (int) getPC();
				break;
			case 1:
				sp = (int) getSP();
				break;
			case 2:
				mp = (int) getMP();
				break;
			case 3:
				rr = (int) getRR();
				break;
			case 4:
				hp = (int) getHP();
				break;
			}
			break;
		case lds:
			memory[++sp] = memory[(int) (sp - 1 + memory[pc++])];
			break;
		case ldsa:
			memory[++sp] = sp - 1 + memory[pc++];
			break;
		case mod:
			memory[sp-1] = memory[sp-1] % memory[sp];
			sp--;
			break;
		case mul:
			memory[sp-1] = memory[sp-1] * memory[sp];
			sp--;
			break;
		case neg:
			memory[sp] = - memory[sp];
			break;
		case noop:
			/*
			 * do nothing :)
			 */
			break;
		case not:
			memory[sp] = ~ memory[sp];
			break;
		case or:
			memory[sp-1] = memory[sp-1] | memory[sp];
			sp--;
			break;
		case sta:
			memory[(int) (memory[sp-1] + memory[pc++])] = memory[sp];
			sp-=2;
			break;
		case sth:
			memory[--hp] = memory[sp];
			memory[sp] = hp;
			break;
		case stl:
			memory[(int) (mp + memory[pc++])] = memory[sp--];
			break;
		case str:
			register[r = (int) memory[pc++]] = memory[sp--];

			/* reload register values */
			switch(r) {
			case 0:
				pc = (int) getPC();
				break;
			case 1:
				sp = (int) getSP();
				break;
			case 2:
				mp = (int) getMP();
				break;
			case 3:
				rr = (int) getRR();
				break;
			case 4:
				hp = (int) getHP();
				break;
			}
			break;
		case sts:
			memory[(int) (sp + memory[pc++])] = memory[sp--];
			break;
		case cmp:
		case sub:
			memory[sp-1] = memory[sp-1] - memory[sp];
			sp--;
			break;
		case xor:
			memory[sp-1] = memory[sp-1] ^ memory[sp];
			sp--;
			break;
		case trap:
			if(!hasFlag(STATUS_NO_TRAPS)){
				setFlag(STATUS_TRAPPED, true);
				trap = memory[pc++];
			}
			break;
		default:
			throw new BadOpCode(op);
		}
		
		setPC(pc);
		setHP(hp);
		setMP(mp);
		setRR(rr);
		setSP(sp);
		
		if(hasFlag(STATUS_TRAPPED)) {
			TrapHandler handler = getCurrentTrapHandler(trap);
			
			enterTrap();
			
			if(handler != null) {
				handler.trap(trap, this);
			}
			setFlag(STATUS_TRAPPED, false);
		}		
	}

	public boolean hasFlag(long flag) {
		return (status & flag) > 0;
	}

	public void setFlag(long flag, boolean value) {
		if (value)
			status |= flag;
		else
			status &= ~flag;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public long getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(KhaosVM o) {
		int t;

		t = Long.compare(status, o.status);
		if (t != 0)
			return t;

		t = register.length - o.register.length;
		if (t != 0)
			return t;

		t = memory.length - o.memory.length;
		if (t != 0)
			return t;

		for (int i = 0; i < register.length; i++) {
			t = Long.compare(register[i], o.register[i]);
			if (t != 0)
				return t;
		}

		for (int i = 0; i < memory.length; i++) {
			t = Long.compare(memory[i], o.memory[i]);
			if (t != 0)
				return t;
		}

		return 0;
	}
	
	private void enterTrap() {
		setSP(getSP() + 1);
		memory[(int) getSP()] = getPC();		
	}
	
	public void leaveTrap() {
		setPC(memory[(int) getSP()]);
		setSP(getSP() - 1);
	}
	
	public void pushStack(long value) {
		setSP(getSP() + 1);
		memory[(int) getSP()] = value;
	}
	
	public long popStack() {
		setSP(getSP() - 1);
		return memory[(int) getSP() + 1];
	}

	/**
	 * @return the input
	 */
	public InputStream getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(InputStream input) {
		this.input = input;
	}

	/**
	 * @return the output
	 */
	public PrintStream getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(PrintStream output) {
		this.output = output;
	}

	/**
	 * @return the error
	 */
	public PrintStream getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(PrintStream error) {
		this.error = error;
	}

	/**
	 * @return the debug
	 */
	public PrintStream getDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(PrintStream debug) {
		this.debug = debug;
	}

	public boolean isHalted() {
		return hasFlag(STATUS_HALT);
	}
	
	/**
	 * @return the trace
	 */
	public boolean isTrace() {
		return trace;
	}
	
	/**
	 * @param trace the trace to set
	 */
	public void setTrace(boolean trace) {
		this.trace = trace;
	}
}
