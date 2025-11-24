package com.cutiedeng.util;

import org.objectweb.asm.*;

import java.util.*;
import java.io.*;

public class AsmOpcodeUtil {
  public static boolean emitAsmOpcode(PrintStream out, int opcode) {
    if (emitAsmOpcodeMethodInsn(out, opcode)) { return true; }    
    return false;
  }
  public static boolean emitAsmOpcodeMethodInsn(PrintStream out, int opcode) {
    String op;
    if (opcode == Opcodes.INVOKEVIRTUAL) {
      op = "INVOKEVIRTUAL"; 
    } else if (opcode == Opcodes.INVOKESPECIAL) {
      op = "INVOKESPECIAL";
    } else if (opcode == Opcodes.INVOKESTATIC) {
      op = "INVOKESTATIC";
    } else if (opcode == Opcodes.INVOKEINTERFACE) {
      op = "INVOKEINTERFACE";
    } else {
      return false;
    }
    out.printf("%s ", op);
    return true;
  }
  public static boolean emitAsmOpcodeJumpInsn(PrintStream out, int opcode) {
    String op;
    if (opcode == Opcodes.IFEQ) {
      op = "IFEQ"; 
    } else if (opcode == Opcodes.IFNE) {
      op = "IFNE";
    } else if (opcode == Opcodes.IFLT) {
      op = "IFLT";
    } else if (opcode == Opcodes.IFGE) {
      op = "IFGE";
    } else if (opcode == Opcodes.IFGT) {
      op = "IFGT";
    } else if (opcode == Opcodes.IFLE) {
      op = "IFLE";
    } else if (opcode == Opcodes.IF_ICMPEQ) {
      op = "IF_ICMPEQ";
    } else if (opcode == Opcodes.IF_ICMPNE) {
      op = "IF_ICMPNE";
    } else if (opcode == Opcodes.IF_ICMPLT) {
      op = "IF_ICMPLT";
    } else if (opcode == Opcodes.IF_ICMPGE) {
      op = "IF_ICMPGE";
    } else if (opcode == Opcodes.IF_ICMPGT) {
      op = "IF_ICMPGT";
    } else if (opcode == Opcodes.IF_ICMPLE) {
      op = "IF_ICMPLE";
    } else if (opcode == Opcodes.IF_ACMPEQ) {
      op = "IF_ACMPEQ";
    } else if (opcode == Opcodes.IF_ACMPNE) {
      op = "IF_ACMPNE";
    } else if (opcode == Opcodes.GOTO) {
      op = "GOTO";
    } else if (opcode == Opcodes.JSR) {
      op = "JSR";
    } else if (opcode == Opcodes.IFNULL) {
      op = "IFNULL";
    } else if (opcode == Opcodes.IFNONNULL) {
      op = "IFNONNULL";
    } else {
      return false;
    }
    out.printf("%s ", op);
    return true;
  }
}
