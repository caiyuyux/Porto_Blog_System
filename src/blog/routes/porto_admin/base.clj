(ns blog.routes.porto-admin.base
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [ring.util.response :refer [redirect response]]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]))


(defn update-tree-json
  [account]
  (let [children_json (str "resources/public/templates/business/" account "/tree_children.json")
        file-tree (file-seq (io/file "resources/public/templates/business" account))]
    (spit children_json "[\n")
    (doseq [f (sort file-tree)
            :when (not (= (.getName f) account))]
      (spit children_json
            (clojure.string/escape
              (str "\t{\n"
                   "\t\"id\": " (str "\""  (.getPath f) "\",\n")
                   "\t\"text\": " (str "\"" (.getName f) "\",\n")
                   "\t\"parent\": " (str "\"" (.getParent f) "\"\n")
                   "\t},\n")
              {\\ "_"})
            :append true)
      )
    (spit children_json
          (str "\t{\"id\": \"privilege\", \"text\": \"privilege\", \"parent\": \""
               "resources_public_templates_business_caiyuyu"
               "\",\"state\":{\"disabled\": true}}\n") :append true)
    (spit children_json "]\n" :append true)
    ))

(defn admin_index_page
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/index.html"
      (let [user {:account account}]
        (do
          (update-tree-json account))
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [news (not-empty (db/get_news user))] {:news news})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))))
