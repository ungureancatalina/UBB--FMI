
;preordine(l1..n) = [], l multimea vida
;                 = l1 , lista are doar elementul l1
;                 = [l1] U preordine(l2) U preordine(l3..n), altfel

(defun preordine(arbore)
	(cond
        ((null arbore) nil)
        ((null (cdr arbore)) (list (car arbore)))
        (t (append(list (car arbore))(preordine (car(cdr arbore)))(preordine (car(cdr(cdr arbore))))))
	)
)
