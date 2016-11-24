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
  (let [account (get-in request [:params :account])
        info (first (db/get_user_info {:account account}))]
    (layout/render
      (str "business/" (info :account) "/blog/" (info :theme) "/index.html")
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (when-let [posts (not-empty (db/get_posts user))]
                 {:posts_all (reduce
                               (fn [m v]
                                 (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))) () posts)})
               (when-let [categories (not-empty (db/get_categories_new user))] {:categories_all categories})
               (when-let [tags (not-empty (db/get_tags user))] {:tags_all tags})
               (when-let [errors (get-in request [:flash :errors])] {:errors errors}))
        ))))

(defn error_page
  [request]
  (layout/error-page {:status 404
                      :title "系统找不到指定文件或路径"
                      :message "你是不是傻？再检查下"}))


(defn post_page
  [account page value]
    (let [info (first (db/get_user_info {:account account}))]
        (layout/render
          (str "business/" account "/blog/" (info :theme) "/" page)
          (let [user {:account account}]
            (merge user
                   (when-let [info (not-empty (db/get_user_info user))] (first info))
                   (if value
                     (merge
                       (when-let [posts (not-empty
                                          (db/get_single_posts
                                            {:id (str "/templates/business/" account "/posts/" value ".html"), :account account}))]
                         {:post_single  (merge (first posts)
                                        {:tags (clojure.string/split (get-in (first posts) [:tags]) #",")}) })
                       {:post_select value}))
                   (when-let [posts (not-empty (db/get_posts user))]
                     {:posts_all (reduce
                                   (fn [m v]
                                     (conj m (merge v
                                                    {:tags (clojure.string/split (:tags v) #",")}
                                                    {:prev_title (:title (let [index (- (.indexOf posts v) 1)]
                                                                           (if (>= index 0) (nth posts index))))}
                                                    {:next_title (:title (let [index (+ (.indexOf posts v) 1)]
                                                                           (if (< index (count posts)) (nth posts index))))}
                                                    ))) () posts)})
                   (when-let [categories (not-empty (db/get_categories_new user))] {:categories_all categories})
                   (when-let [tags (not-empty (db/get_tags user))] {:tags_all tags}))))))

(defn category_page
  [account page value]
  (let [info (first (db/get_user_info {:account account}))]
    (layout/render
      (str "business/" account "/blog/" (info :theme) "/" page)
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (if value
                 (merge
                   (when-let [categories (not-empty
                                           (db/get_category_posts
                                             {:category value, :account account}))]
                     {:category_single (reduce
                                         (fn [m v]
                                           (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))) () categories)})
                   {:category_select value}))
               (when-let [posts (not-empty (db/get_posts user))]
                 {:posts_all (reduce
                               (fn [m v]
                                 (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))) () posts)})
               (when-let [categories (not-empty (db/get_categories_new user))] {:categories_all categories})
               (when-let [tags (not-empty (db/get_tags user))] {:tags_all tags}))))))

(defn tag_page
  [account page value]
  (let [info (first (db/get_user_info {:account account}))]
    (layout/render
      (str "business/" account "/blog/" (info :theme) "/" page)
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (if value
                 (merge
                   (when-let [tags (not-empty (db/get_posts user))]
                     {:tag_single (reduce
                                    (fn [m v]
                                      (if (some #(= % value) (clojure.string/split (get v :tags) #","))
                                        (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))
                                        m))
                                    () tags)})
                   {:tag_select value}))
               (when-let [posts (not-empty (db/get_posts user))]
                 {:posts_all (reduce
                               (fn [m v]
                                 (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))) () posts)})
               (when-let [categories (not-empty (db/get_categories_new user))] {:categories_all categories})
               (when-let [tags (not-empty (db/get_tags user))] {:tags_all tags}))))))

(defn search_page
  [account page value]
  (let [info (first (db/get_user_info {:account account}))]
    (layout/render
      (str "business/" account "/blog/" (info :theme) "/" page)
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (if value
                 (merge
                   (when-let [posts (not-empty
                                      (db/get_search_posts {:upper_value (str "%" (clojure.string/upper-case value) "%"), :account account}))]
                     {:search_single (reduce
                                       (fn [m v]
                                         (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))) () posts)})
                   {:search_select value}))
               (when-let [posts (not-empty (db/get_posts user))]
                 {:posts_all (reduce
                               (fn [m v]
                                 (conj m (merge v
                                                {:tags (clojure.string/split (:tags v) #",")}
                                                {:prev_title (:title (let [index (- (.indexOf posts v) 1)]
                                                                       (if (>= index 0) (nth posts index))))}
                                                {:next_title (:title (let [index (+ (.indexOf posts v) 1)]
                                                                       (if (< index (count posts)) (nth posts index))))}
                                                ))) () posts)})
               (when-let [categories (not-empty (db/get_categories_new user))] {:categories_all categories})
               (when-let [tags (not-empty (db/get_tags user))] {:tags_all tags}))))))

