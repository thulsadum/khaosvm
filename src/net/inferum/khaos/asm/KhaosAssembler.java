/**
 * 
 */
package net.inferum.khaos.asm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import net.inferum.khaos.vm.AssemblyBuilder;
import net.inferum.khaos.vm.KhaosVM;
import net.inferum.khaos.vm.exception.UnresolvedLabel;

/**
 * @author basty
 *
 */
public class KhaosAssembler {
	public static final int VERSION = 1;
	public static final String RELEASE_TYPE = "α";

	private static void usage(PrintStream out) {
		out.println(String.format("KhaosAssembler: kasm-version: %d%s, kvm-version: %d", VERSION, RELEASE_TYPE, KhaosVM.VERSION));
		out.println();
		out.println("usage: [<input file> [...]] <output file>");
		out.println();
		out.println("Default input is stdin, unless an input file is give.");
	}
	
	public static void main(String[] args) throws IOException, UnresolvedLabel {
		InputStream in = System.in;
		OutputStream out = null;
		AssemblyBuilder builder;
		KasmLexer lexer;
		KasmParser parser;

		if(args.length == 0) {
			usage(System.err);
			System.exit(-1);
		}
		
		out = new FileOutputStream(args[args.length - 1]);		
		builder = new AssemblyBuilder();

		boolean debug = System.getProperty("parser.debug") != null;
		
		if(args.length == 1){
			lexer = new KasmLexer(in);
			parser = new KasmParser(lexer);
			parser.errorVerbose = true;
			if(debug) parser.setDebugLevel(10);
			if(!parser.parse()) {
				System.out.println("syntax error(s)!");
				System.exit(-1);
			}
			builder.append(parser.getAssemblyBuilder());
		}
		else
			for(int i = 0; i < args.length - 1; i++) {
				in = args[i].equals("-")?System.in:new FileInputStream(args[i]);
				lexer = new KasmLexer(in);
				parser = new KasmParser(lexer);
				parser.errorVerbose = true;
				if(debug) parser.setDebugLevel(10);
				if(!parser.parse()) {
					System.out.println("syntax error(s)!");
					System.exit(-1);
				}
				builder.append(parser.getAssemblyBuilder());
			}
		
		out.write(builder.link().generate());
		out.close();
	}
}
