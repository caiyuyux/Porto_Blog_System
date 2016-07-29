(ns blog.routes.porto-admin.base
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [ring.util.response :refer [redirect response]]))


(defn admin_index_page
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/index.html"
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [news (not-empty (db/get_news user))] {:news news})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))))
