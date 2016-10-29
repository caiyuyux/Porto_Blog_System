(ns blog.routes.porto-admin.post
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [ring.util.response :refer [redirect response]]))


(defn edit_post_page!
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/edit-post.html"
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               ;(when-let [news (not-empty (db/get_news user))] {:news news})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )
(defn consult_post_page
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/consult-post.html"
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               ;(when-let [news (not-empty (db/get_news user))] {:news news})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )

(defn deleted_post!
  [request]
  (let [id (-> request :params :id)
        type (-> request :params :type)]
    (println id)
    (println )))
