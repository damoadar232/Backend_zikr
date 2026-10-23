CREATE TABLE artists (
    id VARCHAR(120) NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    display_name VARCHAR(200) NULL,
    biography TEXT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_artists PRIMARY KEY (id),
    CONSTRAINT ck_artist_status CHECK (status IN ('ACTIVE', 'INACTIVE'))
);
CREATE TABLE artist_roles (
    artist_id VARCHAR(120) NOT NULL,
    role VARCHAR(20) NOT NULL,
    CONSTRAINT pk_artist_roles PRIMARY KEY (artist_id, role),
    CONSTRAINT fk_artist_role_artist FOREIGN KEY (artist_id) REFERENCES artists (id) ON DELETE CASCADE,
    CONSTRAINT ck_artist_role CHECK (role IN ('SINGER','WRITER','LYRICIST','POET','RECITER','NAAT_KHWAN','COMPOSER','OTHER'))
);
CREATE INDEX idx_artist_roles_role ON artist_roles (role);
INSERT INTO artists (id, full_name, display_name, biography, status) VALUES
    ('owais-raza-qadri', 'Owais Raza Qadri', NULL, 'Renowned reciter of devotional naats and supplications.', 'ACTIVE'),
    ('zohaib-ashrafi', 'Zohaib Ashrafi', NULL, 'Beloved reciter with expressive, heartfelt delivery.', 'ACTIVE'),
    ('traditional', 'Traditional', NULL, 'Community-recited devotional works carried through generations.', 'ACTIVE');
INSERT INTO artist_roles (artist_id, role) VALUES
    ('owais-raza-qadri', 'SINGER'), ('owais-raza-qadri', 'RECITER'), ('owais-raza-qadri', 'NAAT_KHWAN'),
    ('zohaib-ashrafi', 'SINGER'), ('zohaib-ashrafi', 'RECITER'), ('zohaib-ashrafi', 'NAAT_KHWAN'),
    ('traditional', 'RECITER'), ('traditional', 'OTHER');