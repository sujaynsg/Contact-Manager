package com.contactmanager.constants;

import java.io.File;

/**
 * Created by sujay-2613 on 2/7/15.
 */
public class CMConstants
{
	public static final String APP_PATH = getAppPath();

	private static String getAppPath()
	{
		String path = CMConstants.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		String appPath = new File(path).getParentFile().getParentFile().toString();
		return appPath;
	}
}
