CREATE OR REPLACE PROCEDURE  update_report_30_min() RETURNS void AS $$
DECLARE
    start_time TIMESTAMP := NOW();
    row_count INT;
BEGIN
    INSERT INTO procedure_execution_log (procedure_name, start_time, status, message)
    VALUES ('update_report_30_min', start_time, 'IN_PROGRESS', 'Started processing')
    RETURNING id INTO row_count;

    DELETE FROM report_30_min WHERE reference_timestamp < NOW() - INTERVAL '30 days';

    INSERT INTO report_30_min (tenant_id, equipment_id, signal_id, reference_timestamp, tenth_minute, hour, day,
                               mean_v, min_v, max_v, count_of_measure)
    SELECT
        tenant_id,
        equipment_id,
        signal_id,
        date_trunc('minute', reference_timestamp) -
            (EXTRACT(minute FROM reference_timestamp)::int % 30) * INTERVAL '1 minute',
        (EXTRACT(minute FROM reference_timestamp)::int / 30) * 30, -- Rounding to 30 minutes
        hour,
        day,
        AVG(mean_v),
        MIN(min_v),
        MAX(max_v),
        SUM(count_of_measure)
    FROM report_10_min
    WHERE reference_timestamp < NOW() - INTERVAL '30 days'
    GROUP BY tenant_id, equipment_id, signal_id, reference_timestamp, tenth_minute, hour, day;

    DELETE FROM report_10_min WHERE reference_timestamp < NOW() - INTERVAL '30 days' RETURNING 1 INTO row_count;

    UPDATE procedure_execution_log
    SET end_time = NOW(), status = 'SUCCESS', message = 'Processed ' || row_count || ' rows'
    WHERE id = row_count;

EXCEPTION
    WHEN OTHERS THEN
        UPDATE procedure_execution_log
        SET end_time = NOW(), status = 'FAILED', message = SQLERRM
        WHERE id = row_count;
END;
$$ LANGUAGE plpgsql;
