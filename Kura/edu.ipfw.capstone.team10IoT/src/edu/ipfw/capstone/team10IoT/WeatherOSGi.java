package edu.ipfw.capstone.team10IoT;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherOSGi implements DataServiceListener{
	public DataService dataservice;

    private static final Logger s_logger = LoggerFactory.getLogger(WeatherOSGi.class);

    private static final String APP_ID = "edu.ipfw.capstone.Team10IoT";

    protected void activate(ComponentContext componentContext) {

        s_logger.info("Bundle " + APP_ID + " has started!");

        s_logger.debug(APP_ID + ": This is a debug message.");

    }

    protected void deactivate(ComponentContext componentContext) {

        s_logger.info("Bundle " + APP_ID + " has stopped!");

    }

}