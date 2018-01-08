package com.duopoints.service;

import com.duopoints.db.tables.records.LevelPointRequirementsRecord;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class InitService {

    @Autowired
    @Qualifier("configuration")
    private Configuration duoConfig;

    public void populateLevelReq() {
        List<LevelPointRequirementsRecord> levels = new ArrayList<>();

        short points = 50;
        for (short i = 1; i <= 20; i++) {
            LevelPointRequirementsRecord rec = new LevelPointRequirementsRecord();
            rec.setLevelNumber(i);
            rec.setLevelPointRequirement(points);
            levels.add(rec);

            points += 100;
        }

        duoConfig.dsl().batchInsert(levels).execute();
    }
}
