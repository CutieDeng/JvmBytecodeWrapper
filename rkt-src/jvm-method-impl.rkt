#lang racket

(require cutie-ftree)

(require "core.rkt")
(require "jvm-method.rkt")
(require "jvm-block.rkt")

(define JvmMethodCreater (class object% (super-new)
  (field
    [blocks (ordl-make-empty integer-compare)]
    [id->label (ordl-make-empty integer-compare)]
    [label->id (ordl-make-empty string-compare)]
    [id-cnt 1]
    [current-label-id 0]
    [current-insns (ral-empty)]
  )
  (define (generate-id)
    (begin0 id-cnt (set! id-cnt (+ id-cnt 1)))
  )
  (define (id-from-label label)
    (define maybe-id (dict-ref label->id label #f))
    (cond
      [maybe-id maybe-id]
      [else
        (define id (generate-id))
        (set! id->label (dict-set id->label id label))
        (set! label->id (dict-set label->id label id))
        id
      ])
  )
  (define (flush-block)
    (cond [current-label-id (set! blocks (dict-set blocks current-label-id (JvmBlock (ordl-make-empty symbol-compare) current-insns)))])
  )
  (define (flush-insn-enter-id^ id)
    (flush-block)
    (set! current-insns (ral-empty))
    (set! current-label-id id)
  )
  (define/public (insns->jvm-method insns)
    (for ([i insns]) (handle-insn i))
    (flush-block)
    (eprintf "debug: total cnt id: ~a~n" id-cnt)
  )
  (define (handle-insn insn) (match insn
    [(Insn 'CUTIEDENG-LABEL `(,label)) (flush-insn-enter-id^ (id-from-label label))]
    [(and (Insn (or 'IRETURN 'LRETURN 'FRETURN 'DRETURN 'ARETURN 'RETURN) '()) i) (set! current-insns (ral-consr current-insns i)) (flush-block) (set! current-label-id #f) (set! current-insns #f)]
    [(and (Insn 'RET `(,_)) i) (set! current-insns (ral-consr current-insns i)) (flush-block) (set! current-label-id #f) (set! current-insns #f)]
    [(Insn (and (or 'IFEQ 'IFNE 'IFLT 'IFGE 'IFGT 'IFLE 'IF_ICMPEQ 'IF_ICMPNE 'IF_ICMPLT 'IF_ICMPGE 'IF_ICMPGT 'IF_ICMPLE 'IF_ACMPEQ 'IF_ACMPNE 'GOTO 'JSR 'IFNULL 'IFNONNULL) opcode) `(,label))
      (define new-label-id (id-from-label label))
      (define i^ (Insn opcode new-label-id))
      (set! current-insns (ral-consr current-insns i^)) (flush-block) (set! current-label-id #f) (set! current-insns #f)]
    [(Insn 'TABLESWITCH `(,vmin ,vmax ,dflt ,lbls))
      (define dflt^ (id-from-label dflt))
      (define lbls^ (for/fold ([lbls^ (ral-empty)]) ([l lbls])
        (ral-consr lbls^ (id-from-label l))))
      (define i^ (Insn 'TABLESWITCH `(,vmin ,vmax ,dflt^ ,lbls^)))
      (set! current-insns (ral-consr current-insns i^)) (flush-block) (set! current-label-id #f) (set! current-insns #f)]
    [(Insn 'LOOKUPSWITCH `(,dflt ,keys ,lbls))
      (define dflt^ (id-from-label dflt))
      (define keys^ (for/fold ([keys^ (ral-empty)]) ([k keys])
        (ral-consr keys^ k)))
      (define lbls^ (for/fold ([lbls^ (ral-empty)]) ([l lbls])
        (ral-consr lbls^ (id-from-label l))))
      (define i^ (Insn 'LOOKUPSWITCH `(,dflt^ ,keys^ ,lbls^)))
      (set! current-insns (ral-consr current-insns i^)) (flush-block) (set! current-label-id #f) (set! current-insns #f)]
    [(Insn 'TRY-CATCH-BLOCK `(,start ,end ,handler ,type))
      (eprintf "unsupport try(~a ~~ ~a) catch (tt: ~a) { ~a: }~n" start end type handler)
      (void)
    ]
    [i (set! current-insns (ral-consr current-insns i))]
  ))
))

(provide JvmMethodCreater)
