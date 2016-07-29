(defproject blog "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [selmer "1.0.7"]
                 [markdown-clj "0.9.80"]
                 [environ "1.0.1"]
                 [metosin/ring-middleware-format "0.6.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [bouncer "0.3.3"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [org.webjars/bootstrap "3.3.5"]
                 [org.webjars/jquery "2.1.4"]
                 [com.taoensso/tower "3.0.2"]
                 [com.taoensso/timbre "4.1.4"]
                 [com.fzakaria/slf4j-timbre "0.2.1"]
                 [compojure "1.4.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.1.5"]
                 [ring-server "0.4.0"]
                 [ring "1.4.0"]
                 [mount "0.1.3" :exclusions [ch.qos.logback/logback-classic]]
                 [migratus "0.8.7"]
                 [conman "0.5.0"]
                 [postgresql "9.3-1102.jdbc41"]
                 [org.immutant/web "2.1.1" :exclusions [ch.qos.logback/logback-classic]]
                 [buddy "0.7.2"]
                 [clj-time "0.11.0"]
                 [cheshire "5.6.1"]
                 [com.draines/postal "2.0.0"]
                 [me.raynes/fs "1.4.6"]]



  :min-lein-version "2.0.0"
  :uberjar-name "blog.jar"
  :jvm-opts ["-server"]

  :main blog.core
  :migratus {:store :database}

  :plugins [[lein-environ "1.0.1"]
            [migratus-lein "0.2.0"]]

  :profiles
  {:uberjar {:omit-source true
             :env {:production true}
             :aot :all
             :source-paths ["env/prod/clj"]}
   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]
   :project/dev  {:dependencies [[prone "0.8.2"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.4.0"]
                                 [pjstadig/humane-test-output "0.7.0"]
                                 [mvxcvi/puget "1.0.0"]]


                  :source-paths ["env/dev/clj"]
                  :repl-options {:init-ns blog.core}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]
                  ;;when :nrepl-port is set the application starts the nREPL server on load
                  :env {:dev        true
                        :port       80
                        :nrepl-port 7000
                        :log-level  :trace}}
   :project/test {:env {:test       true
                        :port       3001
                        :nrepl-port 7001
                        :log-level  :trace}}

   :profiles/dev  {:env {:database-url "jdbc:postgresql://localhost/blog?user=postgres&password=root"
                         :mail-host "smtp.qq.com"
                         :mail-user "519206112@qq.com"
                         :mail-pass "jtlwobjfpwaobgca"}}
   :profiles/test {}})
