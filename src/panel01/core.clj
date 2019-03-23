;; http://adereth.github.io/blog/2014/04/09/3d-printing-with-clojure/
;; https://github.com/farrellm/scad-clj/blob/master/src/scad_clj/model.clj
;;
;; lein auto run
;;
;; in the .scad buffer:
;; auto-revert-mode 
;; scad-preview-mode

(ns panel01.core
  (:refer-clojure :exclude [use import])
  (:require [scad-clj.scad :refer :all]
            [scad-clj.model :refer :all]))

;; settings
(def wall 1)
(def width 126)
(def height 172)
(def space 5)
(def panelHoleR 3)
(def panelHoleStart 5)
(def holeDistance (+ panelHoleR 10))


(defn fourCorners [ width height model ]
  (union
   (translate [(/ width 2) (/ height 2) 0] model)
   (translate [(/ width 2) (/ height -2) 0] model)
   (translate [(/ width -2) (/ height -2) 0] model)
   (translate [(/ width -2) (/ height 2) 0] model)
  ))

(def mountingHoles
  (fourCorners width height (cylinder 2 10))
)


(defn panelHole [ x y ]
  (translate [ x y 0 ]
             (cylinder panelHoleR 10))
  )

(defn panelHoleY [ x ]
  (let [tHeight (- height (* 2 panelHoleStart))]
  (translate [ 0 (+  (/ tHeight -2) (/ (mod tHeight holeDistance) 2)) 0 ]
  (union
   (map
    (partial panelHole x)
    (range 0 tHeight holeDistance)))
)))

(def panelHoles
  (let [tWidth (- width (* 2 panelHoleStart))]
  (translate [ (+ (/ tWidth -2) (/ (mod tWidth holeDistance) 2) ) 0 0 ]
  (union
   (map
    panelHoleY
    (range 0 tWidth holeDistance)))
  ))
  )


(def mainPanel
  (difference
   (cube (+ (* space 2) width)  (+ (* space 2) height) wall)
   mountingHoles
  panelHoles
   ))

(def mountTube
  (difference
  (cylinder 4 15)
  (cylinder 3 20)
  )
  )

(defn -main
  [& args]
  (println "generatin")
  (spit "panel01.scad" (write-scad mainPanel))
  (spit "tube.scad" (write-scad mountTube))
  )

