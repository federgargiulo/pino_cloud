

CREATE TABLE IF NOT EXISTS procedure_execution_log (
    id SERIAL PRIMARY KEY,
    procedure_name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP DEFAULT NOW(),
    end_time TIMESTAMP,
    status VARCHAR(50),
    message TEXT
);

CREATE TABLE IF NOT EXISTS  report_data_first_stg (
    id SERIAL PRIMARY KEY,
    tenant_id VARCHAR(255) NOT NULL,
    equipment_id VARCHAR(255) NOT NULL,
    signal_id VARCHAR(255) NOT NULL,
    reference_timestamp TIMESTAMP NOT NULL, -- Aggregated timestamp
    year_val INT NOT NULL,
    month_val INT NOT NULL,
    day_val DATE NOT NULL, -- Reference day
    hour_val INT NOT NULL, -- Reference hour
    minute_val INT NOT NULL, -- Reference tenth minute
    mean_val NUMERIC, -- Mean value
    min_val NUMERIC, -- Minimum value
    max_val NUMERIC, -- Maximum value
    count_of_measure INT -- Number of measurements
);



CREATE OR REPLACE PROCEDURE manage_procedure_log(
    IN p_procedure_name VARCHAR(255),
    IN p_status VARCHAR(50),
    IN p_message TEXT,
    IN p_end BOOLEAN DEFAULT FALSE, -- Se TRUE, aggiorna il log esistente
    INOUT p_log_id INT DEFAULT NULL -- Restituisce l'ID del log
)
LANGUAGE plpgsql AS $$
DECLARE
    v_start_time TIMESTAMP := NOW();
BEGIN
    -- Avvia una transazione autonoma per isolare il logging
    BEGIN
        IF NOT p_end THEN
            -- Inserisce un nuovo record all'inizio della procedura e restituisce l'ID
            INSERT INTO procedure_execution_log (procedure_name, start_time, status, message)
            VALUES (p_procedure_name, v_start_time, p_status, p_message)
            RETURNING id INTO p_log_id;
        ELSE
            -- Aggiorna il record esistente con end_time, status finale e messaggio
            UPDATE procedure_execution_log
            SET end_time = NOW(), status = p_status, message = p_message
            WHERE id = p_log_id;
        END IF;

        -- Commit immediato per garantire isolamento
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            -- In caso di errore nel logging, rollback senza bloccare la procedura principale
            ROLLBACK;
            p_log_id := NULL; -- Reset dell'ID in caso di errore
    END;
END;
$$;


