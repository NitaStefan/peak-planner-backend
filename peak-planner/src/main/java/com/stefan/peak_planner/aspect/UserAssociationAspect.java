package com.stefan.peak_planner.aspect;

import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.model.UserOwned;
import com.stefan.peak_planner.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Aspect
@Component
public class UserAssociationAspect {

    private final AuthService authService;

    public UserAssociationAspect(AuthService authService) {
        this.authService = authService;
    }

    @Before("execution(com.stefan.peak_planner.model.UserOwned+ com.stefan.peak_planner.service.*.save*(com.stefan.peak_planner.model.UserOwned+))")
    public void associateUserToSavedEntity(JoinPoint joinPoint) {

        // retrieve current user
        User user = authService.extractUserFromRequest(getCurrentHttpRequest());

        // retrieve the UserOwned entity
        UserOwned entity = (UserOwned) joinPoint.getArgs()[0];

        // set the user to entity
        entity.setUser(user);
    }

    @Before("execution(* com.stefan.peak_planner.service.*.save*(java.util.List+))")
    public void associateUserToListOfSavedEntities(JoinPoint joinPoint) {
        // Retrieve current user
        User user = authService.extractUserFromRequest(getCurrentHttpRequest());

        // Retrieve and update the list of UserOwned entities
        Object firstArg = joinPoint.getArgs()[0];

        if (firstArg instanceof List<?> list)
            list.stream()
                    .filter(UserOwned.class::isInstance)
                    .map(UserOwned.class::cast)          // cast them to UserOwned to access setUser method
                    .forEach(entity -> entity.setUser(user));

    }

    @Around("execution(* com.stefan.peak_planner.service.*.get*(com.stefan.peak_planner.model.User, ..)) || " +
            "execution(* com.stefan.peak_planner.service.*.updateSchedule(com.stefan.peak_planner.model.User, ..))")
    public Object injectTheUserForEntityRetrieval(ProceedingJoinPoint joinPoint) throws Throwable {

        User user = authService.extractUserFromRequest(getCurrentHttpRequest());

        Object[] args = joinPoint.getArgs();

        args[0] = user;

        return joinPoint.proceed(args);
    }

    private HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
