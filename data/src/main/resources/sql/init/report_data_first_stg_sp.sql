CREATE OR REPLACE PROCEDURE update_report_data_first_stg()
LANGUAGE plpgsql AS $$
DECLARE
    start_time TIMESTAMP := NOW();
    row_count INT;
    log_id INT;
    interval_hours INTEGER := 1;
BEGIN
    INSERT INTO procedure_execution_log (id, procedure_name, start_time, status, message)
    VALUES (nextval('procedure_execution_log_seq'), 'update_report_data_first_stg', start_time, 'IN_PROGRESS', 'Started processing')
    RETURNING id INTO log_id;

    -- Eliminazione vecchi record dal report
    DELETE FROM report_data_first_stg WHERE reference_timestamp <= NOW() - INTERVAL '30 days';

    -- Inserimento dati aggregati
    INSERT INTO report_data_first_stg (
        id,
        tenant_id,
        equipment_id,
        signal_id,
        reference_timestamp,
        create_timestamp,
        year_val,
        month_val,
        day_val,
        hour_val,
        minute_val,
        mean_val,
        min_val,
        max_val,
        count_of_measure
    )
    SELECT
        MIN(id),  -- UUID rappresentativo per l’aggregazione
        tenant_id,
        equipment_id,
        signal_id,
        date_trunc('hour', measure_dttm) +
            FLOOR(EXTRACT(minute FROM measure_dttm) / 10) * INTERVAL '10 minutes' AS reference_timestamp,
        start_time AS create_timestamp,
        year_val,
        month_val,
        day_val,
        hour_val,
        (minute_val / 10) * 10 AS minute_val,
        AVG(val::NUMERIC),
        MIN(val::NUMERIC),
        MAX(val::NUMERIC),
        COUNT(*)
    FROM measure
    WHERE date_trunc('hour', measure_dttm) +
            FLOOR(EXTRACT(minute FROM measure_dttm) / 10) * INTERVAL '10 minutes'
        <= date_trunc('minute', start_time - INTERVAL '1 hour' * interval_hours) +
           FLOOR(EXTRACT(minute FROM start_time) / 10) * INTERVAL '10 minutes'
    GROUP BY tenant_id, equipment_id, signal_id, reference_timestamp, year_val, month_val, day_val, hour_val, minute_val;

    -- Eliminazione dei dati più vecchi
    DELETE FROM measure
    WHERE date_trunc('hour', measure_dttm) +
            FLOOR(EXTRACT(minute FROM measure_dttm) / 10) * INTERVAL '10 minutes'
        <= date_trunc('minute', start_time - INTERVAL '1 hour' * interval_hours) +
           FLOOR(EXTRACT(minute FROM start_time) / 10) * INTERVAL '10 minutes';

    GET DIAGNOSTICS row_count = ROW_COUNT;

    UPDATE procedure_execution_log
    SET end_time = NOW(), status = 'SUCCESS', message = 'Processed ' || row_count || ' rows'
    WHERE id = log_id;

END;
$$;