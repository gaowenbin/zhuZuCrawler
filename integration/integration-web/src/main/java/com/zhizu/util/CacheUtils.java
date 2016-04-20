package com.zhizu.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

public class CacheUtils {
    private static final Logger logger = Logger.getLogger(CacheUtils.class);
    public static final CacheManager CACHE_MANAGER = new CacheManager(
            CacheUtils.class.getResourceAsStream("/ehcache.xml"));

    private static final Cache MOBILE_CACHE = CACHE_MANAGER.getCache("mobile");
    private static final Cache JD_CACHE = CACHE_MANAGER.getCache("jd");
    private static final Cache Chsi_CACHE = CACHE_MANAGER.getCache("chsi");
    private static final Cache QQ_CACHE = CACHE_MANAGER.getCache("qq");
    public static final Cache SERVICES_CACHE = CACHE_MANAGER.getCache("services");
    private static final Cache TAOBAO_CACHE = CACHE_MANAGER.getCache("taobao");
    private static final Cache YIHAODIAN_CACHE = CACHE_MANAGER.getCache("yihaodian");
    private static final Cache CREDITREPORT_CACHE = CACHE_MANAGER.getCache("creditReport");
    private static final Cache NETBANKCARD_CACHE = CACHE_MANAGER.getCache("netBankCard");
    private static final Cache SELENIUM_ERROR_CACHE = CACHE_MANAGER.getCache("selenium_error_cache");
    private static final Cache AMAZON_CACHE = CACHE_MANAGER.getCache("amazon");
    private static final Cache Job_CACHE = CACHE_MANAGER.getCache("job");

    // 不要再在这里写了，写到各自的controller或者其它地方去，这里只提供一个CACHE_MANAGER出去
    /**
     * 新增 httpunit
     */
    private static final Cache HTTPUNIT_ERROR_CACHE = CACHE_MANAGER.getCache("httpUnit_error_cache");

    /**
     * 注册的服务
     * 
     * @author zhuyuhang
     * @param key
     * @param serviceInstance
     */
    // public static void putService(String key, ServiceInstance<InstanceDetail>
    // serviceInstance) {
    // Object object = SERVICES_CACHE.get(key);
    // if (object == null) {
    // SERVICES_CACHE.put(new Element(key, serviceInstance));
    // }
    // }

    /**
     * 获取注册的服务
     * 
     * @author zhuyuhang
     * @param key
     * @return
     */
    // @SuppressWarnings("unchecked")
    // public static ServiceInstance<InstanceDetail> getService(String key) {
    // Element element = SERVICES_CACHE.get(key);
    // if (element != null) {
    // return (ServiceInstance<InstanceDetail>) element.getObjectValue();
    // }
    // return null;
    // }

    /**
     * 删除手机抓取器
     * 
     * @author zhuyuhang
     * @param key
     */
    public static void removeMoblieFetcher(String key) {
        MOBILE_CACHE.remove(key);
    }

    /*
     * ========================================以下Http淘宝==========================
     * ===========*
     */
    public static void putHttpTaoBaoFetcher(String key, Object object) {
        Element element = TAOBAO_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TAOBAO_CACHE.put(new Element(key, object));
    }

