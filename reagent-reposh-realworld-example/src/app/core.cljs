(ns app.core
  "This namespace contains your application and is the entrypoint for 'yarn start'."
  (:require [reagent.core :as r]))

;; Mock Data
(defonce mock-articles
  [{:title "무야호"}])

(defn articles [items]
  (if-not items
    [:div.article-preview "Loading..."]
    (if (= 0 (count items))
      [:div.article-preview "No articles are hear... yet."]
      [:div
       (for [article items]
         [:h2 (:title article)])])))

(defn header []
  [:nav {:className "navbar navbar-light"}
   [:div {:className "container"}
    [:a {:className "navbar-brand"} "conduit"]]])

;; Home page
(defn banner [token]
  (when-not token)
  [:div.banner>div.container
   [:h1.logo-font "conduit"]
   [:p "A place to share your knowledge."]])

;; Main view
(defn main-view []
  [:div.col-md-9>div.feed-toggle
   [:ul.nav.nav-pills.outline-active
    [:li.nav-items
     [:a.nav-link.active {:href ""} "Global Feed"]]]
   [articles mock-articles]])

(defn sidebar []
  [:div.col-md-3
   [:div.sidebar
    [:p "Popular Tags"]]])

(defn home-page []
  [:div.home-page
   [banner]
   [:div.container.page>div.row
    [main-view]
    [sidebar]]])

(defn app []
  [:div
   [header]
   [home-page]])

(defn ^:dev/after-load render
  "Render the toplevel component for this app."
  []
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main
  "Run application startup logic."
  []
  (render))