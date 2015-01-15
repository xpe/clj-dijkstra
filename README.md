# dijkstra

A Clojure implementation of the shortest path algorithm for single sources by
Edsger Dijkstra.

## Usage

```clj
(require '[dijkstra.core :refer [dijkstra]])
(def vertexes #{:s :t :x :y :z})
(def edges {[:s :t] 10
            [:s :y] 5
            [:t :x] 1
            [:t :y] 2
            [:y :t] 3
            [:y :x] 9
            [:x :z] 4
            [:z :x] 6
            [:y :z] 2
            [:z :s] 7})
(dijkstra vertexes edges :s)
```

Returns a `[cost predecessor]` value for each destination vertex from the source,
`:s`:

```clj
{:x [9 :t], :t [8 :y], :z [7 :y], :s [0 nil], :y [5 :s]}
```

In other words, the lowest costs are:

* 9 from `:s` to `:x`
* 8 from `:s` to `:t`
* 7 from `:s` to `:z`
* 0 from `:s` to `:s`
* 5 from `:s` to `:y`

And the least-cost paths are:

* `[:s :y :t :x]` for 9
* `[:s :y :t]` for 8
* `[:s :y :z]` for 7
* `[:s]` for 0
* `[:s :y]` for 5

## License

Copyright 2015 David James.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
