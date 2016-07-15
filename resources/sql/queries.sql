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