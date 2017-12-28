(ns blog.routes.porto-admin.blogStyle
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [clj-time.coerce :as c]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [clojure.java.io :as io]
            [clj-jgit.porcelain :as jgit]
            [fs.core :as fs]
            [fs.compression :as compression]
            [ring.util.response :refer [redirect response]])
  (:import (java.util.zip ZipInputStream)))

;(defn repo-init
;  [request]
;  (jgit/git-init "resources/public/templates/business/caiyuyu/blog"))
;
;(def my-repo
;  (jgit/load-repo "resources/public/templates/business/caiyuyu/blog"))

(defn blogStyle_index_page
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/blogStyle.html"
      (let [user {:account account}]
        ;(fs/copy-dir "resources/public/templates/blog_templates/libra" "resources/public/templates/business/caiyuyu/blog/libra")
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [theme (not-empty (db/get_theme user))] {:theme_all theme})
               (when-let [file (not-empty (db/get_file_info user))] {:file file})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )

(defn theme-upload!
  [request]
  (let [file (-> request :params :uploadfile)
        f (first file)
        fileName (-> f :filename)
        Path (str "resources/public/templates/business/" (-> request :session :account) "/blog/")
        name (first (clojure.string/split fileName #"\."))]
    (do
      (if
        ((first (db/exists_theme? {:id name, :account (-> request :session :account)})) :exists)
        (let [newName (str name "_" (c/to-long (l/local-now)))]
          (do
            (io/copy (-> f :tempfile) (io/file (str Path newName ".zip")))
            (db/insert_themes! {:id newName, :account (-> request :session :account), :path (str "business/" (-> request :session :account) "/blog/" newName)})
            (compression/unzip (str Path newName ".zip") Path)))
        (do
          (io/copy (-> f :tempfile) (io/file (str Path fileName)))
          (db/insert_themes! {:id name, :account (-> request :session :account), :path (str "business/" (-> request :session :account) "/blog/" name)})
          (compression/unzip (str Path fileName) Path))
        ))
    '("success")))


(defn update-theme!
  [request]
  (let [id (-> request :params :theme)]
      (db/update_theme! {:account (-> request :session :account), :theme id})
    '("success")))

(defn theme-delete!
  [request]
  (do
    (db/delete_theme! {:account (-> request :session :account), :id (-> request :params :theme)})
    '("success")))
