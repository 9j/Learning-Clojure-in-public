(ns app.core
  "This namespace contains your application and is the entrypoint for 'yarn start'."
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST json-response-format]]))

;; articles state
(defonce articles-state (r/atom nil))

;; URI
(defonce api-uri "https://conduit.productionready.io/api")

(defn handler [response]
  (reset! articles-state response))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "something bad happened: " status " " status-text)))

(defn articles-browse []
  (GET (str api-uri "/" "/articles?limit=10"),
    {:handler handler
     :response-format (json-response-format {:keywords? true})
     :error-handler error-handler}))

(comment (articles-browse)
         (first (:articles (deref articles-state))))

;; Mock Data
(defonce mock-articles
  [{:title "무야호"}])

(defn article-preview [article]
  [:h2 (:title article)])

(defn articles [items]
  (if-not items
    [:div.article-preview "Loading..."]
    (if (= 0 (count items))
      [:div.article-preview "No articles are hear... yet."]
      [:div
       (for [{:keys [slug] :as article} items]
         ^{:key slug} [article-preview article])])))

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
   [articles (:articles (deref articles-state))]])

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
  (articles-browse)
  (render))
