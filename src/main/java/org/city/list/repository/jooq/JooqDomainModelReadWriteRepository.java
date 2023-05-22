package org.city.list.repository.jooq;

import jakarta.annotation.Nonnull;
import org.city.list.exception.NotFoundException;
import org.city.list.model.domain.DomainModel;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UpdatableRecord;
import org.jooq.exception.NoDataFoundException;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

public abstract class JooqDomainModelReadWriteRepository<ID, JooqRecord extends UpdatableRecord<JooqRecord>, M extends DomainModel<ID>>
        extends JooqDomainModelReadOnlyRepository<ID, JooqRecord, M> {

    public JooqDomainModelReadWriteRepository(
            DSLContext db,
            Table<JooqRecord> table,
            TableField<JooqRecord, ID> idField
    ) {
        super(db, table, idField);
    }

    @Transactional
    public M update(@Nonnull M model) throws NotFoundException {
        try {
            requireNonNull(model);
            JooqRecord existingRecord = db.fetchSingle(table, idField.eq(model.id()));
            JooqRecord updatedRecord = applyDiff(existingRecord, model);
            updatedRecord.store();

            return toModel(updatedRecord);
        } catch (NoDataFoundException e) {
            throw new NotFoundException("Not found " + table.getName() + " with id '" + model.id() +"'", e);
        }
    }

    protected abstract JooqRecord applyDiff(JooqRecord record, M model);
}
