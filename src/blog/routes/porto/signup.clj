(ns blog.routes.porto.signup
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [postal.core :as p]
            [clojure.java.io :as io]
            [environ.core :refer [env]]
            [clj-time.local :as l]
            [ring.util.response :refer [redirect response]]))

(defn user-signup-page
  [request]
  (layout/render
    "porto/index.html"
    (-> request :flash)
    (:flash request)))


(defn user-exists?
  [account]
  (:exists (first (db/exists_user? {:account account}))))

(defn email-exists?
  [email]
  (:exists (first (db/exists_email? {:email email}))))

(defn validate-user-signup
  [request]
  (first (b/validate
           (merge (-> request :params)
                  {:code (clojure.string/upper-case (-> request :params :code))
                   :account (clojure.string/lower-case (-> request :params :account))})
           :account [[#(not (user-exists? %)) :message "用户名已经存在"]]
           :email [
                   [= (if-let [value (get (-> request :cookies) "keepEmail")] (value :value)) :message "验证邮箱已变更，请重新发送验证邮件"]
                   [#(not (email-exists? %)) :message "该邮箱已经注册过"]]
           :code [[= (if-let [value (get (-> request :cookies) "code")] (value :value)):message "验证码错误"]]
           :password [v/required
                      [v/min-count 6 :message "密码长度最少为6位"]
                      [v/max-count 25 :message "密码长度最多为25位"]
                      [= (:password-repeat (-> request :params)) :message "两次输入的密码不匹配"]]
           )))

(defn save-user!
  [params]
  (db/create_user!
    (update-in
      (select-keys params [:account :password :nickname :email])
      [:password]
      hashers/encrypt {:algorithm :pbkdf2+sha256})))

(defn create-config!
  [request]
  (let [path "resources/public/templates/business"
        params (-> request :params)
        account (-> request :params :account)
        filePath (str path "/" account "/privilege/" account ".config")
        root_json (str path "/" account "/privilege/tree_root.json")]
    (.mkdir (io/file path account))
    (.mkdir (io/file path account "privilege"))
    (.mkdir (io/file path account "posts"))
    (.mkdir (io/file path account "images"))
    (.mkdir (io/file path account "videos"))
    ;(def filePath (str path "/" account "/" account ".config"))
    (io/copy (io/file "resources/public/images/avatar.jpg")
             (io/file path account "avatar.jpg"))
    (spit filePath (str ":account\t" (params :account) "\n"))
    (spit filePath (str ":email\t" (params :email) "\n") :append true)
    ;;tree root
    (spit root_json "[{\n")
    (spit root_json
          (str "\t\"id\": " (str "\"resources_public_templates_business_" account "\",\n")
               "\t\"text\": " "\"" account "\",\n"
               "\t\"children\": " "true,\n"
               "\t\"type\": " "\"root\",\n"
               "\t\"state\": {\"opened\": true}\n")
          :append true)
    (spit root_json "}]\n" :append true)
    ))

(defn user-signup
  [request]
  (if-let [errors (validate-user-signup request)]
    (-> (redirect "/")
        (assoc :flash (assoc (-> request :params) :errors (merge errors {:type "signup" :value "注册"}))))
    (do
      (create-config! request)
      (save-user! (-> request :params))
      (db/create_new! {:account (-> request :params :account), :obj "1", :type"add", :content "注册了账号", :create_time (l/local-now),
                       :photo nil, :video nil, :music nil, :post nil})
      (-> (redirect "/")
          (assoc :flash (select-keys (-> request :params) [:account]))
          (assoc :session {:account (:account (-> request :params))})))))

(defn smtp []
  {:host (env :mail-host)
   :user (env :mail-user)
   :pass (env :mail-pass)
   :ssl :yes!!!11})

(defn mail [request]
  {:from (str "Porto博客系统 <" (env :mail-user) ">")
   :to (-> request :params :to)
   :subject "来自Porto博客系统的验证邮件"
   :body (str "您好，感谢您注册Porto博客系统，\n您的验证码为：" (-> request :params :code) "，有效时间为10分钟。")})

(defn register-email [request]
  (p/send-message (smtp) (mail request)))