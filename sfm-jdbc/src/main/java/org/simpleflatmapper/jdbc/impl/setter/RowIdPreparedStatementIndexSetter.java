package org.simpleflatmapper.jdbc.impl.setter;

import java.sql.PreparedStatement;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Types;

public class RowIdPreparedStatementIndexSetter implements PreparedStatementIndexSetter<RowId> {
    @Override
    public void set(PreparedStatement target, RowId value, int columnIndex) throws SQLException {
        if (value == null) {
            target.setNull(columnIndex, Types.ROWID);
        } else {
            target.setRowId(columnIndex, value);
        }
    }
}
