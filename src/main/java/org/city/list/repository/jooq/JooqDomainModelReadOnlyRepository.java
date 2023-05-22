package org.city.list.repository.jooq;

import jakarta.annotation.Nonnull;
import org.city.list.model.domain.DomainModel;
import org.jooq.Record;
import org.jooq.*;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.city.list.Tables.CITY;

public abstract class JooqDomainModelReadOnlyRepository<ID, JooqRecord extends Record, M extends DomainModel<ID>> {

    protected final DSLContext db;
    protected final Table<JooqRecord> table;
    protected final TableField<JooqRecord, ID> idField;

    public JooqDomainModelReadOnlyRepository(
            DSLContext db,
            Table<JooqRecord> table,
            TableField<JooqRecord, ID> idField) {
        this.db = requireNonNull(db);
        this.table = requireNonNull(table);
        this.idField = requireNonNull(idField);
    }

    protected abstract M toModel(JooqRecord record);

    public Optional<M> findOne(Condition... condition) {
        return db.fetchOptional(table, condition)
                .map(this::toModel);
    }

    public long count(Condition... conditions) {
        return db.fetchCount(db.selectFrom(CITY).where(conditions));
    }

    public Optional<M> findById(@Nonnull ID id) {
        return findOne(idField.eq(id));
    }

    public List<M> findAll(Condition... conditions) {
        return db.selectFrom(table)
                .where(conditions)
                .fetch()
                .map(this::toModel);
    }
}
