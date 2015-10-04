package com.ejb.sessions;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless(name = "SearchFacade")
@LocalBean
public class SearchFacadeBean implements Serializable
{

    private static final long serialVersionUID = -1L;

    HashMap countryMap = new HashMap();

    public List wineSearch(String wineType)
    {
        List wineList = new ArrayList();
        if (wineType.equals("Red")) {
            wineList.add("Bordeaux");
            wineList.add("Merlot");
            wineList.add("Pinot Noir");
        }
        else if (wineType.equals("White")) {
            wineList.add("Chardonnay");
        }

        return wineList;
    }

    @PostConstruct
    public void initializeCountryWineList()
    {
        countryMap.put("Australia", "Sauvignon Blanc");
        countryMap.put("Australia", "Grenache");
        countryMap.put("France", "Gewurztraminer");
        countryMap.put("France", "Bordeaux");
    }

    @PreDestroy
    public void destroyWineList()
    {
        countryMap.clear();
    }

    /*
    @AroundInvoke
    public Object TimerLog(InvocationContext ctx)
            throws Exception {
        String beanClassName = ctx.getClass().getName();
        String businessMethodName = ctx.getMethod().getName();
        String target = beanClassName + "." + businessMethodName;
        long startTime = System.currentTimeMillis();
        System.out.println("Invoking " + target);
        try {
            return ctx.proceed();
        }
        finally {
            System.out.println("Exiting " + target);
            long totalTime = System.currentTimeMillis() - startTime;
            System.out.println("Business method " + businessMethodName + "in " +
                    beanClassName + "takes " + totalTime +
                    "ms to execute");
        }
    }
    */
}