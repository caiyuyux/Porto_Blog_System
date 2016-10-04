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
  (let [children_json (str "resources/public/templates/business/" account "/privilege/tree_children.json")
        file-tree (file-seq (io/file "resources/public/templates/business" account))
        filter_dir (str "resources\\public\\templates\\business\\" account "\\privilege")]
    (spit children_json "[\n")
    (doseq [f (sort file-tree)
            :when (not (= (.getName f) account))
            :when (not (= (.getParent f) filter_dir))
            :when (not (= (.getPath f) filter_dir))]
      (spit children_json
            (clojure.string/escape
              (str "\t{\n"
                   "\t\"id\": " (str "\""  (.getPath f) "\",\n")
                   "\t\"text\": " (str "\"" (.getName f) "\",\n")
                   "\t\"parent\": " (str "\"" (.getParent f) "\"\n")
                   "\t},\n")
              {\\ "_"})
            :append true)

      (let [aim (str "resources_public_templates_business_" account)
            filename (.getName f)
            parent (clojure.string/escape (.getParent f) {\\ "_"})
            id (last (clojure.string/split (clojure.string/escape (.getPath f) {\\ "/"}) #"/" 3))]
        (if (=  (str aim  "_images") parent)
          (if-not (:exists (first (db/exists_image_file? {:imageid id})))
            (db/insert_image! {:imageid id, :filename filename, :account account, :time (l/local-now), :type "image"})))
        (if (=  (str aim "_videos") parent)
          (if-not (:exists (first (db/exists_video_file? {:videoid id})))
            (db/insert_video! {:videoid id, :filename filename, :account account, :time (l/local-now) :type "video"})))
        (if (=  (str aim "_posts") parent)
          (if-not (:exists (first (db/exists_post_file? {:postid id})))
            (db/insert_post! {:postid id, :filename filename, :account account, :time (l/local-now) :type "document"}))))
      )

    (spit children_json
          (str "\t{\"id\": \"resources_public_templates_business_" account "_privilege\",\"text\": \"privilege\", \"parent\": \""
               "resources_public_templates_business_" account
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
               (when-let [file (not-empty (db/get_file_info user))] {:file file})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )

;;文件名重复问题
(defn file-upload!
  [request]
  (let [file (-> request :params :uploadfile)
        f (first file)
        fileName (-> f :filename)
        imagePath (str "resources/public/templates/business/" (-> request :session :account) "/images/")
        videoPath (str "resources/public/templates/business/" (-> request :session :account) "/videos/")
        postPah (str "resources/public/templates/business/" (-> request :session :account) "/posts/")]

    (println (-> f :content-type))
    (if (.contains ["image/jpeg", "image/gif", "image/png"]
                   (-> f :content-type))
      (do
        (println (str imagePath fileName))
        (io/copy (-> f :tempfile) (io/file (str imagePath fileName)))
        '("image success")))

    (if (.contains ["video/avi", "application/vnd.rn-realmedia-vbr", "video/mp4", "video/x-flv"]
          (-> f :content-type))
      (do
        (io/copy (-> f :tempfile) (io/file (str videoPath fileName)))
            '("video success")))

    (if (.contains ["text/plain", "application/msword", "text/html", "application/octet-stream"]
          (-> f :content-type))
      (do
        (io/copy (-> f :tempfile) (io/file (str postPah fileName)))
            '("post success")))
    '("success")
    ))