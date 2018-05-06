package com.microwise.common.util;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Wang yunlong
 * @time 12-12-14 下午2:46
 */
public class BeanMapsTest {
    @Test
    public void testProcessList() {
        List<Wang> wangs = new ArrayList<Wang>();
        Wang wang1 = new Wang(1, "wind");
        Wang wang2 = new Wang(2, "cloud");
        wangs.add(wang1);
        wangs.add(wang2);
        List<Map<String, Object>> listMap = BeanMaps.toMap(wangs, new String[]{"id", "name"});
        Assert.assertEquals(listMap.size(), 2);
        Assert.assertEquals(listMap.get(0).get("id"), 1);
        Assert.assertEquals(listMap.get(0).get("name"), "wind");
        Assert.assertEquals(listMap.get(1).get("id"), 2);
        Assert.assertEquals(listMap.get(1).get("name"), "cloud");

    }

    @Test
    public void testProcess() {
        Wang wang = new Wang(1, "wind");
        Map<String, Object> map = BeanMaps.toMap(wang, new String[]{"id", "name"});
        Assert.assertEquals(map.get("id"), 1);
        Assert.assertEquals(map.get("name"), "wind");
    }

    @Test
    public void testNameIsNotPropertyToList() {
        List<Wang> wangs = new ArrayList<Wang>();
        Wang wang1 = new Wang(1, "wind");
        Wang wang2 = new Wang(2, "cloud");
        wangs.add(wang1);
        wangs.add(wang2);
        List<Map<String, Object>> listMap = BeanMaps.toMap(wangs, new String[]{"id", "name"}, new String[]{"newId", "newName"});
        Assert.assertEquals(listMap.size(), 2);
        Assert.assertEquals(listMap.get(0).get("newId"), 1);
        Assert.assertEquals(listMap.get(0).get("newName"), "wind");
        Assert.assertEquals(listMap.get(1).get("newId"), 2);
        Assert.assertEquals(listMap.get(1).get("newName"), "cloud");
    }

    @Test
    public void testNameIsNotProperty() {
        Wang wang = new Wang(1, "wind");
        Map<String, Object> map = BeanMaps.toMap(wang, new String[]{"id", "name"}, new String[]{"newId", "newName"});
        Assert.assertEquals(map.get("newId"), 1);
        Assert.assertEquals(map.get("newName"), "wind");
    }

    @Test
    public void testAllPropertiesToMapList() {
        List<Wang> wangs = new ArrayList<Wang>();
        Wang wang1 = new Wang(1, "wind");
        Wang wang2 = new Wang(2, "cloud");
        wangs.add(wang1);
        wangs.add(wang2);
        List<Map<String, Object>> listMap = BeanMaps.toMap(wangs);
        Assert.assertEquals(listMap.size(), 2);
        Assert.assertEquals(listMap.get(0).get("id"), 1);
        Assert.assertEquals(listMap.get(0).get("name"), "wind");
        Assert.assertEquals(listMap.get(1).get("id"), 2);
        Assert.assertEquals(listMap.get(1).get("name"), "cloud");
    }

    @Test
    public void testAllPropertiesToMap() {
        Wang wang = new Wang(1, "wind");
        Map<String, Object> map = BeanMaps.toMap(wang);
        Assert.assertEquals(map.get("id"), 1);
        Assert.assertEquals(map.get("name"), "wind");
    }

    static class Wang {
        private int id;
        private String name;

        public Wang(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
