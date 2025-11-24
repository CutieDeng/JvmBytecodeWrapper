#lang racket

(require "dep.rkt")

(define libs '(
  ((name . "asm")(url . "https://repo1.maven.org/maven2/org/ow2/asm/asm/9.9/asm-9.9.jar"))
  ((name . "asm-commons")(url . "https://repo1.maven.org/maven2/org/ow2/asm/asm-commons/9.9/asm-commons-9.9.jar"))
))

(define out-dir "out")

(define cont #f)

(define (download-libs)
  (make-directory* "libs")
  (parameterize ([current-directory (build-path "libs")])
    (for/and ([l libs])
      (eprintf "start download ~a~n" (dict-ref l 'name))
      (cond
        [(let/cc k (set! cont k) #f) (system* (find-executable-path "curl") "-O" "-L" "-C" "-" (dict-ref l 'url))]
        [else (system* (find-executable-path "curl") "-O" (dict-ref l 'url))]
      )
    )
  )
  (let/cc k (set! cont k) (void))
  (eprintf "jobs already done~n"))

(define (compile srcs)
  (define fs (for/list ([s srcs]) (future (lambda ()
    (system* (find-executable-path "javac") "-d" out-dir (path->string (build-path "src" s)) "-cp" "libs/*:src")
  ))))
  ; (for ([f fs]) (touch f))
  (for/and ([f fs]) (touch f))
)

(define (run)
  (and
    (parameterize ([current-directory (build-path out-dir)])
      (system* (find-executable-path "java") "-cp" "../libs/asm-9.9.jar:" entry)
    )
    (eprintf "execute ClassTransofmr.java successfully~n")
  )
)

(and
; (download-libs)
  (compile compile-files)
  (run)
)
