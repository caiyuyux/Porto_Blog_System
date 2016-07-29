(ns blog.routes.porto-admin.resources
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [clojure.java.io :as io]
            [ring.util.response :refer [redirect response]]))

(defn update-tree-json
  [account]
  (let [children_json (str "resources/public/templates/business/privilege" account "/tree_children.json")
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

(defn resources_index_page
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/resources.html"
      (let [user {:account account}]
        (update-tree-json account)
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               ;(when-let [news (not-empty (db/get_news user))] {:news news})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )