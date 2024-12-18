;4. Sa se construiasca o functie care intoarce suma atomilor numerici dintr-o lista, de la orice nivel.
;(load "C:\\Users\\ungur\\OneDrive\\Desktop\\facultate_materiale\\AN_2\\SEM_1\\programare logica si functionala\\lab6\\4.lsp")
;(suma '(1 (2 3) 4 (5 6 7)))
;(suma '(R 2 (3 4) A (D 6) F))

;suma(l) = l, l atom numeric
;        = 0, l atom nenumeric
;        = semn_suma(i=1,n) de li ,altfel, unde l lista, l=l1..ln

(defun suma(l)
    (cond
        ((numberp l) l)
        ((atom l) 0)
        (t (apply '+ (mapcar #'suma l)))
    )
)
