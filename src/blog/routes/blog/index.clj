(ns blog.routes.blog.index
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [clojure.java.io :as io]
            [clj-jgit.porcelain :as jgit]
            [fs.core :as fs]
            [ring.util.response :refer [redirect response]]))

(defn index_page
  [request]
  (let [account (get-in request [:params :account])]
    (println "aaaaaaaaaaaaaaaaa")
    (layout/render
      (str "business/" account "/blog/index.html")
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [file (not-empty (db/get_file_info user))] {:file file})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))))
