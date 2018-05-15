(ns datamap.server
  (:require [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [clojure.java.io :as io]
            [ring.util.response :as response]
            [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [datamap.db :as db])
  (:gen-class))

(defn wrap-middleware [handler]
  (-> handler
      (wrap-defaults site-defaults)
      wrap-restful-format
      wrap-reload))

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name    "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css "/css/site.css")])

(defn dataset-added []
  (html5
   (head)
   [:body {:class "body-container"}
    [:h1 "Dataset added!"]]))

(defn count-datasets []
  (html5
   (head)
   [:body {:class "body-container"}
    [:h1 (format "%d datasets" (count (db/get-all-datasets db/db)))]]))

(defn display-datasets []
  (db/get-all-datasets db/db))

(defroutes routes
  (GET "/" []
       (-> "public/index.html"
           io/resource
           io/input-stream
           response/response
           (assoc :headers {"Content-Type" "text/html; charset=utf-8"})))
  (GET "/add" []
       (do (db/insert-dataset db/db
                              {:info {:name        "Jeu de données"
                                      :description "Une description"}})
           (dataset-added)))
  (GET "/count" [] (count-datasets))
  (GET "/create" [] (do (db/create-datasets-table db/db) "Table créée."))
  (GET "/drop" [] (do (db/drop-datasets-table db/db) "Table effacée."))
  (GET "/all" [] (display-datasets))
  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'routes))

;; (def server (run-jetty app {:port 3000 :join? false}))
;; (.stop server)

(defn -main [& args]
  (let [port 3000]
    (run-jetty app {:port port :join? false})))
