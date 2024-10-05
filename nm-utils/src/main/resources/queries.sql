--BEGIN INSERT INTERVAL
INSERT INTO rm_commons_dates(d_year,d_month,d_week,d_day,d_hour,d_minutes)
SELECT date_trunc('year',sub2.d_minutes) ,date_trunc('month',sub2.d_minutes) ,date_trunc('week',sub2.d_minutes),
	date_trunc('day',sub2.d_minutes),date_trunc('hour',sub2.d_minutes),sub2.d_minutes
FROM
(
SELECT date_trunc('minute',sub.gen) as d_minutes 
FROM
 (SELECT generate_series(now()-interval '${begin}', now()+interval '${end}', '${duration}') as gen) as sub
GROUP BY d_minutes
) as sub2
WHERE sub2.d_minutes NOT IN (SELECT d_minutes FROM rm_commons_dates)
-- END INSERT INTERVAL
