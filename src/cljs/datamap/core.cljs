(ns datamap.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [datamap.events :as events]
            [datamap.views :as views]
            [datamap.config :as config]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

;; (secretary/defroute "/" []
;;   (re-frame/dispatch [::events/set-active-panel :home-panel]))

;; (secretary/defroute "/about" []
;;   (re-frame/dispatch [::events/set-active-panel :about-panel]))

(defn ^:export init []
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (secretary/dispatch! path))
    :path-exists?
    (fn [path]
      (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
