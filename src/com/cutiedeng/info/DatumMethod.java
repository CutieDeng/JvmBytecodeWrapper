package com.cutiedeng.info;

import org.objectweb.asm.*;

import java.io.*;
import java.util.*;

import com.cutiedeng.util.*;

public class DatumMethod {
  public class DatumLocalVar {
    public String name;  
    public String descriptor;
    public String start;
    public String end;
    public long index;
    public void toString(PrintStream out) {
      out.printf("#s(LocalVar ");
      // name
      StringUtil.toQuotedString(out, name);
      // descriptor
      StringUtil.toQuotedString(out, descriptor);
      // start
      StringUtil.toQuotedString(out, start);
      // end
      StringUtil.toQuotedString(out, end);
      // index
      out.printf("%d ", out, index);
      out.printf(") ");
    }
  }
  public String name;
  public String descriptor;
  public ArrayList<String> access = new ArrayList();
  public ArrayList<DatumInsn> insns;
  public ArrayList<DatumLocalVar> vars;
  public static DatumMethod create(String name, String descriptor, int access) {
    DatumMethod self = new DatumMethod();
    self.name = name;
    self.descriptor = descriptor;
    AccessUtil.parseAccess(self.access, access);
    return self;
  }
  public void toString(PrintStream out) {
    out.printf("#s(Method ");
    // name
    StringUtil.toQuotedString(out, name);
    // descriptor
    StringUtil.toQuotedString(out, descriptor);
    // access
    out.printf("(");
    for (String a: access) {
      out.printf("%s ", a);
    }
    out.printf(") ");
    // exceptions
    out.printf("() ");
    // local vars
    out.printf("(");
    for (DatumLocalVar v: vars) {
      v.toString(out);
    }
    out.printf(") ");
    // insns
    out.printf("(");
    for (DatumInsn i: insns) {
      i.toString(out);
    }
    out.printf(") ");
    out.printf(") ");
  }
}
