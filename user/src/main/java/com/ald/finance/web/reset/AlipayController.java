package com.ald.finance.web.reset;

/**
 * Created by zhangliang on 2018/5/19.
 */


import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.StudentAppointment;
import com.ald.finance.dal.entity.StudentBuyRecord;
import com.ald.finance.dal.entity.User;
import com.ald.finance.service.StudentAppointmentService;
import com.ald.finance.service.StudentBuyRecordService;
import com.ald.finance.service.UserService;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import net.guerlab.sdk.alipay.controller.AlipayAbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/pay/alipay")
public class AlipayController extends AlipayAbstractController {


    @Value("${sdk.alipay.app-id}")
    private String appId;

    @Value("${sdk.alipay.private-key}")
    private String privateKey;

    @Value("${sdk.alipay.alipay-public-key}")
    private String alipayPublicKey;

    @Autowired
    StudentBuyRecordService studentBuyRecordService;

    @Autowired
    StudentAppointmentService studentAppointmentService;
    @Autowired
    UserService userService;

    /**
     * 支付请求
     */
    @GetMapping("/app/{orderType}/{orderId}")
    public ModelAndView app(@PathVariable String orderType, @PathVariable Long orderId, ModelAndView modelAndView) throws Exception {
        //付款金额，必填
        String total_amount = "";
        String subject = "";
        Long teacherId = null;
        if ("buy".equals(orderType)) {
            StudentBuyRecord record = studentBuyRecordService.findOneById(orderId);
            if (record == null) {
                throw new BusinessException("参数错误");
            }
            total_amount = record.getPackagePrice() + "";
            teacherId = record.getTeacherId();
            subject = "外教之家课程购买--%s--1/" + record.getPackageType() + "人套餐(" + record.getPackageIndex() + "*" + record.getPackageTimes() + ")";
        } else {
            StudentAppointment record = studentAppointmentService.findOneById(orderId);
            if (record == null) {
                throw new BusinessException("参数错误");
            }
            total_amount = record.getPrice() + "";
            teacherId = record.getTeacherId();
            subject = "外教之家课程预订--%s";
        }

        User teacher = userService.findOne(teacherId);
        if (teacher == null) {
            throw new BusinessException("参数错误");
        }
        subject = String.format(subject, teacher.getUserNickname());

        //设置阿里支付接口
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://www.ybhxjy.cn/pay/alipay/callback/" + orderType + "/" + orderId);
        alipayRequest.setNotifyUrl("http://www.ybhxjy.cn/pay/alipay/notify/" + orderType + "/" + orderId);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = UUID.randomUUID().toString();

        //商品描述，可空
        String body = "";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        modelAndView.addObject("body", result);
        modelAndView.setViewName("alipay");
        return modelAndView;
    }

    @RequestMapping("/callback/{orderType}/{orderId}")
    public String return1(@PathVariable String orderType, @PathVariable Long orderId, HttpServletRequest request) {
        save(orderType, orderId, request);
        return "redirect:" + "/myClass.html";
    }

    @RequestMapping("/notify/{orderType}/{orderId}")
    public String notify(@PathVariable String orderType, @PathVariable Long orderId, HttpServletRequest request) {
        if (save(orderType, orderId, request)) {
            return "success";
        }
        return "error";
    }

    private boolean save(String orderType, Long orderId, HttpServletRequest request) {
        //验签失败
        if (!notify0(request.getParameterMap())) {
            return false;
        }
        if (orderType.equals("buy")) {
            studentBuyRecordService.paySuccess(orderId);
        } else {
            studentAppointmentService.paySuccess(orderId);
        }
        return true;
    }
}
