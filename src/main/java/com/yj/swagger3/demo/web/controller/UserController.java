package com.yj.swagger3.demo.web.controller;

import com.yj.swagger3.demo.web.model.User;
import com.yj.swagger3.demo.web.model.UserVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 用户控制器
 * @author DUCHONG
 * @since 2020-10-24 16:18
 **/
@Slf4j
@Api(tags = "用户控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    static List<User> list= new ArrayList<>();
    static Long maxLimit=20L ;
    static {

        for (long i = 0; i < maxLimit; i++) {
            User u=new User()
                    .setId(i)
                    .setUserName(UUID.randomUUID().toString())
                    .setAddress("address-"+i)
                    .setPhone("1001011-"+i);
            list.add(u);

        }
    }

    /**
     * 获取用户信息列表
     * @return
     */
    @ApiOperation(value = "获取用户列表",notes = "获取所有用户信息",tags ={"获取用户列表 tags"},response = List.class)
    @GetMapping("/getUserList")
    public List<User> getUserList(){

        return list;
    }

    /**
     * 根据用户id和address获取用户信息
     * @param userId
     * @param address
     * @return
     */
    @ApiOperation(value = "获取用户信息接口",notes = "获取所有用户信息-单个入参",tags ={"获取用户信息接口 tags"},response = User.class)
    @GetMapping("/getUser")
    public User getUser(@ApiParam(name = "userId",value = "用户id",required = true) Long userId,
                        @ApiParam(name = "address",value = "用户id",required = true) String address){

       return  list.stream().filter(u->u.getId().equals(userId) && u.getAddress().equals(address))
                     .filter(Objects::nonNull)
                     .findFirst()
                     .orElse(new User())
                ;
    }

    /**
     * 根据用户id和address获取用户信息
     * @param userVO
     * @return
     */
    @ApiOperation(value = "获取用户信息接口2",notes = "获取用户信息接口2-实体对象入参",tags ={"获取用户信息接口2 tags"},response = User.class)
    @GetMapping("/getUser2")
    public User getUser2(@RequestBody UserVO userVO){

        return  list.stream().filter(u->u.getId().equals(userVO.getId())
                                    && u.getAddress().equals(userVO.getAddress()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(new User())
                ;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @ApiOperation(value = "删除用户接口",notes = "删除用户接口-入参userId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
    })
    @PostMapping("/deleteUser")
    public String deleteUser(Long userId){

        list.stream().filter(u->u.getId().equals(userId))
                    .filter(Objects::isNull)
                    .findFirst()
                    .ifPresent(u1->list.removeIf(Objects::isNull))
        ;

        return "success";
    }
}
