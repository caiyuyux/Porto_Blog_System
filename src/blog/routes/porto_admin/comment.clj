(ns blog.routes.porto-admin.comment
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [clojure.java.io :as io]
            [ring.util.response :refer [redirect response]]))

(defn comments_index_page
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/comments.html"
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               ;(when-let [file (not-empty (db/get_file_info user))] {:file file})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )