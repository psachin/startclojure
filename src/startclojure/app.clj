(ns startclojure.app
  ;; (:use (clojure.pprint))
  (:use (compojure handler
                   [core :only (GET POST defroutes)]))
  (:require [ring.util.response :as response]
            [ring.adapter.jetty :as jetty]))

;; (use 'clojure.pprint)

(defonce counter (atom 1000))

(defonce urls (atom {}))

(defn shorten
  [url]
  (let [id (swap! counter inc)
        id (Long/toString id 36)]
    (swap! urls assoc id url)
    id))


(defn homepage
  [request]
  (str @urls))


(defn redirect
  [id]
  (response/redirect (@urls id)))


(defroutes app
  (GET "/" request (homepage request))
  (GET "/:id" [id] (redirect id)))


;; (def server (jetty/run-jetty #'app {:port 8080 :join? false}))
