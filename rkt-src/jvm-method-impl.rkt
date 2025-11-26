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
  (define (set-id-with-label label)
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
  (define (flush-insn-enter-^id id)
    (cond [current-label-id (set! blocks (dict-set blocks current-label-id current-insns))])
    (set! current-insns (ral-empty))
    (set! current-label-id id)
  )
  (define/public (insns->jvm-method insns)
    (for ([i insns]) (handle-insn i) )
    (for ([k (in-dict-keys label->id)]) (eprintf "label: ~a~n" k))
  )
  (define (handle-insn insn) (match insn
    [(Insn 'CUTIEDENG-LABEL `(,label)) (flush-insn-enter-^id (set-id-with-label label))]
    [i (set! current-insns (ral-consr current-insns i))]
  ))
))

(provide JvmMethodCreater)
