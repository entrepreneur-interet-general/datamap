(ns datamap.db-utils
  (:require [cheshire.core :as json]
            [clojure.java.jdbc :as jdbc]))

(import 'org.postgresql.util.PGobject)

;; FIXME https://github.com/jkk/honeysql/issues/176
(defn- value-to-json-pgobject [value]
  (doto (PGobject.)
    (.setType "jsonb")
    (.setValue (json/generate-string value))))

(extend-protocol jdbc/ISQLValue
  clojure.lang.IPersistentMap
  (sql-value [value] (value-to-json-pgobject value)))

(extend-protocol jdbc/IResultSetReadColumn
  org.postgresql.util.PGobject
  (result-set-read-column [pgobj metadata idx]
    (let [type  (.getType pgobj)
          value (.getValue pgobj)]
      (if (#{"jsonb" "json"} type)
        (json/parse-string value true)
        value))))


