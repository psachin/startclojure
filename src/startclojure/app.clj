(ns startclojure.app
  (:require [ring.adapter.jetty :as jetty])
  )

(use 'clojure.pprint)

(defn app
  [request]
  {:status 200
   :body (with-out-str
           (pprint request))})

;; (def server (jetty/run-jetty #'app {:port 8080 :join? false}))
