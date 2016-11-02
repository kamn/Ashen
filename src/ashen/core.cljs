(ns ashen.core
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]))

(nodejs/enable-util-print!)

(def fs (nodejs/require "fs"))


(defn write-to-file [novel]
  (fs.writeFile "novel/ash.txt" novel, (fn [err]
    (if err
      (println err)
      (println "Novel saved!")))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [count 50000
        novel (string/join " " (repeat count "ash"))]
          (write-to-file novel)
          (println "Hello, World!")))

(set! *main-cli-fn* -main)
