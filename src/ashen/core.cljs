(ns ashen.core
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]
            [ashen.tracery :refer [base-grammar gen-on-grammar]]
            [ashen.agent :refer [tick-agent update-agent create-agent explain-state-change]]
            [ashen.strips :refer [solve-simple-ashen step-to-sentence]]
            [ashen.location :refer [random-location-type rand-name gen-location]]))

(nodejs/enable-util-print!)

(def fs (nodejs/require "fs"))
(def strips (nodejs/require "strips-sync"))
(def tracery (nodejs/require "tracery-grammar"))


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
(defn write-to-file [novel]
  (fs.writeFile "novel/ash-v3.txt" novel,
    (fn [err]
      (if err
        (println err)
        (println "Novel saved!")))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [len 50000
        novel (string/join " " (repeat len "ash"))]
       (def potential-story (mapv #(gen-on-grammar base-grammar) (range 0 1500)))

       (println (count potential-story))

       (println (word-count (string/join "\n\n" potential-story)))
       ;;(write-to-file (string/join "\n\n" potential-story))
       ;;(solve-example-problem)
       ;;(def solution (solve-simple))
       ;;(println solution)

       (def solution (aget (solve-simple-ashen) 0))
       (println solution)
       (println (.-path solution))
       (println (aget (.-path solution) 0))
       (def solution-step (aget (.-path solution) 0))
       (println "step-to-sentence")
       (println (step-to-sentence solution-step))

       (println (random-location-type))
       (println (rand-name))
       (println (gen-location))

       (println "status-change")
       (def orig-state (create-agent "Ash" ""))
       (def ticked-state (tick-agent orig-state))
       (def mod-state (update-agent ticked-state [:asleep] false))
       (def mod-state (update-agent mod-state [:location] "Cave"))

       (println orig-state)
       (println mod-state)
       (println (explain-state-change mod-state))
       ;;(println (.-path (solve-simple)))
       (println "")))

(set! *main-cli-fn* -main)
