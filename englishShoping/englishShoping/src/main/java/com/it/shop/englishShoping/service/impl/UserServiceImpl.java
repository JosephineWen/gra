package com.it.shop.englishShoping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.it.shop.englishShoping.domain.Keywords;
import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.Shop;
import com.it.shop.englishShoping.domain.User;
import com.it.shop.englishShoping.mapper.KeyWordsMapper;
import com.it.shop.englishShoping.mapper.ProductDao;
import com.it.shop.englishShoping.mapper.ShopDao;
import com.it.shop.englishShoping.mapper.UserDao;
import com.it.shop.englishShoping.service.UserService;
import com.it.shop.englishShoping.utils.MD5Utils;
import com.it.shop.englishShoping.utils.RandomCodeUtils;
import com.it.shop.englishShoping.utils.Result;
import com.it.shop.englishShoping.utils.WordFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Transactional
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ShopDao shopDao;

    /**
     * 用户注册
     * @param phone
     * @return
     */
    @Override
    public Result insertPhone(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        User user = userDao.selectOne(queryWrapper);
        if (null!=user){
            return Result.fail("The phone number is already registered");
        }
        String redisCode = stringRedisTemplate.opsForValue().get(phone);
        if (StringUtils.isNotEmpty(redisCode)){
            return Result.fail("It can only be initiated once per minute");
        }
        String code = RandomCodeUtils.generateCode();
        stringRedisTemplate.opsForValue().set(phone,code, 1, TimeUnit.MINUTES);
        System.out.printf("code"+stringRedisTemplate.opsForValue().get(phone));
        return Result.success(code);
    }

    @Override
    public Result verifyCode(String phone,String code) {
        String redisCode = stringRedisTemplate.opsForValue().get(phone);
        if (StringUtils.isEmpty(redisCode)){
            return Result.fail("Verification code expired");
        }
        if (! redisCode.equals(code)){
            return Result.fail("Verification code error");
        }
        User user = new User();
        user.setStatus(1);
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setPhone(phone);
        user.setUsername(UUID.randomUUID().toString());
        user.setImgurl("defult.jpg");
        userDao.insert(user);
        User byId = userDao.getById(id);
       if (StringUtils.isNotEmpty(byId.getCode())){
            if (byId.getCode().equals("2")){
                String id1 = byId.getId();
                QueryWrapper queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("userid",id1);
                Shop shop = shopDao.selectOne(queryWrapper2);
                byId.setShop( shop.getName());
            }
        }

        return Result.success(byId);
    }

    @Override
    public Result updateStatus(User user) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",user.getId());
        updateWrapper.set("status",user.getStatus());
        if (userDao.update(new User(),updateWrapper)>0){
            return Result.success();
        }
        return Result.fail();
    }



    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        // 从数据库中获取存储的加密后的密码
        String storedPassword = userDao.getPhoneByPassword(user.getPhone());
        if (StringUtils.isEmpty(storedPassword)){
            return Result.fail("The user does not exist!");
        }
        // 将用户输入的密码进行MD5加密
        String inputPassword = MD5Utils.encrypt(user.getPassword());
        // 比较加密后的密码
        if (storedPassword.equals(inputPassword)) {
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone",user.getPhone());
            queryWrapper.eq("password",inputPassword);
            User user1 = userDao.selectOne(queryWrapper);
            user1.setPassword("");
            if (user1.getCode().equals("2")){
                String id1 = user1.getId();
                QueryWrapper queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("userid",id1);
                Shop shop = shopDao.selectOne(queryWrapper2);
                user1.setPassword("");
                user1.setShop( shop.getName());
            }
            if (user1.getStatus()==1){
                user1.setPassword("");
                return Result.success(user1);
            }else{
                return Result.fail("User login prohibited");
            }
        } else {
            return Result.fail("Incorrect username or password");
        }
    }


    /**
     * 获取用户列表
     * @return
     */
    @Override
    public List<User> getListUser(Integer code) {
        List<User> listUser = userDao.getListUser(code);
        List<User> arr = new ArrayList<>();
        if (listUser.size()==0){
            listUser = new ArrayList<>();
        }
        for (User user : listUser){
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid",user.getId());
            Shop shop = shopDao.selectOne(queryWrapper);
            if (null != shop){
                user.setShop(shop.getName());
            }
            arr.add(user);
        }
        return arr;
    }


    /***
     * 获取单个用户
     * @param id
     * @return
     */
    @Override
    public User getById(String id) {
        User byId = userDao.getById(id);
        if (byId==null) {
            byId = new User();
        }
        if (byId.getCode().equals("2")){
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid",byId.getId());
            Shop shop = shopDao.selectOne(queryWrapper);
            byId.setShop(shop.getName());
            byId.setPassword("");
        }
        return byId;
    }

    /**
     * 更新用户头像
     * @param id
     * @param imgurl
     */
    @Override
    public void updateByIdToImgurl(String id, String imgurl) {
        userDao.updateByIdToImgurl(id,imgurl);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Override
    public boolean updateById(User user) {
        User user1 = user;
        String newPassword = user1.getPassword();
        User user2 = userDao.selectById(user.getId());
        String password1 = user2.getPassword();
        if (StringUtils.isNotEmpty(user1.getPassword())){
            if (!newPassword.equals(password1)){
                String encrypt = MD5Utils.encrypt(newPassword);
                user1.setPassword(encrypt);
            }
        }else {
            user1.setPassword(password1);
        }

        if (StringUtils.isEmpty(user.getShop())){
            return  userDao.updateById(user1)>0;
        }else{
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid",user.getId());
            Shop shop = shopDao.selectOne(queryWrapper);
            shop.setName(user.getShop());
            shopDao.updateById(shop);
            return  userDao.updateById(user)>0;
        }

    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public boolean add(User user) {
        User user1 = user;
        user1.setCode("1");
        user1.setStatus(1);
        String inputPassword = MD5Utils.encrypt(user.getPassword());
        user1.setPassword(inputPassword);
        String ids = UUID.randomUUID().toString();
        user1.setId(ids);
        user1.setUsername(user.getName());
        if (StringUtils.isNotEmpty(user.getShop())){
            user1.setCode("2");
            Shop shop = new Shop();
            shop.setName(user.getShop());
            shop.setId(UUID.randomUUID().toString());
            shop.setUserid(ids);
            shopDao.insert(shop);
        }
        return userDao.insert(user1)>0;
    }

    @Override
    public boolean addTwo(User user) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",user.getPhone());
        User user1 =  userDao.selectOne(queryWrapper);
        user1.setCode(user.getCode());
        String inputPassword = MD5Utils.encrypt(user.getPassword());
        user1.setPassword(inputPassword);
        user1.setUsername(user.getUsername());
        if (user.getCode().equals("2")){
            Shop shop = new Shop();
            shop.setName(user.getUsername());
            shop.setId(UUID.randomUUID().toString());
            shop.setUserid(user1.getId());
            shopDao.insert(shop);
        }
        return userDao.updateById(user1)>0;
    }


    /**
     * 删除用户
     * @param id
     */
    @Override
    public void deleteById(String id) {
        userDao.deleteById(id);
    }




    @Override
    public boolean updateByMoney(String id, Double money) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        System.out.println(id+"+"+money);
        updateWrapper.eq("id",id);
        updateWrapper.set("money",money);
        return userDao.update(new User(),updateWrapper)>0;
    }

}
