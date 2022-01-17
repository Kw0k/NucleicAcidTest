package fun.kwok.natserver.controller;


import fun.kwok.natserver.entity.ResultInfo;
import fun.kwok.natserver.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {
    @Autowired
    ResultService resultService;

    @ResponseBody
    @PostMapping("/search") //用户端核酸检测结果查询 不需要进行登录验证 也不需要微信内打开
    public ResultInfo searchResult(
            @RequestParam("idcardnum") String idcardnum,
            @RequestParam("tname") String tname){
        return new ResultInfo(true,null,"查询成功",resultService.getResultByTnameAndIdCardNum(idcardnum, tname));
    }


}
