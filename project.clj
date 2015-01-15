(defproject dijkstra "0.1.0-SNAPSHOT"
  :description "Dijkstra's shortest-paths algorithm in Clojure."
  :url "http://github.com/bluemont/dijkstra"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]]
  :profiles
  {:dev
   {:source-paths ["dev"]
    :dependencies [[org.clojure/tools.namespace "0.2.8"]]}})
