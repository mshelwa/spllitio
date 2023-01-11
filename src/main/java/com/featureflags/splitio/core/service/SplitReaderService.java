package com.featureflags.splitio.core.service;

public interface SplitReaderService {

	String getValue(String splitName, String userId, String userAttribute);
}
