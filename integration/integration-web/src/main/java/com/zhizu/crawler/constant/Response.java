package com.zhizu.crawler.constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Response {
    /**
     * 请求成功
     */
    public static final String ERROR_CODE_SUCCESS = "00000";
    /**
     * 没有session
     */
    public static final String NO_SESSION = "10001";
    /**
     * 手机号码不合法（可能不支持）
     */
    public static final String MOBILE_ERR_CODE_ILLEGAL_PHONE_NUMER = "10002";
    /**
     * 获取验证码图片失败
     */
    public static final String MOBILE_ERR_CODE_UNNVAILABLE_CAPTCHA_CODE = "10003";
    /**
     * 验证码错误
     */
    public static final String MOBILE_ERR_CODE_WRONG_CAPTCHA_CODE = "10004";
    /**
     * 用户名或者密码错误
     */
    public static final String MOBILE_ERR_CODE_WRONG_USERNAME_OR_PASSWROD = "10005";
    /**
     * 发送短信失败
     */
    public static final String MOBILE_ERR_CODE_SEND_SMS = "10006";
    /**
     * 短信验证码错误
     */
    public static final String MOBILE_ERR_CODE_WRONG_SMS_CODE = "10007";

    /**
     * 获取手机账单失败
     */
    public static final String MOBILE_ERR_CODE_LOAD_BILLS = "10008";

    /**
     * 短信验证码为空
     */
    public static final String MOBILE_ERR_CODE_NULL_SMS_CODE = "10009";
    /**
     * 需要网站密码
     */
    public static final String MOBILE_NEED_WEBSITE_PASSWORD = "10020";
    /**
     * 需要网站密码和服务密码
     */
    public static final String MOBILE_NEED_WEBSITE_AND_SERVICE_PASSWORD = "10021";
    /**
     * 需要刷新验证码
     * 
     * @author zhuyuhang
     */
    public static final String MOBILE_REFRESH_CAPTCHA_CODE = "10022";
    /**
     * 密码过于简单或为原始密码，请修改密码后登录
     * 
     * @author zhuyuhang
     */
    public static final String MOBILE_PASSWORD_TOO_SIMPLE = "10023";
    /**
     * 淘宝短信验验证登陆失败或者淘宝登陆改版
     */
    public static final String TAOBAO_SECOND_LOGIN_CODE = "10010";
    /**
     * 淘宝最新二次验证 (没有手机号 填充 phone)
     */
    public static final String ERROR_NEW_TAOBAO_CODE = "10011";
    /**
     * 发送验证码频繁
     */
    public static final String ERROR_SEND_SMS_CODE_OFTEN = "10012";
    /**
     * 发送验证码超时
     */
    public static final String ERROR_SEND_SMS_CODE_TIMEOUT = "10013";
    /**
     * 登陆支付宝失败
     */
    public static final String ERROR_LOGIN_ALIPAY_CODE = "10014";
    /**
     * 登陆淘宝失败
     */
    public static final String ERROR_LOGIN_TAOBAO_CODE = "10015";

    /**
     * 不支持用户名 即邮箱登陆
     */
    public static final String UN_SUPPORTED_CONTENT = "10016";
    /**
     * 淘宝没有绑定手机 异地登陆
     */
    public static final String UN_BINDING_PHONE = "10017";
    /**
     * 您的账户存在安全问题，已被保护，请用电脑登录淘宝网www.taobao.com自助开通
     */
    public static final String TAOBAO_SECURITY_CODE = "10018";
    /**
     * 登陆支付宝失败_之_你的支付宝账户存在安全风险。 请修改支付宝登录密码和支付密码
     */
    public static final String ERROR_LOGIN_ALIPAY_SECURITY_CODE = "10019";
    /**
     * 支付宝cvs下载失败
     */
    public static final String ERROR_LOGIN_ALIPAY_CVS_CODE = "44441";
    /**
     * 征信报告用户名或者密码错误
     */
    public static final String CREDIT_REPORT_ERR_WRONG_USERNAME_OR_PASSWORD = "20001";
    /**
     * 征信报告验证码错误
     */
    public static final String CREDIT_REPORT_ERR_WRONG_CAPTCHA_CODE = "20002";
    /**
     * 征信报告查询码错误/提交身份验证码时提示:身份验证码不正确。
     */
    public static final String CREDIT_REPORT_ERR_WRONG_TRACECODE = "20003";
    /**
     * 获取征信报告失败 。信用卡和机构查询记录明细缺失
     */
    public static final String CREDIT_REPORT_ERR_WRONG = "20004";

    /**
     * 目前系统尚未收录您的个人信息，无法进行注册。
     */
    public static final String CREDIT_REPORT_ERR_WRONG_REG = "20005";

    /**
     * 动态码发送失败，请稍后重试。
     */
    public static final String CREDIT_REPORT_ERR_WRONG_ACVITAVECODE = "20006";
    /**
     * 登录名已经存在
     */
    public static final String CREDIT_REPORT_ERR_WRONG_LOGINNAME = "20007";
    /**
     * 注册失败
     */
    public static final String CREDIT_REPORT_ERR_WRONG_SAVEUSER = "20008";
    /**
     * 登录成功，用户已经生成个人征信报告
     */
    public static final String CREDIT_REPORT_SUC_CREDIT_YES = "20009";
    /**
     * 获取申请问题集成功
     */
    public static final String CREDIT_REPORT_SUC_CREDIT_NO = "20010";
    /**
     * 获取申请问题集失败
     */
    public static final String CREDIT_REPORT_ERR_CREDIT_NO = "20011";
    /**
     * 登录成功，用户个人征信报告申请处理中
     */
    public static final String CREDIT_REPORT_SUC_CREDIT_DOING = "20012";

    /**
     * 您无法使用该功能找回登录名，可能是因为您的安全等级为低、未注册或已销户，请重新注册。 登录时为已销户
     */
    public static final String CREDIT_REPORT_ERR_WRONG_FINDLOGINNAME = "20013";
    /**
     * 提交的重置密码申请正在审核中，请耐心等待。
     */
    public static final String CREDIT_REPORT_ERR_CHECKLOGINNAME_NO = "20014";
    /**
     * 您已注册过用户，请返回首页直接登录。
     */
    public static final String CREDIT_REPORT_ERR_HAVEUSER = "20015";
    /**
     * 此手机号码已注册。
     */
    public static final String CREDIT_REPORT_ERR_WRONG_REPHONE = "20016";
    /**
     * 密码连续输入错误。系统已对您的登录名进行锁定，请10分钟后在登录。
     */
    public static final String CREDIT_REPORT_ERR_WRONG_PASSWORD_TOOMUCH = "20017";
    /**
     * 征信报告手机验证码错误/过期
     */
    public static final String CREDIT_REPORT_ERR_WRONG_WRONG_SMS_CODE = "20018";
    /**
     * 页面过期
     */
    public static final String CREDIT_REPORT_ERR_WRONG_PAGEINVAILD_REAWRPASSWORD = "20019";
    /**
     * 目前系统尚未收录足够的信息对您的身份进行“问题验证”。
     */
    public static final String CREDIT_REPORT_ERR_WRONG_NOMESSAGE_VALIDATE = "20020";
    /**
     * 您已经注册过其他用户，请使用做过身份认证的用户重新登录
     */
    public static final String CREDIT_REPORT_ERR_WRONG_USER_ORDERUSER = "20021";

    /**
     * 三项标识不匹配
     */
    public static final String CREDIT_REPORT_ERR_WRONG_THREE_IDENTIFICATION = "20022";

    /**
     * 登录名不存在
     */
    public static final String CREDIT_REPORT_ERR_WRONG_NOLOGINNAME = "20023";
    /**
     * 邮箱不支持
     */
    public static final String EMAIL_ERR_UNSUPPORTED = "30001";
    /**
     * 用户名密码错误，请确认开启imap或者pop3
     */
    public static final String EMAIL_ERR_USERNAME_PASSWORD = "30002";
    /**
     * 没有开通pop3协议
     */
    public static final String EMAIL_ERR_NO_POP3 = "30005";
    /**
     * 打开收件箱错误
     */
    public static final String EMAIL_ERR_OPEN_INBOX = "30003";
    public static final String EMAIL_ERR_FETCH_MAIL_CONUT = "30004";
    /**
     * 网银登陆其他错误
     */
    public static final String BANK_ERR_LOGIN_OTHER = "40001";
    /**
     * 网银账户信息或流水下载失败
     */
    public static final String BANK_ERR_DOWNLOAD = "40002";
    /**
     * 密码错误次数已超限
     */
    public static final String BANK_ERR_WRONG_PASSWROD_MAX = "40003";
    /**
     * 该客户只能以用户名登录
     */
    public static final String BANK_ERR_WRONG_ONLY_USERNAME = "40004";
    /**
     * 密码过于简单,请先通过电话银行进行密码修改
     */
    public static final String BANK_ERR_WRONG_SIMPLE_PASSWORD = "40005";
    /**
     * 您的登录密码已连续输错3次，密码将被锁定2小时
     */
    public static final String BANK_ERR_WRONG_LOCK_PASSWORD = "40006";
    /**
     * 登录成功，进入手机动态验证码页面。
     */
    public static final String BANK_HAVE_PHONE_MSG = "40007";
    /**
     * 手机动态密码已过期，请重新获取
     */
    public static final String BANK_ERR_WRONG_PHONE_MSG_OVERDUE = "40008";
    /**
     * 用户名或登录密码有误，连续输错3次，密码将锁定2小时。
     */
    public static final String BANK_ERR_WRONG_PRELOCK_PASSWORD = "40009";
    /**
     * 登录账号或密码错误，如果密码连续输错3次，系统将锁定当日操作权限，如果密码连续输错10次，请到柜台解锁!
     */
    public static final String BANK_ERR_WRONG_PRELOCK2_PASSWORD = "40010";

    /**
     * 您的网银今日登录密码输入错误次数过多，为了保障您的资金安全，您的网银已被暂时锁定，请您等待明日自动解锁，或前往柜台进行解锁。
     */
    public static final String BANK_ERR_WRONG_PRELOCK3_PASSWORD = "40011";

    /**
     * 用户名或登录密码有误，密码连续输错3次，客户将会冻结。
     */
    public static final String BANK_ERR_WRONG_PRELOCK4_PASSWORD = "40012";
    /**
     * 密码输入错误超3次，客户已冻结
     */
    public static final String BANK_ERR_WRONG_PRELOCK5_PASSWORD = "40013";

    /**
     * 该用户为新注册用户，请登录网银进行“登录设置”后重试；或使用其他用户登录
     */
    public static final String BANK_ERR_WRONG_NEW_USER = "40014";
    /**
     * 提示：您尚未设置网上银行登录密码，请登录网银进行“登录设置”后重试；或使用其他用户登录。
     */
    public static final String BANK_ERR_WRONG_NO_SET_PASSWORD = "40015";

    /**
     * 请登录网银进行身份认证后，再重试。
     */
    public static final String BANK_ERR_ATTESTATION = "40016";
    /**
     * 您输入的卡号与实际不符，请重新登录。
     */
    public static final String BANK_ERR_WRONG_NO_MATCH_ACCOUNTNO = "40017";

    /**
     * 暂不支持该银行，请更换其他银行重试。
     */
    public static final String BANK_ERR_NO_SUPPORT = "40018";
    /**
     * 您是自助注册客户，无此交易的权限，请到我行网点进行正式注册。
     */
    public static final String BANK_ERR_SELF_REGISTERED_CUSTOMER = "40019";
    /**
     * 您的密码过于简单，请登录网银重新设置登录密码。
     */
    public static final String BANK_ERR_NEED_MODIFY_PASSWORD = "40020";
    /**
     * 您输入的卡（账）号尚未注册我行网银或您的网银已注销
     */
    public static final String BANK_ERR_NO_REGISTER = "40021";
    /**
     * 暂不支持令牌动态密码登录，请更换其他银行重试。
     */
    public static final String BANK_WRONG_NO_U_shield_ORDER = "40022";
    /**
     * 只签约大众版的老网银客户，首次登录新网银时请输入已追加网银的卡号和对应的查询密码进行登录。
     * 既签约大众版又签约贵宾版/商户版的老网银客户，首次登录新网银时请输入贵宾版/商户版的登录密码进行登录。
     * 新网银客户，请输入在线注册或柜台签约时设置的登录密码进行登录。
     */
    // 民生银行特有状态码
    // public static final String BANK_WRONG_NUMBER_TYPE_DIFFENT = "40023";

    /**
     * 信用卡无近6个月账单
     */
    // public static final String CREDIT_ERR_WRONG_NO_BILL = "50001";

    /**
     * 系统级别的error
     */
    public static final String ERROR_CODE_SYSTEM = "90000";
    /**
     * 未知错误
     */
    public static final String ERROR_CODE_UNKNOWN = "90001";
    /**
     * 接口参数错误
     */
    public static final String ERR_CODE_INTER_PRAM = "90002";
    /**
     * 网络超时
     */
    public static final String ERR_CODE_CONN_TIMEOUT = "90003";

    /**
     * 查询没有该记录
     */
    public static final String SELECT_NO_RECORD = "90004";

    /**
     * 保存失败
     */
    public static final String ERR_CODE_SAVE = "90005";

    /**
     * 需要取消登录保护，仅用于移动统一接口
     */
    public static final String CANCEL_LOGIN_PROTECTION = "90006";

    /**
     * 移动统一接口验证短信密码
     */
    public static final String MOBILE_SYSTEM_BUSY = "90007";

    /**
     * 服务方式受限 ，请前往营业厅办理
     */
    public static final String MOBILE_SYSTEM_LIMIT = "90008";

    /**
     * 当天验证密码错误次数已达上限，请明天再试
     */
    public static final String MOBILE_LOGIN_TOMORROW = "90009";

    /**
     * 下载不成功的标志
     */
    public static final String IS_NOT_SUCC = "0";

    /**
     * 请求过程中的一些临时状态
     */
    public static final String LOGIN_RESULT_STATUS_1 = "L0001";// 登录成功
    public static final String LOGIN_RESULT_STATUS_2 = "L0002";// 下载账户信息成功
    public static final String LOGIN_RESULT_STATUS_3 = "L0003";// 下载交易流水成功

    /**
     * 请求成功
     */
    public static final Response SUCCESS = new Response(true, ERROR_CODE_SUCCESS);
    // 增加CREDIT_ERR_WRONG_LOCK_BILL
    public static final Response DEFAULT = new Response(false, NO_SESSION),
            MOBILE_ILLEGAL_PHONE_NUMER = new Response(false, MOBILE_ERR_CODE_ILLEGAL_PHONE_NUMER),
            MOBILE_UNNVAILABLE_CAPTCHA_CODE = new Response(false, MOBILE_ERR_CODE_UNNVAILABLE_CAPTCHA_CODE),
            MOBILE_WRONG_CAPTCHA_CODE = new Response(false, MOBILE_ERR_CODE_WRONG_CAPTCHA_CODE),
            MOBILE_WRONG_USERNAME_OR_PASSWROD = new Response(false, MOBILE_ERR_CODE_WRONG_USERNAME_OR_PASSWROD),
            MOBILE_WRONG_SEND_SMS = new Response(false, MOBILE_ERR_CODE_SEND_SMS),
            MOBILE_WRONG_SMS_CODE = new Response(false, MOBILE_ERR_CODE_WRONG_SMS_CODE),

            MOBILE_WRONG_LOAD_BILLS = new Response(false, MOBILE_ERR_CODE_LOAD_BILLS),
            CREDIT_REPORT_WRONG_USERNAME_OR_PASSWORD = new Response(false, CREDIT_REPORT_ERR_WRONG_USERNAME_OR_PASSWORD),
            // CREDIT_REPORT_WRONG_CANCELLATION_TOOMUCH = new Response(false,
            // CREDIT_REPORT_ERR_WRONG_CANCELLATION_TOOMUCH),
            CREDIT_REPORT_WRONG_PASSWORD_TOOMUCH = new Response(false, CREDIT_REPORT_ERR_WRONG_PASSWORD_TOOMUCH),
            CREDIT_REPORT_WRONG_CAPTCHA_CODE = new Response(false, CREDIT_REPORT_ERR_WRONG_CAPTCHA_CODE),
            CREDIT_REPORT_WRONG_TRACECODE = new Response(false, CREDIT_REPORT_ERR_WRONG_TRACECODE),
            CREDIT_REPORT_WRONG_NOMESSAGE_VALIDATE = new Response(false, CREDIT_REPORT_ERR_WRONG_NOMESSAGE_VALIDATE),
            CREDIT_REPORT_WRONG_PAGEINVAILD_REAWRPASSWORD = new Response(false,
                    CREDIT_REPORT_ERR_WRONG_PAGEINVAILD_REAWRPASSWORD), CREDIT_REPORT_WRONG = new Response(false,
                    CREDIT_REPORT_ERR_WRONG), CREDIT_REPORT_WRONG_USER_ORDERUSER = new Response(false,
                    CREDIT_REPORT_ERR_WRONG_USER_ORDERUSER), CREDIT_REPORT_WRONG_THREE_IDENTIFICATION = new Response(
                    false, CREDIT_REPORT_ERR_WRONG_THREE_IDENTIFICATION),
            CREDIT_REPORT_WRONG_NOLOGINNAME = new Response(false, CREDIT_REPORT_ERR_WRONG_NOLOGINNAME),
            CREDIT_REPORT_WRONG_REG = new Response(false, CREDIT_REPORT_ERR_WRONG_REG),
            CREDIT_REPORT_WRONG_ACVITAVECODE = new Response(false, CREDIT_REPORT_ERR_WRONG_ACVITAVECODE),
            CREDIT_REPORT_WRONG_LOGINNAME = new Response(false, CREDIT_REPORT_ERR_WRONG_LOGINNAME),
            CREDIT_REPORT_WRONG_SAVEUSER = new Response(false, CREDIT_REPORT_ERR_WRONG_SAVEUSER),
            CREDIT_REPORT_WRONG_REPHONE = new Response(false, CREDIT_REPORT_ERR_WRONG_REPHONE),
            CREDIT_REPORT_WRONG_FINDLOGINNAME = new Response(false, CREDIT_REPORT_ERR_WRONG_FINDLOGINNAME),
            CREDIT_REPORT_WRONG_CHECKLOGINNAME_NO = new Response(false, CREDIT_REPORT_ERR_CHECKLOGINNAME_NO),
            CREDIT_REPORT_WRONG_HAVEUSER = new Response(false, CREDIT_REPORT_ERR_HAVEUSER),
            EMAIL_UNSUPPORTED = new Response(false, EMAIL_ERR_UNSUPPORTED),
            CREDIT_REPORT_WRONG_SMS_CODE = new Response(false, CREDIT_REPORT_ERR_WRONG_WRONG_SMS_CODE),
            EMAIL_USERNAME_PASSWORD = new Response(false, EMAIL_ERR_USERNAME_PASSWORD),
            EMAIL_USER_NO_POP = new Response(false, EMAIL_ERR_NO_POP3), BANK_WRONG_PASSWROD_MAX = new Response(false,
                    BANK_ERR_WRONG_PASSWROD_MAX), BANK_WRONG_ONLY_USERNAME = new Response(false,
                    BANK_ERR_WRONG_ONLY_USERNAME), BANK_WRONG_SIMPLE_PASSWORD = new Response(false,
                    BANK_ERR_WRONG_SIMPLE_PASSWORD), BANK_WRONG_LOCK_PASSWORD = new Response(false,
                    BANK_ERR_WRONG_LOCK_PASSWORD), BANK_WRONG_PRELOCK_PASSWORD = new Response(false,
                    BANK_ERR_WRONG_PRELOCK_PASSWORD), BANK_WRONG_PRELOCK2_PASSWORD = new Response(false,
                    BANK_ERR_WRONG_PRELOCK2_PASSWORD), BANK_WRONG_PRELOCK3_PASSWORD = new Response(false,
                    BANK_ERR_WRONG_PRELOCK3_PASSWORD), BANK_WRONG_PRELOCK4_PASSWORD = new Response(false,
                    BANK_ERR_WRONG_PRELOCK4_PASSWORD), BANK_WRONG_PRELOCK5_PASSWORD = new Response(false,
                    BANK_ERR_WRONG_PRELOCK5_PASSWORD), BANK_WRONG_PHONE_MSG_OVERDUE = new Response(false,
                    BANK_ERR_WRONG_PHONE_MSG_OVERDUE), BANK_WRONG_LOGIN_OTHER = new Response(false,
                    BANK_ERR_LOGIN_OTHER), BANK_WRONG_DOWNLOAD = new Response(false, BANK_ERR_DOWNLOAD),
            SYSTEM_WRONG_UNKNOWN = new Response(false, ERROR_CODE_UNKNOWN), BANK_WRONG_NEW_USER = new Response(false,
                    BANK_ERR_WRONG_NEW_USER), BANK_WRONG_NO_SET_PASSWORD = new Response(false,
                    BANK_ERR_WRONG_NO_SET_PASSWORD),
            BANK_WRONG_ATTESTATION = new Response(false, BANK_ERR_ATTESTATION),
            BANK_WRONG_NO_MATCH_ACCOUNTNO = new Response(false, BANK_ERR_WRONG_NO_MATCH_ACCOUNTNO),
            BANK_WRONG_NO_SUPPORT = new Response(false, BANK_ERR_NO_SUPPORT), LOGIN_RESULT_STATUS_1_S = new Response(
                    true, LOGIN_RESULT_STATUS_1), LOGIN_RESULT_STATUS_2_S = new Response(true, LOGIN_RESULT_STATUS_2),
            LOGIN_RESULT_STATUS_3_S = new Response(true, LOGIN_RESULT_STATUS_3),
            BANK_WRONG_SELF_REGISTERED_CUSTOMER = new Response(false, BANK_ERR_SELF_REGISTERED_CUSTOMER),
            BANK_WRONG_NEED_MODIFY_PASSWORD = new Response(false, BANK_ERR_NEED_MODIFY_PASSWORD),
            LOGIN_PROTECTION = new Response(false, CANCEL_LOGIN_PROTECTION), SYSTEM_BUSY = new Response(false,
                    MOBILE_SYSTEM_BUSY), SYSTEM_LIMIT = new Response(false, MOBILE_SYSTEM_LIMIT),
            LOGIN_TOMORROW = new Response(false, MOBILE_LOGIN_TOMORROW), BANK_WRONG_NO_REGISTER = new Response(false,
                    BANK_ERR_NO_REGISTER), BANK_WRONG_NO_U_shield = new Response(false, BANK_WRONG_NO_U_shield_ORDER);
    // ,BANK_WRONG_NUMBER_TYPE = new Response(false,
    // BANK_WRONG_NUMBER_TYPE_DIFFENT);

    /**
     * 请求失败 系统错误
     */
    public static final Response SYSTEM_ERROR = new Response(false, ERROR_CODE_SYSTEM);
    /**
     * 响应状态
     * 
     * @author zhuyuhang
     */
    private boolean success = false;
    /**
     * 错误码
     * 
     * @author zhuyuhang
     */
    private String errorCode = "";
    /**
     * 结果
     * 
     * @author zhuyuhang
     */
    private Object result;

    public Response() {

    }

    public Response(Object result) {
        this(true, ERROR_CODE_SUCCESS, result);
    }

    public Response(boolean success, String errorCode) {
        this(success, errorCode, null);
    }

    public Response(boolean success, String errorCode, Object result) {
        this.success = success;
        this.errorCode = errorCode;
        this.result = result;
    }

    public static Response getErrorResponse(String errorCode) {
        return new Response(false, errorCode);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, sfs);
    }

    private static final SerializerFeature sfs[] = new SerializerFeature[] { SerializerFeature.WriteMapNullValue };

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(Response.MOBILE_WRONG_USERNAME_OR_PASSWROD));
    }
}
