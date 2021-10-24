CREATE SCHEMA demo AUTHORIZATION postgres;
CREATE TABLE IF NOT EXISTS demo.dates
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    date date NOT NULL,
    CONSTRAINT dates_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;
ALTER TABLE demo.dates OWNER to postgres;