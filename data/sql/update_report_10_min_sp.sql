CREATE OR REPLACE PROCEDURE update_report_10_min()
language plpgsql as $$
DECLARE
    start_time TIMESTAMP := NOW();
    row_count INT;
BEGIN
    -- Insert log start
    INSERT INTO procedure_execution_log (procedure_name, start_time, status, message)
    VALUES ('update_report_10_min', start_time, 'IN_PROGRESS', 'Started processing')
    RETURNING id INTO row_count;

    -- Delete old records from report_10_min
    DELETE FROM report_10_min WHERE reference_timestamp >= NOW() - INTERVAL '30 days';

    -- Insert aggregated data
    INSERT INTO report_10_min (tenant_id, equipment_id, signal_id, reference_timestamp, tenth_minute, hour, day,
                               mean_v, min_v, max_v, count_of_measure)
    SELECT
        tenant_id,
        equipment_id,
        signal_id,
        date_trunc('minute', measure_dttm) -
            (EXTRACT(minute FROM measure_dttm)::int % 10) * INTERVAL '1 minute', -- Aggregated timestamp
        (EXTRACT(minute FROM measure_dttm)::int / 10) * 10, -- Tenth minute
        EXTRACT(hour FROM measure_dttm), -- Reference hour
        DATE(measure_dttm), -- Reference day
        AVG(val::NUMERIC),
        MIN(val::NUMERIC),
        MAX(val::NUMERIC),
        COUNT(*)
    FROM measure
    WHERE measure_dttm >= NOW() - INTERVAL '30 days'
    GROUP BY tenant_id, equipment_id, signal_id, reference_timestamp, tenth_minute, hour, day;

    -- Count deleted rows
    DELETE FROM measure WHERE measure_dttm < NOW() - INTERVAL '30 days' RETURNING 1 INTO row_count;

    -- Update log with success
    UPDATE procedure_execution_log
    SET end_time = NOW(), status = 'SUCCESS', message = 'Processed ' || row_count || ' rows'
    WHERE id = row_count;

EXCEPTION
    WHEN OTHERS THEN
        UPDATE procedure_execution_log
        SET end_time = NOW(), status = 'FAILED', message = SQLERRM
        WHERE id = row_count;
END;
$$;