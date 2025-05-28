package com.igor.bondezam.ToDoTask.config.filter.control;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FilterControl {

    public void applyFilter(Session session, String filterName) {
        this.applyFilter(session, filterName, new HashMap<>());
    }

    public void applyFilter(Session session, String filterName, Map<String, Object> paramaters) {
            Filter filter = session.enableFilter(filterName);
            paramaters.forEach(filter::setParameter);
    }

    public void disableFilter(Session session, String filterName) {
        session.disableFilter(filterName);
    }
}
