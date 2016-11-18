(ns blog.routes.porto-admin.profile
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [ring.util.response :refer [redirect response]]
            [clojure.data.codec.base64 :as b64]
            [clojure.java.io :as io]))

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

(defn chop-header [s]
  (nth (first (re-seq #"(data:image/.*;base64,)(.*)" s)) 2))

(defn decode-str [s]
  (b64/decode (.getBytes s)))

(defn write-img! [id base64 account]
  (clojure.java.io/copy
    (decode-str (chop-header base64))
    (io/file (str "resources/public/templates/business/" account "/" id ".jpg"))))


(defn update-profile
  [request]

    (let [account (-> request :params :account)
          avatar (-> request :params :avatar)]
      (if avatar
        (write-img! "avatar" avatar account))
      (db/update_user_profile! (-> request :params))
      (db/create_new! {:account account, :obj "profile", :type"update", :content "更新了资料", :create_time (l/local-now),
                       :image nil, :video nil, :music nil, :post nil})

      "success"))