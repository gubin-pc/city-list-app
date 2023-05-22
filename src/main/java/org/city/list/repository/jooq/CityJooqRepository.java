package org.city.list.repository.jooq;

import jakarta.annotation.Nonnull;
import org.city.list.exception.NotFoundException;
import org.city.list.model.domain.City;
import org.city.list.model.view.CityView;
import org.city.list.repository.CityViewRepository;
import org.city.list.repository.CrudRepository;
import org.city.list.tables.records.CityRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.city.list.Tables.CITY;
import static org.jooq.impl.DSL.noCondition;

@Component
public class CityJooqRepository
        extends JooqDomainModelReadWriteRepository<Long, CityRecord, City>
        implements CrudRepository<City>, CityViewRepository {

    public CityJooqRepository(DSLContext db) {
        super(db, CITY, CITY.ID);
    }

    @Override
    protected CityRecord applyDiff(CityRecord record, City model) {
        record.setName(model.name());
        record.setPhoto(model.photo());
        return record;
    }

    @Override
    protected City toModel(CityRecord record) {
        return new City(
                record.getId(),
                record.getName(),
                record.getPhoto()
        );
    }

    @Override
    public City getById(@Nonnull Long id) {
        return super.findOne(CITY.ID.eq(id))
                .orElseThrow(() -> new NotFoundException("Not found " + table.getName() + " with id '" + id + "'"));
    }

    @Override
    public Page<CityView> findAll(@Nonnull Pageable pageable, String name) {
        requireNonNull(pageable);
        Condition condition = noCondition();
        if (name != null)
            condition = condition.or(CITY.NAME.startsWithIgnoreCase(name));
        return findAll(pageable, condition);
    }

    private Page<CityView> findAll(@Nonnull Pageable pageable, Condition... conditions) {
        requireNonNull(pageable);
        List<CityView> cityViews = db.selectFrom(CITY)
                .where(conditions)
                .limit(pageable.getPageSize()).offset(pageable.getOffset())
                .fetch()
                .map(cityRecordToCityViewMapper);
        long count = count(conditions);
        return new PageImpl<>(cityViews, pageable, count);
    }

    @Override
    public List<City> findAll() {
        return super.findAll();
    }

    private static final RecordMapper<CityRecord, CityView> cityRecordToCityViewMapper =
            record -> new CityView(record.getId(), record.getName(), record.getPhoto());
}
