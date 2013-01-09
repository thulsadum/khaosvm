%define package net.inferum.khaos.asm 

%code imports {

import java.io.IOException;
import java.util.ArrayList;

import net.inferum.khaos.vm.AssemblyBuilder;
import net.inferum.khaos.vm.exception.UnresolvedLabel;

}

%language "Java"
%name-prefix "KasmParser"
%define parser_class_name "KasmParser"
%define public
//%define stype "AssemblyBuilder"

	/* terminals */
	/* misc */
%token EOL
%token NUM LABEL REGISTER
%token BYTE SHORT INTEGER LONG STRING
%token LBYTE LSHORT LINTEGER LLONG LSTRING
%token HALT NOOP TRAP
	/* loading */
%token LDC LDS LDSA LDL LDLA LDA LDAA LDH LDR LDRR
	/* storing */
%token STA STL STS STH STR
	/* arithmetic */
%token NEG ADD SUB MUL DIV MOD
	/* logic */
%token NOT OR AND XOR CMP
	/* branching */
%token BRA BSR JSR BEQ BNE BLT BLE BGT BGE RET

%type<Byte> LBYTE
%type<Short> LSHORT
%type<Integer> LINTEGER
%type<Long> NUM LLONG
%type<String> LABEL REGISTER LSTRING

%type<ArrayList> bs
%type<ArrayList> ss
%type<ArrayList> is
%type<ArrayList> ls

%code {
	private AssemblyBuilder ab = new AssemblyBuilder();
	
	public AssemblyBuilder getAssemblyBuilder() {
		return this.ab;
	}

	public byte[] getCode() throws UnresolvedLabel, IOException {
		return this.ab.link().generate();
	}
	
	private static byte[] conv8(ArrayList<Byte> l){
		byte[] a = new byte[l.size()];
		for(int i = 0; i < a.length; i++)
			a[i] = l.get(i);
		return a;		 
	}
	
	private static short[] conv16(ArrayList<Short> l){
		short[] a = new short[l.size()];
		for(int i = 0; i < a.length; i++)
			a[i] = l.get(i);
		return a;		 
	}
	
	private static int[] conv32(ArrayList<Integer> l){
		int[] a = new int[l.size()];
		for(int i = 0; i < a.length; i++)
			a[i] = l.get(i);
		return a;		 
	}
	
	private static long[] conv64(ArrayList<Long> l){
		long[] a = new long[l.size()];
		for(int i = 0; i < a.length; i++)
			a[i] = l.get(i);
		return a;		 
	}
}

%%

start: macrodef program ;

macrodef: ;

program: program labeldef instruction EOL | EOL|;

labeldef:
	| labeldef LABEL ':' {ab.label($2);}


instruction:
	|	misc
	|	data
	|	loading
	|	storing
	|	arithmetic
	|	logic
	|	branching 
	;	

data:
		STRING LSTRING {ab.data($2);}
	|	BYTE bs {ab.data(conv8($2));}
	|	SHORT ss {ab.data(conv16($2));}
	|	INTEGER is {ab.data(conv32($2));}
	|	LONG ls {ab.data(conv64($2));}
	;

bs: LBYTE {$$=new ArrayList<Byte>(); ((ArrayList<Byte>)$$).add($1);} | bs LBYTE {$$=$1; $1.add($2);};
ss: LSHORT {$$=new ArrayList<Short>(); ((ArrayList<Short>)$$).add($1);} | ss LSHORT {$$=$1; $1.add($2);};
is: LINTEGER {$$=new ArrayList<Integer>(); ((ArrayList<Integer>)$$).add($1);} | is LINTEGER {$$=$1; $1.add($2);};
ls: LLONG {$$=new ArrayList<Long>(); ((ArrayList<Long>)$$).add($1);} | ls LLONG {$$=$1; $1.add($2);};

misc:
		HALT		{ab.halt();}
	|	NOOP		{ab.noop();}
	|	TRAP NUM	{ab.trap($2);}
	;
	
loading: ldc | ldh| ldr| ldrr | lda |ldaa | lds |ldsa | ldl |ldla ;
storing: sta | stl | sth | sts | str ;
branching: bra | bsr | jsr | beq | bne | bgt | bge | blt | ble ;

arithmetic: 
		ADD {ab.add();} 
	|	SUB {ab.sub();}
	|	MUL {ab.mul();}
	|	DIV {ab.div();}
	|	MOD {ab.mod();}
	|	NEG {ab.neg();}
	;
logic:
		AND	{ab.and();}
	|	OR	{ab.or();}
	|	XOR	{ab.xor();}
	|	NOT	{ab.not();}
	|	CMP	{ab.cmp();}
	;

ldc:
		LDC NUM {ab.ldc($2.longValue());}
	 |	LDC LABEL {ab.ldc($2);}
	;

ldh:
		LDH NUM {ab.ldh($2.longValue());}
	 |	LDH LABEL {ab.ldh($2);}
	 ;
	 
ldr:
		LDR REGISTER {ab.ldr($2);}
	;

ldrr:
		LDRR REGISTER REGISTER {ab.ldrr($2, $3);}
	;

lda:
		LDA NUM {ab.lda($2.longValue());}
	 |	LDA LABEL {ab.lda($2);}
	 ;
	 
ldaa:
		LDAA NUM {ab.ldaa($2.longValue());}
	 |	LDAA LABEL {ab.ldaa($2);}
	 ;
	 
lds:
		LDS NUM {ab.lds($2.longValue());}
	 |	LDS LABEL {ab.lds($2);}
	 ;
	 
ldsa:
		LDSA NUM {ab.ldsa($2.longValue());}
	 |	LDSA LABEL {ab.ldsa($2);}
	 ;
	 
ldl:
		LDL NUM {ab.ldl($2.longValue());}
	 |	LDL LABEL {ab.ldl($2);}
	 ;
	 
ldla:
		LDLA NUM {ab.ldla($2.longValue());}
	 |	LDLA LABEL {ab.ldla($2);}
	 ;
	 
	
		 
sta:
		STA NUM {ab.sta($2.longValue());}
	 |	STA LABEL {ab.sta($2);}
	 ;

stl:
		STL NUM {ab.stl($2.longValue());}
	 |	STL LABEL {ab.stl($2);}
	 ;

sth:
		STH NUM {ab.sth();}
	 ;

sts:
		STS NUM {ab.sts($2.longValue());}
	 |	STS LABEL {ab.sts($2);}
	 ;

str:
		STR REGISTER {ab.str($2);}
	 ;


	 
bra:
		BRA NUM {ab.bra($2.longValue());}
	 |	BRA LABEL {ab.bra($2);}
	;

bsr:
		BSR NUM {ab.bsr($2.longValue());}
	 |	BSR LABEL {ab.bsr($2);}
	;

jsr:
		JSR {ab.jsr();}
	;

beq:
		BEQ NUM {ab.beq($2.longValue());}
	 |	BEQ LABEL {ab.beq($2);}
	;

bne:
		BNE NUM {ab.bne($2.longValue());}
	 |	BNE LABEL {ab.bne($2);}
	;

bgt:
		BGT NUM {ab.bgt($2.longValue());}
	 |	BGT LABEL {ab.bgt($2);}
	;

bge:
		BGE NUM {ab.bge($2.longValue());}
	 |	BGE LABEL {ab.bge($2);}
	;

blt:
		BLT NUM {ab.blt($2.longValue());}
	 |	BLT LABEL {ab.blt($2);}
	;

ble:
		BLE NUM {ab.ble($2.longValue());}
	 |	BLE LABEL {ab.ble($2);}
	;

