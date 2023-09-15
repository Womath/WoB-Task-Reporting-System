DROP VIEW IF EXISTS report, monthly_data_view, monthly_email_view, monthly_report;
DROP TABLE IF EXISTS locations, listing_statuses, marketplaces, listings;

CREATE TABLE locations (
	id UUID PRIMARY KEY NOT NULL,
	manager_name TEXT,
	phone TEXT,
	address_primary TEXT,
	address_secondary TEXT,
	country TEXT,
	town TEXT,
	postal_code TEXT
);

CREATE TABLE listing_statuses (
	id INTEGER PRIMARY KEY NOT NULL,
	status_name TEXT
);

CREATE TABLE marketplaces (
	id INTEGER PRIMARY KEY NOT NULL,
	marketplace_name TEXT
);

CREATE TABLE listings (
	id UUID PRIMARY KEY NOT NULL,
	title TEXT NOT NULL,
	description TEXT NOT NULL,
	location_id UUID NOT NULL REFERENCES locations(id),
	listing_price NUMERIC(10, 2) NOT NULL CHECK (listing_price > 0),
	currency CHAR(3) NOT NULL,
	quantity NUMERIC(10, 0) NOT NULL CHECK (quantity > 0),
	listing_status INTEGER NOT NULL REFERENCES listing_statuses(id),
	marketplace INTEGER NOT NULL REFERENCES marketplaces(id), 
	upload_time DATE,
	owner_email_address TEXT NOT NULL CHECK (owner_email_address ~*'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

CREATE VIEW report AS
SELECT
	ROW_NUMBER() OVER () AS id,
	COUNT(*) AS total_listing_count,
	SUM(CASE WHEN marketplaces.marketplace_name = 'EBAY' THEN 1 ELSE 0 END) AS ebay_listing_count,
	SUM(CASE WHEN marketplaces.marketplace_name = 'EBAY' THEN listing_price ELSE 0 END) AS ebay_total_listing_price,
	ROUND(AVG(CASE WHEN marketplaces.marketplace_name = 'EBAY' THEN listing_price ELSE NULL END), 2) AS ebay_avg_listing_price,
	SUM(CASE WHEN marketplaces.marketplace_name = 'AMAZON' THEN 1 ELSE 0 END) AS amazon_listing_count,
	SUM(CASE WHEN marketplaces.marketplace_name = 'AMAZON' THEN listing_price ELSE 0 END) AS amazon_total_listing_price,
	ROUND(AVG(CASE WHEN marketplaces.marketplace_name = 'AMAZON' THEN listing_price ELSE NULL END), 2) AS amazon_avg_listing_price,
	(SELECT owner_email_address
     FROM listings
     GROUP BY owner_email_address
     ORDER BY COUNT(*) DESC
     LIMIT 1) AS best_lister_email_address
FROM listings
INNER JOIN marketplaces
ON listings.marketplace = marketplaces.id;

CREATE VIEW monthly_data_view AS
SELECT 
    TO_CHAR(l.upload_time, 'YYYY-MM') AS year_month,
    SUM(CASE WHEN m.marketplace_name = 'EBAY' THEN 1 ELSE 0 END) AS ebay_listing_count,
    SUM(CASE WHEN m.marketplace_name = 'EBAY' THEN l.listing_price ELSE 0 END) AS ebay_total_listing_price,
    ROUND(AVG(CASE WHEN m.marketplace_name = 'EBAY' THEN l.listing_price ELSE NULL END), 2) AS ebay_avg_listing_price,
    SUM(CASE WHEN m.marketplace_name = 'AMAZON' THEN 1 ELSE 0 END) AS amazon_listing_count,
    SUM(CASE WHEN m.marketplace_name = 'AMAZON' THEN l.listing_price ELSE 0 END) AS amazon_total_listing_price,
    ROUND(AVG(CASE WHEN m.marketplace_name = 'AMAZON' THEN l.listing_price ELSE NULL END), 2) AS amazon_avg_listing_price
FROM listings AS l
INNER JOIN marketplaces AS m
ON l.marketplace = m.id
GROUP BY year_month;

CREATE VIEW monthly_email_view AS
SELECT
    TO_CHAR(upload_time, 'YYYY-MM') AS year_month,
    owner_email_address,
    COUNT(owner_email_address) AS email_count,
    ROW_NUMBER() OVER (PARTITION BY TO_CHAR(upload_time, 'YYYY-MM') ORDER BY COUNT(owner_email_address) DESC) AS row_num
FROM listings
GROUP BY TO_CHAR(upload_time, 'YYYY-MM'), owner_email_address;

CREATE VIEW monthly_report AS
SELECT
	ROW_NUMBER() OVER () AS id,
    mdv.year_month,
    mdv.ebay_listing_count,
    mdv.ebay_total_listing_price,
    mdv.ebay_avg_listing_price,
    mdv.amazon_listing_count,
    mdv.amazon_total_listing_price,
    mdv.amazon_avg_listing_price,
	mev.owner_email_address AS best_lister_email_address
FROM monthly_data_view AS mdv
LEFT JOIN monthly_email_view AS mev
ON mdv.year_month = mev.year_month
WHERE mev.row_num = 1
GROUP BY mdv.year_month, mdv.ebay_listing_count, mdv.ebay_total_listing_price, mdv.ebay_avg_listing_price, 
mdv.amazon_listing_count, mdv.amazon_total_listing_price, mdv.amazon_avg_listing_price, mev.owner_email_address
ORDER BY mdv.year_month ASC;