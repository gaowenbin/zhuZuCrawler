package com.zhizu.crawler.cache.listener;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1 -7
 * Time: 上午10:32
 * To change this template use File | Settings | File Templates.
 */
import java.io.Closeable;
import java.io.IOException;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

import org.apache.log4j.Logger;

public class CrawlerCacheEventListener implements CacheEventListener {
    private static final Logger logger = Logger.getLogger(CrawlerCacheEventListener.class);
    public static final CacheEventListener INSTANCE = new CrawlerCacheEventListener();

    @Override
    public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
        logger.info(element.getObjectKey() + " element removed frome" + cache.getName());
    }

    @Override
    public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
        logger.info(element.getObjectKey() + " element put in " + cache.getName());
    }

    @Override
    public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
        logger.info(element.getObjectKey() + " element Updated in " + cache.getName());
    }

    @Override
    public void notifyElementExpired(Ehcache cache, Element element) {
        Closeable closeable = (Closeable) element.getObjectValue();
        String className = closeable == null ? "" : closeable.getClass().getName();
        logger.info("关闭资源1(notifyElementExpired)[" + element.getObjectKey() + ", " + className + "]");
        // 过期的话就需要关闭资源
        updateService(cache, element, true);
    }

    @Override
    public void notifyElementEvicted(Ehcache cache, Element element) {
        Closeable closeable = (Closeable) element.getObjectValue();
        String className = closeable == null ? "" : closeable.getClass().getName();
        logger.info("关闭资源2(notifyElementEvicted)[" + element.getObjectKey() + ", " + className + "]");
        updateService(cache, element, true);
    }

    @Override
    public void notifyRemoveAll(Ehcache cache) {
        logger.debug("All cache has been removed.");
    }

    @Override
    public void dispose() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 更新注册中心的服务
     * 
     * @author zhuyuhang
     * @param cache
     * @param closeResource
     *            是否关闭资源
     */
    private void updateService(Ehcache cache, Element element, boolean closeResource) {
        logger.info("更新注册中心服务状态:" + cache.getName());
        if (closeResource) {
            if (element != null && element.getObjectValue() instanceof Closeable) {
                Closeable closeable = (Closeable) element.getObjectValue();
                logger.info("关闭资源[" + element.getObjectKey() + ", "
                        + (closeable == null ? "" : closeable.getClass().getName()) + "]");
                try {
                    closeable.close();
                    closeable = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
