(ns datamap.events
  (:require [re-frame.core :as re-frame]
            [datamap.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-about-modal!
 (fn [db [_ about-modal]]
   (assoc db :about-modal about-modal)))

(re-frame/reg-event-db
 ::set-view!
 (fn [db [_ view]]
   (assoc db :view view)))
