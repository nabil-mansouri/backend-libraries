-- DROP CMS
DROP TABLE nm_cms_actor_anonymous;
DROP TABLE nm_cms_contents_composed_nm_cms_contents_images;
DROP TABLE nm_cms_contents_composed_nm_cms_contents_text;
DROP TABLE nm_cms_contents_images;
DROP TABLE nm_cms_contents_simple;
DROP TABLE nm_cms_contents_text;
DROP TABLE nm_cms_subject_data;
DROP TABLE nm_cms_subject_any;
DROP TABLE nm_cms_cms;
DROP TABLE nm_cms_contents_composed;
DROP TABLE nm_cms_contents;
DROP TABLE nm_cms_actor;
DROP TABLE nm_cms_subject;

-- DROP COMMUNICATION
DROP TABLE nm_communication_actor_anonymous;
DROP TABLE nm_communication_actor_email;
DROP TABLE nm_communication_content_file;
DROP TABLE nm_communication_content_template;
DROP TABLE nm_communication_content_text;
DROP TABLE nm_communication_history_subject_joined;
DROP TABLE nm_communication_history_subject_content;
DROP TABLE nm_communication_history_subject_app_apptext;
DROP TABLE nm_communication_history_subject_app_appdata;
DROP TABLE nm_communication_history_subject;
DROP TABLE nm_communication_subject_any;
DROP TABLE nm_communication_subject_simple;
DROP TABLE nm_communication_message_contents;
DROP TABLE nm_communication_content;
DROP TABLE nm_communication_message_nm_communication_actor;
DROP TABLE nm_communication_message_nm_communication_medium;
DROP TABLE nm_communication_medium;
DROP TABLE nm_communication_channel CASCADE;
DROP TABLE nm_communication_message CASCADE;
DROP TABLE nm_communication_actor CASCADE;
DROP TABLE nm_communication_template_nm_communication_template_arguments;
DROP TABLE nm_communication_template_arguments;
DROP TABLE nm_communication_template;
DROP TABLE nm_communication_trigger_subject; 
DROP TABLE nm_communication_history_actor; 
DROP TABLE nm_communication_subject; 


-- DROP APP DATA
DROP TABLE nm_app_data_text;
DROP TABLE nm_app_data_path;
DROP TABLE nm_app_data_byte;
DROP TABLE nm_app_data;

-- DROP HISTORY
DROP TABLE nm_app_history_subject;
DROP TABLE nm_app_history_actor;
DROP TABLE nm_app_history;
--VIEW PRICE FILTER
DROP TABLE IF EXISTS rm_view_price_value_filters CASCADE;
DROP VIEW IF EXISTS rm_view_price_value_filters CASCADE;
CREATE OR REPLACE VIEW rm_view_price_value_filters AS 
SELECT 	val.id as valueid,
		val.price_id as priceid,
		filtero.ordertype,
		filterr.restaurant_id as restaurantid, 
		filterfrom.date_value as datefrom, 
		filterto.date_value as dateto,
		subject.id as subjectid, 
		subject.root as subjectroot, 
		row_number() OVER () AS id 
FROM rm_price_value as val 
INNER JOIN rm_price_price price ON (price.id=val.price_id)
INNER JOIN rm_price_subject subject ON (price.subject_id=subject.id)
LEFT JOIN 
( 
	SELECT * 
	FROM rm_price_value_filter_order_type o 
	INNER JOIN rm_price_value_filter f ON(f.id=o.id)
) as filtero ON (val.id =filtero.value_id)
LEFT JOIN 
 ( 
	SELECT * 
	FROM rm_price_value_filter_restaurant o 
	INNER JOIN rm_price_value_filter f ON(f.id=o.id)
) as filterr ON (val.id =filterr.value_id)
LEFT JOIN 
 ( 
	SELECT * 
	FROM rm_price_filter_date o 
	INNER JOIN rm_price_filter f ON(f.id=o.id AND f.type ='LimitInTimeFrom')
) as filterfrom ON (val.price_id =filterfrom.price_id)
LEFT JOIN 
 ( 
	SELECT * 
	FROM rm_price_filter_date o 
	INNER JOIN rm_price_filter f ON(f.id=o.id AND f.type ='LimitInTimeTo')
) as filterto ON (val.price_id =filterto.price_id);

-- VIEW MEASURE ORDER
DROP TABLE IF EXISTS rm_view_measure_order_rm_commons_dates;
DROP TABLE IF EXISTS rm_view_measure_order CASCADE;
CREATE OR REPLACE VIEW rm_view_measure_order AS 
SELECT ord.id as orderid, 
		ord.idcart as cartid, 
		ord.ordertype as ordertype, 
		ord.total ordertotal,ord.client_id as clientid,
		ord.restaurant_id as restaurantid, 
		date_trunc('minute',ordts.created) transactionminute,
		date_trunc('hour',ordts.created) transactionhour,
		date_trunc('day',ordts.created) transactionday,
		date_trunc('week',ordts.created) transactionweek,
		date_trunc('month',ordts.created) transactionmonth,
		date_trunc('year',ordts.created) transactionyear, 
		row_number() OVER () AS id 
