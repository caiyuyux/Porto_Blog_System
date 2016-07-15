(ns blog.config
  (:require [taoensso.timbre :as timbre]))

(def defaults
  {:init
   (fn []
     (timbre/info "\n-=[blog started successfully]=-"))
   :middleware identity})
