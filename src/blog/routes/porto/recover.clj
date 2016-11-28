(ns blog.routes.porto.recover
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [postal.core :as p]
            [clj-time.local :as l]
            [environ.core :refer [env]]
            [ring.util.response :refer [redirect response]]))

(defn smtp []
  {:host (env :mail-host)
   :user (env :mail-user)
   :pass (env :mail-pass)
   :ssl :yes!!!11})

(defn mail [request]
  {:from (env :mail-user)
   :to (-> request :params :to)
   :subject "来自Porto博客系统的验证邮件"
   :body (str "您好，该邮箱绑定的账号正在请求密码重置功能，您的验证码为：" (-> request :params :code) "，有效时间为10分钟，若非本人操作请忽略。")})

(defn recover-email [request]
  (p/send-message (smtp) (mail request)))

(defn validate-user-recover
  [request]
  (first (b/validate
           (merge (-> request :params)
                  {:code (clojure.string/upper-case (-> request :params :code))
                   :account (clojure.string/lower-case (-> request :params :account))})
           :email [[= (if-let [value (get (-> request :cookies) "keepEmail2")] (value :value)) :message "验证邮箱已变更，请重新发送验证邮件"]]
           :code [[= (if-let [value (get (-> request :cookies) "code2")] (value :value)):message "验证码错误"]]
           :password [v/required
                      [= (:password-repeat (-> request :params)) :message "两次输入的密码不匹配"]])))

(defn update-user!
  [params]
  (db/create_user!
    (update-in
      (select-keys params [:account :password :nickname :email])
      [:password]
      hashers/encrypt {:algorithm :pbkdf2+sha256})))

(defn user-recover
  [request]
  (if-let [errors (validate-user-recover request)]
    (-> (redirect "/")
        (assoc :flash (assoc (-> request :params) :errors (merge errors {:type "recover" :value "找回密码"}))))
    (do
      (update-user! (-> request :params))
      (db/create_new! {:account (-> request :params :account), :obj "recover", :type"update", :content "重置了密码", :create_time (l/local-now),
                       :image nil, :video nil, :music nil, :post nil})
      (-> (redirect "/")
          (assoc :flash (select-keys (-> request :params) [:account]))
          (assoc :session {:account (:account (-> request :params))})))))