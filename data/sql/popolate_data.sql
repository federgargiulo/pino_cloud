CREATE OR REPLACE PROCEDURE populate_report_data_first_stg(
    in_tenant_id TEXT,
    in_signal_id TEXT,
    in_equipment_id TEXT,
    start_value NUMERIC
)
LANGUAGE plpgsql
AS $$
DECLARE
    start_ts TIMESTAMP := date_trunc('day', now()) - INTERVAL '1 year';
    end_ts TIMESTAMP := date_trunc('day', now());
    ts TIMESTAMP := start_ts;
    angle FLOAT := 0;
    id_val BIGINT;
    max_val NUMERIC(38,2);
    min_val NUMERIC(38,2);
    mean_val NUMERIC(38,2);
    amplitude NUMERIC := 50;
BEGIN
    WHILE ts < end_ts LOOP
        angle := EXTRACT(EPOCH FROM ts - start_ts) / (60 * 60 * 24) * 2 * pi(); -- angolo su base giornaliera

        -- andamento sinusoidale con offset su start_value
        mean_val := start_value + amplitude * sin(angle);
        max_val := mean_val + 5;
        min_val := mean_val - 5;

        -- ottieni id dalla sequence
        SELECT nextval('report_data_first_stg_seq') INTO id_val;

        INSERT INTO public.report_data_first_stg (
            id,
            count_of_measure,
            day_val,
            equipment_id,
            hour_val,
            max_val,
            mean_val,
            min_val,
            minute_val,
            month_val,
            reference_timestamp,
            signal_id,
            tenant_id,
            year_val
        )
        VALUES (
            id_val,
            10,
            EXTRACT(DAY FROM ts),
            in_equipment_id,
            EXTRACT(HOUR FROM ts),
            max_val,
            mean_val,
            min_val,
            EXTRACT(MINUTE FROM ts),
            EXTRACT(MONTH FROM ts),
            ts,
            in_signal_id,
            in_tenant_id,
            EXTRACT(YEAR FROM ts)
        );

        ts := ts + INTERVAL '10 minutes';
    END LOOP;
END;
$$;