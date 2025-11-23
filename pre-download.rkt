#lang racket

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
  (eprintf "jobs already done~n")
)

(define (compile)
  (and
    (system* (find-executable-path "javac") "-d" out-dir "src/com/cutiedeng/ClassTransform.java" "-cp" "libs/*")
    (eprintf "compile ClassTransform.java successfully~n")
  )
)

(define (run)
  (and
    (parameterize ([current-directory (build-path out-dir)])
      (system* (find-executable-path "java") "-cp" "../libs/asm-9.9.jar:" "com/cutiedeng/ClassTransform")
    )
    (eprintf "execute ClassTransofmr.java successfully~n")
  )
)

; (download-libs)
(and
  (compile)
  (run)
)
