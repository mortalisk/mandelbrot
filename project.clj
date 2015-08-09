(defproject mandel "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [quil "2.2.6"]]
  :main mandel.core
  :jvm-opts ["-Xmx1g" "-server"]
  :global-vars {*warn-on-reflection* true
                *assert* false})
