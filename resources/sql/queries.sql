-----------------------porto start--------------------------

-- :name exists_user? :? :*
-- :doc returns a boolean stating if the user already exists
SELECT EXISTS (
    SELECT account
    FROM users
    WHERE account = :account);

-- :name exists_email? :? :*
-- :doc returns a boolean stating if the email already exists
SELECT EXISTS (
    SELECT email
    FROM users
    WHERE email = :email);

-- :name create_user! :! :n
-- :doc creates a new user record
INSERT INTO users
(account, password, nickname, email)
VALUES (:account, :password, :account, :email);

-- :name get_password :? :*
-- :doc retrieve a user password given the account
SELECT password
FROM users
WHERE account = :account;

-- :name create_retrieve_token! :! :n
-- :doc did not manage to create a nice ON CONFLICT condition - to be done
INSERT INTO retrieve_account_tokens (account, token, created_at)
VALUES (:account, :token, :created_at);

-- :name user_has_retrieve_token? :? :*
-- :doc returns a token if it already exists
SELECT EXISTS (
    SELECT account
    FROM retrieve_account_tokens
    WHERE account = :account);

-- :name update_retrieve_token! :! :n
-- :doc
UPDATE retrieve_account_tokens
SET token = :token, created_at = :created_at
WHERE account = :account;

-- :name token_details_for_retrieve_account :? :*
-- :doc
SELECT account, created_at
FROM retrieve_account_tokens
WHERE token = :value;

------------------------porto end----------------------------

--------------------porto_admin start------------------------
-- :name get_user_info :? :*
-- :doc return all info of account
SELECT *
FROM users
WHERE account = :account;

-- :name update_user_mind! :! :n
-- :doc
UPDATE users
SET mind = :profileMind
WHERE account = :account;

-- :name update_user_profile! :! :n
-- :doc
UPDATE users
SET nickname = :profileNickname, describe = :profileDescribe, domain = :profileDomain, name = :profileName,
    disqus = :profileDisqus, leancloud = :profileLeancloud,  status = :profileStatus, disqus_access_token = :profileDisqus_ACCESS_TOKEN, disqus_apikey = :profileDisqus_APIKEY,
    blog_subject = :blogSubject, blog_describe = :blogDescribe
WHERE account = :account;

-- :name create_new! :! :n
-- :doc
INSERT INTO news (obj, create_time, account, image, video, music, type, post, content)
VALUES (:obj, :create_time, :account, :image, :video, :music, :type, :post, :content);

-- :name get_news :? :*
-- :doc
SELECT obj, create_time, image, video, music, type, post, content
FROM news
WHERE account = :account;

-- :name exists_image_file? :? :*
-- :doc
SELECT EXISTS (
    SELECT id
    FROM images
    WHERE id = :imageid);

-- :name exists_video_file? :? :*
-- :doc
SELECT EXISTS (
    SELECT id
    FROM videos
    WHERE id = :videoid);

-- :name exists_post_file? :? :*
-- :doc
SELECT EXISTS (
    SELECT id
    FROM posts
    WHERE id = :postid);

-- :name insert_image! :! :n
-- :doc
INSERT INTO images (id, filename, account, time, type)
VALUES (:imageid, :filename, :account, :time, :type);

-- :name delete-image-by-id :! :n
delete from images where id = :id;

-- :name insert_video! :! :n
-- :doc
INSERT INTO videos (id, filename, account, time, type)
VALUES (:videoid, :filename, :account, :time, :type);

-- :name delete-video-by-id :! :n
delete from videos where id = :id;

-- :name insert_post! :! :n
-- :doc
INSERT INTO  posts (id, title, tags, categories, md, html, account, time, type, image, video)
VALUES (:postid, :title, :tags, :categories, :md, :html, :account, :time, :type, :image, :video);

-- :name update_post! :! :n
-- :doc
UPDATE  posts SET id=:postid, title=:title, tags=:tags, categories=:categories, md=:md, html=:html, update_time=:time, type=:type, image=:image, video=:video
WHERE id=:id;

-- :name delete-post-by-id :! :n
delete from posts where id = :id;

-- :name get_file_info :? :*
-- :doc
SELECT id, type, time, filename
FROM images
WHERE account = :account
UNION
SELECT id, type, time, filename
FROM videos
WHERE account = :account
UNION
SELECT id, type, time, title
FROM posts
WHERE account = :account;

-- :name insert_categories! :! :n
-- :doc
INSERT INTO categories (id, count, account)
VALUES (:name, :count, :account);

-- :name get_categories :? :*
-- :doc
SELECT id, count
FROM categories
WHERE account = :account;

-- :name get_categories_new :? :*
-- :doc
SELECT id, count
FROM categories
WHERE account = :account  AND count>0;

-- :name get_tags :? :*
-- :doc
SELECT id, count
FROM tags
WHERE account = :account AND count>0;

-- :name get_posts :? :*
-- :doc
SELECT id, title, tags, md, html, image, video, music, update_time, type, time, categories
FROM posts
WHERE account = :account;

-- :name get_single_posts :? :*
-- :doc
SELECT id, account, title, tags, md, html, image, video, music, update_time, type, time, categories
FROM posts
WHERE id = :id AND  account = :account;

-- :name get_category_posts :? :*
-- :doc
SELECT id, account, title, tags, md, html, image, video, music, update_time, type, time, categories
FROM posts
WHERE categories = :category AND account = :account;

-- :name add_categories_count! :! :n
-- :doc
UPDATE categories SET count=count+1
WHERE account=:account AND id=:categories;

-- :name remove_categories_count! :! :n
-- :doc
UPDATE categories SET count=count-1
WHERE account=:account AND id=:old_cate;

-- :name delete_categories! :! :n
-- :doc
DELETE FROM categories
WHERE id=:id AND account=:account;

-- :name insert_tags! :! :n
-- :doc
INSERT INTO tags (id, count, account)
VALUES (:id, :count, :account);

-- :name add_tags_count! :! :n
-- :doc
UPDATE tags SET count=count+1
WHERE account=:account AND id=:id;

-- :name remove_tags_count! :! :n
-- :doc
UPDATE tags SET count=count-1
WHERE account=:account AND id=:tags;

-- :name exists_tag? :? :*
-- :doc
SELECT EXISTS (
    SELECT id
    FROM tags
    WHERE account = :account AND id = :id);

-- :name exists_category? :? :*
-- :doc
SELECT EXISTS (
    SELECT id
    FROM categories
    WHERE account = :account AND id = :id AND count>0);

-- :name exists_post? :? :*
-- :doc
SELECT EXISTS (
    SELECT id
    FROM posts
    WHERE account = :account AND title = :id);

---------------------porto_admin end-------------------------