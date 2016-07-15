(ns blog.routes.porto-admin.base
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
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
               ;(when-let [all_info (not-empty (db/all_for_user user))] {:all_info all_info})
               ;(when-let [followers (not-empty (db/follower_for_user user))] {:followers followers})
               ;;select result
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))))
