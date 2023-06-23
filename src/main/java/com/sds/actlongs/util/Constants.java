package com.sds.actlongs.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String HYPHEN = "-";
	public static final String DOT = ".";
	public static final String WILDCARD = "*";
	public static final String ALL_SUB_PATHS = "/**";
	public static final String ALL_PATHS = "/*";
	public static final String CATEGORY_PREFIX = "/";

	public static final String API_LOGIN = "/members/login";
	public static final String S3_BUCKET_HOST_THUMBNAIL_URL = "https://act-longs.s3.ap-northeast-2.amazonaws.com/thumbnails";
}
