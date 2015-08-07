(ns mandel.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [mandel.complex :refer :all])
   (:import [mandel.complex complex]))


(defn mandel [c]
  (let [end 1000]
    (loop [z (complex. 0 0)
           n 0]
      (if (< n end)
        (if (> (abs-square z) 4.0)
          (/ n end)
          (recur (plus (times z z) c)
                 (+ n 1)))
        (double -1.0)))))

(defn setup [])

(def wo 0.0000001)
(def ox 0.2694911)
(def oy -0.004556107)

(defn set-color [^double m index ^ints pxls]
  (if (< m 0)
    (aset pxls index (^int q/color 0 0 0))
    (aset pxls index (^int q/color (* m 255) (* m 128) 200))))

(defrecord point [^double x ^double y])

(defn draw []
   (time
    (let [
            ^ints pxls (q/pixels)
            width (q/width)
            height (q/height)
            left (- ox (/ wo 2))
            top (+ oy (/ wo 2))
          ]

        (doall
          (pmap
            #(doall
                (map
                  (fn [^point p]
                    (let [
                          x (.x p)
                          y (.y p)
                          row (* y width)
                          xc (double (+ (* (/ x width)  wo) left))
                          yc (double (- (* (/ y height) wo) top))
                          m (mandel (complex. xc yc ))
                          index (+ row x) ]
                      (set-color m index pxls)))
                  %))

            (partition 16
              (for [y (range height) x (range width) ] (point. x y)))))
        (println "drew")
        (q/update-pixels))))

(q/defsketch example
  :title "image demo"
  :setup setup
  :draw draw
  :size [800 800]
  :renderer :p2d)