(defn other_page
  [account page type value]
  (let [info (first (db/get_user_info {:account account}))]
    (layout/render
      (str "business/" account "/blog/" (info :theme) "/" page)
      (let [user {:account account}]
        (merge user
               (when-let [info (not-empty (db/get_user_info user))] (first info))
               (cond
                 (= type "post")
                 (if value
                   (merge
                     (when-let [posts (not-empty
                                        (db/get_single_posts
                                          {:id (str "/templates/business/" account "/posts/" value ".html"), :account account}))]
                       {:post_single (merge (first posts)
                                            {:tags (clojure.string/split (get-in (first posts) [:tags]) #",")})})
                     {:post_select value}))
                 (= type "category")
                 (if value
                   (merge
                     (when-let [categories (not-empty
                                             (db/get_category_posts
                                               {:category value, :account account}))]
                       {:category_single (reduce
                                           (fn [m v]
                                             (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))) () categories)})
                     {:category_select value}))
                 (= type "tag")
                 (if value
                   (merge
                     (when-let [tags (not-empty (db/get_posts user))]
                       {:tag_single (reduce
                                      (fn [m v]
                                        (if (some #(= % value) (clojure.string/split (get v :tags) #","))
                                          (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))
                                          m))
                                      () tags)})
                     {:tag_select value}))

                 (= type "search")
                 (if value
                   (merge
                     (when-let [posts (not-empty
                                        (db/get_search_posts {:upper_value (str "%" (clojure.string/upper-case value) "%"), :account account}))]
                       {:search_single (reduce
                                         (fn [m v]
                                           (conj m (merge v (assoc v :tags (clojure.string/split (:tags v) #","))))) () posts)})
                     {:search_select value})))

               (when-let [posts (not-empty (db/get_posts user))]
                 {:posts_all (reduce
                               (fn [m v]
                                 (conj m (merge v
                                                {:tags (clojure.string/split (:tags v) #",")}
                                                {:prev_title (:title (let [index (- (.indexOf posts v) 1)]
                                                                       (if (>= index 0) (nth posts index))))}
                                                {:next_title (:title (let [index (+ (.indexOf posts v) 1)]
                                                                       (if (< index (count posts)) (nth posts index))))}
                                                ))) () posts)})
               (when-let [categories (not-empty (db/get_categories_new user))] {:categories_all categories})
               (when-let [tags (not-empty (db/get_tags user))] {:tags_all tags}))))))


(defn choose_page
  [request]
  (let [account (get-in request [:params :account])
        params (clojure.string/split (-> request :params :*) #"/" 4)
        first_param (first params)
        middle_param (get params 1)
        last_param (get params 2)]

    (cond
      (= "post" first_param)
      (if
        (if middle_param
             (:exists
               (first (db/exists_post? {:id middle_param, :account account}))) true)
        (post_page account "post.html" middle_param)
        (-> (redirect (str "/blog/" account "/post"))))
      (= "category" first_param)
      (if
        (if middle_param
          (:exists
            (first (db/exists_category? {:id middle_param, :account account}))) true)
        (category_page account "category.html" middle_param)
        (-> (redirect (str "/blog/" account "/category"))))

      (= "tag" first_param)
      (if
        (if middle_param
          (:exists
            (first (db/exists_tag? {:id middle_param, :account account}))) true)
        (tag_page account "tag.html" middle_param)
        (-> (redirect (str "/blog/" account "/tag"))))

      (= "search" first_param)
      (if-let [search_value (get (-> request :params) :search)]
        (search_page account "search.html" search_value)
        (-> (redirect (str "/blog/" account))))

      :else (if middle_param
              (cond
                (= "post" middle_param)
                (if
                  (if last_param
                    (:exists
                      (first (db/exists_post? {:id last_param, :account account}))) true)
                  (other_page account (str first_param ".html") middle_param last_param)
                  (-> (redirect (str "/blog/" account "/" first_param))))
                (= "category" middle_param)
                (if
                  (if last_param
                    (:exists
                      (first (db/exists_category? {:id last_param, :account account}))) true)
                  (other_page account (str first_param ".html") middle_param last_param)
                  (-> (redirect (str "/blog/" account "/" first_param))))
                (= "tag" middle_param)
                (if
                  (if last_param
                    (:exists
                      (first (db/exists_category? {:id last_param, :account account}))) true)
                  (other_page account (str first_param ".html") middle_param last_param)
                  (-> (redirect (str "/blog/" account "/" first_param))))

                :else (-> (redirect (str "/blog/" account "/" first_param))))
              (if-let [search_value (get (-> request :params) :search)]
                (other_page account (str first_param ".html") "search" search_value)
                (other_page account (str first_param ".html") nil nil))
              ))))
