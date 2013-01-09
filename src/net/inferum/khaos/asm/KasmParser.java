/* A Bison parser, made by GNU Bison 2.7.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java
   
      Copyright (C) 2007-2012 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

package net.inferum.khaos.asm;
/* First part of user declarations.  */

/* "%code imports" blocks.  */
/* Line 32 of lalr1.java  */
/* Line 3 of "KasmParser.y"  */


import java.io.IOException;
import java.util.ArrayList;

import net.inferum.khaos.vm.AssemblyBuilder;
import net.inferum.khaos.vm.exception.UnresolvedLabel;



/* Line 32 of lalr1.java  */
/* Line 51 of "KasmParser.java"  */

/**
 * A Bison parser, automatically generated from <tt>KasmParser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class KasmParser
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.7";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";


  /** True if verbose error messages are enabled.  */
  public boolean errorVerbose = false;



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int EOL = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int NUM = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int LABEL = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int REGISTER = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int BYTE = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int SHORT = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int INTEGER = 264;
  /** Token number, to be returned by the scanner.  */
  public static final int LONG = 265;
  /** Token number, to be returned by the scanner.  */
  public static final int STRING = 266;
  /** Token number, to be returned by the scanner.  */
  public static final int LBYTE = 267;
  /** Token number, to be returned by the scanner.  */
  public static final int LSHORT = 268;
  /** Token number, to be returned by the scanner.  */
  public static final int LINTEGER = 269;
  /** Token number, to be returned by the scanner.  */
  public static final int LLONG = 270;
  /** Token number, to be returned by the scanner.  */
  public static final int LSTRING = 271;
  /** Token number, to be returned by the scanner.  */
  public static final int HALT = 272;
  /** Token number, to be returned by the scanner.  */
  public static final int NOOP = 273;
  /** Token number, to be returned by the scanner.  */
  public static final int TRAP = 274;
  /** Token number, to be returned by the scanner.  */
  public static final int LDC = 275;
  /** Token number, to be returned by the scanner.  */
  public static final int LDS = 276;
  /** Token number, to be returned by the scanner.  */
  public static final int LDSA = 277;
  /** Token number, to be returned by the scanner.  */
  public static final int LDL = 278;
  /** Token number, to be returned by the scanner.  */
  public static final int LDLA = 279;
  /** Token number, to be returned by the scanner.  */
  public static final int LDA = 280;
  /** Token number, to be returned by the scanner.  */
  public static final int LDAA = 281;
  /** Token number, to be returned by the scanner.  */
  public static final int LDH = 282;
  /** Token number, to be returned by the scanner.  */
  public static final int LDR = 283;
  /** Token number, to be returned by the scanner.  */
  public static final int LDRR = 284;
  /** Token number, to be returned by the scanner.  */
  public static final int STA = 285;
  /** Token number, to be returned by the scanner.  */
  public static final int STL = 286;
  /** Token number, to be returned by the scanner.  */
  public static final int STS = 287;
  /** Token number, to be returned by the scanner.  */
  public static final int STH = 288;
  /** Token number, to be returned by the scanner.  */
  public static final int STR = 289;
  /** Token number, to be returned by the scanner.  */
  public static final int NEG = 290;
  /** Token number, to be returned by the scanner.  */
  public static final int ADD = 291;
  /** Token number, to be returned by the scanner.  */
  public static final int SUB = 292;
  /** Token number, to be returned by the scanner.  */
  public static final int MUL = 293;
  /** Token number, to be returned by the scanner.  */
  public static final int DIV = 294;
  /** Token number, to be returned by the scanner.  */
  public static final int MOD = 295;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT = 296;
  /** Token number, to be returned by the scanner.  */
  public static final int OR = 297;
  /** Token number, to be returned by the scanner.  */
  public static final int AND = 298;
  /** Token number, to be returned by the scanner.  */
  public static final int XOR = 299;
  /** Token number, to be returned by the scanner.  */
  public static final int CMP = 300;
  /** Token number, to be returned by the scanner.  */
  public static final int BRA = 301;
  /** Token number, to be returned by the scanner.  */
  public static final int BSR = 302;
  /** Token number, to be returned by the scanner.  */
  public static final int JSR = 303;
  /** Token number, to be returned by the scanner.  */
  public static final int BEQ = 304;
  /** Token number, to be returned by the scanner.  */
  public static final int BNE = 305;
  /** Token number, to be returned by the scanner.  */
  public static final int BLT = 306;
  /** Token number, to be returned by the scanner.  */
  public static final int BLE = 307;
  /** Token number, to be returned by the scanner.  */
  public static final int BGT = 308;
  /** Token number, to be returned by the scanner.  */
  public static final int BGE = 309;
  /** Token number, to be returned by the scanner.  */
  public static final int RET = 310;



  

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>KasmParser</tt>.
   */
  public interface Lexer {
    

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token. */
    int yylex () throws java.io.IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * 
     * @param s The string for the error message.  */
     void yyerror (String s);
  }

  /** The object doing lexical analysis for us.  */
  private Lexer yylexer;
  
  



  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public KasmParser (Lexer yylexer) {
    this.yylexer = yylexer;
    
  }

  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final java.io.PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  private final int yylex () throws java.io.IOException {
    return yylexer.yylex ();
  }
  protected final void yyerror (String s) {
    yylexer.yyerror (s);
  }

  

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push (int state, Object value			    ) {
      height++;
      if (size == height)
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  

	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      
      valueStack[height] = value;
    }

    public final void pop () {
      pop (1);
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height + 1, null);
        
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (java.io.PrintStream out)
    {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++)
        {
	  out.print (' ');
	  out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).  */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).  */
  public static final int YYABORT = 1;

  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.  */
  public static final int YYERROR = 2;

  // Internal return codes that are not supported for user semantic
  // actions.
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;

  private int yyerrstatus_ = 0;

  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.  */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) 
  {
    Object yyval;
    

    /* If YYLEN is nonzero, implement the default value of the action:
       `$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);

    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
	  case 8:
  if (yyn == 8)
    /* Line 350 of lalr1.java  */
/* Line 97 of "KasmParser.y"  */
    {ab.label(((String)(yystack.valueAt (3-(2)))));};
  break;
    

  case 17:
  if (yyn == 17)
    /* Line 350 of lalr1.java  */
/* Line 111 of "KasmParser.y"  */
    {ab.data(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 18:
  if (yyn == 18)
    /* Line 350 of lalr1.java  */
/* Line 112 of "KasmParser.y"  */
    {ab.data(conv8(((ArrayList)(yystack.valueAt (2-(2))))));};
  break;
    

  case 19:
  if (yyn == 19)
    /* Line 350 of lalr1.java  */
/* Line 113 of "KasmParser.y"  */
    {ab.data(conv16(((ArrayList)(yystack.valueAt (2-(2))))));};
  break;
    

  case 20:
  if (yyn == 20)
    /* Line 350 of lalr1.java  */
/* Line 114 of "KasmParser.y"  */
    {ab.data(conv32(((ArrayList)(yystack.valueAt (2-(2))))));};
  break;
    

  case 21:
  if (yyn == 21)
    /* Line 350 of lalr1.java  */
/* Line 115 of "KasmParser.y"  */
    {ab.data(conv64(((ArrayList)(yystack.valueAt (2-(2))))));};
  break;
    

  case 22:
  if (yyn == 22)
    /* Line 350 of lalr1.java  */
/* Line 118 of "KasmParser.y"  */
    {yyval=new ArrayList<Byte>(); ((ArrayList<Byte>)yyval).add(((Byte)(yystack.valueAt (1-(1)))));};
  break;
    

  case 23:
  if (yyn == 23)
    /* Line 350 of lalr1.java  */
/* Line 118 of "KasmParser.y"  */
    {yyval=((ArrayList)(yystack.valueAt (2-(1)))); ((ArrayList)(yystack.valueAt (2-(1)))).add(((Byte)(yystack.valueAt (2-(2)))));};
  break;
    

  case 24:
  if (yyn == 24)
    /* Line 350 of lalr1.java  */
/* Line 119 of "KasmParser.y"  */
    {yyval=new ArrayList<Short>(); ((ArrayList<Short>)yyval).add(((Short)(yystack.valueAt (1-(1)))));};
  break;
    

  case 25:
  if (yyn == 25)
    /* Line 350 of lalr1.java  */
/* Line 119 of "KasmParser.y"  */
    {yyval=((ArrayList)(yystack.valueAt (2-(1)))); ((ArrayList)(yystack.valueAt (2-(1)))).add(((Short)(yystack.valueAt (2-(2)))));};
  break;
    

  case 26:
  if (yyn == 26)
    /* Line 350 of lalr1.java  */
/* Line 120 of "KasmParser.y"  */
    {yyval=new ArrayList<Integer>(); ((ArrayList<Integer>)yyval).add(((Integer)(yystack.valueAt (1-(1)))));};
  break;
    

  case 27:
  if (yyn == 27)
    /* Line 350 of lalr1.java  */
/* Line 120 of "KasmParser.y"  */
    {yyval=((ArrayList)(yystack.valueAt (2-(1)))); ((ArrayList)(yystack.valueAt (2-(1)))).add(((Integer)(yystack.valueAt (2-(2)))));};
  break;
    

  case 28:
  if (yyn == 28)
    /* Line 350 of lalr1.java  */
/* Line 121 of "KasmParser.y"  */
    {yyval=new ArrayList<Long>(); ((ArrayList<Long>)yyval).add(((Long)(yystack.valueAt (1-(1)))));};
  break;
    

  case 29:
  if (yyn == 29)
    /* Line 350 of lalr1.java  */
/* Line 121 of "KasmParser.y"  */
    {yyval=((ArrayList)(yystack.valueAt (2-(1)))); ((ArrayList)(yystack.valueAt (2-(1)))).add(((Long)(yystack.valueAt (2-(2)))));};
  break;
    

  case 30:
  if (yyn == 30)
    /* Line 350 of lalr1.java  */
/* Line 124 of "KasmParser.y"  */
    {ab.halt();};
  break;
    

  case 31:
  if (yyn == 31)
    /* Line 350 of lalr1.java  */
/* Line 125 of "KasmParser.y"  */
    {ab.noop();};
  break;
    

  case 32:
  if (yyn == 32)
    /* Line 350 of lalr1.java  */
/* Line 126 of "KasmParser.y"  */
    {ab.trap(((Long)(yystack.valueAt (2-(2)))));};
  break;
    

  case 57:
  if (yyn == 57)
    /* Line 350 of lalr1.java  */
/* Line 134 of "KasmParser.y"  */
    {ab.add();};
  break;
    

  case 58:
  if (yyn == 58)
    /* Line 350 of lalr1.java  */
/* Line 135 of "KasmParser.y"  */
    {ab.sub();};
  break;
    

  case 59:
  if (yyn == 59)
    /* Line 350 of lalr1.java  */
/* Line 136 of "KasmParser.y"  */
    {ab.mul();};
  break;
    

  case 60:
  if (yyn == 60)
    /* Line 350 of lalr1.java  */
/* Line 137 of "KasmParser.y"  */
    {ab.div();};
  break;
    

  case 61:
  if (yyn == 61)
    /* Line 350 of lalr1.java  */
/* Line 138 of "KasmParser.y"  */
    {ab.mod();};
  break;
    

  case 62:
  if (yyn == 62)
    /* Line 350 of lalr1.java  */
/* Line 139 of "KasmParser.y"  */
    {ab.neg();};
  break;
    

  case 63:
  if (yyn == 63)
    /* Line 350 of lalr1.java  */
/* Line 142 of "KasmParser.y"  */
    {ab.and();};
  break;
    

  case 64:
  if (yyn == 64)
    /* Line 350 of lalr1.java  */
/* Line 143 of "KasmParser.y"  */
    {ab.or();};
  break;
    

  case 65:
  if (yyn == 65)
    /* Line 350 of lalr1.java  */
/* Line 144 of "KasmParser.y"  */
    {ab.xor();};
  break;
    

  case 66:
  if (yyn == 66)
    /* Line 350 of lalr1.java  */
/* Line 145 of "KasmParser.y"  */
    {ab.not();};
  break;
    

  case 67:
  if (yyn == 67)
    /* Line 350 of lalr1.java  */
/* Line 146 of "KasmParser.y"  */
    {ab.cmp();};
  break;
    

  case 68:
  if (yyn == 68)
    /* Line 350 of lalr1.java  */
/* Line 150 of "KasmParser.y"  */
    {ab.ldc(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 69:
  if (yyn == 69)
    /* Line 350 of lalr1.java  */
/* Line 151 of "KasmParser.y"  */
    {ab.ldc(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 70:
  if (yyn == 70)
    /* Line 350 of lalr1.java  */
/* Line 155 of "KasmParser.y"  */
    {ab.ldh(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 71:
  if (yyn == 71)
    /* Line 350 of lalr1.java  */
/* Line 156 of "KasmParser.y"  */
    {ab.ldh(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 72:
  if (yyn == 72)
    /* Line 350 of lalr1.java  */
/* Line 160 of "KasmParser.y"  */
    {ab.ldr(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 73:
  if (yyn == 73)
    /* Line 350 of lalr1.java  */
/* Line 164 of "KasmParser.y"  */
    {ab.ldrr(((String)(yystack.valueAt (3-(2)))), ((String)(yystack.valueAt (3-(3)))));};
  break;
    

  case 74:
  if (yyn == 74)
    /* Line 350 of lalr1.java  */
/* Line 168 of "KasmParser.y"  */
    {ab.lda(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 75:
  if (yyn == 75)
    /* Line 350 of lalr1.java  */
/* Line 169 of "KasmParser.y"  */
    {ab.lda(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 76:
  if (yyn == 76)
    /* Line 350 of lalr1.java  */
/* Line 173 of "KasmParser.y"  */
    {ab.ldaa(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 77:
  if (yyn == 77)
    /* Line 350 of lalr1.java  */
/* Line 174 of "KasmParser.y"  */
    {ab.ldaa(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 78:
  if (yyn == 78)
    /* Line 350 of lalr1.java  */
/* Line 178 of "KasmParser.y"  */
    {ab.lds(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 79:
  if (yyn == 79)
    /* Line 350 of lalr1.java  */
/* Line 179 of "KasmParser.y"  */
    {ab.lds(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 80:
  if (yyn == 80)
    /* Line 350 of lalr1.java  */
/* Line 183 of "KasmParser.y"  */
    {ab.ldsa(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 81:
  if (yyn == 81)
    /* Line 350 of lalr1.java  */
/* Line 184 of "KasmParser.y"  */
    {ab.ldsa(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 82:
  if (yyn == 82)
    /* Line 350 of lalr1.java  */
/* Line 188 of "KasmParser.y"  */
    {ab.ldl(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 83:
  if (yyn == 83)
    /* Line 350 of lalr1.java  */
/* Line 189 of "KasmParser.y"  */
    {ab.ldl(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 84:
  if (yyn == 84)
    /* Line 350 of lalr1.java  */
/* Line 193 of "KasmParser.y"  */
    {ab.ldla(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 85:
  if (yyn == 85)
    /* Line 350 of lalr1.java  */
/* Line 194 of "KasmParser.y"  */
    {ab.ldla(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 86:
  if (yyn == 86)
    /* Line 350 of lalr1.java  */
/* Line 200 of "KasmParser.y"  */
    {ab.sta(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 87:
  if (yyn == 87)
    /* Line 350 of lalr1.java  */
/* Line 201 of "KasmParser.y"  */
    {ab.sta(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 88:
  if (yyn == 88)
    /* Line 350 of lalr1.java  */
/* Line 205 of "KasmParser.y"  */
    {ab.stl(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 89:
  if (yyn == 89)
    /* Line 350 of lalr1.java  */
/* Line 206 of "KasmParser.y"  */
    {ab.stl(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 90:
  if (yyn == 90)
    /* Line 350 of lalr1.java  */
/* Line 210 of "KasmParser.y"  */
    {ab.sth();};
  break;
    

  case 91:
  if (yyn == 91)
    /* Line 350 of lalr1.java  */
/* Line 214 of "KasmParser.y"  */
    {ab.sts(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 92:
  if (yyn == 92)
    /* Line 350 of lalr1.java  */
/* Line 215 of "KasmParser.y"  */
    {ab.sts(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 93:
  if (yyn == 93)
    /* Line 350 of lalr1.java  */
/* Line 219 of "KasmParser.y"  */
    {ab.str(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 94:
  if (yyn == 94)
    /* Line 350 of lalr1.java  */
/* Line 225 of "KasmParser.y"  */
    {ab.bra(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 95:
  if (yyn == 95)
    /* Line 350 of lalr1.java  */
/* Line 226 of "KasmParser.y"  */
    {ab.bra(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 96:
  if (yyn == 96)
    /* Line 350 of lalr1.java  */
/* Line 230 of "KasmParser.y"  */
    {ab.bsr(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 97:
  if (yyn == 97)
    /* Line 350 of lalr1.java  */
/* Line 231 of "KasmParser.y"  */
    {ab.bsr(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 98:
  if (yyn == 98)
    /* Line 350 of lalr1.java  */
/* Line 235 of "KasmParser.y"  */
    {ab.jsr();};
  break;
    

  case 99:
  if (yyn == 99)
    /* Line 350 of lalr1.java  */
/* Line 239 of "KasmParser.y"  */
    {ab.beq(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 100:
  if (yyn == 100)
    /* Line 350 of lalr1.java  */
/* Line 240 of "KasmParser.y"  */
    {ab.beq(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 101:
  if (yyn == 101)
    /* Line 350 of lalr1.java  */
/* Line 244 of "KasmParser.y"  */
    {ab.bne(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 102:
  if (yyn == 102)
    /* Line 350 of lalr1.java  */
/* Line 245 of "KasmParser.y"  */
    {ab.bne(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 103:
  if (yyn == 103)
    /* Line 350 of lalr1.java  */
/* Line 249 of "KasmParser.y"  */
    {ab.bgt(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 104:
  if (yyn == 104)
    /* Line 350 of lalr1.java  */
/* Line 250 of "KasmParser.y"  */
    {ab.bgt(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 105:
  if (yyn == 105)
    /* Line 350 of lalr1.java  */
/* Line 254 of "KasmParser.y"  */
    {ab.bge(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 106:
  if (yyn == 106)
    /* Line 350 of lalr1.java  */
/* Line 255 of "KasmParser.y"  */
    {ab.bge(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 107:
  if (yyn == 107)
    /* Line 350 of lalr1.java  */
/* Line 259 of "KasmParser.y"  */
    {ab.blt(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 108:
  if (yyn == 108)
    /* Line 350 of lalr1.java  */
/* Line 260 of "KasmParser.y"  */
    {ab.blt(((String)(yystack.valueAt (2-(2)))));};
  break;
    

  case 109:
  if (yyn == 109)
    /* Line 350 of lalr1.java  */
/* Line 264 of "KasmParser.y"  */
    {ab.ble(((Long)(yystack.valueAt (2-(2)))).longValue());};
  break;
    

  case 110:
  if (yyn == 110)
    /* Line 350 of lalr1.java  */
/* Line 265 of "KasmParser.y"  */
    {ab.ble(((String)(yystack.valueAt (2-(2)))));};
  break;
    


/* Line 350 of lalr1.java  */
/* Line 963 of "KasmParser.java"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    yyn = yyr1_[yyn];
    int yystate = yypgoto_[yyn - yyntokens_] + yystack.stateAt (0);
    if (0 <= yystate && yystate <= yylast_
	&& yycheck_[yystate] == yystack.stateAt (0))
      yystate = yytable_[yystate];
    else
      yystate = yydefgoto_[yyn - yyntokens_];

    yystack.push (yystate, yyval);
    return YYNEWSTATE;
  }

  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
	      if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }

  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
			         Object yyvaluep				 )
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
	      + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }

  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse () throws java.io.IOException
  {
    /// Lookahead and lookahead in internal form.
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;

    YYStack yystack = new YYStack ();

    /* Error handling.  */
    int yynerrs_ = 0;
    

    /// Semantic value of the lookahead.
    Object yylval = null;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval);

    int label = YYNEWSTATE;
    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
	   pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);

        /* Accept?  */
        if (yystate == yyfinal_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yy_pact_value_is_default_ (yyn))
          {
            label = YYDEFAULT;
	    break;
          }

        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {
	    yycdebug ("Reading a token: ");
	    yychar = yylex ();
            
            yylval = yylexer.getLVal ();
          }

        /* Convert token to internal form.  */
        if (yychar <= EOF)
          {
	    yychar = yytoken = EOF;
	    yycdebug ("Now at end of input.\n");
          }
        else
          {
	    yytoken = yytranslate_ (yychar);
	    yy_symbol_print ("Next token is", yytoken,
			     yylval);
          }

        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;

        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
	    if (yy_table_value_is_error_ (yyn))
	      label = YYERRLAB;
	    else
	      {
	        yyn = -yyn;
	        label = YYREDUCE;
	      }
          }

        else
          {
            /* Shift the lookahead token.  */
	    yy_symbol_print ("Shifting", yytoken,
			     yylval);

            /* Discard the token being shifted.  */
            yychar = yyempty_;

            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;

            yystate = yyn;
            yystack.push (yystate, yylval);
            label = YYNEWSTATE;
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
	yystate = yystack.stateAt (0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs_;
            if (yychar == yyempty_)
              yytoken = yyempty_;
            yyerror (yysyntax_error (yystate, yytoken));
          }

        
        if (yyerrstatus_ == 3)
          {
	    /* If just tried and failed to reuse lookahead token after an
	     error, discard it.  */

	    if (yychar <= EOF)
	      {
	      /* Return failure if at end of input.  */
	      if (yychar == EOF)
	        return false;
	      }
	    else
	      yychar = yyempty_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*---------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `---------------------------------------------------*/
      case YYERROR:

        
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;	/* Each real token shifted decrements this.  */

        for (;;)
          {
	    yyn = yypact_[yystate];
	    if (!yy_pact_value_is_default_ (yyn))
	      {
	        yyn += yyterror_;
	        if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
	          {
	            yyn = yytable_[yyn];
	            if (0 < yyn)
		      break;
	          }
	      }

	    /* Pop the current state because it cannot handle the error token.  */
	    if (yystack.height == 0)
	      return false;

	    
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }

	

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval);

        yystate = yyn;
	yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
  }

  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (errorVerbose)
      {
        /* There are many possibilities here to consider:
           - Assume YYFAIL is not used.  It's too flawed to consider.
             See
             <http://lists.gnu.org/archive/html/bison-patches/2009-12/msg00024.html>
             for details.  YYERROR is fine as it does not invoke this
             function.
           - If this state is a consistent state with a default action,
             then the only way this function was invoked is if the
             default action is an error action.  In that case, don't
             check for expected tokens because there are none.
           - The only way there can be no lookahead present (in tok) is
             if this state is a consistent state with a default action.
             Thus, detecting the absence of a lookahead is sufficient to
             determine that there is no unexpected or expected token to
             report.  In that case, just report a simple "syntax error".
           - Don't assume there isn't a lookahead just because this
             state is a consistent state with a default action.  There
             might have been a previous inconsistent state, consistent
             state with a non-default action, or user semantic action
             that manipulated yychar.  (However, yychar is currently out
             of scope during semantic actions.)
           - Of course, the expected token list depends on states to
             have correct lookahead information, and it depends on the
             parser not to perform extra reductions after fetching a
             lookahead from the scanner and before detecting a syntax
             error.  Thus, state merging (from LALR or IELR) and default
             reductions corrupt the expected token list.  However, the
             list is correct for canonical LR with one exception: it
             will still contain any token that will not be accepted due
             to an error action in a later state.
        */
        if (tok != yyempty_)
          {
            // FIXME: This method of building the message is not compatible
            // with internationalization.
            StringBuffer res =
              new StringBuffer ("syntax error, unexpected ");
            res.append (yytnamerr_ (yytname_[tok]));
            int yyn = yypact_[yystate];
            if (!yy_pact_value_is_default_ (yyn))
              {
                /* Start YYX at -YYN if negative to avoid negative
                   indexes in YYCHECK.  In other words, skip the first
                   -YYN actions for this state because they are default
                   actions.  */
                int yyxbegin = yyn < 0 ? -yyn : 0;
                /* Stay within bounds of both yycheck and yytname.  */
                int yychecklim = yylast_ - yyn + 1;
                int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
                int count = 0;
                for (int x = yyxbegin; x < yyxend; ++x)
                  if (yycheck_[x + yyn] == x && x != yyterror_
                      && !yy_table_value_is_error_ (yytable_[x + yyn]))
                    ++count;
                if (count < 5)
                  {
                    count = 0;
                    for (int x = yyxbegin; x < yyxend; ++x)
                      if (yycheck_[x + yyn] == x && x != yyterror_
                          && !yy_table_value_is_error_ (yytable_[x + yyn]))
                        {
                          res.append (count++ == 0 ? ", expecting " : " or ");
                          res.append (yytnamerr_ (yytname_[x]));
                        }
                  }
              }
            return res.toString ();
          }
      }

    return "syntax error";
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yy_pact_value_is_default_ (int yyvalue)
  {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code> value indicates a syntax error.
   * @param yyvalue   the value to check
   */
  private static boolean yy_table_value_is_error_ (int yyvalue)
  {
    return yyvalue == yytable_ninf_;
  }

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
  private static final byte yypact_ninf_ = -6;
  private static final byte yypact_[] =
  {
        -6,     1,     8,    -6,    -6,    84,    -5,    29,    75,    73,
      77,    79,    80,    -6,    -6,    85,     3,     5,    46,    48,
      50,    52,    54,    56,    82,    86,    58,    60,    62,    89,
      91,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    64,    66,    -6,    68,    70,    72,    74,    76,
      78,    87,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    83,    -6,    88,    -6,    90,
      -6,    92,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    93,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6
  };

  /* YYDEFACT[S] -- default reduction number in state S.  Performed when
     YYTABLE doesn't specify something else to do.  Zero means the
     default is an error.  */
  private static final byte yydefact_[] =
  {
         3,     0,     6,     1,     5,     7,     9,     0,     0,     0,
       0,     0,     0,    30,    31,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    62,    57,    58,    59,    60,    61,    66,    64,    63,
      65,    67,     0,     0,    98,     0,     0,     0,     0,     0,
       0,     0,    11,    10,    12,    13,    16,    14,    15,    33,
      34,    35,    36,    37,    38,    39,    40,    41,    42,    43,
      44,    45,    46,    47,    48,    49,    50,    51,    52,    53,
      54,    55,    56,     8,    22,    18,    24,    19,    26,    20,
      28,    21,    17,    32,    68,    69,    78,    79,    80,    81,
      82,    83,    84,    85,    74,    75,    76,    77,    70,    71,
      72,     0,    86,    87,    88,    89,    91,    92,    90,    93,
      94,    95,    96,    97,    99,   100,   101,   102,   107,   108,
     109,   110,   103,   104,   105,   106,     4,    23,    25,    27,
      29,    73
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] =
  {
        -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,    -6,
      -6
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final byte
  yydefgoto_[] =
  {
        -1,     1,     2,     5,     6,    51,    52,    85,    87,    89,
      91,    53,    54,    55,    56,    57,    58,    59,    60,    61,
      62,    63,    64,    65,    66,    67,    68,    69,    70,    71,
      72,    73,    74,    75,    76,    77,    78,    79,    80,    81,
      82
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If YYTABLE_NINF_, syntax error.  */
  private static final short yytable_ninf_ = -3;
  private static final short
  yytable_[] =
  {
         7,     3,     8,     9,    10,    11,    12,    94,    95,    96,
      97,     4,    13,    14,    15,    16,    17,    18,    19,    20,
      21,    22,    23,    24,    25,    26,    27,    28,    29,    30,
      31,    32,    33,    34,    35,    36,    37,    38,    39,    40,
      41,    42,    43,    44,    45,    46,    47,    48,    49,    50,
      98,    99,   100,   101,   102,   103,   104,   105,   106,   107,
     108,   109,   112,   113,   114,   115,   116,   117,   120,   121,
     122,   123,   124,   125,   126,   127,   128,   129,   130,   131,
     132,   133,   134,   135,    -2,    83,    86,    84,   110,    93,
     136,    88,   111,   118,    90,   137,    92,   119,     0,   141,
       0,   138,     0,     0,   139,     0,     0,   140
  };

  /* YYCHECK.  */
  private static final byte
  yycheck_[] =
  {
         5,     0,     7,     8,     9,    10,    11,     4,     5,     4,
       5,     3,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
       4,     5,     4,     5,     4,     5,     4,     5,     4,     5,
       4,     5,     4,     5,     4,     5,     4,     5,     4,     5,
       4,     5,     4,     5,     4,     5,     4,     5,     4,     5,
       4,     5,     4,     5,     0,    56,    13,    12,     6,     4,
       3,    14,     6,     4,    15,    12,    16,     6,    -1,     6,
      -1,    13,    -1,    -1,    14,    -1,    -1,    15
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,    58,    59,     0,     3,    60,    61,     5,     7,     8,
       9,    10,    11,    17,    18,    19,    20,    21,    22,    23,
      24,    25,    26,    27,    28,    29,    30,    31,    32,    33,
      34,    35,    36,    37,    38,    39,    40,    41,    42,    43,
      44,    45,    46,    47,    48,    49,    50,    51,    52,    53,
      54,    62,    63,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    80,    81,    82,    83,    84,
      85,    86,    87,    88,    89,    90,    91,    92,    93,    94,
      95,    96,    97,    56,    12,    64,    13,    65,    14,    66,
      15,    67,    16,     4,     4,     5,     4,     5,     4,     5,
       4,     5,     4,     5,     4,     5,     4,     5,     4,     5,
       6,     6,     4,     5,     4,     5,     4,     5,     4,     6,
       4,     5,     4,     5,     4,     5,     4,     5,     4,     5,
       4,     5,     4,     5,     4,     5,     3,    12,    13,    14,
      15,     6
  };

  /* TOKEN_NUMBER_[YYLEX-NUM] -- Internal symbol number corresponding
     to YYLEX-NUM.  */
  private static final short
  yytoken_number_[] =
  {
         0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299,   300,   301,   302,   303,   304,
     305,   306,   307,   308,   309,   310,    58
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    57,    58,    59,    60,    60,    60,    61,    61,    62,
      62,    62,    62,    62,    62,    62,    62,    63,    63,    63,
      63,    63,    64,    64,    65,    65,    66,    66,    67,    67,
      68,    68,    68,    69,    69,    69,    69,    69,    69,    69,
      69,    69,    69,    70,    70,    70,    70,    70,    71,    71,
      71,    71,    71,    71,    71,    71,    71,    72,    72,    72,
      72,    72,    72,    73,    73,    73,    73,    73,    74,    74,
      75,    75,    76,    77,    78,    78,    79,    79,    80,    80,
      81,    81,    82,    82,    83,    83,    84,    84,    85,    85,
      86,    87,    87,    88,    89,    89,    90,    90,    91,    92,
      92,    93,    93,    94,    94,    95,    95,    96,    96,    97,
      97
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     2,     0,     4,     1,     0,     0,     3,     0,
       1,     1,     1,     1,     1,     1,     1,     2,     2,     2,
       2,     2,     1,     2,     1,     2,     1,     2,     1,     2,
       1,     1,     2,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     1,     2,     2,
       2,     2,     2,     3,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     1,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "EOL", "NUM", "LABEL", "REGISTER",
  "BYTE", "SHORT", "INTEGER", "LONG", "STRING", "LBYTE", "LSHORT",
  "LINTEGER", "LLONG", "LSTRING", "HALT", "NOOP", "TRAP", "LDC", "LDS",
  "LDSA", "LDL", "LDLA", "LDA", "LDAA", "LDH", "LDR", "LDRR", "STA", "STL",
  "STS", "STH", "STR", "NEG", "ADD", "SUB", "MUL", "DIV", "MOD", "NOT",
  "OR", "AND", "XOR", "CMP", "BRA", "BSR", "JSR", "BEQ", "BNE", "BLT",
  "BLE", "BGT", "BGE", "RET", "':'", "$accept", "start", "macrodef",
  "program", "labeldef", "instruction", "data", "bs", "ss", "is", "ls",
  "misc", "loading", "storing", "branching", "arithmetic", "logic", "ldc",
  "ldh", "ldr", "ldrr", "lda", "ldaa", "lds", "ldsa", "ldl", "ldla", "sta",
  "stl", "sth", "sts", "str", "bra", "bsr", "jsr", "beq", "bne", "bgt",
  "bge", "blt", "ble", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        58,     0,    -1,    59,    60,    -1,    -1,    60,    61,    62,
       3,    -1,     3,    -1,    -1,    -1,    61,     5,    56,    -1,
      -1,    68,    -1,    63,    -1,    69,    -1,    70,    -1,    72,
      -1,    73,    -1,    71,    -1,    11,    16,    -1,     7,    64,
      -1,     8,    65,    -1,     9,    66,    -1,    10,    67,    -1,
      12,    -1,    64,    12,    -1,    13,    -1,    65,    13,    -1,
      14,    -1,    66,    14,    -1,    15,    -1,    67,    15,    -1,
      17,    -1,    18,    -1,    19,     4,    -1,    74,    -1,    75,
      -1,    76,    -1,    77,    -1,    78,    -1,    79,    -1,    80,
      -1,    81,    -1,    82,    -1,    83,    -1,    84,    -1,    85,
      -1,    86,    -1,    87,    -1,    88,    -1,    89,    -1,    90,
      -1,    91,    -1,    92,    -1,    93,    -1,    94,    -1,    95,
      -1,    96,    -1,    97,    -1,    36,    -1,    37,    -1,    38,
      -1,    39,    -1,    40,    -1,    35,    -1,    43,    -1,    42,
      -1,    44,    -1,    41,    -1,    45,    -1,    20,     4,    -1,
      20,     5,    -1,    27,     4,    -1,    27,     5,    -1,    28,
       6,    -1,    29,     6,     6,    -1,    25,     4,    -1,    25,
       5,    -1,    26,     4,    -1,    26,     5,    -1,    21,     4,
      -1,    21,     5,    -1,    22,     4,    -1,    22,     5,    -1,
      23,     4,    -1,    23,     5,    -1,    24,     4,    -1,    24,
       5,    -1,    30,     4,    -1,    30,     5,    -1,    31,     4,
      -1,    31,     5,    -1,    33,     4,    -1,    32,     4,    -1,
      32,     5,    -1,    34,     6,    -1,    46,     4,    -1,    46,
       5,    -1,    47,     4,    -1,    47,     5,    -1,    48,    -1,
      49,     4,    -1,    49,     5,    -1,    50,     4,    -1,    50,
       5,    -1,    53,     4,    -1,    53,     5,    -1,    54,     4,
      -1,    54,     5,    -1,    51,     4,    -1,    51,     5,    -1,
      52,     4,    -1,    52,     5,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final short yyprhs_[] =
  {
         0,     0,     3,     6,     7,    12,    14,    15,    16,    20,
      21,    23,    25,    27,    29,    31,    33,    35,    38,    41,
      44,    47,    50,    52,    55,    57,    60,    62,    65,    67,
      70,    72,    74,    77,    79,    81,    83,    85,    87,    89,
      91,    93,    95,    97,    99,   101,   103,   105,   107,   109,
     111,   113,   115,   117,   119,   121,   123,   125,   127,   129,
     131,   133,   135,   137,   139,   141,   143,   145,   147,   150,
     153,   156,   159,   162,   166,   169,   172,   175,   178,   181,
     184,   187,   190,   193,   196,   199,   202,   205,   208,   211,
     214,   217,   220,   223,   226,   229,   232,   235,   238,   240,
     243,   246,   249,   252,   255,   258,   261,   264,   267,   270,
     273
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,    90,    90,    92,    94,    94,    94,    96,    97,   100,
     101,   102,   103,   104,   105,   106,   107,   111,   112,   113,
     114,   115,   118,   118,   119,   119,   120,   120,   121,   121,
     124,   125,   126,   129,   129,   129,   129,   129,   129,   129,
     129,   129,   129,   130,   130,   130,   130,   130,   131,   131,
     131,   131,   131,   131,   131,   131,   131,   134,   135,   136,
     137,   138,   139,   142,   143,   144,   145,   146,   150,   151,
     155,   156,   160,   164,   168,   169,   173,   174,   178,   179,
     183,   184,   188,   189,   193,   194,   200,   201,   205,   206,
     210,   214,   215,   219,   225,   226,   230,   231,   235,   239,
     240,   244,   245,   249,   250,   254,   255,   259,   260,   264,
     265
  };

  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
	      + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
		       yyrhs_[yyprhs_[yyrule] + yyi],
		       ((yystack.valueAt (yynrhs-(yyi + 1)))));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] =
  {
         0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    56,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 107;
  private static final int yynnts_ = 41;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 3;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 57;

  private static final int yyuser_token_number_max_ = 310;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */
/* Line 922 of lalr1.java  */
/* Line 48 of "KasmParser.y"  */

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


/* Line 922 of lalr1.java  */
/* Line 1745 of "KasmParser.java"  */

}


