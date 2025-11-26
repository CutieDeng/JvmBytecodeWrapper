#lang racket

(struct JvmBlock (info insns) #:transparent)

(provide (struct-out JvmBlock))
