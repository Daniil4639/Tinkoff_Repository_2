package edu.jooq.tables.records;

import edu.jooq.tables.Links;
import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class LinksRecord extends UpdatableRecordImpl<LinksRecord> implements Record5<Long, String, OffsetDateTime, OffsetDateTime, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINKS.ID</code>.
     */
    public void setId(@Nullable Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINKS.ID</code>.
     */
    @Nullable
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINKS.URL</code>.
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINKS.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000000000)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINKS.UPDATED_AT</code>.
     */
    public void setUpdatedAt(@NotNull OffsetDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINKS.UPDATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getUpdatedAt() {
        return (OffsetDateTime) get(2);
    }

    /**
     * Setter for <code>LINKS.CREATED_AT</code>.
     */
    public void setCreatedAt(@NotNull OffsetDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINKS.CREATED_AT</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getCreatedAt() {
        return (OffsetDateTime) get(3);
    }

    /**
     * Setter for <code>LINKS.LAST_CHECK</code>.
     */
    public void setLastCheck(@NotNull OffsetDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>LINKS.LAST_CHECK</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public OffsetDateTime getLastCheck() {
        return (OffsetDateTime) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row5<Long, String, OffsetDateTime, OffsetDateTime, OffsetDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row5<Long, String, OffsetDateTime, OffsetDateTime, OffsetDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Links.LINKS.ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Links.LINKS.URL;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field3() {
        return Links.LINKS.UPDATED_AT;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field4() {
        return Links.LINKS.CREATED_AT;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field5() {
        return Links.LINKS.LAST_CHECK;
    }

    @Override
    @Nullable
    public Long component1() {
        return getId();
    }

    @Override
    @NotNull
    public String component2() {
        return getUrl();
    }

    @Override
    @NotNull
    public OffsetDateTime component3() {
        return getUpdatedAt();
    }

    @Override
    @NotNull
    public OffsetDateTime component4() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public OffsetDateTime component5() {
        return getLastCheck();
    }

    @Override
    @Nullable
    public Long value1() {
        return getId();
    }

    @Override
    @NotNull
    public String value2() {
        return getUrl();
    }

    @Override
    @NotNull
    public OffsetDateTime value3() {
        return getUpdatedAt();
    }

    @Override
    @NotNull
    public OffsetDateTime value4() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public OffsetDateTime value5() {
        return getLastCheck();
    }

    @Override
    @NotNull
    public LinksRecord value1(@Nullable Long value) {
        setId(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value2(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value3(@NotNull OffsetDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value4(@NotNull OffsetDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value5(@NotNull OffsetDateTime value) {
        setLastCheck(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord values(@Nullable Long value1, @NotNull String value2, @NotNull OffsetDateTime value3, @NotNull OffsetDateTime value4, @NotNull OffsetDateTime value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinksRecord
     */
    public LinksRecord() {
        super(Links.LINKS);
    }

    /**
     * Create a detached, initialised LinksRecord
     */
    @ConstructorProperties({ "id", "url", "updatedAt", "createdAt", "lastCheck" })
    public LinksRecord(@Nullable Long id, @NotNull String url, @NotNull OffsetDateTime updatedAt, @NotNull OffsetDateTime createdAt, @NotNull OffsetDateTime lastCheck) {
        super(Links.LINKS);

        setId(id);
        setUrl(url);
        setUpdatedAt(updatedAt);
        setCreatedAt(createdAt);
        setLastCheck(lastCheck);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised LinksRecord
     */
    public LinksRecord(edu.jooq.tables.pojos.Links value) {
        super(Links.LINKS);

        if (value != null) {
            setId(value.getId());
            setUrl(value.getUrl());
            setUpdatedAt(value.getUpdatedAt());
            setCreatedAt(value.getCreatedAt());
            setLastCheck(value.getLastCheck());
            resetChangedOnNotNull();
        }
    }
}
