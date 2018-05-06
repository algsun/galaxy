package com.microwise.common.sys.test;

import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author gaohui
 * @date 14-1-7 11:16
 */
public class YamlTest {
    @Test
    public void testLoad() throws IOException {
        Yaml yaml = new Yaml();

        InputStream input = Resources.getResource("cleanDB.yml").openStream();
        Map<String, Object> result = (Map<String, Object>) yaml.load(input);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get("driver"));

        if (input != null) {
            input.close();
        }
    }
}
