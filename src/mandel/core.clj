(ns mandel.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [mandel.complex :refer :all])
   (:import [mandel.complex complex]))

(def viewwidth 0.0000001)
(def centerx 0.2694911)
(def centery -0.004556107)

(defn mandel [^complex c]
  (let [end 1000]
    (loop [z (complex. 0 0)
           n 0]
      (if (< n end)
        (if (> (abs-square z) 4.0)
          (/ n end)
          (recur (plus (times z z) c)
                 (+ n 1)))
        (double -1.0)))))

(defrecord point [^double x ^double y])

(defn draw []
     (let [^ints image (q/pixels)
            width (q/width)
            height (q/height)
            left (- centerx (/ viewwidth 2))
            top (+ centery (/ viewwidth 2))
            mandel-color (fn [^point p]
                          (let [x (.x p)
                                y (.y p)
                                index (+ x (* y width))
                                xc (double (+ (* (/ x width)  viewwidth) left))
                                yc (double (- (* (/ y height) viewwidth) top))
                                m (mandel (complex. xc yc )) ]
                            (if (< m 0)
                              [index (q/color 0 0 0)]
                              [index (q/color (* m 255) (* m 128) 200)])))
           set-color (fn [[i c]] (aset image ^int i ^int c))]
      (time
      (->> (for [y (range height) x (range width) ] (point. x y))
           (partition 8)
           (pmap #(->> %
                         (map mandel-color)
                         (map set-color)
                         (doall)))
           (doall)))
      (println "drew")
      (q/update-pixels)))

(defn setup [])

(q/defsketch example
  :title "image demo"
  :setup setup
  :draw draw
  :size [800 800]
  :renderer :p2d)
