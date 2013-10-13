(defproject spira "0.1.0-SNAPSHOT"
  :description "Trädgårdsplanerare för amatörodlare"
  :url "https://github.com/4ZM/spira"
  :license {:name "GNU General Public License (GPLv3)"
            :url "http://www.gnu.org/licenses/gpl.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.1"]
                 [cheshire "4.0.0"]
                 [hiccup "1.0.0"]]
  :plugins [[lein-ring "0.7.1"], [lein-difftest "2.0.0"]]
  :ring {:handler spira.service.routes/app}
)
