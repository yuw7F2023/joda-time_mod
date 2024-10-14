/*
 *  Copyright 2001-2005 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.time;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.joda.time.chrono.ISOChronology;
/**
 * This class is a Junit unit test for the
 * org.joda.time.DateTimeComparator class.
 *
 * @author Guy Allard
 */
public class TestDateTimeComparator extends TestCase {

    private static final Chronology ISO = ISOChronology.getInstance();
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestDateTimeComparator.class);
    }

    public TestDateTimeComparator(String name) {
        super(name);
    }

    /**
     * A reference to a DateTime object.
     */
    DateTime aDateTime = null;
    /**
     * A reference to a DateTime object.
     */
    DateTime bDateTime = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for millis of seconds.
     */
    Comparator cMillis = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for seconds.
     */
    Comparator cSecond = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for minutes.
     */
    Comparator cMinute = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for hours.
     */
    Comparator cHour = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for day of the week.
     */
    Comparator cDayOfWeek = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for day of the month.
     */
    Comparator cDayOfMonth = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for day of the year.
     */
    Comparator cDayOfYear = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for week of the weekyear.
     */
    Comparator cWeekOfWeekyear = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for year given a week of the year.
     */
    Comparator cWeekyear = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for months.
     */
    Comparator cMonth = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for year.
     */
    Comparator cYear = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for the date portion of an
     * object.
     */
    Comparator cDate = null;
    /**
     * A reference to a DateTimeComparator object
     * (a Comparator) for the time portion of an
     * object.
     */
    Comparator cTime = null;
    /**
     * Junit <code>setUp()</code> method.
     */
    @Override
    public void setUp() /* throws Exception */ {
        Chronology chrono = ISOChronology.getInstanceUTC();

        // super.setUp();
        // Obtain comparator's
        cMillis = DateTimeComparator.getInstance(null, DateTimeFieldType.secondOfMinute());
        cSecond = DateTimeComparator.getInstance(DateTimeFieldType.secondOfMinute(), DateTimeFieldType.minuteOfHour());
        cMinute = DateTimeComparator.getInstance(DateTimeFieldType.minuteOfHour(), DateTimeFieldType.hourOfDay());
        cHour = DateTimeComparator.getInstance(DateTimeFieldType.hourOfDay(), DateTimeFieldType.dayOfYear());
        cDayOfWeek = DateTimeComparator.getInstance(DateTimeFieldType.dayOfWeek(), DateTimeFieldType.weekOfWeekyear());
        cDayOfMonth = DateTimeComparator.getInstance(DateTimeFieldType.dayOfMonth(), DateTimeFieldType.monthOfYear());
        cDayOfYear = DateTimeComparator.getInstance(DateTimeFieldType.dayOfYear(), DateTimeFieldType.year());
        cWeekOfWeekyear = DateTimeComparator.getInstance(DateTimeFieldType.weekOfWeekyear(), DateTimeFieldType.weekyear());
        cWeekyear = DateTimeComparator.getInstance(DateTimeFieldType.weekyear());
        cMonth = DateTimeComparator.getInstance(DateTimeFieldType.monthOfYear(), DateTimeFieldType.year());
        cYear = DateTimeComparator.getInstance(DateTimeFieldType.year());
        cDate = DateTimeComparator.getDateOnlyInstance();
        cTime = DateTimeComparator.getTimeOnlyInstance();
    }

    /**
     * Junit <code>tearDown()</code> method.
     */
    @Override
    protected void tearDown() /* throws Exception */ {
        // super.tearDown();
        aDateTime = null;
        bDateTime = null;
        //
        cMillis = null;
        cSecond = null;
        cMinute = null;
        cHour = null;
        cDayOfWeek = null;
        cDayOfMonth = null;
        cDayOfYear = null;
        cWeekOfWeekyear = null;
        cWeekyear = null;
        cMonth = null;
        cYear = null;
        cDate = null;
        cTime = null;
    }

    //-----------------------------------------------------------------------
    public void testClass() {
        assertEquals(true, Modifier.isPublic(DateTimeComparator.class.getModifiers()));
        assertEquals(false, Modifier.isFinal(DateTimeComparator.class.getModifiers()));
        assertEquals(1, DateTimeComparator.class.getDeclaredConstructors().length);
        assertEquals(true, Modifier.isProtected(DateTimeComparator.class.getDeclaredConstructors()[0].getModifiers()));
    }
    
    //-----------------------------------------------------------------------
    public void testStaticGetInstance() {
        DateTimeComparator c = DateTimeComparator.getInstance();
        assertEquals(null, c.getLowerLimit());
        assertEquals(null, c.getUpperLimit());
        assertEquals("DateTimeComparator[]", c.toString());
    }        
    public void testStaticGetDateOnlyInstance() {
        DateTimeComparator c = DateTimeComparator.getDateOnlyInstance();
        assertEquals(DateTimeFieldType.dayOfYear(), c.getLowerLimit());
        assertEquals(null, c.getUpperLimit());
        assertEquals("DateTimeComparator[dayOfYear-]", c.toString());
        
        assertSame(DateTimeComparator.getDateOnlyInstance(), DateTimeComparator.getDateOnlyInstance());
    }
    public void testStaticGetTimeOnlyInstance() {
        DateTimeComparator c = DateTimeComparator.getTimeOnlyInstance();
        assertEquals(null, c.getLowerLimit());
        assertEquals(DateTimeFieldType.dayOfYear(), c.getUpperLimit());
        assertEquals("DateTimeComparator[-dayOfYear]", c.toString());
        
        assertSame(DateTimeComparator.getTimeOnlyInstance(), DateTimeComparator.getTimeOnlyInstance());
    }
    
    public void testNullNowCheckedOnce() {
        // checks a race condition against the system clock, issue #404
        for (int i = 0; i < 10000; i++) {
            if (DateTimeComparator.getInstance().compare(null, null) != 0) {
                fail("Comparing (null, null) should always return 0");
            }
        }
    }
    
    //-----------------------------------------------------------------------
    public void testEqualsHashCode() {
        DateTimeComparator c1 = DateTimeComparator.getInstance();
        assertEquals(false, c1.equals(null));
        assertEquals(true, c1.hashCode() == c1.hashCode());
        
        DateTimeComparator c2 = DateTimeComparator.getTimeOnlyInstance();
        assertEquals(false, c2.equals(c1));
        assertEquals(false, c1.equals(c2));
        assertEquals(false, c2.equals(null));
        assertEquals(false, c1.hashCode() == c2.hashCode());
        
        DateTimeComparator c3 = DateTimeComparator.getTimeOnlyInstance();
        assertEquals(false, c3.equals(c1));
        assertEquals(false, c1.equals(c3));
        assertEquals(false, c1.hashCode() == c3.hashCode());
        assertEquals(true, c2.hashCode() == c3.hashCode());
        
        DateTimeComparator c4 = DateTimeComparator.getDateOnlyInstance();
        assertEquals(false, c4.hashCode() == c3.hashCode());
    }
    
    //-----------------------------------------------------------------------
    public void testSerialization1() throws Exception {
        DateTimeField f = ISO.dayOfYear();
        f.toString();
        DateTimeComparator c = DateTimeComparator.getInstance(DateTimeFieldType.hourOfDay(), DateTimeFieldType.dayOfYear());
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(c);
        oos.close();
        byte[] bytes = baos.toByteArray();
        
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        DateTimeComparator result = (DateTimeComparator) ois.readObject();
        ois.close();
        
        assertEquals(c, result);
    }

    //-----------------------------------------------------------------------
    /**
     * Test all basic comparator operation with DateTime objects.
     */

    /**
     * Test all basic comparator operation with Calendar objects.
     */
    public void testBasicComps5() {
        Calendar aDateTime
            = Calendar.getInstance();   // right now
        Calendar bDateTime = aDateTime;
        assertEquals( "MILLIS", 0, cMillis.compare( aDateTime, bDateTime ) );
        assertEquals( "SECOND", 0, cSecond.compare( aDateTime, bDateTime ) );
        assertEquals( "MINUTE", 0, cMinute.compare( aDateTime, bDateTime ) );
        assertEquals( "HOUR", 0, cHour.compare( aDateTime, bDateTime ) );
        assertEquals( "DOW", 0, cDayOfWeek.compare( aDateTime, bDateTime ) );
        assertEquals( "DOM", 0, cDayOfMonth.compare( aDateTime, bDateTime ) );
        assertEquals( "DOY", 0, cDayOfYear.compare( aDateTime, bDateTime ) );
        assertEquals( "WOW", 0, cWeekOfWeekyear.compare( aDateTime, bDateTime ) );
        assertEquals( "WY", 0, cWeekyear.compare( aDateTime, bDateTime ) );
        assertEquals( "MONTH", 0, cMonth.compare( aDateTime, bDateTime ) );
        assertEquals( "YEAR", 0, cYear.compare( aDateTime, bDateTime ) );
        assertEquals( "DATE", 0, cDate.compare( aDateTime, bDateTime ) );
        assertEquals( "TIME", 0, cTime.compare( aDateTime, bDateTime ) );
    }   // end of testBasicComps

     /**
      * Test sorting with full default comparator.
      */
     public void testListBasic() {
        String[] dtStrs = {
            "1999-02-01T00:00:00",
            "1998-01-20T00:00:00"
        };
        //
        List sl = loadAList( dtStrs );
        boolean isSorted1 = isListSorted( sl );
        Collections.sort( sl );
        boolean isSorted2 = isListSorted( sl );
        assertEquals("ListBasic", !isSorted1, isSorted2);
     } // end of testListBasic


     /**
      * Test sorting with year (given week) comparator.
      */
    public void testListYOYY() {
        // ?? How to catch end conditions ??
        String[] dtStrs = {
            "2010-04-01T10:00:00",
            "2002-01-01T10:00:00"
        };
        //
        List sl = loadAList( dtStrs );
        boolean isSorted1 = isListSorted( sl );
        Collections.sort( sl, cWeekyear );
        boolean isSorted2 = isListSorted( sl );
        assertEquals("ListYOYY", !isSorted1, isSorted2);
    } // end of testListYOYY

     /**
      * Test sorting with year comparator.
      */
     public void testListYear() {
        String[] dtStrs = {
            "1999-02-01T00:00:00",
            "1998-02-01T00:00:00",
            "2525-02-01T00:00:00",
            "1776-02-01T00:00:00",
            "1863-02-01T00:00:00",
            "1066-02-01T00:00:00",
            "2100-02-01T00:00:00"
        };
        //
        List sl = loadAList( dtStrs );
        boolean isSorted1 = isListSorted( sl );
        Collections.sort( sl, cYear );
        boolean isSorted2 = isListSorted( sl );
        assertEquals("ListYear", !isSorted1, isSorted2);
     } // end of testListYear

     /**
      * Test sorting with date only comparator.
      */
    public void testListDate() {
        String[] dtStrs = {
            "1999-02-01T00:00:00",
            "1998-10-03T00:00:00",
            "2525-05-20T00:00:00",
            "1776-12-25T00:00:00",
            "1863-01-31T00:00:00",
            "1066-09-22T00:00:00",
            "2100-07-04T00:00:00"
        };
        //
        List sl = loadAList( dtStrs );
        boolean isSorted1 = isListSorted( sl );
        Collections.sort( sl, cDate );
        boolean isSorted2 = isListSorted( sl );
        assertEquals("ListDate", !isSorted1, isSorted2);
    } // end of testListDate


    /**
     * Test comparator operation with an invalid object type.
     */
    public void testInvalidObj() {
        aDateTime = getADate("2000-01-01T00:00:00");
        try {
            cYear.compare("FreeBird", aDateTime);
            fail("Invalid object failed");
        } catch (IllegalArgumentException cce) {}
    }

    // private convenience methods
    //-----------------------------------------------------------------------
    /**
     * Creates a date to test with.
     */
    private DateTime getADate(String s) {
        DateTime retDT = null;
        try {
            retDT = new DateTime(s, DateTimeZone.UTC);
        } catch (IllegalArgumentException pe) {
            pe.printStackTrace();
        }
        return retDT;
    }

    /**
     * Load a string array.
     */
    private List loadAList(String[] someStrs) {
        List newList = new ArrayList();
        try {
            for (int i = 0; i < someStrs.length; ++i) {
                newList.add(new DateTime(someStrs[i], DateTimeZone.UTC));
            } // end of the for
        } catch (IllegalArgumentException pe) {
            pe.printStackTrace();
        }
        return newList;
    }

    /**
     * Check if the list is sorted.
     */
    private boolean isListSorted(List tl) {
        // tl must be populated with DateTime objects.
        DateTime lhDT = (DateTime)tl.get(0);
        DateTime rhDT = null;
        Long lhVal = new Long( lhDT.getMillis() );
        Long rhVal = null;
        for (int i = 1; i < tl.size(); ++i) {
            rhDT = (DateTime)tl.get(i);
            rhVal = new Long( rhDT.getMillis() );
            if ( lhVal.compareTo( rhVal) > 0 ) return false;
            //
            lhVal = rhVal;  // swap for next iteration
            lhDT = rhDT;    // swap for next iteration
        }
        return true;
    }

}
