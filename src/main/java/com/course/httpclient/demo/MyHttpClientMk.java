package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyHttpClientMk {

    private String url;
    private ResourceBundle bundle;
    private String token;


    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("mk.url");
    }

    /*
     * @description 展业中心，登陆接口
     * @author BingLi
     * @version 1.0
     * @date 2019-12-10
    * */
    @Test
    public void testLogin() throws IOException {

        String uri = bundle.getString("mk.login");
        String testUrl = this.url+uri;

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(testUrl);

        JSONObject param = new JSONObject();
        param.put("app_key","test");
        param.put("data","%7B%22mobile%22%3A%2213333333333%22%2C%22password%22%3A%22MTMzMzMzMzMzMzM%3D%22%7D");
        param.put("format","json");
        param.put("name","gwy.base.login");
        param.put("sign","74BCD8ABBA1906779862B9CBE90CFBDE");
        param.put("timestamp","2019-12-10 13:32:00");
        param.put("version","");

        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        JSONObject resultJson = new JSONObject(result);
        String code = resultJson.getString("code");
        Assert.assertEquals("0",code);
        JSONObject data = resultJson.getJSONObject("data");
        this.token = data.getString("token");

    }

    /*
    *
    * @description 展业中心，精品海报，查询海报分类列表
    * @author BingLi
    * @version 1.0
    * @date 2019-12-10
    */

    @Test(dependsOnMethods = {"testLogin"})
    public void testGetPosterCategory() throws IOException{

        String uri = bundle.getString("mk.poster.category");
        String testUrl = this.url+uri;

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(testUrl);

        get.setHeader("content-type","application/json");
        get.setHeader("token",token);

        HttpResponse response = client.execute(get);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        JSONObject resultJson = new JSONObject(result);
        int code = resultJson.getInt("code");
        Assert.assertEquals(0,code);
        String msg = resultJson.getString("msg");
        Assert.assertEquals("成功",msg);


    }

    @Test(priority = 2)
    public void testX(){
        System.out.println("这是第1个测试case");
    }

    @Test(priority = 1)
    public void testY(){
        System.out.println("这是第2个测试case");
    }

    @Test(priority = 3)
    public void testZ(){
        System.out.println("这是第3个测试case");
    }

    @Test(dependsOnMethods = {"testLogin"})
    public void testAddPosterCategory() throws IOException{

        String uri = bundle.getString("mk.add.category");
        String testUrl = this.url+uri;

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(testUrl);

        JSONObject param = new JSONObject();
        param.put("name","测试分类24");

        post.setHeader("content-type","application/json");
        post.setHeader("token",token);
//        post.setHeader("Authorization",token);
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        JSONObject resultJson = new JSONObject(result);
        int code = resultJson.getInt("code");
        String msg = resultJson.getString("msg");
        Assert.assertEquals(0,code);
        Assert.assertEquals("成功",msg);

    }

}

