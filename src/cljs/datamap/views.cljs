(ns datamap.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r]
            [datamap.db :as db]
            [antizer.reagent :as ant]
            [datamap.subs :as subs]
            [datamap.events :as events]))

(def pagination {:show-size-changer true
                 :page-size-options ["5" "10" "20"]
                 :show-total        #(str "Total : " % " jeux de données ")})

(defn comparison [data1 data2 field]
  (compare (get (js->clj data1 :keywordize-keys true) field) 
           (get (js->clj data2 :keywordize-keys true) field)))

;; We need to use dataIndex instead of data-index
(def columns [{:title "Name" :dataIndex "name" :sorter #(comparison %1 %2 :name)}
              {:title "Description" :dataIndex "description" :sorter #(comparison %1 %2 :address)}])

(def people [{:id 1 :name "Jeu 1" :description "Une description du jeu 1"}
             {:id 2 :name "Jeu 2" :description "Une description du jeu 2"}
             {:id 3 :name "Jeu 3" :description "Une description du jeu 3"}
             {:id 4 :name "Jeu 4" :description "Une description du jeu 4"}
             {:id 5 :name "Jeu 5" :description "Une description du jeu 5"}
             {:id 6 :name "Jeu 6" :description "Une description du jeu 6"}
             {:id 7 :name "Jeu 7" :description "Une description du jeu 7"}
             {:id 8 :name "Jeu 8" :description "Une description du jeu 8"}
             {:id 9 :name "Jeu 9" :description "Une description du jeu 9"}
             {:id 10 :name "Jeu 10" :description "Une description du jeu 10"}
             {:id 11 :name "Jeu 11" :description "Une description du jeu 11"}
             {:id 12 :name "Jeu 12" :description "Une description du jeu 12"}])

(defn add-actions-column [columns data-atom]
  (conj columns
        {:title "Éditer"
         :render
         #(r/as-element
           [ant/button {:icon "edit" :type "warning"
                        :on-click
                        (fn []
                          (reset! data-atom
                                  (remove (fn [d] (= (get (js->clj %2) "id") 
                                                     (:id d)))
                                          @data-atom)))}])}
        {:title "Supprimer"
         :render 
         #(r/as-element 
           [ant/button {:icon "delete" :type "danger"
                        :on-click
                        (fn []
                          (reset! data-atom
                                  (remove (fn [d] (= (get (js->clj %2) "id") 
                                                     (:id d)))
                                          @data-atom)))}])}))

(defn datatable []
  (let [data (r/atom people)]
    (fn []
      [ant/table 
       {:columns    (add-actions-column columns data)
        :dataSource @data :pagination pagination :row-key "id"
        :row-selection 
        {:on-change
         #(let [selected (js->clj %2 :keywordize-keys true)]
            (ant/message-info (str "Vous avez choisi : " (map :name selected))))}}])))

(defn dataset-form []
  (fn [props]
    (let [my-form (ant/get-form)]
      [ant/form
       [ant/row {:gutter 24}
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "name"
                              {:initial-value "Nom du jeu de données"
                               :rules         [{:required true}]}
                              [ant/input])]]
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "service-name"
                              {:initial-value "Description"
                               :rules         [{:required true}]}
                              [ant/input])]]]
       [ant/row {:gutter 24}
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "service-type"
                              {:initial-value "Type de service"
                               :rules         [{:required true}]}
                              [ant/input])]]
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "public-policy"
                              {:initial-value "Politique publique"
                               :rules         [{:required true}]}
                              [ant/input])]]]
       [ant/row {:gutter 24}
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "source"
                              {:initial-value "Source"
                               :rules         [{:required true}]}
                              [ant/input])]]
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "keyword"
                              {:initial-value "Mots-clefs"
                               :rules         [{:required true}]}
                              [ant/input])]]]
       [ant/row {:gutter 24}
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "license"
                              {:initial-value "Licence"
                               :rules         [{:required true}]}
                              [ant/input])]]
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "format"
                              {:initial-value "Format"
                               :rules         [{:required true}]}
                              [ant/input])]]]
       [ant/row {:gutter 24}
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "open-data-status"
                              {:initial-value "Statut open data"
                               :rules         [{:required true}]}
                              [ant/input])]]
        [ant/col {:span 12}
         [ant/form-item
          (ant/decorate-field my-form "url"
                              {:initial-value "Lien public"
                               :rules         [{:required true}]}
                              [ant/input])]]]
       [ant/form-item {:label "Date de mise à jour"}
        (ant/decorate-field my-form "updated"
                            [ant/date-picker {:format "MMM Do YYYY"}])]
       [ant/row
        [ant/col {:offset 20}
         [ant/button
          {:type     "danger"
           ;; :size     "large"
           :on-click #(.log js/console
                            (pr-str (js->clj
                                     (ant/get-fields-value my-form)
                                     :keywordize-keys true
                                     )))}
          "Envoyer"]]]])))

(defn init-dataset-form []
  (ant/create-form (dataset-form)))

(defn about-modal []
  (fn []
    (let [modal (re-frame/subscribe [::subs/about-modal])]
      [ant/modal
       {:visible   @modal
        :title     "Cartographie des données"
        :ok-text   "Compris !"
        :on-cancel #()
        :on-ok     #(re-frame/dispatch [::events/set-about-modal! false])}
       [:p "Paragraphe décrivant le site de saisie des données."]])))

(defn display-data []
  [ant/layout-content {:class "content-area"}
   [:h1 "Liste des jeux de données"]
   [:br]
   [datatable]])

(defn display-form []
  [ant/layout-content {:class "content-area"}
   [:h1 "Nouveau jeu de données"]
   [:br]
   [init-dataset-form]])

(defn layout-content-view [view-name]
  (case view-name
    :data [display-data]
    :form [display-form]
    [:p "Rien"]))

(defn main-panel []
  [ant/locale-provider {:locale (ant/locales "fr_FR")}
   [ant/layout
    [about-modal] ;; Initialize modal
    [ant/layout-header {:class "banner"}
     [ant/row {:gutter 36}
      [ant/col {:span 2}
       [ant/popover {:content "La liste des jeux de données"}
        [ant/button
         {:type     "primary"
          :on-click #(re-frame/dispatch [::events/set-view! :data])}
         "Lister"]]]
      [ant/col {:span 2}
       [ant/popover {:content "Ajouter un jeu de données"}
        [ant/button
         {:type     "primary"
          :on-click #(re-frame/dispatch [::events/set-view! :form])}
         "Ajouter"]]]
      [ant/col {:span 1 :offset 17}
       [ant/popover {:content "Informations"}
        [ant/button
         {:on-click #(re-frame/dispatch [::events/set-about-modal! :true])}
         [ant/icon {:class "banner-logo" :type "question-circle-o"}]]]]
      [ant/col {:span 1}
       [ant/popover {:content "Sauvegarder les modifications"}
        [ant/button
         [ant/icon {:class "banner-logo" :type "save"}]]]]
      [ant/col {:span 1}
       [ant/popover {:content "Créer un compte ou vous connecter."}
        [ant/button
         [ant/icon {:class "banner-logo" :type "login"}]]]]]]
    [layout-content-view @(re-frame/subscribe [::subs/view])]
    [ant/layout-footer
     [:p "Pied de page."]]]])
