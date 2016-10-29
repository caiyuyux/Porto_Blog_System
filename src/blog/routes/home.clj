(ns blog.routes.home
  (:require [blog.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [blog.db.core :as db]
            [ring.util.response :refer [redirect response]]
            [buddy.hashers :as hashers]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [crypto.random :refer [url-part]]
            [clj-time.core :as t]
            [clj-time.coerce :as c]
            [clj-time.local :as l]
            [clj-time.jdbc]
            [environ.core :refer [env]]
            [blog.routes.porto.signin :as signin]
            [blog.routes.porto.signup :as signup]
            [blog.routes.porto.recover :as recover]
            [blog.routes.porto-admin.base :as admin]
            [blog.routes.porto-admin.profile :as profile]
            [blog.routes.porto-admin.post :as post]
            [blog.routes.porto-admin.resources :as resources]
            [blog.routes.porto-admin.comment :as comment]
            [blog.routes.porto-admin.blogStyle :as blogStyle]
            [blog.routes.blog.index :as blog]
            [ring.util.response :as response]))

;this is a handler, it takes a request and gives a response
;the response will render a template with an associated context-map

(defn home-page
  [request]
  (layout/render
    "porto/index.html"
    (if-let [account {:account (-> request :session :account)}]
      (merge account
             (when-let [errors (get-in request [:flash :errors])] {:errors errors})))))

(defn download
  [request]
  ;(-> (clojure.java.io/input-stream "http://localhost/templates/business/caiyuyu/images/125033ikkyzvfhfszgqqqo.jpg")
  ;    response
  ;    (response/content-type "application/octet-stream"))
  {
   :filename "demo.jpg"
   :content-type "application/octet-stream"
   :url "http://localhost/templates/business/caiyuyu/images/125033ikkyzvfhfszgqqqo.jpg"
   }
  )

(defroutes home-routes
           (GET "/"                request (home-page               request))
           (GET "/download"        request (download                request))
           ;(POST "/"                request (home-page               request))

           ;;--------------------------porto--------------------------------

           ;; index
           ;(GET "/:account/index"  request (index/index-page        request))
           ;(POST "/:account/index" request (index/index             request))

           ;; signup and signin
           (GET  "/user-signup"     request (signup/user-signup-page request))
           (POST "/user-signup"     request (signup/user-signup      request))
           (GET  "/user-signin"     request (signin/user-signin-page request))
           (POST "/user-signin"     request (signin/user-signin      request))
           (GET  "/user-logout"     request (signin/user-logout      request))
           (GET  "/cookies-pass"    request (signin/cookies-pass     request))
           (GET  "/valid-token"     request (signin/valid-token?     request))
           (GET  "/register-email"  request (signup/register-email   request))
           (GET  "/recover-email"   request (recover/recover-email   request))

           (GET  "/features"        request (layout/render "porto/features.html"   (-> request :flash) (:flash request)))
           (GET  "/faqs"            request (layout/render "porto/faqs.html"       (-> request :flash) (:flash request)))
           (GET  "/contact-us"      request (layout/render "porto/contact-us.html" (-> request :flash) (:flash request)))
           (GET  "/about-us"        request (layout/render "porto/about-us.html"   (-> request :flash) (:flash request)))

           ;; -----------------------porto_admin-----------------------------

           (GET  "/admin"                 request (admin/admin_index_page          request))
           (POST "/admin"                 request (admin/admin_index_page          request))
           (POST "/admin_update-profile"  request (profile/update-profile          request))
           (POST "/admin_update-mind"     request (profile/update-mind             request))

           (GET  "/admin_edit-post"       request (post/edit_post_page!            request))
           (GET  "/admin_consult-post"    request (post/consult_post_page          request))
           (GET  "/admin_resources"       request (resources/resources_index_page  request))
           (GET  "/admin_comments"        request (comment/comments_index_page     request))
           (GET  "/admin_blogStyle"       request (blogStyle/blogStyle_index_page  request))
           (POST "/admin_file-upload"     request (resources/file-upload!          request))
           (POST "/admin_deletedPost"     request (post/deleted_post!              request))

           ;;------------------------------ blog -----------------------------------------
           (GET  "/blog/:account" request (blog/index_page request))
           (POST "/blog/:account" request (blog/index_page request))
           ; (GET "/:account/panel"  request (panel/admin-panel-page  request))
           ; (POST "/:account/panel" request (panel/admin-panel       request))

           (GET "/test" request (l/local-now)))