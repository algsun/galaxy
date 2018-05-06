package com.microwise.common.sys.freemarker;

import com.microwise.common.sys.freemarker.tools.UILocaleBundleTools;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

/**
 * @author gaohui
 * @date 13-5-16 09:51
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UILocaleBundleToolsTest {
    @Test
    public void testAppLocale() {
        String locale = UILocaleBundleTools.appLocale();
        Assert.assertNotNull(locale);

        Assert.assertEquals("shangbo", locale);
    }

    @Test
    public void testGetString() {
        String logo = UILocaleBundleTools.ResourceBundles.getStr("custom/ui", "app.logo", null);

        Assert.assertNotNull(logo);
        Assert.assertEquals("galaxy-logo.png", logo);

        String notExists = UILocaleBundleTools.ResourceBundles.getStr("custom/ui", "iAmNotExists", null);
        Assert.assertNull(notExists);
    }

    @Test
    public void testGetStringWithLocale() {
        String logo = UILocaleBundleTools.ResourceBundles.getStr("custom/ui", "app.logo", "shangbo");

        Assert.assertNotNull(logo);
        Assert.assertEquals("shangbo-logo-white.png", logo);

        String notExists = UILocaleBundleTools.ResourceBundles.getStr("custom/ui", "iAmNotExists", "shangbo");
        Assert.assertNull(notExists);
    }

    @Test
    public void testGetStrAuto() {
        String logo = UILocaleBundleTools.ResourceBundles.getStrAuto("custom/ui", "app.logo");

        Assert.assertNotNull(logo);
        Assert.assertEquals("shangbo-logo-white.png", logo);
    }

    @Test
    public void testGetBool() {
        boolean bool = UILocaleBundleTools.ResourceBundles.getBool("custom/ui", "boolean", null);
        Assert.assertTrue(bool);

        bool = UILocaleBundleTools.ResourceBundles.getBool("custom/ui", "boolean", "shangbo");
        Assert.assertFalse(bool);

        bool = UILocaleBundleTools.ResourceBundles.getBoolAuto("custom/ui", "boolean");
        Assert.assertFalse(bool);
    }

    @Test
    public void testGetInt() {
        int i = UILocaleBundleTools.ResourceBundles.getInt("custom/ui", "int", null);
        Assert.assertEquals(i, 5201314);

        i = UILocaleBundleTools.ResourceBundles.getIntAuto("custom/ui", "int");
        Assert.assertEquals(i, 20130516);
    }

    @Test
    public void testGetStrs() {
        List<String> strings = UILocaleBundleTools.ResourceBundles.getStrs("custom/ui", "strings", null);

        Assert.assertNotNull(strings);
        Assert.assertEquals(3, strings.size());
        Assert.assertEquals("a", strings.get(0));
        Assert.assertEquals("b", strings.get(1));
        Assert.assertEquals("c", strings.get(2));


        strings = UILocaleBundleTools.ResourceBundles.getStrsAuto("custom/ui", "strings");

        Assert.assertNotNull(strings);
        Assert.assertEquals(3, strings.size());
        Assert.assertEquals("a", strings.get(0));
        Assert.assertEquals("b", strings.get(1));
        Assert.assertEquals("c", strings.get(2));

        strings = UILocaleBundleTools.ResourceBundles.getStrsAuto("custom/ui", "iAmNotExists");
        Assert.assertEquals(0, strings.size());
    }
}
