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

(defn resources_index_page
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/resources.html"
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [file (not-empty (db/get_file_info user))] {:file file})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )

;;文件名重复文件会覆盖的问题未解决
(defn file-upload!
  [request]
  (let [file (-> request :params :uploadfile)
        f (first file)
        fileName (-> f :filename)
        imagePath (str "resources/public/templates/business/" (-> request :session :account) "/images/")
        videoPath (str "resources/public/templates/business/" (-> request :session :account) "/videos/")]

    (if (.contains ["image/jpeg", "image/gif", "image/png"]
                   (-> f :content-type))
      (do
        (io/copy (-> f :tempfile) (io/file (str imagePath fileName)))
        (db/insert_image! {:imageid (str "/templates/business/" (-> request :session :account) "/images/" fileName), :filename fileName, :account (-> request :session :account), :time (l/local-now), :type "image"})))

    (if (.contains ["video/avi", "application/vnd.rn-realmedia-vbr", "video/mp4", "video/x-flv"]
          (-> f :content-type))
      (do
        (io/copy (-> f :tempfile) (io/file (str videoPath fileName)))
        (db/insert_video! {:videoid (str "/templates/business/" (-> request :session :account) "/videos/" fileName), :filename fileName, :account (-> request :session :account), :time (l/local-now) :type "video"})))

    '("success")
    ))