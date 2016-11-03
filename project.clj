(defproject ashen "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.170"]]
  :plugins [[lein-cljsbuild "1.1.1"]]
  :clean-targets ^{:protect false} ["resources"]

  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src"]
                        :compiler {
                                   :main ashen.core
                                   :output-to "resources/public/js/dev/ashen.js"
                                   :output-dir "resources/public/js/dec"
                                   :target :nodejs
                                   :optimizations :none
                                   :source-map true}}]})
