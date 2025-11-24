package com.cutiedeng.info;

import org.objectweb.asm.*;

import java.io.*;
import java.util.*;

import com.cutiedeng.util.*;

public class DatumInsn {
  public String opcodeP;
  public int opcode;
  public ArrayList<Object> args;
  public static DatumInsn create(int opcode, ArrayList<Object> args) {
    DatumInsn self = new DatumInsn();
    self.opcode = opcode;
    self.args = args;
    return self;
  }
  public static DatumInsn createOpcode(String opcode, ArrayList<Object> args) {
    DatumInsn self = new DatumInsn();
    self.opcodeP = opcode;
    self.args = args;
    return self;
  }
  public void toString(PrintStream out) {
    out.printf("#s(Insn ");
    // opcode
    if (opcodeP == null) {
      AsmOpcodeUtil.emitAsmOpcode(out, opcode);
    } else {
      out.printf("%s ", opcodeP);
    }
    out.printf("(");
    // args
    for (Object a: args) {
      if (a instanceof Long) {
        out.printf("%d ", a);
      } else if (a instanceof String) {
        StringUtil.toQuotedString(out, (String) a);
      } else if (a instanceof Boolean) {
        out.printf("%s ", (boolean) ((Boolean) a) ? "#t" : "#f");
      } else {
        throw new IllegalArgumentException(String.format("invalid args for #s(Insn %s .. %s #:type %s ..)", opcode, a, a.getClass()));
      }
    }
    out.printf(") ");
    out.printf(") ");
  }
}
