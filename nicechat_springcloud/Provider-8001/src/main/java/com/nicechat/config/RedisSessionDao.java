package com.nicechat.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisSessionDao extends AbstractSessionDAO {

    public long PC_EXPIRE_TIME = 60 * 60;//秒为单位

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = SessionCons.TOKEN_PREFIX + UUID.randomUUID().toString();
        assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(sessionId, session);
        redisTemplate.expire(sessionId, PC_EXPIRE_TIME, TimeUnit.SECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = (Session) redisTemplate.opsForValue().get(sessionId);
        if (null != session) {
            session.setTimeout(PC_EXPIRE_TIME * 1000);
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (null != session && null != session.getId()) {
            redisTemplate.opsForValue().set(session.getId(), session);
            session.setTimeout(PC_EXPIRE_TIME * 1000);
            redisTemplate.expire(session.getId(), PC_EXPIRE_TIME,
                    TimeUnit.SECONDS);
        }
    }

    @Override
    public void delete(Session session) {
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Serializable> keys = redisTemplate.keys(SessionCons.TOKEN_PREFIX_KEY);
        if (keys.size() == 0) {
            return Collections.emptySet();
        }
        List<Session> sessions = redisTemplate.opsForValue().multiGet(keys);
        return Collections.unmodifiableCollection(sessions);
    }
}
