(ns ashen.tracery
  (:require [cljs.nodejs :as nodejs]
            [clojure.string :as string]))

(def tracery (nodejs/require "tracery-grammar"))
(def tcom (nodejs/require "thesaurus-com"))

(nodejs/enable-util-print!)

;;TODO - use a name generator
(defn gen-hero-name []
  ["Ash" "Kiln" "Flame" "Blaze" "Pyre"])

(defn gen-intro []
  ["#hero# woke in a #start-place#."])

(defn gen-intro-desc []
  ["#hero# felt his #body-part# and realized it was #old#."])

(defn gen-place []
  ["church" "cave" "forest" "swamp" "prison" "boat"])

(def old (.-synonyms (.search tcom "old")))
(def sad (.-synonyms (.search tcom "sadness")))
(def desire (.-synonyms (.search tcom "desire")))
(def move (.-synonyms (.search tcom "move")))
(def saw (.-synonyms (.search tcom "saw")))
;;TODO- use the library that can acces thesaurs
;;https://github.com/zeke/moby

(def base-grammar
  (clj->js
    {:animal ["panda" "fox" "capybara" "iguana"]
     :emotion ["sad" "happy" "angry" "jealous"]
     :sad sad
     :old old
     :desire desire
     :move move
     :saw saw
     :body-part ["face" "nose" "eye" "ear" "hair" "hand" "leg" "arm" "foot"]
     :place (gen-place)
     :hero-name (gen-hero-name)
     :intro (gen-intro)
     :intro-desc (gen-intro-desc)
     :intro-emotion ["A great #sad# fell over him. There was a #desire# to #move#."]
     :intro-end ["#hero# #saw# the #place# and started to #move# towards it."]
     :story ["#intro# #intro-desc# #intro-emotion# #intro-end#"]
     :origin ["#[hero:#hero-name#][start-place:#place#]story#"]}))
