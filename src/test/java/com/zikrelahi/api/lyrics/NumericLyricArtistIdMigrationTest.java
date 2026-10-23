package com.zikrelahi.api.lyrics;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NumericLyricArtistIdMigrationTest {

    @Test
    void preservesExistingStringReferencesAndTranslationsWithoutBackfilling() {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:numeric-lyric-id-migration;DB_CLOSE_DELAY=-1;MODE=MySQL", "sa", "");
        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .target(MigrationVersion.fromVersion("13"))
                .load()
                .migrate();

        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.update("""
                INSERT INTO lyrics (
                    legacy_id, title, slug, artist_id, writer_id, writer_name, status
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """, "numeric-id-migration", "Numeric ID Migration", "numeric-id-migration",
                "traditional", "owais-raza-qadri", "Owais Raza Qadri", "DRAFT");
        Long lyricId = jdbc.queryForObject(
                "SELECT id FROM lyrics WHERE legacy_id = 'numeric-id-migration'", Long.class);
        jdbc.update("""
                INSERT INTO lyric_language_versions (lyric_id, language_code, document, plain_text, status)
                VALUES (?, 'ur', '{"type":"doc","content":[{"type":"paragraph","content":[{"type":"text","text":"preserved"}]}]}', 'preserved', 'DRAFT')
                """, lyricId);
        Long assignmentArtistId = jdbc.queryForObject(
                "SELECT id FROM artists WHERE artist_key = 'zohaib-ashrafi'", Long.class);
        jdbc.update("""
                INSERT INTO lyric_artist_ids (lyric_id, artist_id, role, position)
                VALUES (?, ?, 'RECITER', 0)
                """, lyricId, assignmentArtistId);

        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load()
                .migrate();

        assertNull(jdbc.queryForObject("SELECT artist_id FROM lyrics WHERE id = ?", Long.class, lyricId));
        assertNull(jdbc.queryForObject("SELECT writer_id FROM lyrics WHERE id = ?", Long.class, lyricId));
        assertEquals("traditional", jdbc.queryForObject(
                "SELECT artist_id_legacy FROM lyrics WHERE id = ?", String.class, lyricId));
        assertEquals("owais-raza-qadri", jdbc.queryForObject(
                "SELECT writer_id_legacy FROM lyrics WHERE id = ?", String.class, lyricId));
        assertEquals(1, jdbc.queryForObject(
                "SELECT COUNT(*) FROM lyric_language_versions WHERE lyric_id = ? AND plain_text = 'preserved'",
                Integer.class, lyricId));
        assertEquals(1, jdbc.queryForObject(
                "SELECT COUNT(*) FROM lyric_artist_ids WHERE lyric_id = ? AND artist_id = ?",
                Integer.class, lyricId, assignmentArtistId));
    }
}
