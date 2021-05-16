(ns decision-tree.math)

(let [val-log2 (Math/log 2)]
  (defn log2
    "Return the log_2 of x"
    [x]
    (/ (Math/log x) val-log2)))

(defn entropy
  "Compute the entropy of a collection of pairs [[i1, o1], [i2, o2], ...]"
  [k coll]
  (let [n (count coll)
        freqs (frequencies (map k coll))
        counts (vals freqs)]
    (reduce +
            (map (fn [x]
                   (* (- x) (log2 x)))
                 (map (fn [x]
                        (/ x n))
                      counts)))))

(defn information-gain
  "Return the information gain by splitting with a fn, which could be a key"
  [parent-f split-f coll]
  (let [n (count coll)
        ent (entropy parent-f coll)
        parts (vals (group-by split-f coll))]
    (- ent
       (reduce +
               (map (fn [x]
                      (* (entropy parent-f x)
                         (/ (count x) n)))
                    parts)))))

(defn gini-impurity
  "Return the Gini impurity of a dataset."
  [f coll]
  (let [n (count coll)
        parts (group-by f coll)]
    (reduce +
            (map (fn [[k v]]
                   (let [p (/ (count v) n)]
                     (* p (- 1 p))))
                 parts))))

(defn gini-gain
  "Return the gain on Gini impuirty from splitting with a fn, which could be a key"
  [parent-f split-f coll]
  (let [n (count coll)
        ent (gini-impurity parent-f coll)
        parts (vals (group-by split-f coll))]
    (- ent
       (reduce +
               (map (fn [x]
                      (* (gini-impurity parent-f x)
                         (/ (count x) n)))
                    parts)))))
