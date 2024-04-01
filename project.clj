(defproject tammymakesthings/machinecfg "0.1.0-SNAPSHOT"
  :description "Simple machine configuration in Clojure"
  :url "https://github.com/tammymakesthings/machinecfg"
  :license {:name "MIT"
            :url "https://mit-license.org/"
            :distribution :repo}

  :min-lein-version "2.0.0"
  :main ^:skip-aot machinecfg.core
  :target-path "target/%s"
  :pedantic? :warn

  :jar-name "machinecfg.jar"
  :uberjar-name "machinecfg-standalone.jar"
  :omit-source false
  :jar-exclusions [#"(?:^|/).git/"
                   #"(?:^|/).svn"]
  :auto-clean true
  :install-releases? false
  :deploy-branches ["main"]

  :dependencies [[org.clojure/clojure "1.11.2"]
                 [cheshire "5.12.0"]
                 [org.babashka/cli "0.8.58"]
                 [org.clj-commons/clj-ssh "0.6.6"]
                 [spootnik/unilog "0.7.32"
                  :exclusions [com.fasterxml.jackson.core/jackson-core]]
                 [clansi "1.0.0"]
                 [progrock "0.1.2"]
                 [com.velisco/tagged "0.5.0"]]

  :dev-dependencies [[com.bhauman/rebel-readline "0.1.4"]
                     [lambdaisland/kaocha "1.88.1376" :exclusions [org.clojure/spec.alpha
                                                                   org.clojure/tools.reader]]]

  :plugins [[cider/cider-nrepl "0.47.1"]
            [lein-cprint "1.3.3"]
            [philoskim/debux "0.9.1"]
            [com.jakemccrary/lein-test-refresh "0.25.0"]
            [lein-cljfmt "0.9.2" :exclusions [org.clojure/clojure
                                              org.clojure/tools.cli
                                              org.clojure/spec.alpha
                                              org.clojure/core.specs.alpha]]]

  :profiles {:debug {:debug true
                     :injections []}

             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}

             :test {:dependencies [[lambdaisland/kaocha "1.88.1376" :exclusions [org.clojure/spec.alpha
                                                                                 org.clojure/tools.reader]]]}

             :global-vars {*warn-on-reflection* false
                           *assert* true}
             :dev {:dependencies [[lambdaisland/kaocha "1.88.1376" :exclusions [org.clojure/spec.alpha
                                                                                org.clojure/tools.reader]]

                                  [clj-stacktrace "0.2.4"]]
                   :resource-paths ["dummy-data"]
                   :global-vars {*warn-on-reflection* false
                                 *assert* true}}

             :repl {:plugins [[cider/cider-nrepl "0.47.1"]
                              [com.bhauman/rebel-readline "0.1.4"]]
                    :repl-options {:init-ns machinecfg.core
                                   :timeout 45000}
                    :warn-on-reflection false}}

  :aliases {"test" ["run" "-m" "kaocha.runner" "--focus" ":unit"]
            "test-unit" ["run" "-m" "kaocha.runner" "--focus" ":unit"]
            "test-integration" ["run" "-m" "kaocha.runner" "--focus" ":integration"]
            "test-all" ["run" "-m" "kaocha.runner" "--focus" ":all"]
            "repl" ["trampoline" "run" "-m" "rebel-readline.main"]
            "go" ^:pass-through-help ["run" "-m"]}

  :prep-tasks []
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["deploy"]]

  :javac-options ["-target" "1.6" "-source" "1.6"]
  :jvm-opts ["-Xms256m"
             "-Xmx512m"]
  :global-vars {*assert* false})
