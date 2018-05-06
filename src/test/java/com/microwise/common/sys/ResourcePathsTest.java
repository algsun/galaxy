package com.microwise.common.sys;

import com.microwise.blackhole.sys.ResourcePaths;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author liuzhu
 * @date 13-9-24
 */
public class ResourcePathsTest {
    @Test
    public void test() {
        File f = new File("abc", "1234");
        File f2 = new File("abc", "/1234");
        File f3 = new File("abc",File.separator +  "1234");
        Assert.assertEquals(f.toString(), f2.toString());
        Assert.assertEquals(f3.toString(), f2.toString());
    }

    @Test
    public void testGetPath(){
        String path = ResourcePaths.galaxyResourcesDir("path");
        Assert.assertEquals(0, new File("galaxy-resources/path").compareTo(new File(path)));
    }

}
