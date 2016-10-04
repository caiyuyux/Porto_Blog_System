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
SELECT nickname, name, status, email, describe, domain, avatar, disqus, privilege, mind, leancloud
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
    disqus = :profileDisqus, leancloud = :profileLeancloud,  status = :profileStatus
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

-- :name insert_video! :! :n
-- :doc
INSERT INTO videos (id, filename, account, time, type)
VALUES (:videoid, :filename, :account, :time, :type);

-- :name insert_post! :! :n
-- :doc
INSERT INTO posts (id, title, account, time, type)
VALUES (:postid, :filename, :account, :time, :type);

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
---------------------porto_admin end-------------------------