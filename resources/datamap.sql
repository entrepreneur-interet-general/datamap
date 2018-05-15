-- :name create-datasets-table :! :raw
-- :doc Create the "datasets" table.
CREATE TABLE datasets (
 ID serial NOT NULL PRIMARY KEY,
 info json NOT NULL
);

-- :name drop-datasets-table :! :raw
-- :doc Drop the "datasets" table.
DROP TABLE datasets;

-- :name insert-dataset :! :raw
-- :doc Insert info as a new dataset.
INSERT INTO datasets (info)
VALUES (:info)

-- :name get-all-datasets :? :raw
-- :doc Return all datasets.
SELECT * FROM datasets;
