package com.gll.onlinelearning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.gll.onlinelearning.entity.Comment;
import com.gll.onlinelearning.entity.Post;
import com.gll.onlinelearning.entity.vo.AllCommentResultVO;
import com.gll.onlinelearning.entity.vo.AllLoveResultVO;
import com.gll.onlinelearning.entity.vo.PageOfPostResultVO;
import com.gll.onlinelearning.mapper.CommentMapper;
import com.gll.onlinelearning.mapper.LoveMapper;
import com.gll.onlinelearning.service.CommentService;
import com.gll.onlinelearning.service.LoveService;
import com.gll.onlinelearning.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class OnlinelearningApplicationTests {

    @Resource
    private PostService postService;

    @Resource
    private LoveService loveService;

    @Resource
    private LoveMapper loveMapper;

    @Resource
    private CommentService commentService;

    @Resource
    private CommentMapper commentMapper;

    @Test
    void generateCode() {
        //1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //2、全局配置
        GlobalConfig gc = new GlobalConfig();
        //最好指定绝对路径来生成代码文件
        gc.setOutputDir("D:\\...\\online-learning-backend" + "/src/main/java");

        gc.setAuthor("gll");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖

        //UserService
        gc.setServiceName("%sService");    //去掉Service接口的首字母I

        gc.setIdType(IdType.AUTO); //主键策略
        gc.setDateType(DateType.ONLY_DATE); //定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        //3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/online_learn?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        //4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("onlinelearning"); //模块名
        //包：com.gll.onlinelearning
        pc.setParent("com.gll");
        //包  com.gll.onlinelearning.controller
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        //5、策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude("love");

        strategy.setNaming(NamingStrategy.underline_to_camel); //数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); //lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);
        //6、执行
        mpg.execute();
    }

    /**
     * 测试字段值为 json 字符串的自动转换
     */
    @Test
    void testNewTransferJson() {
        Post post = new Post();
        post.setContent("啦啦啦22啦，我是买包的小11还价");
        post.setUid(4);
        post.setPictures(Arrays.asList("1", "2"));
        postService.save(post);
        System.out.println(post.getPictures());
    }

    /**
     * 测试逻辑删除
     */
    @Test
    void testLogicOp() {
        /*Love love = new Love();
        love.setPostId(2);
        love.setUid(3);
        loveService.save(love);
        boolean isOk = loveService.removeById(1);
        System.out.println(isOk ? "Yes" : "No");

        List<Love> list = loveService.list();
        //若字段值为 null，则不会添加到 json 中
        System.out.println(JSON.toJSONString(list));

        Love one = loveService.getById(1);
        System.out.println(one);*/

        Map<String, Object> map = loveService.changeLove(5, 4);
        System.out.println(map);
    }

    @Test
    void testUpdatePost() {
        Post post = new Post();
        post.setId(1);
        post.setContent("里斯");
        post.setPictures(Arrays.asList("cs", "cdsc", "cscscs"));
        boolean flag = postService.updatePost(post);
        System.out.println(flag ? "Yes" : "No");
        System.out.println(postService.getById(1));
    }

    @Test
    void testGetAllLoveInfo() {
        List<AllLoveResultVO> list = loveMapper.getLoveListByPostId(6);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    void testGetPageOfPost() {
        List<PageOfPostResultVO> list = postService.getPageOfPost(1, "");
        System.out.println(JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect));

        int total = postService.getCountOfPostByContent("");
        System.out.println(total);
    }

    @Test
    void testAddComment() {
        Comment comment = new Comment();
        comment.setUid(2);
        comment.setContent("v地方v地方v地方v的");
        comment.setParentId(1);
        comment.setLevel(2);
        comment.setPostId(5);
        comment.setTargetId(1); //对那个用户id进行回复
        //插入后那些不填充的字段，但是数据库已经设置默认字段了，返回的结果该字段还是为空
        System.out.println("插入前：" + comment);
        boolean isOk = commentService.save(comment);
        System.out.println(isOk ? "Yes" : "No");
        System.out.println("插入后：" + comment);
    }

    @Test
    void testGetAllComment() {
        List<AllCommentResultVO> list = commentMapper.getAllCommentByPostId(5);
        System.out.println(JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect)); //禁止循环引用
    }
}
