CREATE OR REPLACE PROCEDURE update_report_10_min()
LANGUAGE plpgsql AS $$
DECLARE
    start_time TIMESTAMP := NOW();
    row_count INT;
    log_id INT;
	interval_hours INTEGER :=1;
BEGIN
     INSERT INTO procedure_execution_log (procedure_name, start_time, status, message)
        VALUES ('update_report_10_min', start_time, 'IN_PROGRESS', 'Started processing')
        RETURNING id INTO log_id;



        -- Eliminazione vecchi record dal report
        DELETE FROM report_10_min WHERE reference_timestamp <= NOW() - INTERVAL '30 days';

        -- Inserimento dati aggregati
        INSERT INTO report_10_min (tenant_id, equipment_id, signal_id, reference_timestamp,
                                   year_val, month_val, day_val, hour_val, tenth_minute,
                                   mean_v, min_v, max_v, count_of_measure)
        SELECT
            tenant_id,
            equipment_id,
            signal_id,
            date_trunc('minute', measure_dttm) -
                (EXTRACT(minute FROM measure_dttm)::int % 10) * INTERVAL '1 minute' AS reference_timestamp,
            year_val,
            month_val,
            day_val,
            hour_val,
            (minute_val % 10) * 10 AS minute_round,
            AVG(val::NUMERIC),
            MIN(val::NUMERIC),
            MAX(val::NUMERIC),
            COUNT(*)
        FROM measure
        WHERE  date_trunc('minute', measure_dttm) -
      (EXTRACT(minute FROM measure_dttm)::int % 10) * INTERVAL '1 minute'
      < date_trunc('minute', NOW() - INTERVAL '1 hour' * interval_hours) -
      (EXTRACT(minute FROM NOW())::int % 10) * INTERVAL '1 minute'
        GROUP BY tenant_id, equipment_id, signal_id, reference_timestamp, year_val, month_val, hour_val, day_val, minute_round;

        -- Eliminazione dei dati più vecchi
        DELETE FROM measure WHERE  date_trunc('minute', measure_dttm) -
                                        (EXTRACT(minute FROM measure_dttm)::int % 10) * INTERVAL '1 minute'
                                        < date_trunc('minute', NOW() - INTERVAL '1 hour' * interval_hours) -
                                        (EXTRACT(minute FROM NOW())::int % 10) * INTERVAL '1 minute';

		GET DIAGNOSTICS row_count = ROW_COUNT;

        UPDATE procedure_execution_log
        SET end_time = NOW(), status = 'SUCCESS', message = 'Processed ' || row_count || ' rows'
        WHERE id = log_id;


END;
$$;
