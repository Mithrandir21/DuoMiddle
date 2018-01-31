package com.duopoints.models.composites;

import com.duopoints.db.tables.pojos.PointType;
import com.duopoints.db.tables.pojos.PointTypeCategory;

public class CompositePointType extends PointType {

    private PointTypeCategory pointTypeCategory;

    public CompositePointType(PointType value, PointTypeCategory pointTypeCategory) {
        super(value);
        this.pointTypeCategory = pointTypeCategory;
    }

    public PointTypeCategory getPointTypeCategory() {
        return pointTypeCategory;
    }

    public void setPointTypeCategory(PointTypeCategory pointTypeCategory) {
        this.pointTypeCategory = pointTypeCategory;
    }
}
