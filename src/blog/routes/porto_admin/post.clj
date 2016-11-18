(ns blog.routes.porto-admin.post
  (:require [blog.layout :as layout]
            [blog.db.core :as db]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [clj-time.local :as l]
            [clj-time.core :as t]
            [crypto.random :refer [url-part]]
            [cheshire.core :refer :all]
            [ring.util.response :refer [redirect response]]
            [clojure.java.io :as io]
            [cheshire.core :as json]))


(defn add_new
  [request]
  (do
    (db/add_categories_count!
      (merge (-> request :session)
             (-> request :params)))
    (let [str (clojure.string/split (-> request :params :tags) #"\,")]
      (println
        (map
          (fn [x]
            (if (:exists (first (db/exists_tag? (merge (-> request :session) {:id x}))))
              (db/add_tags_count!
                (merge
                  (-> request :session)
                  {:id x}))
              (db/insert_tags!
                (merge
                  (-> request :session)
                  {:id x, :count 1}))
              ))
          str))
      )
    )
  )

(defn remove_old
  [request]
  (do
    (db/remove_categories_count!
      (merge (-> request :session)
             (-> request :params)))
    (let [str (clojure.string/split (-> request :params :old_tag) #"\,")]
      (println
        (map
          (fn [x]
              (db/remove_tags_count!
                (merge
                  (-> request :session)
                  {:tags x}))
              )
          str))
      )
    ))

(defn new_categories!
  [request]
  (db/insert_categories!
    (merge (-> request :params)
           (-> request :session)
           {:count 0}))
  (generate-string(when-let [categories (not-empty (db/get_categories (-> request :session)))] {:categories_all categories})))

(defn delete_categories!
  [request]
  (db/delete_categories!
    (merge (-> request :session)
           (-> request :params)))
  (generate-string(when-let [categories (not-empty (db/get_categories (-> request :session)))] {:categories_all categories})))

(defn edit_post_page!
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/edit-post.html"
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [categories (not-empty (db/get_categories user))] {:categories_all categories})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )

(defn update_post_page!
  [request]
  (println (db/get_single_posts (merge (-> request :params)
                                       (-> request :session))))
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/edit-post.html"
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [categories (not-empty (db/get_categories user))] {:categories_all categories})
               (when-let [post (not-empty (db/get_single_posts (merge (-> request :params)
                                                                      (-> request :session))))] (first post))
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    ))

(defn save-post!
  [request]
  (let [account (-> request :session :account)
        postPath (str "/templates/business/" account "/posts/" (-> request :params :title) ".html")
        filePath (str "/templates/business/" account )]
    (db/insert_post!
      (merge (-> request :params)
             {:postid postPath, :account account, :time (l/local-now) :type "document"}
             (if (= "local" (-> request :params :fileStyle))
               (if-not (empty? (-> request :params :image))
                   (do
                     (io/copy (io/file (-> request :params :file :tempfile))
                              (io/file
                                (str "resources/public/" filePath "/images/" (-> request :params :fileName))))
                     (db/insert_image! {:imageid (str filePath "/images/" (-> request :params :fileName)),
                                        :filename (-> request :params :fileName),
                                        :account account,
                                        :time (l/local-now),
                                        :type "image"})
                     {:image (str filePath "/images/" (-> request :params :fileName))})))
             (if (= "local" (-> request :params :fileStyle))
               (if-not (empty? (-> request :params :video))
                 (do
                   (io/copy (io/file (-> request :params :file :tempfile))
                            (io/file
                              (str "resources/public/" filePath "/videos/" (-> request :params :fileName))))
                   (db/insert_video! {:videoid (str filePath "/videos/" (-> request :params :fileName)),
                                      :filename (-> request :params :fileName),
                                      :account account,
                                      :time (l/local-now)
                                      :type "video"})
                   {:video (str filePath "/videos/" (-> request :params :fileName))})))
             ))
    (spit (str "resources/public" postPath) (-> request :params :html))
    (add_new request)
    "success"))

(defn modify-post!
  [request]
  (let [account (-> request :session :account)
        postPath (str "/templates/business/" account "/posts/" (-> request :params :title) ".html")
        filePath (str "/templates/business/" account )]
    (db/update_post!
      (merge (-> request :params)
             {:postid postPath, :account account, :time (l/local-now) :type "document"}
             (if (= "local" (-> request :params :fileStyle))
               (if-not (empty? (-> request :params :image))
                 (do
                   (io/copy (io/file (-> request :params :file :tempfile))
                            (io/file
                              (str "resources/public/" filePath "/images/" (-> request :params :fileName))))
                   (db/insert_image! {:imageid (str filePath "/images/" (-> request :params :fileName)),
                                      :filename (-> request :params :fileName),
                                      :account account,
                                      :time (l/local-now),
                                      :type "image"})
                   {:image (str filePath "/images/" (-> request :params :fileName))})))
             (if (= "local" (-> request :params :fileStyle))
               (if-not (empty? (-> request :params :video))
                 (do
                   (io/copy (io/file (-> request :params :file :tempfile))
                            (io/file
                              (str "resources/public/" filePath "/videos/" (-> request :params :fileName))))
                   (db/insert_video! {:videoid (str filePath "/videos/" (-> request :params :fileName)),
                                      :filename (-> request :params :fileName),
                                      :account account,
                                      :time (l/local-now)
                                      :type "video"})
                   {:video (str filePath "/videos/" (-> request :params :fileName))})))
             ))
    (spit (str "resources/public" postPath) (-> request :params :html))
    (remove_old request)
    (add_new request)

    "success"))

(defn consult_post_page
  [request]
  (if-let [account (-> request :session :account)]
    (layout/render
      "porto_admin/consult-post.html"
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [post (not-empty (db/get_posts user))] {:post post})
               (when-let [categories (not-empty (db/get_categories user))] {:categories_all categories})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))
    (-> (redirect "/")
        (assoc :flash {:errors {:type "signin" :value "登录" :warning "Please login first"}}))
    )
  )

(defn deleted_post!
  [request]
  (let [type (get (clojure.string/split (-> request :params :id) #"/") 4)]
    (if (= type "images")
      (db/delete-image-by-id (-> request :params)))
    (if (= type "videos")
      (db/delete-video-by-id (-> request :params)))
    (if (= type "posts")
      (let [post (first (db/get_single_posts
                          (merge (-> request :params)
                                 (-> request :session))))
            str (clojure.string/split (-> post :tags) #"\,")]
        (db/delete-post-by-id (-> request :params))
        (db/remove_categories_count!
          (merge (-> request :session)
                 {:old_cate (-> post :categories)}))
        (println (map (fn [x] (db/remove_tags_count! (merge
                  (-> request :session) {:tags x}))) str))
        ))
    "return"))
