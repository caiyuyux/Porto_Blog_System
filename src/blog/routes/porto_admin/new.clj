(ns blog.routes.porto-admin.new
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [ring.util.response :refer [redirect response]]))

(defn judge_news_type
  [infoMap]
  ())