FROM rm_order_order ord 
LEFT JOIN rm_order_transaction ordt ON (ord.transaction_id=ordt.id) 
LEFT JOIN rm_transaction_state ordts ON (ordt.laststate_id= ordts.id)  ;

-- VIEW MEASURE PRODUCTS
DROP TABLE IF EXISTS rm_view_measure_product_rm_commons_dates;
DROP TABLE IF EXISTS rm_view_measure_product CASCADE;
CREATE OR REPLACE VIEW rm_view_measure_product AS 
SELECT ord.id as orderid,
		ord.idcart as cartid, 
		ord.ordertype as ordertype, 
		ord.total ordertotal,
		ord.client_id as clientid, 
		ord.restaurant_id as restaurantid, 
		date_trunc('minute',ordts.created) transactionminute,
		date_trunc('hour',ordts.created) transactionhour,
		date_trunc('day',ordts.created) transactionday,
		date_trunc('week',ordts.created) transactionweek,
		date_trunc('month',ordts.created) transactionmonth,
		date_trunc('year',ordts.created) transactionyear, 
		prodi.idproduct productid,
		prodi.price productprice, 
		prodi.type producttype,
		row_number() OVER () AS id 
FROM rm_order_order ord 
LEFT JOIN rm_order_transaction ordt ON (ord.transaction_id=ordt.id) 
LEFT JOIN rm_transaction_state ordts ON (ordt.laststate_id= ordts.id) 
LEFT JOIN rm_order_order_rm_product_instance ordprodi ON (ord.id=ordprodi.rm_order_order_id) 
LEFT JOIN rm_product_instance prodi ON (ordprodi.details_id=prodi.id);


-- VIEW CLIENT AGE ACTION
DROP TABLE IF EXISTS rm_view_client_age_action;
DROP VIEW IF EXISTS rm_view_client_age_action;
CREATE OR REPLACE VIEW rm_view_client_age_action AS 
 SELECT 
 	c.id client_id,
    ca.id as action_id,
    ca.type,
    ca.created as action_date, 
	DATE_PART('epoch',  NOW()-ca.created) as duration
	FROM rm_client_client c
   INNER JOIN rm_client_action ca ON c.id = ca.client_id
   WHERE ca.created >= ALL
   	(SELECT sub_ca.created FROM rm_client_action sub_ca
   		WHERE ca.id <>sub_ca.id AND c.id=sub_ca.client_id 
   				AND sub_ca.type=ca.type);
   				
-- VIEW ORDER AGE
DROP TABLE IF EXISTS rm_view_order_age;
DROP VIEW IF EXISTS rm_view_order_age;
CREATE OR REPLACE VIEW rm_view_order_age AS 
 SELECT 
 	trs.id,
 	ord.client_id,
	DATE_PART('epoch',  NOW()-trs.created) as duration,
	trs.type,
	trs.created last_date
	FROM rm_order_order ord
   INNER JOIN rm_order_state trs ON (ord.id= trs.order_id) 
   WHERE trs.created >= ALL
   	(
   		SELECT sub_trs.created 
   		FROM rm_order_order sub_ord
   		INNER JOIN rm_order_state sub_trs ON (ord.id= sub_trs.order_id) 
   		WHERE trs.id <>sub_trs.id 
   		AND ord.id=sub_ord.client_id 
   		AND trs.type=sub_trs.type 
   	);
   	
-- view tracking
DROP TABLE IF EXISTS rm_view_discount_tracking_stats;
DROP VIEW IF EXISTS rm_view_discount_tracking_stats;
CREATE OR REPLACE VIEW rm_view_discount_tracking_stats AS 
 SELECT 
 	dt.id,
 	dt.created,
 	dt.client_id,
 	dt.discount_id,
 	sub.nb_used
	FROM rm_discount_tracking dt,
   	(
   		SELECT dt.id, count(dtls.type) as nb_used
   		FROM rm_discount_tracking dt
   		INNER JOIN rm_discount_tracking_lifecycle dtl ON (dtl.id=dt.lifecycle_id)
   		INNER JOIN rm_discount_tracking_lifecycle_state dtls ON (dtl.id=dtls.lifecycle_id)
   		WHERE dtls.type='Used'
   		GROUP BY dt.id
   	 ) as sub
   	 WHERE dt.id=sub.id;