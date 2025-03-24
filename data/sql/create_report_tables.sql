

CREATE TABLE IF NOT EXISTS procedure_execution_log (
    id SERIAL PRIMARY KEY,
    procedure_name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP DEFAULT NOW(),
    end_time TIMESTAMP,
    status VARCHAR(50),
    message TEXT
);

CREATE TABLE IF NOT EXISTS report_10_min (
    id SERIAL PRIMARY KEY,
    tenant_id VARCHAR(255) NOT NULL,
    equipment_id VARCHAR(255) NOT NULL,
    signal_id VARCHAR(255) NOT NULL,
    data_rif TIMESTAMP NOT NULL, -- Timestamp aggregato
    decimo_minuto INT NOT NULL, -- Decimo minuto di riferimento
    ora INT NOT NULL, -- Ora di riferimento
    giorno DATE NOT NULL, -- Giorno di riferimento
    mean_v NUMERIC, -- Media valore
    min_v NUMERIC, -- Valore minimo
    max_v NUMERIC, -- Valore massimo
    countOfMeasure INT -- Numero misurazioni
);

CREATE TABLE IF NOT EXISTS report_30_min  (
    id SERIAL PRIMARY KEY,
    tenant_id VARCHAR(255) NOT NULL,
    equipment_id VARCHAR(255) NOT NULL,
    signal_id VARCHAR(255) NOT NULL,
    data_rif TIMESTAMP NOT NULL, -- Timestamp aggregato
    decimo_minuto INT NOT NULL, -- Decimo minuto di riferimento
    ora INT NOT NULL, -- Ora di riferimento
    giorno DATE NOT NULL, -- Giorno di riferimento
    mean_v NUMERIC, -- Media valore
    min_v NUMERIC, -- Valore minimo
    max_v NUMERIC, -- Valore massimo
    countOfMeasure INT -- Numero misurazioni
);

CREATE TABLE IF NOT EXISTS  report_1_hour  (
    id SERIAL PRIMARY KEY,
    tenant_id VARCHAR(255) NOT NULL,
    equipment_id VARCHAR(255) NOT NULL,
    signal_id VARCHAR(255) NOT NULL,
    data_rif TIMESTAMP NOT NULL, -- Timestamp aggregato
    decimo_minuto INT NOT NULL, -- Decimo minuto di riferimento
    ora INT NOT NULL, -- Ora di riferimento
    giorno DATE NOT NULL, -- Giorno di riferimento
    mean_v NUMERIC, -- Media valore
    min_v NUMERIC, -- Valore minimo
    max_v NUMERIC, -- Valore massimo
    countOfMeasure INT -- Numero misurazioni
);

CREATE TABLE cron_lock (
    task_name VARCHAR(255) PRIMARY KEY,
    locked BOOLEAN NOT NULL,
    last_run TIMESTAMP
);



