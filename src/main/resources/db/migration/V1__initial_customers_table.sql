CREATE TABLE IF NOT EXISTS customers
(
    customer_id           varchar(255) NOT NULL,
    address               varchar(255) DEFAULT NULL,
    contact_mobile_number varchar(255) DEFAULT NULL,
    contact_name          varchar(255) DEFAULT NULL,
    contact_number        varchar(255) DEFAULT NULL,
    fax_number            varchar(255) DEFAULT NULL,
    name                  varchar(255) DEFAULT NULL,
    note                  varchar(255) DEFAULT NULL,
    postal_code           varchar(255) DEFAULT NULL,
    receiver              varchar(255) DEFAULT NULL,
    tax_id                varchar(255) DEFAULT NULL,
    PRIMARY KEY
        (
         customer_id
            )
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;