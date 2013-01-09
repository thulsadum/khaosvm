// vim: ft=jflex

package net.inferum.khaos.asm;

import static net.inferum.khaos.asm.KasmParser.*;

%%

%public
%class KasmLexer
%implements KasmParser.Lexer
%integer
%line
%column


NL = \r|\n|\r\n
SPACE = [ \t\f]

DIGIT = [0-9]
INTEGER = 0 | \-?[1-9]{DIGIT}*

OCTDIGIT = [0-7]
HEXDIGIT = [0-9a-fA-F]

ALPHA = [a-zA-Z_]
ALPHANUM = {ALPHA} | {DIGIT}

%{
	private Object yylval;
	private StringBuilder string = new StringBuilder();

	@Override
	public Object getLVal() {
		return yylval;
	}

	@Override
	public void yyerror(String msg) {
		System.err.println(String.format("%d:%d - %s", yyline+1, yycolumn+1, msg));
	}
%}

%x S_STRING S_BYTE S_SHORT S_INTEGER S_LONG

%%

halt	{return HALT;}
noop	{return NOOP;}
trap	{return TRAP;}

ldc		{return LDC;}
ldh		{return LDH;}
ldr		{return LDR;}
ldrr	{return LDRR;}

lds		{return LDS;}
ldsa	{return LDSA;}
ldl		{return LDL;}
ldla	{return LDLA;}
lda		{return LDA;}
ldaa	{return LDAA;}

sta		{return STA;}
stl		{return STL;}
sth		{return STH;}
sts		{return STS;}
str		{return STR;}

neg		{return NEG;}
add		{return ADD;}
sub		{return SUB;}
mul		{return MUL;}
div		{return DIV;}
mod		{return MOD;}

not		{return NOT;}
or		{return OR;}
and		{return AND;}
xor		{return XOR;}
cmp		{return CMP;}

bra		{return BRA;}
bsr		{return BSR;}
jsr		{return JSR;}
beq		{return BEQ;}
bne		{return BNE;}
bgt		{return BGT;}
bge		{return BGE;}
blt		{return BLT;}
ble		{return BLE;}
ret		{return RET;}

byte{SPACE}*\{	{yybegin(S_BYTE); return BYTE;}
short{SPACE}*\{	{yybegin(S_SHORT); return SHORT;}
int(eger)?{SPACE}*\{	{yybegin(S_INTEGER); return INTEGER;}
long{SPACE}*\{	{yybegin(S_LONG); return LONG;}

<S_BYTE> {
	{INTEGER}		{yylval = Byte.parseByte(yytext()); return LBYTE;}
	0x{HEXDIGIT}+	{yylval = Byte.parseByte(yytext().substring(2), 16); return LBYTE;}
	0{OCTDIGIT}+	{yylval = Byte.parseByte(yytext().substring(1), 8); return LBYTE;}
}

<S_SHORT> {
	{INTEGER}		{yylval = Short.parseShort(yytext()); return LSHORT;}
	0x{HEXDIGIT}+	{yylval = Short.parseShort(yytext().substring(2), 16); return LSHORT;}
	0{OCTDIGIT}+	{yylval = Short.parseShort(yytext().substring(1), 8); return LSHORT;}
}

<S_INTEGER> {
	{INTEGER}		{yylval = Integer.parseInt(yytext()); return LINTEGER;}
	0x{HEXDIGIT}+	{yylval = Integer.parseInt(yytext().substring(2), 16); return LINTEGER;}
	0{OCTDIGIT}+	{yylval = Integer.parseInt(yytext().substring(1), 8); return LINTEGER;}
}

<S_LONG> {
	{INTEGER}		{yylval = Long.parseLong(yytext()); return LLONG;}
	0x{HEXDIGIT}+	{yylval = Long.parseLong(yytext().substring(2), 16); return LLONG;}
	0{OCTDIGIT}+	{yylval = Long.parseLong(yytext().substring(1), 8); return LLONG;}
}

<S_BYTE,S_SHORT,S_INTEGER,S_LONG>{
	\}	{yybegin(YYINITIAL);}
	
	;.*			{}
	{NL}+		{}
	{SPACE}+	{}
}

string	{return STRING;}

PC|SP|MP|RR|HP	{yylval = yytext(); return REGISTER;}
R{DIGIT}+	{yylval = yytext(); return REGISTER;}

{INTEGER}	{yylval = new Long(Integer.parseInt(yytext())); return NUM;}
0{OCTDIGIT}+	{yylval = new Long(Long.parseLong(yytext().substring(1),8)); return NUM;}
0x{HEXDIGIT}+	{yylval = new Long(Long.parseLong(yytext().substring(2),16)); return NUM;}

@?{ALPHA}{ALPHANUM}* {yylval = yytext().substring(0, yytext().length()-1); return LABEL;} 

	/* string */
\"	{yybegin(S_STRING); string.setLength(0);}
<S_STRING> {
  \"                             { yybegin(YYINITIAL);
  									yylval = string.toString(); 
                                   return LSTRING; }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }
  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\                             { string.append('\\'); }
  \\.							 { string.append(yytext());}
}

;.*		{/* ignore comment */}
{NL}		{return EOL;}
{SPACE}+	{/* ignore */}
.	{return yytext().charAt(0);}
