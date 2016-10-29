(ns blog.routes.porto-admin.profile
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [ring.util.response :refer [redirect response]]))

(defn update-mind
  [request]
  (if-let [account (-> request :session :account)]
    (do
      (db/update_user_mind! (-> request :params))
      (db/create_new! {:account account, :obj "mind", :type"update", :content "更新了心情", :create_time (l/local-now),
                       :image nil, :video nil, :music nil, :post nil})
      (-> (redirect "/admin")
          (assoc :flash nil)))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )

(defn update-profile
  [request]
  (if-let [account (-> request :session :account)]
    (do
      (db/update_user_profile! (-> request :params))
      (db/create_new! {:account account, :obj "profile", :type"update", :content "更新了资料", :create_time (l/local-now),
                       :image nil, :video nil, :music nil, :post nil})
      (-> (redirect "/admin")
          (assoc :flash nil))
      )
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))))