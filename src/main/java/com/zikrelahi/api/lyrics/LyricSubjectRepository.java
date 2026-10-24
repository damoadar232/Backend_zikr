package com.zikrelahi.api.lyrics;

import com.zikrelahi.api.common.ContentStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LyricSubjectRepository extends JpaRepository<LyricSubject, String> {

    @Query(value = """
            SELECT subject.id AS id, subject.name AS name, mapping.id AS numericId
            FROM lyric_subjects subject
            JOIN subject_numeric_mappings mapping ON mapping.subject_key = subject.id
            ORDER BY subject.name, subject.id
            """, nativeQuery = true)
    List<LyricSubjectOption> findAllOptions();

    @Query("""
            SELECT DISTINCT subject
            FROM LyricSubject subject
            JOIN subject.lyrics lyric
            WHERE lyric.status = :status
            ORDER BY subject.name, subject.id
            """)
    List<LyricSubject> findByLyricStatus(@Param("status") ContentStatus status);

    interface LyricSubjectOption {
        String getId();

        String getName();

        Long getNumericId();
    }
}