package com.cd.o2o.web.shopadmin;

import com.cd.o2o.entity.Area;
import com.cd.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 获取区域列表，并返回到前端
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody   //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中，可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> list(){
        Map<String, Object> map = new HashMap<String, Object>();

        List<Area> list = areaService.getAreaList();
        map.put("lists",list);
        map.put("total",list.size());

        return map;
    }

}
