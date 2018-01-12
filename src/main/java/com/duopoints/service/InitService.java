package com.duopoints.service;

import com.duopoints.db.Sequences;
import com.duopoints.db.tables.records.LevelPointRequirementsRecord;
import com.duopoints.db.tables.records.PointeventemotionRecord;
import com.duopoints.db.tables.records.PointtypeRecord;
import com.duopoints.db.tables.records.PointtypecategoryRecord;
import org.jooq.Configuration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class InitService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;

    public void populateLevelReq() {
        duo.alterSequence(Sequences.LEVEL_POINT_REQUIREMENTS_LEVEL_NUMBER_SEQ).restart().execute();

        List<LevelPointRequirementsRecord> levels = new ArrayList<>();

        short points = 50;
        for (short i = 1; i <= 20; i++) {
            LevelPointRequirementsRecord rec = new LevelPointRequirementsRecord();
            rec.setLevelNumber(i);
            rec.setLevelPointRequirement(points);
            levels.add(rec);

            points += 100;
        }

        duo.batchInsert(levels).execute();
    }


    public void populatePointsInfo() {
        // First we have to populate the PointTypeCategories (Helpful, Economical, Adventure, etc)
        List<PointtypecategoryRecord> categoryRecords = new ArrayList<>();

        categoryRecords.add(new PointtypecategoryRecord().value2("Helpful").value3("Helpful Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Caring").value3("Caring Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Loving").value3("Loving Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Supportive").value3("Supportive Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Thoughtful").value3("Thoughtful Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Shopping").value3("Shopping Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Adventure").value3("Adventure Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Tasks").value3("Tasks Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Economical").value3("Economical Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Handy-Man").value3("Handy-Man Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Gifts").value3("Gifts Desc"));
        categoryRecords.add(new PointtypecategoryRecord().value2("Housekeeping").value3("Housekeeping Desc"));

        duo.alterSequence(Sequences.POINTTYPECATEGORY_POINTTYPECATEGORY_NUMBER_SEQ).restart().execute();
        duo.batchInsert(categoryRecords).execute();


        // Now we have to create the PointTypes (Made breakfast, Gave massage, Walked the dog, etc).
        List<PointtypeRecord> typeRecords = new ArrayList<>();

        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Kiss good morning").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Brought breakfast in bed").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Made breakfast").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Gave massage").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Cleaned the room").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Put the toilet seat down").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Walked the dog").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Feed the dog").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Feed the pet").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Gave ride to work").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Picked up from work").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Out for Ice Cream").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Showed up as a surprise").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Met family").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Took extended family out for food").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Went along with extended family out for food").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Went to religious activity").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Went on a trip").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Made warm drink").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Made cool drink").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Made cocktail").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Brought a drink").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 11).value3("Let partner drive").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 1).value3("Took partner out for Breakfast").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 1).value3("Took partner out for Lunch").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 1).value3("Took partner out for Dinner").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 1).value3("Romantic Night out").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 2).value3("Gave Flowers").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 2).value3("Gave Chocolates").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 2).value3("Gave Candy").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 2).value3("Gave Jewellery").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 2).value3("Gave Wine").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 3).value3("Called just to talk").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 3).value3("Asked about partners day").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 3).value3("Listened to partner talk about their day").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 3).value3("Told partner about their day").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 3).value3("Had interesting discussion").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 4).value3("Was there for partner").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 4).value3("Followed partners suggestion").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 4).value3("Was encouraging").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 4).value3("Let partner take the lead").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 4).value3("Included partner").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 4).value3("Bragged about partner").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 4).value3("Showed partner off").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 4).value3("Cheered partner on").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 5).value3("Comforted partner").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 5).value3("Stood up for partner").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 5).value3("Made partner feel safe").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 5).value3("Made partner feel special").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 5).value3("Made partner feel important").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 5).value3("Made partner feel loved").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 5).value3("Made partner feel supported").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Took partner to a concert").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Took partner to a show").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Took partner to a comedy show").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Read partner a book").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Helped partner studying").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Quizzed partner").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Took partner to a gallery").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Took partner to a museum").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Took partner to a park").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 6).value3("Went on a picnic").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 7).value3("Made pancakes").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 7).value3("Made waffles").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 7).value3("Made cookies").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 7).value3("Did house chores").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 7).value3("Repairs").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 8).value3("Did the laundry").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 8).value3("Washed the dishes").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 8).value3("Set the table").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 8).value3("Cleared the dishes").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 8).value3("Went grocery shopping").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 8).value3("Prepared kids lunch").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 8).value3("Took the kids to school").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 8).value3("Cleaned pet area").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 9).value3("Did maintenance on the car").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 9).value3("Did repairs on the car").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 10).value3("Organized party").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 10).value3("Organized event").value4("Desc"));
        typeRecords.add(new PointtypeRecord().value2((short) 10).value3("Organized birthday").value4("Desc"));

        duo.alterSequence(Sequences.POINTTYPE_POINTTYPE_NUMBER_SEQ).restart().execute();
        duo.batchInsert(typeRecords).execute();


        // Now we need to add the Point Event Emotions
        List<PointeventemotionRecord> eventEmotions = new ArrayList<>();

        eventEmotions.add(new PointeventemotionRecord().value2("Happy").value3("Happy Desc"));
        eventEmotions.add(new PointeventemotionRecord().value2("Funny").value3("Funny Desc"));
        eventEmotions.add(new PointeventemotionRecord().value2("Thankful").value3("Thankful Desc"));
        eventEmotions.add(new PointeventemotionRecord().value2("Excited").value3("Excited Desc"));
        eventEmotions.add(new PointeventemotionRecord().value2("Confident").value3("Confident Desc"));
        eventEmotions.add(new PointeventemotionRecord().value2("Relaxed").value3("Relaxed Desc"));
        eventEmotions.add(new PointeventemotionRecord().value2("Comfortable").value3("Comfortable Desc"));

        duo.alterSequence(Sequences.POINTEVENTEMOTION_POINTEVENTEMOTION_NUMBER_SEQ).restart().execute();
        duo.batchInsert(eventEmotions).execute();
    }
}
