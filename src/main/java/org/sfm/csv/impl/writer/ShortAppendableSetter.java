package org.sfm.csv.impl.writer;

import org.sfm.reflect.primitive.ShortSetter;

public class ShortAppendableSetter implements ShortSetter<Appendable> {

    private final CellWriter cellWriter;

    public ShortAppendableSetter(CellWriter cellWriter) {
        this.cellWriter = cellWriter;
    }

    @Override
    public void setShort(Appendable target, short value) throws Exception {
        cellWriter.writeValue(Short.toString(value), target);
    }
}