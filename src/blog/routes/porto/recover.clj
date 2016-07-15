(ns blog.routes.porto.recover
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [postal.core :as p]
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