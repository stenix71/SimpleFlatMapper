package org.simpleflatmapper.core.reflect.asm;

import org.junit.Test;
import org.simpleflatmapper.core.reflect.Getter;
import org.simpleflatmapper.core.reflect.primitive.IntGetter;
import org.simpleflatmapper.test.beans.DbFinalObject;
import org.simpleflatmapper.test.beans.DbObject;

import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class InstantiatorKeyTest {

    @Test
    public void testEqualsOnSameKey() throws NoSuchMethodException {
        InstantiatorKey k = new InstantiatorKey(DbObject.class, Date.class);
        assertTrue(k.equals(k));
    }

    @Test
    public void testEqualsOnSameSourceAndTargetValues() throws NoSuchMethodException {
        InstantiatorKey k1 = new InstantiatorKey(DbObject.class, Date.class);
        InstantiatorKey k2 = new InstantiatorKey(DbObject.class, Date.class);
        assertTrue(k1.equals(k2));
    }

    @Test
    public void testEqualsOnSameSourceAndTargetAndInjectParamValues() throws NoSuchMethodException {
        final InjectedParam[] injectedParameters = createInjectedParameters("param", Getter.class);
        InstantiatorKey k1 = new InstantiatorKey(DbObject.class.getConstructor(), injectedParameters, Date.class);
        InstantiatorKey k2 = new InstantiatorKey(DbObject.class.getConstructor(), injectedParameters, Date.class);
        assertTrue(k1.equals(k2));
    }
    private InjectedParam[] createInjectedParameters(String param) {
        return createInjectedParameters(param, Getter.class);
    }
    private InjectedParam[] createInjectedParameters(String param, Class<?> getterClass) {
        return new InjectedParam[] {new InjectedParam(param, getterClass)};
    }

    @Test
    public void testNotEqualsOnDiffSourceAndSameTargetValues() throws NoSuchMethodException {
        InstantiatorKey k1 = new InstantiatorKey(DbObject.class, Date.class);
        InstantiatorKey k2 = new InstantiatorKey(DbObject.class, InputStream.class);
        InstantiatorKey k3 = new InstantiatorKey(DbObject.class, null);
        assertFalse(k1.equals(k2));
        assertFalse(k1.equals(k3));
        assertFalse(k3.equals(k1));
    }

    @Test
    public void testNotEqualsOnSameSourceAndTargetAndDiffInjectParamValues() throws NoSuchMethodException {
        InstantiatorKey k1 = new InstantiatorKey(DbObject.class.getConstructor(), createInjectedParameters("param"), Date.class);
        InstantiatorKey k2 = new InstantiatorKey(DbObject.class.getConstructor(), createInjectedParameters("param2"), Date.class);
        InstantiatorKey k3 = new InstantiatorKey(DbObject.class.getConstructor(), null, Date.class);
        InstantiatorKey k4 = new InstantiatorKey(DbObject.class.getConstructor(), createInjectedParameters("param", IntGetter.class), Date.class);
        assertFalse(k1.equals(k2));
        assertFalse(k1.equals(k3));
        assertFalse(k3.equals(k1));
        assertFalse(k4.equals(k1));
    }

    @Test
    public void testNotEqualsDiffConstructor() throws NoSuchMethodException {
        InstantiatorKey k1 = new InstantiatorKey(DbObject.class.getConstructor(), createInjectedParameters("param"), Date.class);
        InstantiatorKey k2 = new InstantiatorKey(DbFinalObject.class.getDeclaredConstructors()[0], createInjectedParameters("param"), Date.class);
        InstantiatorKey k3 = new InstantiatorKey(null, createInjectedParameters( "param"), Date.class);
        assertFalse(k1.equals(k2));
        assertFalse(k1.equals(k3));
        assertFalse(k3.equals(k1));
    }

    @Test
    public void testNotEqualsOnNull() throws NoSuchMethodException {
        InstantiatorKey k1 = new InstantiatorKey(DbObject.class, Date.class);
        assertFalse(k1.equals(null));
    }

    @Test
    public void testNotEqualsOnDiffClass() throws NoSuchMethodException {
        InstantiatorKey k1 = new InstantiatorKey(DbObject.class, Date.class);
        assertFalse(k1.equals(new Object()));
    }

}