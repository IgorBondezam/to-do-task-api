package com.igor.bondezam.ToDoTask.config.filter;


import java.util.Map;
import java.util.Optional;

import com.igor.bondezam.ToDoTask.config.filter.control.FilterControl;
import com.igor.bondezam.ToDoTask.context.UserContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class FilterAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private jakarta.persistence.EntityManager em;

    @Autowired
    private ApplicationContext applicationContext;

    @Before("execution(public * org.springframework.data.repository.Repository+.*(..)) || bean(*Service)")
    public void beforeService() {
        Session session = (Session) em.getDelegate();
        if (session.isConnected()) {
            try {
                execute(session, applicationContext.getBean(FilterControl.class));
            } catch (BeansException e) {
                logger.debug(e.getMessage(), e);
                FilterControl filterControl = new FilterControl();
                execute(session, filterControl);
            }
        }
    }

    private void execute(Session session, FilterControl filterControl) {
        Optional.ofNullable(UserContextHolder.getAuthenticatedUserId())
                .ifPresent(id -> filterControl.applyFilter(session, "userFilter", Map.of("userId", id)));
    }

}
