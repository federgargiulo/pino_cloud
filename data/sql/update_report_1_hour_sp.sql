CREATE OR REPLACE PROCEDURE  update_report_1_hour() RETURNS void AS $$
DECLARE
    start_time TIMESTAMP := NOW();
    row_count INT;
BEGIN
    INSERT INTO procedure_execution_log (procedure_name, start_time, status, message)
    VALUES ('update_report_1_hour', start_time, 'IN_PROGRESS', 'Started processing')
    RETURNING id INTO row_count;

    DELETE FROM report_1_hour WHERE reference_timestamp < NOW() - INTERVAL '90 days';

    INSERT INTO report_1_hour (tenant_id, equipment_id, signal_id, reference_timestamp, tenth_minute, hour, day,
                               mean_v, min_v, max_v, count_of_measure)
    SELECT
        tenant_id,
        equipment_id,
        signal_id,
        date_trunc('hour', reference_timestamp),
        0, -- Tenth minute set to 0 (not relevant for hourly aggregation)
        hour,
        day,
        AVG(mean_v),
        MIN(min_v),
        MAX(max_v),
        SUM(count_of_measure)
    FROM report_30_min
    WHERE reference_timestamp < NOW() - INTERVAL '90 days'
    GROUP BY tenant_id, equipment_id, signal_id, reference_timestamp, tenth_minute, hour, day;

    DELETE FROM report_30_min WHERE reference_timestamp < NOW() - INTERVAL '90 days' RETURNING 1 INTO row_count;

    UPDATE procedure_execution_log
    SET end_time = NOW(), status = 'SUCCESS', message = 'Processed ' || row_count || ' rows'
    WHERE id = row_count;

EXCEPTION
    WHEN OTHERS THEN
        UPDATE procedure_execution_log
        SET end_time = NOW(), status = 'FAILED', message = SQLERRM
        WHERE id = row_count;
END;
$$ ;
