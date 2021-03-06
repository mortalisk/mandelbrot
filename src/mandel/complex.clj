(ns mandel.complex)

(deftype complex [^double real ^double imag])

(defn plus [^complex z1 ^complex z2]
  (let [x1 (.real z1)
        y1 (.imag z1)
        x2 (.real z2)
        y2 (.imag z2)]
    (complex. (+ x1 x2) (+ y1 y2))))

(defn times [^complex z1 ^complex z2]
  (let [x1 (.real z1)
        y1 (.imag z1)
        x2 (.real z2)
        y2 (.imag z2)]
    (complex. (- (* x1 x2) (* y1 y2)) (+ (* x1 y2) (* y1 x2)))))

(defn abs-square [^complex z]
   (+ (* (.real z) (.real z))
                (* (.imag z) (.imag z))))
