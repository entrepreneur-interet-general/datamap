(ns datamap.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::about-modal
 (fn [db]
   (:about-modal db)))

(re-frame/reg-sub
 ::view
 (fn [db]
   (:view db)))
