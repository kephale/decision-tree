(ns decision-tree.tree
  (:require [decision-tree.math :as dtm]
            [clj-random.core :as random]))

;; A decision tree is represented recursively as terminals or [fn {fn-output1 tree1 fn-output2 tree2}]

(defn tree-unroller
  "Function to unroll a tree so they are comparable."
  [tree]
  (tree-seq #(or (vector? %)
                 (map? %))
            identity tree))

(defn compare-trees
  "Compare two trees to test if they are the same."
  [a b]
  (every? identity
          (map = 
               (tree-unroller a)
               (tree-unroller b))))

(defn apply-tree
  "Apply a tree to a given input."
  [t in]
  (if (vector? t)
    (let [split-fn (first t)
          split-val (split-fn in)
          branch-map (second t)
          outcome (get branch-map split-val)]
      (apply-tree outcome in))
    t))

(defn compare-tree-output
  "Compare two trees to test if they are the same."
  [a b coll]
  (let [a-out (map #(apply-tree a %) coll)
        b-out (map #(apply-tree b %) coll)]
    (every? identity
            (map = a-out b-out))))                 

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
              next-fs (disj fs f)
              m (group-by f coll)]
          [f (zipmap (keys m)
                     (mapv (fn [part]
                             (fit-decision-tree target-f next-fs part))
                           (vals m)))])))))

