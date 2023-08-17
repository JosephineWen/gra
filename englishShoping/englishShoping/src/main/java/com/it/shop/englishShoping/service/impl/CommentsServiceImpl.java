package com.it.shop.englishShoping.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.it.shop.englishShoping.domain.*;
import com.it.shop.englishShoping.mapper.CommentsDao;
import com.it.shop.englishShoping.mapper.KeyWordsMapper;
import com.it.shop.englishShoping.mapper.ProductDao;
import com.it.shop.englishShoping.mapper.UserDao;
import com.it.shop.englishShoping.service.CommentsService;
import com.it.shop.englishShoping.utils.WordFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class CommentsServiceImpl implements CommentsService {

    List<Keywords> list = new ArrayList<>();
    @Autowired
    private KeyWordsMapper keyWordsMapper;


    @Autowired
    private ProductDao productDao;

    @Autowired
    private CommentsDao commentsDao ;

    @Autowired
    private UserDao userDao;


    @Override
    public List<Comments> findByAll(Comments comments) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        //单个
        if (StringUtils.isNotEmpty(comments.getId())) {
            queryWrapper.eq("id",comments.getId());
        }

        //是否被屏蔽
        if (StringUtils.isNotEmpty(comments.getSort2())){
            queryWrapper.eq("sort",comments.getSort2());
        }

        //单个用户
        if (StringUtils.isNotEmpty(comments.getUserId())){
            queryWrapper.eq("user_id",comments.getUserId());
        }

        //单个商品
        if (StringUtils.isNotEmpty(comments.getProductId())){
            queryWrapper.eq("product_id",comments.getProductId());
        }
//        queryWrapper.ne("status",2);
        //店铺查询全部评价
//        if (StringUtils.isNotEmpty(comments.getShopId())){
//           QueryWrapper queryWrapper1 = new QueryWrapper<>();
//           queryWrapper1.eq("pinglinid",comments.getShopId());
//            List<Product> products = productDao.selectList(queryWrapper1);
//            List<String> list1 = new ArrayList<>();
//            for (Product product : products){
//                list1.add(product.getId());
//            }
//            queryWrapper.in("product_id",list1);
//        }
        List<Comments> list1 = commentsDao.selectList(queryWrapper);
        List<Comments> arr = new ArrayList<>();
        for (Comments comments1 : list1){
            System.out.println("--------comm"+comments1);
            if (StringUtils.isEmpty(comments1.getParent())){
                QueryWrapper queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("parent",comments1.getId());
                Comments comments2 = commentsDao.selectOne(queryWrapper1);
                if (null != comments2){
                    CommDao commDao = new CommDao();
                    commDao.setId(comments2.getId());
                    commDao.setSort(comments2.getSort());
                    commDao.setText(comments2.getText());
                    commDao.setUserId(comments2.getUserId());
                    commDao.setParent(comments.getParent());
                    commDao.setTime(comments2.getTime());
                    comments1.setList(comments2);
                }
                String userId = comments1.getUserId();
                User user = userDao.selectById(userId);
                comments1.setUserName(user.getName());
                arr.add(comments1);
            }else {
                arr = list1;
                if (StringUtils.isNotEmpty(comments.getSort2())){

                }

            }
        }
    if (arr.size()==0){
        return list1;
    }

        return arr;
    }

    @Override
    public List<Comments> findByAllTwo(Comments comments) {
        System.out.println(comments);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        List<Comments> list1 = commentsDao.selectList(queryWrapper);
        List<Comments> arr = new ArrayList<>();
        for (Comments comments1 : list1){
            String productId = comments1.getProductId();
            Product product = productDao.selectById(productId);
            if (StringUtils.isNotEmpty(comments.getShop())){
                if (!product.getPinglinid().equals(comments.getShop())){
                    continue;
                }
            }
            String userId = comments1.getUserId();
            User user = userDao.selectById(userId);
            comments1.setUserName(user.getUsername());
            arr.add(comments1);

        }
        return arr;
    }

    @Override
    public String deleteById(String id) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent",id);
        List<Comments> list1 = commentsDao.selectList(queryWrapper);
        for (Comments comments : list1){
            commentsDao.deleteById(comments.getId());
        }
        return  commentsDao.deleteById(id)>0?"删除成功":"删除失败";
    }

    @Override
    public String add(Comments comments) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        Comments comments1 = comments;
        if (StringUtils.isNotEmpty(comments1.getText())){
            String oldText = comments1.getText();
            String newText = intoText(oldText);
            comments1.setText(newText);
            if (oldText.equals(newText)){
                comments1.setSort(0);
            }else{
                comments1.setSort(1);
            }
            if (StringUtils.isNotEmpty(comments1.getParent())){
                UpdateWrapper updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",comments1.getParent());
                updateWrapper.set("status",2);
                commentsDao.update(new Comments(),updateWrapper);
            }
        }else{
            return "请输入正确的评价";
        }
        comments1.setId(UUID.randomUUID().toString());
        comments1.setTime(formattedDate);
        return commentsDao.insert(comments1)>0?"评价成功":"评价失败";
    }




    @Override
    public String updateByStatus(Comments Comments) {
        return null;
    }



    /**
     * 评论检索关键词
     * @param text
     * @return
     */
    @Override
    public String intoText(String text) {
        WordFilter wordFilter = new WordFilter(findByAll(1));
        return wordFilter.filterWords(text);
    }

    @Override
    public String deleteByIdTwo(String id) {
        return commentsDao.deleteById(id)>0?"删除成功":"删除失败";
    }

    @Override
    public List<Comments> findByAllThree(Comments comments) {
//        QueryWrapper queryWrapper = new QueryWrapper<>();
//        if (StringUtils.isNotEmpty(comments.getShopId())){
//            QueryWrapper queryWrapper1 = new QueryWrapper<>();
//            queryWrapper1.eq("pinglinid",comments.getShopId());
//            List<Product> products = productDao.selectList(queryWrapper1);
//            QueryWrapper queryWrapper2 = new QueryWrapper<>();
//            List<String> list1 = new ArrayList<>();
//            if (products.size()==0){
//                return  null;
//            }
//            for (Product product:products){
//              list1.add(product.getId());
//            }
//            queryWrapper2.in("product_id",list1);
//            queryWrapper2.ne("status",2);
//            List list2 = commentsDao.selectList(queryWrapper2);
//            return list2;
//
//        }
        return commentsDao.findByListss(comments.getShopId());
    }

    @Override
    public List<Comments> selectByProduct(Comments comments) {
        List<Comments> list = new ArrayList<>();
        List<Comments> comments1 = commentsDao.seletByPrid(comments.getProductId());
        for (Comments comments2 : comments1){
            String id = comments2.getId();
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent",id);
            Comments comments3 = commentsDao.selectOne(queryWrapper);
            if (null!=comments3){
                String userId = comments3.getUserId();
                QueryWrapper queryWrapper1 =new QueryWrapper<>();
                queryWrapper1.eq("id",userId);
                User user = userDao.selectOne(queryWrapper1);
                comments3.setUserName(user.getUsername());
                comments2.setList(comments3);
            }
            list.add(comments2);


        }
        return list;
    }

    public List<Keywords> findByAll(Integer id) {
        if (list.size()==0){
            list =   keyWordsMapper.findByAll();
        }
        return list;
    }
}
