(ns blog.routes.porto.signin
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [ring.util.response :refer [redirect response]]))

(defn user-signin-page
  [{:keys [flash]}]
  (layout/render
    "porto/index.html"
    flash)
  )

(defn user-exists?
  [account]
  (:exists (first (db/exists_user? {:account account}))))

(defn cookie-exists?
  [request]
  (:exists (get (-> request :cookies) (-> request :params :account))))

(defn valid-token?
  [request]
  (if-let [token (get (-> request :cookies) (-> request :params :account))]
    (if
      (let [token-map (first (db/token_details_for_retrieve_account token))
            timestamp (:created_at token-map)
            now (l/local-now)
            difference-in-minutes (t/in-minutes (t/interval timestamp now))]
        (and
          (not (empty? token-map))
          (< difference-in-minutes 30)))
      nil
      "error_cookie")
    "no_cookie")
  )

(defn validate-user-login
  [request]
  (if (valid-token? request)
    (first
      (let [params (-> request :params)]
      	(b/validate params
      		:account [[user-exists? :message "用户名不存在"]]
            :password [[hashers/check (:password (first (db/get_password (select-keys params [:account])))) 
                        :message "密码有误或授权凭据失效，请重新输入"]])))
	; )
    nil))

(defn user-signin
  [request]
  (if-let [errors (validate-user-login request)]
    (-> (redirect "/")
        (assoc :flash (assoc (-> request :params) :errors (merge errors {:type "signin" :value "登录"}))))
    (-> (redirect "/")
        (assoc :session {:account (:account (-> request :params))})
        )))

(defn user-logout
  [request]
  (-> (redirect "/")
      (assoc :session {:account nil})))

(defn create-token
  [account]
  (let [token (url-part 50)]
    (do
      (if (:exists (first (db/user_has_retrieve_token? {:account account})))
        (db/update_retrieve_token! {:token token
                                    :account account
                                    :created_at (l/local-now)})
        (db/create_retrieve_token! {:token token
                                    :account account
                                    :created_at (l/local-now)}))
      token))
  )



(defn cookies-pass
  [request]
  (if (validate-user-login request)
    (generate-string {:token nil})
    (generate-string {:token (create-token (-> request :params :account))})
    )
  )