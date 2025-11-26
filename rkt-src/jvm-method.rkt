#lang racket

(struct JvmMethod (info blocks) #:transparent)

(provide (struct-out JvmMethod))
