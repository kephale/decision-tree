(ns decision-tree.tree
  (:require [decision-tree.math :as dtm]
            [clj-random.core :as random]))

;; We consider a decision tree to be a set of predicates that split nodes into discrete sets

(defn most-informative
  "Return the most informative function w.r.t. a given collection"
  [target-f fs coll]
  (apply max-key
         #(dtm/gini-gain target-f % coll)
         fs))

(defn fit-decision-tree
  "Fit a decision tree to a collection w.r.t. a given fn, which could be a key
  target-f - the target function/key
  fs - a collection of grouping functions
  coll - collection to build tree for"
  [target-f fs coll]
  (if (empty? fs)    
    (ffirst (sort-by (comp count second)
                     (random/lshuffle (seq (group-by target-f coll)))))
    (let [ent (dtm/entropy target-f coll)]
      (if (zero? ent)
        (target-f (first coll))
        (let [f (most-informative target-f fs coll)
              next-fs (disj fs f)]
          [f (mapv (fn [part]
                     (fit-decision-tree target-f next-fs part))
                   (vals (group-by f coll)))])))))

