package com.microwise.blackhole.service;

import com.microwise.blackhole.bean.Post;
import com.microwise.common.sys.test.CleanDBTest;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author gaohui
 * @date 13-5-9 14:15
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostServiceTest extends CleanDBTest {

    @Autowired
    private PostService postService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("common/dbxml/blackhole/PostServiceTest.xml");
    }

    @Test
    public void testFindCount() {
        int count = postService.findCount(-1);
        Assert.assertEquals(4, count);

        count = postService.findCount(Post.SCOPE_PUBLIC);
        Assert.assertEquals(2, count);

        count = postService.findCount(Post.SCOPE_INTERNAL);
        Assert.assertEquals(2, count);
    }

    @Test
    public void testFindLatest() {
        List<Post> posts = postService.findLatest(-1, 100);

        Assert.assertEquals(4, posts.size());
        Assert.assertTrue(posts.get(0).getCreateDate().after(posts.get(1).getCreateDate()));
    }

    @Test
    public void testFindLatestByType() {
        List<Post> posts = postService.findLatest(Post.SCOPE_PUBLIC, 100);

        Assert.assertEquals(2, posts.size());
        Assert.assertTrue(posts.get(0).getCreateDate().after(posts.get(1).getCreateDate()));
        for (Post post : posts) {
            Assert.assertEquals(Post.SCOPE_PUBLIC, post.getScope());
        }
    }

    @Test
    public void testFindLatestPaging() {
        List<Post> posts = postService.findLatest(1, 1, 1);

        Assert.assertEquals(1, posts.size());
    }
}
