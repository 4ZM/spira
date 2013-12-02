(defproject spira "0.1.0-SNAPSHOT"
  :description "Trädgårdsplanerare för amatörodlare"

  :url "https://github.com/4ZM/spira"

  :license {:name "GNU Affero General Public License (AGPLv3)"
            :url "http://www.gnu.org/licenses/agpl.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.1"]
                 [cheshire "4.0.0"]
                 [ring/ring-json "0.2.0"]
                 [com.datomic/datomic-free "0.8.4260"]]

  :profiles {:dev {:dependencies [[midje "1.4.0"]]
                   :plugins [[lein-midje "2.0.1"]]}}

  :plugins [[lein-ring "0.7.1"]]

  :ring {:handler spira.rest-adapter.app/app}
)
