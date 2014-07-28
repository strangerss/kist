(defproject
  kist
  "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :ring
  {:handler kist.handler/app,
   :init kist.handler/init,
   :destroy kist.handler/destroy}
  :plugins
  [[lein-ring "0.8.10"]
   [lein-environ "0.5.0"]
   [lein-cljsbuild "1.0.3"]] ;
  :hooks [leiningen.cljsbuild]
  :cljsbuild
  {:builds
   [{:id "dev"
     :source-paths ["src-cljs"]
     :compiler
     {:optimizations :none
      :output-to "resources/public/js/main.js"
      :output-dir "resources/public/js/"
      :pretty-print true
      :source-map true}}
    {:id "release"
     :source-paths ["src-cljs"]
     :compiler
     {:output-to "resources/public/js/main.js"
      :optimizations :advanced
      :pretty-print false
      :output-wrapper false
      :closure-warnings {:non-standard-jsdoc :off}}}]}
  :url "http://example.com/FIXME"
  :profiles
  {:uberjar {:aot :all},
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}},
   :dev
   {:dependencies
    [[ring-mock "0.1.5"]
     [ring/ring-devel "1.3.0"]
     [pjstadig/humane-test-output "0.6.0"]],
    :injections
    [(require 'pjstadig.humane-test-output)
     (pjstadig.humane-test-output/activate!)],
    :env {:dev true}}}
  :dependencies
  [[log4j
    "1.2.17"
    :exclusions
    [javax.mail/mail
     javax.jms/jms
     com.sun.jdmk/jmxtools
     com.sun.jmx/jmxri]]
   [selmer "0.6.8"]
   [com.taoensso/timbre "3.2.1"]
   [com.h2database/h2 "1.4.178"]
   [noir-exception "0.2.2"]
   [markdown-clj "0.9.44"]
   [environ "0.5.0"]
   [korma "0.3.2"]
   [org.clojure/clojure "1.6.0"]
   [ring-server "0.3.1"]
   [com.taoensso/tower "2.0.2"]
   [lib-noir "0.8.4"]
   [org.clojure/clojure "1.6.0"]
   [org.clojure/clojurescript "0.0-2268"]
   [domina "1.0.3-SNAPSHOT"]]
  :repl-options
  {:init-ns kist.repl}
  :min-lein-version "2.0.0")
