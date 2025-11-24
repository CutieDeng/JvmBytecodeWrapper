package com.cutiedeng;

import org.objectweb.asm.*;

import java.io.*;
import java.util.*;

public class ClassTransform {
  private int uselessId = 0;
  public static String quotedStr(String origin) {
    if (origin.contains("\"")) {
      throw new AssertionError(String.format("unsupport quoted of input str: '%s'", origin));
    }
    return "\"" + origin + "\"";
  }
  public String jarPath = "lib/asm-9.9.jar";
  public String clazzName = "com.cutiedeng.ClassTransform";
  public PrintStream out = System.out;
  static void handleAcc(PrintStream out, int accmode) {
    if ((accmode & Opcodes.ACC_ABSTRACT) != 0) {
      out.printf("ABSTRACT ");
    }
    if ((accmode & Opcodes.ACC_ANNOTATION) != 0) {
      out.printf("ANNOTATION ");
    }
    if ((accmode & Opcodes.ACC_BRIDGE) != 0) {
      out.printf("BRIDGE ");
    }
    if ((accmode & Opcodes.ACC_DEPRECATED) != 0) {
      out.printf("DEPRECATED ");
    }
    if ((accmode & Opcodes.ACC_ENUM) != 0) {
      out.printf("ENUM ");
    }
    if ((accmode & Opcodes.ACC_FINAL) != 0) {
      out.printf("FINAL ");
    }
    if ((accmode & Opcodes.ACC_INTERFACE) != 0) {
      out.printf("INTERFACE ");
    }
    if ((accmode & Opcodes.ACC_MANDATED) != 0) {
      out.printf("MANDATED ");
    }
    if ((accmode & Opcodes.ACC_MODULE) != 0) {
      out.printf("MODULE ");
    }
    if ((accmode & Opcodes.ACC_NATIVE) != 0) {
      out.printf("NATIVE ");
    }
    if ((accmode & Opcodes.ACC_OPEN) != 0) {
      out.printf("OPEN ");
    }
    if ((accmode & Opcodes.ACC_PRIVATE) != 0) {
      out.printf("PRIVATE ");
    }
    if ((accmode & Opcodes.ACC_PROTECTED) != 0) {
      out.printf("PROTECTED ");
    }
    if ((accmode & Opcodes.ACC_PUBLIC) != 0) {
      out.printf("PUBLIC ");
    }
    if ((accmode & Opcodes.ACC_RECORD) != 0) {
      out.printf("RECORD ");
    }
    if ((accmode & Opcodes.ACC_STATIC) != 0) {
      out.printf("STATIC ");
    }
    if ((accmode & Opcodes.ACC_STATIC_PHASE) != 0) {
      out.printf("STATIC_PHASE ");
    }
    if ((accmode & Opcodes.ACC_STRICT) != 0) {
      out.printf("STRICT ");
    }
    if ((accmode & Opcodes.ACC_SUPER) != 0) {
      out.printf("SUPER ");
    }
    if ((accmode & Opcodes.ACC_SYNCHRONIZED) != 0) {
      out.printf("SYNCHRONIZED ");
    }
    if ((accmode & Opcodes.ACC_SYNTHETIC) != 0) {
      out.printf("SYNTHETIC ");
    }
    if ((accmode & Opcodes.ACC_TRANSIENT) != 0) {
      out.printf("TRANSIENT ");
    }
    if ((accmode & Opcodes.ACC_TRANSITIVE) != 0) {
      out.printf("TRANSITIVE ");
    }
    if ((accmode & Opcodes.ACC_VARARGS) != 0) {
      out.printf("VARARGS ");
    }
    if ((accmode & Opcodes.ACC_VOLATILE) != 0) {
      out.printf("VOLATILE ");
    }
  }
  public static void main(String[] args) throws Throwable {
    new ClassTransform().executeClazz();
  }
  public class AVisitor extends ClassVisitor {
    public boolean firstVisitField = false;
    public boolean firstVisitMethod = false;
    public AVisitor() {
      super(Opcodes.ASM9);
    }
    public class FVisitor extends FieldVisitor {
      public FVisitor() {
        super(Opcodes.ASM9);
      }
    }
    public class A2Visitor extends AnnotationVisitor {
      public A2Visitor() {
        super(Opcodes.ASM9);
      }
    }
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      System.err.printf("=== clazz ===%n");
      System.err.printf("version: 0x%x%n", version);
      System.err.printf("access: 0x%x%n", access);
      System.err.printf("name: %s%n", name);
      if (signature != null) {
        System.err.printf("sig: %s%n", signature);
      } else {
        System.err.printf("!sig%n");
      }
      System.err.printf("super clazz: %s%n", superName);
      System.err.printf("interfaces: %s%n", Arrays.asList(interfaces));
      out.printf("#s(Class %s ", quotedStr(name));
    }
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
      System.err.printf("descriptor: %s, visible: %s%n", descriptor, visible);
      return new A2Visitor();
    }
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
      if (!firstVisitField) {
        out.printf("(");
        firstVisitField = true;
      }
      System.err.printf("=== field ===%n");
      System.err.printf("field: %s (%s)%n", name, descriptor);
      // type analyzer
      var tt = Type.getType(descriptor);
      System.err.printf("clazz name: %s%n", tt.getClassName());
      System.err.printf("internal name: %s%n", tt.getInternalName());
      if (signature != null) {
        System.err.printf("sig: %s%n", signature);
      } else {
        System.err.printf("!sig%n");
      }
      System.err.printf("access: 0x%x%n", access);
      System.err.printf("value: %s%n", value);
      out.printf("#s(Field %s %s ", quotedStr(name), quotedStr(descriptor));
      out.printf("(");
      handleAcc(out, access);
      out.printf(")");
      out.printf(" #f)");
      return new FVisitor(); 
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
      if (!firstVisitField) {
        out.printf("() ");
      }
      if (!firstVisitMethod) {
        out.printf(") (");
        firstVisitMethod = true;
      }
      System.err.printf("=== method ===%n");
      System.err.printf("method: %s (%s)%n", name, descriptor);
      // type analyzer
      // var tt = Type.getType(descriptor);
      // System.err.printf("clazz name: %s%n", tt.getClassName());
      // System.err.printf("internal name: %s%n", tt.getInternalName());
      if (signature != null) {
        System.err.printf("sig: %s%n", signature);
      } else {
        System.err.printf("!sig%n");
      }
      System.err.printf("access: 0x%x%n", access);
      if (exceptions == null) { exceptions = new String[] {}; };
      System.err.printf("exceptions: %s%n", Arrays.asList(exceptions));
      out.printf("#s(Method %s %s ", quotedStr(name), quotedStr(descriptor));
      out.printf("(");
      handleAcc(out, access);
      out.printf(") ");
      out.printf("#f) ");
      return null;
    }
    @Override
    public void visitEnd() {
      System.err.printf("=== clazz end ===%n");
      if (!firstVisitField) {
        out.printf("() ");
      }
      if (!firstVisitMethod) {
        out.printf("() ");
      }
      out.printf(")");
      out.printf(")%n");
    }
  }
  public void executeClazz() throws IOException {
    var r = new ClassReader(clazzName);
    r.accept(new AVisitor(), 0);
  }
}
