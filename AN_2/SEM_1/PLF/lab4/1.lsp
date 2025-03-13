;problema 1a)
(defun inserare(l a poz)
    (cond
        ((null l) nil)
        ((= 0 (mod poz 2)) (append (list (car l) a) (inserare (cdr l) a (+ 1 poz))))
        (t (cons (car l) (inserare (cdr l) a (+ 1 poz))))
    )
)

(defun inserareMain(l a)
    (inserare l a 1)
)




;problema 1b)
(defun listaAtomi(l)
    (cond
        ((null l) nil)
        ((atom (car l)) (append (list (car l)) (listaAtomi (cdr l))))
        ((listp (car l)) (append (listaAtomi (car l)) (listaAtomi (cdr l))))
    )
)

(defun inversareLista(l)
    (cond
        ((null l) nil)
        (t (append (inversareLista (cdr l)) (list (car l))))
    )
)

(defun listaAtomiMain(l)
    (inversareLista (listaAtomi l))
)




;problema 1c)
(defun cmmdc(a b div)
    (cond
        ((= div 1) 1)
        ((AND (= (mod a div) 0) (= (mod b div) 0)) div)
        (t (cmmdc a b (- div 1)))
    )
)

(defun cmmdcMain(a b)
    (cond
        ((OR (= a 1) (= b 1)) 1)
        ((= a b) a)
        ((> a b) (cmmdc a b b))
        ((< a b) (cmmdc a b a))
    )
)

(defun cmmdcLista(l)
    (cond
        ((null l) 1)
        ((listp (car l)) (cmmdcMain (cmmdcLista (car l)) (cmmdcLista (cdr l))))
        ((numberp (car l)) (cmmdcMain (car l) (cmmdcLista (cdr l))))
        (T (cmmdcLista (cdr l)))
    )
)




;problema 1d)
(defun nrAparitii(l el)
    (cond
        ((null l) 0)
        ((listp (car l)) (+ (nrAparitii (car l) el) (nrAparitii (cdr l) el)))
        ((= (car l) el) (+ 1 (nrAparitii (cdr l) el)))
        (t (nrAparitii (cdr l) el))
    )
)