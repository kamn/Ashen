(ns ashen.location
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]))

(def Random (nodejs/require "random-js"))
(def randopeep (nodejs/require "randopeep"))

(def mt (.engines.mt19937 Random))
(def engine (.seed mt 1000))

(def location-types ["church" "cave" "forest" "swamp" "prison" "boat" "town" "city" "ruins"
                     "island" "lake" "garden" "fortress" "road" "dungeon" "keep" "mountain" "chasm" "tower"])

(defn random-location-type []
  (string/capitalize (.pick Random engine (clj->js location-types))))

(def peep-params
  {:justLast true
   :prefix false})

(defn rand-name []
  (def rname (.name randopeep (clj->js peep-params)))
  (if (empty? rname)
    (rand-name)
    rname))

(defn gen-location []
  (def loc (random-location-type))
  (def pname (rand-name))
  (if (= ((.integer Random 0 1) engine) 0)
    (str loc " of " pname)
    (str pname "'s " loc)))

(defn gen-world-locations []
  {1 {:type :cave
      :connected [2]
      :items []}
   2 {:type :forest
      :connected [1]
      :items [:fire]}})    
