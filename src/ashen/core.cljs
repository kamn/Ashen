(ns ashen.core
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]
            [ashen.tracery :refer [base-grammar]]))

(nodejs/enable-util-print!)

(def fs (nodejs/require "fs"))
(def strips (nodejs/require "strips"))
(def tracery (nodejs/require "tracery-grammar"))

(def example-domain "(define (domain blocksworld)
  (:requirements :strips)
  (:action move
     :parameters (?b ?t1 ?t2)
     :precondition (and (block ?b) (table ?t1) (table ?t2) (on ?b ?t1) (not (on ?b ?t2))
     :effect (and (on ?b ?t2)) (not (on ?b ?t1))))
     )")


(def example-problem "
  (define (problem move-blocks-from-a-to-b)
      (:domain blocksworld)
    (:init (and (block a) (block b) (table x) (table y)
           (on a x) (on b x)))
    (:goal (and (on a y) (on b y)))
  )")

(def base-t-grammar (tracery.createGrammar base-grammar))
(.addModifiers base-t-grammar tracery.baseEngModifiers)

;; A simple cost function for now
(defn cost [state]
  10)

(defn word-count [s]
  (-> s
       (string/split #"\s+")
       (count)))

;;
(defn solve-example-problem []
  (strips.load "strips/example-domain.txt" "strips/example-problem.txt"
    (fn [domain problem]
       (let [solutions (strips.solve domain problem cost)]
         (println (first solutions))))))

;;
(defn write-to-file [novel]
  (fs.writeFile "novel/ash-v2.txt" novel,
    (fn [err]
      (if err
        (println err)
        (println "Novel saved!")))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [len 50000
        novel (string/join " " (repeat len "ash"))]
       (println (.flatten base-t-grammar "#origin#"))
       (println (word-count "this is a test."))
       (def potential-story (mapv #(.flatten base-t-grammar "#origin#") (range 0 1500)))

       (println (count potential-story))

       (println (word-count (string/join "\n\n" potential-story)))
       (write-to-file (string/join "\n\n" potential-story))
       ;;(solve-example-problem)
       (println "")))

(set! *main-cli-fn* -main)
