(ns dijkstra.core-test
  (:require [clojure.test :refer :all]
            [dijkstra.core :refer :all]))

(deftest dijkstra-test
  (testing "dijkstra example"
    (let [vertexes #{:s :t :x :y :z}
          edges {[:s :t] 10
                 [:s :y] 5
                 [:t :x] 1
                 [:t :y] 2
                 [:y :t] 3
                 [:y :x] 9
                 [:x :z] 4
                 [:z :x] 6
                 [:y :z] 2
                 [:z :s] 7}]
      (is (= (dijkstra vertexes edges :s)
             {:x [9 :t], :t [8 :y], :z [7 :y], :s [0 nil], :y [5 :s]}))
      (is (= (dijkstra vertexes edges :t)
             {:x [1 :t], :t [0 nil], :z [4 :y], :s [11 :z], :y [2 :t]}))
      (is (= (dijkstra vertexes edges :x)
             {:x [0 nil], :t [19 :y], :z [4 :x], :s [11 :z], :y [16 :s]}))
      (is (= (dijkstra vertexes edges :y)
             {:x [4 :t], :t [3 :y], :z [2 :y], :s [9 :z], :y [0 nil]}))
      (is (= (dijkstra vertexes edges :z)
             {:x [6 :z], :t [15 :y], :z [0 nil], :s [7 :z], :y [12 :s]})))))
