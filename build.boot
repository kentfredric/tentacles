; vim: syntax=clojure
(set-env!
 :project 'irresponsible/tentacles
 :version "0.6.2"
 :source-paths   #{"src"}
 :resource-paths #{"src" "resources"}
 :dependencies '[[org.clojure/clojure "1.9.0-alpha17" :scope "provided"]
                 [clj-http "3.4.1"]
                 [cheshire "5.7.0"]
                 [com.cemerick/url "0.1.1"]
                 [org.clojure/data.codec "0.1.0"]
                 [environ "1.1.0"]
                 [adzerk/boot-test "1.2.0" :scope "test"]]
)
 
(require '[adzerk.boot-test :as t])

(task-options!
 pom {:project (get-env :project)
       :version (get-env :version)
       :description "A library for working with the Github API."
       :url (str "https://github.com/" (get-env :project))
       :scm {:url (str "https://github.com/" (get-env :project))}
       :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}}
 push {:tag true
       :ensure-branch "master"
       :ensure-release true
       :ensure-clean true
       :gpg-sign true
       :repo "clojars"})

(deftask testing []
  (set-env! :source-paths   #(conj % "test")
            :resource-paths #(conj % "test"))
  identity)

(deftask test []
  (testing)
  (t/test))

(deftask autotest []
  (testing)
  (comp (watch) (t/test)))

;; RMG Only stuff
(deftask release []
  (comp (pom) (jar) (push)))
