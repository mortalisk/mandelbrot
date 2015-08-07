(ns mandel.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(deftype complex [^double real ^double imag])

(defn plus [^complex z1 ^complex z2]
  (let [x1 (double (.real z1))
        y1 (double (.imag z1))
        x2 (double (.real z2))
        y2 (double (.imag z2))]
    (complex. (+ x1 x2) (+ y1 y2))))

(defn times [^complex z1 ^complex z2]
  (let [x1 (double (.real z1))
        y1 (double (.imag z1))
        x2 (double (.real z2))
        y2 (double (.imag z2))]
    (complex. (- (* x1 x2) (* y1 y2)) (+ (* x1 y2) (* y1 x2)))))

(defn abs [^complex z]
  (Math/sqrt (+ (* (.real z) (.real z))
                (* (.imag z) (.imag z)))))

(defn mandel [c]
  (let [end 512]
    (loop [z (complex. 0 0)
           n 0]
      (if (< n end)
        (if (> (abs z) 2)
          (/ n end)
          (recur (plus (times z z) c)
                 (+ n 1)))
        (double -1.0)))))

(defn setup [])


(def wo (double (/ 1 20000000)))
(def ox 0.2694911)
(def oy -0.004556107)

(defn draw []
  (if (q/mouse-pressed?) (println "pressed"))
  (let [pxls (q/pixels)
        width (q/width)
        height (q/height)
        left (- ox (/ wo 2))
        top (+ oy (/ wo 2))]
      (dotimes [y height]
        (let [row (* y width)]
          (dotimes [x width]
            (let [xc (double (+ (* (/ x width)  wo) left))
                  yc (double (- (* (/ y height) wo) top))
                  m  (mandel (complex. xc yc ))]
              (if (< m 0)
                (aset pxls (+ row x) (q/color 0 0 0))
                (aset pxls (+ row x) (q/color (* m 255)
                                              (* m 128)
                                              200))))))))
  (println "drew")
  (q/update-pixels))

(q/defsketch example
  :title "image demo"
  :setup setup
  :draw draw
  :size [200 200]
  :renderer :p2d)
