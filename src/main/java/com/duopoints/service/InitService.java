package com.duopoints.service;

import com.duopoints.db.Sequences;
import com.duopoints.db.tables.records.LevelPointRequirementsRecord;
import com.duopoints.db.tables.records.PointEventEmotionRecord;
import com.duopoints.db.tables.records.PointTypeCategoryRecord;
import com.duopoints.db.tables.records.PointTypeRecord;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
        List<PointTypeCategoryRecord> categoryRecords = new ArrayList<>();

        categoryRecords.add(new PointTypeCategoryRecord().value2("Helpful").value3("Helpful Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Caring").value3("Caring Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Loving").value3("Loving Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Supportive").value3("Supportive Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Thoughtful").value3("Thoughtful Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Shopping").value3("Shopping Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Adventure").value3("Adventure Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Tasks").value3("Tasks Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Economical").value3("Economical Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Handy-Man").value3("Handy-Man Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Gifts").value3("Gifts Desc").value4("ACTIVE"));
        categoryRecords.add(new PointTypeCategoryRecord().value2("Housekeeping").value3("Housekeeping Desc").value4("ACTIVE"));

        duo.alterSequence(Sequences.POINT_TYPE_CATEGORY_POINT_TYPE_CATEGORY_NUMBER_SEQ).restart().execute();
        duo.batchInsert(categoryRecords).execute();


        // Now we have to create the PointTypes (Made breakfast, Gave massage, Walked the dog, etc).
        List<PointTypeRecord> typeRecords = new ArrayList<>();
        Random r = new Random();

        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Kiss good morning").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Brought breakfast in bed").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Made breakfast").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Gave massage").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Cleaned the room").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Put the toilet seat down").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Walked the dog").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Feed the dog").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Feed the pet").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Gave ride to work").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Picked up from work").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Out for Ice Cream").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Showed up as a surprise").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Met family").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Took extended family out for food").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Went along with extended family out for food").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Went to religious activity").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Went on a trip").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Made warm drink").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Made cool drink").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Made cocktail").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Brought a drink").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 11).value3((short) r.nextInt(1000)).value4("Let partner drive").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 1).value3((short) r.nextInt(1000)).value4("Took partner out for Breakfast").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 1).value3((short) r.nextInt(1000)).value4("Took partner out for Lunch").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 1).value3((short) r.nextInt(1000)).value4("Took partner out for Dinner").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 1).value3((short) r.nextInt(1000)).value4("Romantic Night out").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 2).value3((short) r.nextInt(1000)).value4("Gave Flowers").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 2).value3((short) r.nextInt(1000)).value4("Gave Chocolates").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 2).value3((short) r.nextInt(1000)).value4("Gave Candy").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 2).value3((short) r.nextInt(1000)).value4("Gave Jewellery").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 2).value3((short) r.nextInt(1000)).value4("Gave Wine").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 3).value3((short) r.nextInt(1000)).value4("Called just to talk").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 3).value3((short) r.nextInt(1000)).value4("Asked about partners day").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 3).value3((short) r.nextInt(1000)).value4("Listened to partner talk about their day").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 3).value3((short) r.nextInt(1000)).value4("Told partner about their day").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 3).value3((short) r.nextInt(1000)).value4("Had interesting discussion").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 4).value3((short) r.nextInt(1000)).value4("Was there for partner").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 4).value3((short) r.nextInt(1000)).value4("Followed partners suggestion").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 4).value3((short) r.nextInt(1000)).value4("Was encouraging").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 4).value3((short) r.nextInt(1000)).value4("Let partner take the lead").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 4).value3((short) r.nextInt(1000)).value4("Included partner").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 4).value3((short) r.nextInt(1000)).value4("Bragged about partner").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 4).value3((short) r.nextInt(1000)).value4("Showed partner off").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 4).value3((short) r.nextInt(1000)).value4("Cheered partner on").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 5).value3((short) r.nextInt(1000)).value4("Comforted partner").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 5).value3((short) r.nextInt(1000)).value4("Stood up for partner").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 5).value3((short) r.nextInt(1000)).value4("Made partner feel safe").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 5).value3((short) r.nextInt(1000)).value4("Made partner feel special").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 5).value3((short) r.nextInt(1000)).value4("Made partner feel important").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 5).value3((short) r.nextInt(1000)).value4("Made partner feel loved").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 5).value3((short) r.nextInt(1000)).value4("Made partner feel supported").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Took partner to a concert").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Took partner to a show").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Took partner to a comedy show").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Read partner a book").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Helped partner studying").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Quizzed partner").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Took partner to a gallery").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Took partner to a museum").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Took partner to a park").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 6).value3((short) r.nextInt(1000)).value4("Went on a picnic").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 7).value3((short) r.nextInt(1000)).value4("Made pancakes").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 7).value3((short) r.nextInt(1000)).value4("Made waffles").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 7).value3((short) r.nextInt(1000)).value4("Made cookies").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 7).value3((short) r.nextInt(1000)).value4("Did house chores").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 7).value3((short) r.nextInt(1000)).value4("Repairs").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 8).value3((short) r.nextInt(1000)).value4("Did the laundry").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 8).value3((short) r.nextInt(1000)).value4("Washed the dishes").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 8).value3((short) r.nextInt(1000)).value4("Set the table").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 8).value3((short) r.nextInt(1000)).value4("Cleared the dishes").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 8).value3((short) r.nextInt(1000)).value4("Went grocery shopping").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 8).value3((short) r.nextInt(1000)).value4("Prepared kids lunch").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 8).value3((short) r.nextInt(1000)).value4("Took the kids to school").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 8).value3((short) r.nextInt(1000)).value4("Cleaned pet area").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 9).value3((short) r.nextInt(1000)).value4("Did maintenance on the car").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 9).value3((short) r.nextInt(1000)).value4("Did repairs on the car").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 10).value3((short) r.nextInt(1000)).value4("Organized party").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 10).value3((short) r.nextInt(1000)).value4("Organized event").value5("Desc").value6("ACTIVE"));
        typeRecords.add(new PointTypeRecord().value2((short) 10).value3((short) r.nextInt(1000)).value4("Organized birthday").value5("Desc").value6("ACTIVE"));

        duo.alterSequence(Sequences.POINT_TYPE_POINT_TYPE_NUMBER_SEQ).restart().execute();
        duo.batchInsert(typeRecords).execute();


        // Now we need to add the Point Event Emotions
        List<PointEventEmotionRecord> eventEmotions = new ArrayList<>();

        eventEmotions.add(new PointEventEmotionRecord().value2("Happy").value3("Happy Desc").value4("ACTIVE"));
        eventEmotions.add(new PointEventEmotionRecord().value2("Funny").value3("Funny Desc").value4("ACTIVE"));
        eventEmotions.add(new PointEventEmotionRecord().value2("Thankful").value3("Thankful Desc").value4("ACTIVE"));
        eventEmotions.add(new PointEventEmotionRecord().value2("Excited").value3("Excited Desc").value4("ACTIVE"));
        eventEmotions.add(new PointEventEmotionRecord().value2("Confident").value3("Confident Desc").value4("ACTIVE"));
        eventEmotions.add(new PointEventEmotionRecord().value2("Relaxed").value3("Relaxed Desc").value4("ACTIVE"));
        eventEmotions.add(new PointEventEmotionRecord().value2("Comfortable").value3("Comfortable Desc").value4("ACTIVE"));

        duo.alterSequence(Sequences.POINT_EVENT_EMOTION_POINT_EVENT_EMOTION_NUMBER_SEQ).restart().execute();
        duo.batchInsert(eventEmotions).execute();
    }
}