    public static Object getHttpTaoBaoFetcher(String key) {
        Element element = TAOBAO_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpTaoBaoFetcher(String key) {
        TAOBAO_CACHE.remove(key);
    }

    /*
     * ========================================以下一号店==========================
     * ===========*
     */
    public static void putHttpYiHaoDianFetcher(String key, Object object) {
        Element element = YIHAODIAN_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TAOBAO_CACHE.put(new Element(key, object));
    }

    public static Object getHttpYiHaoDianFetcher(String key) {
        Element element = YIHAODIAN_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpYiHaoDianFetcher(String key) {
        YIHAODIAN_CACHE.remove(key);
    }

    /*
     * ============================JD========================
     */
    public static void putHttpJD(String key, Object object) {
        Element element = JD_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JD_CACHE.put(new Element(key, object));
    }

    public static Object getHttpJD(String key) {
        Element element = JD_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpJD(String key) {
        JD_CACHE.remove(key);
    }

    /*
     * ============================Chsi========================
     */
    public static void putHttpChsi(String key, Object object) {
        Element element = Chsi_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Chsi_CACHE.put(new Element(key, object));
    }

    public static Object getHttpChsi(String key) {
        Element element = Chsi_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpChsi(String key) {
        Chsi_CACHE.remove(key);
    }

    /*
     * ============================QQ========================
     */
    public static void putHttpQQ(String key, Object object) {
        Element element = QQ_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        QQ_CACHE.put(new Element(key, object));
    }

    public static Object getHttpQQ(String key) {
        Element element = QQ_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpQQ(String key) {
        QQ_CACHE.remove(key);
    }

    /*
     * ========================================以下Http简版征信报告======================
     */
    public static void putHttpCreditReportFetcher(String key, Object object) {
        Element element = CREDITREPORT_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CREDITREPORT_CACHE.put(new Element(key, object));
    }

    public static Object getHttpCreditReportFetcher(String key) {
        Element element = CREDITREPORT_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpCreditReportFetcher(String key) {
        CREDITREPORT_CACHE.remove(key);
    }

    /*
     * ========================================以上Http简版征信报告======================
     */

    /*
     * ========================================以下Http借记卡======================
     */
    public static void putHttpDebitCardFetcher(String key, Object object) {
        Element element = NETBANKCARD_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            // 杀掉再用的浏览器（IE、firefox）
            // ProcessUtils.killProcess(new String[] { "firefox.exe",
            // "iexplore.exe", "IEDriverServer_32.exe" });
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        NETBANKCARD_CACHE.put(new Element(key, object));
    }

    public static Object getHttpDebitCardFetcher(String key) {
        Element element = NETBANKCARD_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    /**
     * 更新网银的Cache
     * 
     * @author wang
     * @param key
     * @return
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    public static void upDateDebitCardFetcher(String key) {
        Object object = getHttpDebitCardFetcher(key);
        if (object != null) {
            List<String> keys = NETBANKCARD_CACHE.getKeys();
            for (String keyVal : keys) {
                logger.info("keys:" + keyVal + "\tvalue:" + NETBANKCARD_CACHE.get(keyVal).getObjectValue());
            }
            logger.info("删除缓存:" + NETBANKCARD_CACHE.remove(key));
            NETBANKCARD_CACHE.put(new Element(key, object, false, 300, 2700));
            keys = NETBANKCARD_CACHE.getKeys();
            logger.info("更新缓存后");
            for (String keyVal : keys) {
                logger.info("keys:" + keyVal + "\tvalue:" + NETBANKCARD_CACHE.get(keyVal).getObjectValue());
            }
        } else {
            logger.info("失去了缓存:" + key);
        }
    }

    public static void removeHttpDebitCardFetcher(String key) {
        NETBANKCARD_CACHE.remove(key);
    }

    /*
     * ========================================以上Http借记卡======================
     */

    /*
     * ========================================以下HttpQQ截图信息======================
     */
    public static void putHttpQQSeleniumFetcher(String key, Object object) {
        Element element = CREDITREPORT_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CREDITREPORT_CACHE.put(new Element(key, object));
    }

    public static Object getHttpQQSeleniumFetcher(String key) {
        Element element = CREDITREPORT_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpQQSeleniumFetcher(String key) {
        CREDITREPORT_CACHE.remove(key);
    }

    /*
     * ========================================以上HttpQQ截图信息======================
     */

    public static void putSeleniumError(String key, String seleniumCode) {
        SELENIUM_ERROR_CACHE.put(new Element(key, seleniumCode));
    }

    public static String getSeleniumError(String key) {
        if (null != SELENIUM_ERROR_CACHE.get(key)) {
            return (String) SELENIUM_ERROR_CACHE.get(key).getObjectValue();
        }
        return null;
    }

    /* =========================httpunit================================ */

    public static void putHttpUnitError(String key, String seleniumCode) {
        HTTPUNIT_ERROR_CACHE.put(new Element(key, seleniumCode));
    }

    public static String getHttpUnitError(String key) {
        if (null != HTTPUNIT_ERROR_CACHE.get(key)) {
            return (String) HTTPUNIT_ERROR_CACHE.get(key).getObjectValue();
        }
        return null;
    }

    /* =============================亚马逊 Amazon==================== */

    public static void putHttpAmazon(String key, Object object) {
        Element element = AMAZON_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AMAZON_CACHE.put(new Element(key, object));
    }

    public static Object getHttpAmazonFetcher(String key) {
        Element element = AMAZON_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpAmazon(String key) {
        AMAZON_CACHE.remove(key);
    }

    /*
     * ============================job========================
     */
    public static void putHttpJob(String key, Object object) {
        Element element = Job_CACHE.get(key);
        if (element != null && element.getObjectValue() != null && element.getObjectValue() instanceof Closeable) {
            Closeable closeable = (Closeable) element.getObjectValue();
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Job_CACHE.put(new Element(key, object));
    }

    public static Object getHttpJob(String key) {
        Element element = Job_CACHE.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public static void removeHttpJob(String key) {
        Job_CACHE.remove(key);
    }

}
