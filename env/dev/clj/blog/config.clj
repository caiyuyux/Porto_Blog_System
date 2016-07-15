(ns blog.config
  (:require [selmer.parser :as parser]
            [taoensso.timbre :as timbre]
            [blog.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (timbre/info "\n-=[blog started successfully using the development profile]=-"))
   :middleware wrap-dev})
