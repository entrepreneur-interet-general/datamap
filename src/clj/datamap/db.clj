(ns datamap.db
  (:require [hugsql.core :as sql]
            [datamap.db-utils :refer :all]
            [cheshire.core :as json]))

(def db
  {:dbtype   "postgresql"
   :dbname   "datamap"
   :host     "localhost"
   :port     5432
   :user     "postgres"
   :password ""})

(sql/def-db-fns "datamap.sql")

;; (create-datasets-table db)
;; (drop-datasets-table db)

;; (insert-dataset-sqlvec {:info (json/parse-string "{\"test\":\"un\"}")})
;; (insert-dataset db {:info {:test "trois" :et {:aussi "cinq"}}})
;; (get-all-datasets db)
