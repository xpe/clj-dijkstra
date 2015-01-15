(ns dijkstra.core
  "A Clojure implementation of the shortest path algorithm for single
  sources by Edsger Dijkstra [1].

  This code is based on Introduction to Algorithms (3rd Edition, 2009)
  by Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford
  Stein, pages 643 to 659. Below, I abbreviate the book as ItA.

  Key details:

  * This code represents costs (i.e. edge weights) as Java primitive
  longs [2].

  * Costs must be non-negative.

  Differences with ItA:

  * I use the term 'cost' instead of 'weight' (used in ItA).

  * I setup the data structures a bit differently than ItA. This code
  considers vertexes (a set) and edges (a map of {[vertex-1 vertex-2]
  cost} as immutable. This affects the next point directly.

  * In the ItA Dijkstra pseudo-code, each vertex has two subproperties:
  a cost and a predecessor. This code uses a different approach because
  it treats the vertexes as immutable. Instead, the same information is
  kept in a map (usually called 'vcp' because the initials correspond to
  vertex, cost, predecessor). In these maps, each pair takes the form
  {vertex [cost predecessor]}. The main function, `dijkstra` returns a
  map in this format, as does `relax`, a supporting function.

  [1]: http://en.wikipedia.org/wiki/Edsger_W._Dijkstra
  [2]: https://docs.oracle.com/javase/8/docs/api/java/lang/Long.html")

(defn init
  "Returns a map with {vertex [cost predecessor]} pairs initialized to
  their default values, namely, [Long/MAX_VALUE nil]. (See the
  'Initialize Single Source' function in ItA, page 648.)"
  [vertexes source]
  {:pre [(set? vertexes)]}
  (-> (zipmap vertexes (repeat [Long/MAX_VALUE nil]))
      (assoc source [0 nil])))

(defn cost-for-vertexes
  "Returns a seq of [vertex cost] pairs, using only vertexes from q,
  (a set of vertexes)."
  [q vcp]
  {:pre [(map? vcp)]}
  (map (fn [v] [v (first (vcp v))]) q))

(defn q-min
  "Returns a vertex with minimum cost from q, a set of vertexes. If
  there are ties for a minimum, returns an arbitrary one. This should
  not affect the overall Dijkstra algorithm."
  [q vcp]
  (->> (cost-for-vertexes q vcp)
       (sort-by second)
       first
       first))

(defn relax
  "Relaxes the edge [v1 v2] and returns an updated cost and predecessor
  for each vertex; more precisely, returns a map with {vertex [cost
  predecessor]} pairs.

  From ItA, page 649, 'The process of relaxing an edge consists of
  testing whether we can improve the current shortest path to [v2] found
  so far by going through [v1], and if so, updating the [cost] and
  [predecessor] of [v2].' (Note: the [brackets] indicate where this
  code's terminology is used instead of the ItA names.)

  Arguments:
  v1    : vertex 1
  v2    : vertex 2
  edges : a map of {vertex-vector cost} pairs
  vcp   : a map with {vertex [cost predecessor]} pairs."
  [v1 v2 edges vcp]
  {:pre [(map? edges) (map? vcp)]}
  (let [[c1 _] (vcp v1)
        [c2 _] (vcp v2)
        c' (+ c1 (edges [v1 v2]))]
    (if (< c' c2)
      (assoc vcp v2 [c' v1])
      vcp)))

(defn adj-vertexes
  "Return vertexes adjacent to vertex v."
  [v edges]
  {:pre [(map? edges)]}
  (->> edges
       keys
       (filter #(= v (first %)))
       (map #(second %))))

(defn dijkstra
  "Returns a cost and predecessor for each vertex; more precisely,
  returns a map with {vertex [cost predecessor]} pairs using Dijkstra's
  shortest path algorithm for single-sources (see ItA, page 658). The
  costs (i.e. edge weights) must be non-negative.

  Arguments:
  vertexes : a set of vertexes (unique values, such as keywords)
  edges    : a map of {vertex-vector cost} pairs
  source   : a vertex

  Internal variables:
  vcp : the result (a map with {vertex [cost predecessor]} pairs.
  q   : a set of unprocessed vertexes
  s   : a set of processed vertexes"
  [vertexes edges source]
  {:pre [(set? vertexes) (map? edges) (every? #(>= % 0) (vals edges))]}
  (loop [vcp (init vertexes source)
         q vertexes
         s #{}]
    (if (empty? q)
      vcp
      (let [u (q-min q vcp)
            q' (disj q u)
            s' (conj s u)
            vs (adj-vertexes u edges)
            vcp' (reduce #(relax u %2 edges %1) vcp vs)]
        (recur vcp' q' s')))))
