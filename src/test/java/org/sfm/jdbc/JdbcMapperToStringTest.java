package org.sfm.jdbc;


import org.junit.Test;
import org.sfm.beans.DbObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcMapperToStringTest {

    @Test
    public void testStaticJdbcMapperNoAsm() {
        JdbcMapper<DbObject> mapper = JdbcMapperFactoryHelper.noAsm()
                .newBuilder(DbObject.class).addMapping("id").addMapping("name").mapper();

        assertTrue(mapper.toString().startsWith("StaticJdbcMapper{MapperImpl{instantiator=EmptyConstructorInstantiator"));
    }

    @Test
    public void testStaticJdbcMapperAsm() {
        JdbcMapper<DbObject> mapper = JdbcMapperFactoryHelper.asm()
                .newBuilder(DbObject.class).addMapping("id").addMapping("name").mapper();

        String input = mapper.toString();

        assertTrue(input.startsWith("StaticJdbcMapper{AsmMapperFromResultSetToDbObjectInj2_I"));
    }


    @Test
    public void testDynamicJdbcMapperNoAsm() throws SQLException {
        JdbcMapper<DbObject> mapper = JdbcMapperFactoryHelper.noAsm()
                .newMapper(DbObject.class);

        ResultSet rs = mock(ResultSet.class);
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(rs.getMetaData()).thenReturn(metaData);
        when(metaData.getColumnCount()).thenReturn(1);
        when(metaData.getColumnLabel(1)).thenReturn("id");

        mapper.iterator(rs);


        assertTrue(mapper.toString().startsWith("DynamicJdbcMapper{target=class org.sfm.beans.DbObject, "));
    }
}
