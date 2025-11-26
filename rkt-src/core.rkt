#lang racket

(struct Class (name access fields methods annotations inner-classes) #:prefab)
(struct Field (name tt access static/init-value) #:prefab)
(struct Method (name tt access exceptions insns) #:prefab)
(struct Annotation (name tt) #:prefab)
(struct InnerClass (name access outer-name inner-name) #:prefab)
(struct Insn (opcode args) #:prefab)

(provide (struct-out Class) (struct-out Field) (struct-out Method) (struct-out Annotation) (struct-out InnerClass) (struct-out Insn))
