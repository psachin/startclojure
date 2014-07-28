(ns startclojure.app
  (:use (compojure handler
                   [core :only (GET POST defroutes)]))
  (:require [net.cgrand.enlive-html :as en]
            [ring.util.response :as response]
            [ring.adapter.jetty :as jetty]))

(use 'clojure.pprint)

(defonce counter (atom 1000))

(defonce urls (atom {}))

(defn shorten
  [url]
  (let [id (swap! counter inc)
        id (Long/toString id 36)]
    (swap! urls assoc id url)
    id))


(en/deftemplate homepage
  (en/xml-resource "homepage.html")
  [request])


(defn redirect
  [id]
  (response/redirect (@urls id)))


(defroutes app*
  (GET "/" request (homepage request))
  (POST "/shorten" request
        ;; {:status 200
        ;;  :body (with-out-str (pprint request))
        ;;  :headers {"Content-Type" "text/plain"}}
       (let [id (shorten (-> request :params :url))]
         (response/redirect "/"))
       )
  (GET "/:id" [id] (redirect id)))


(def app (compojure.handler/site app*))


;; (def server (jetty/run-jetty #'app {:port 8080 :join? false}))
