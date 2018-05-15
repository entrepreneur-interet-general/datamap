(defproject datamap "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.postgresql/postgresql "42.2.2"]
                 [com.layerware/hugsql "0.4.8"]
                 [cheshire "5.8.0"]
                 [ring-server "0.5.0"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojurescript "1.10.238"]
                 [reagent "0.8.0"]
                 [re-frame "0.10.5"]
                 ;; FIXME: needed but why?
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [org.clojure/core.async "0.4.474"]
                 [antizer "0.2.2"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.4"
                  :exclusions [org.clojure/tools.reader]]
                 ;; Keep it at the end to avoid conflict wrt tools.reader
                 [ring-middleware-format "0.7.2"]
                 [reagent-utils "0.3.1"]
                 [org.clojure/test.check "0.9.0"]]
  :target-path "target/%s"
  :main datamap.server
  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources"]
  :plugins [[lein-cljsbuild "1.1.5"]]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :figwheel {:css-dirs ["resources/public/css"]}
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :profiles
  {:dev     {:dependencies [[binaryage/devtools "0.9.10"]
                            [figwheel-sidecar "0.5.16"]
                            [com.cemerick/piggieback "0.2.2"]]
             :plugins      [[lein-figwheel "0.5.16"]]}
   :prod    {}
   :uberjar {:aot :all}}
  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "datamap.core/mount-root"}
     :compiler     {:main                 datamap.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}
    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            datamap.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})
