package com.cutiedeng.info;

import org.objectweb.asm.*;

import java.io.*;
import java.util.*;

import com.cutiedeng.util.*;

public class DatumInsn {
  public String opcode;
  public ArrayList<Object> args;
  public static DatumInsn create(String opcode, ArrayList<Object> args) {
    DatumInsn self = new DatumInsn();
    self.opcode = opcode;
    self.args = args;
    return self;
  }
  public void toString(PrintStream out) {
    out.printf("#s(Insn ");
    // opcode
    out.printf("%s ", opcode);
    out.printf("(");
    // args
    for (Object a: args) {
      if (a instanceof Long) {
        out.printf("%d ", a);
      } else if (a instanceof String) {
        StringUtil.toQuotedString(out, (String) a);
      } else {
        throw new IllegalArgumentException(String.format("invalid args for #s(Insn %s .. %s #:type %s ..)", opcode, a, a.getClass()));
      }
    }
    out.printf(") ");
    out.printf(") ");
  }
}
