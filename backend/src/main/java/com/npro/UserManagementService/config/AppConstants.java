package com.npro.UserManagementService.config;

public class AppConstants {

    public static final boolean INIT_DATA = true;
    public static final boolean CLEAN_DB = false;
    public static final String MAX_RECORDS_S = "100";
    public static final String PAGE_NUMBER = "0";
    public static final String DEFAULT_SORT_BY = "username";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";
    public static final String PAGE_SIZE = "10";
    public static final long ACCESS_TOKEN_VALIDITY =  1 * 60 * 1000; // 5 minutes
        public static final long REFRESH_TOKEN_VALIDITY = 2 * 60 * 1000;
//    public static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;

}
