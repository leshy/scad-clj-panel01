(defproject panel01 "0.1.0-SNAPSHOT"
  :description "panel01"
  :license {:name "gpl" }
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [scad-clj "0.5.2"]
                 ]
  :plugins [[lein-auto "0.1.3"]]
  :main ^:skip-aot panel01.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
