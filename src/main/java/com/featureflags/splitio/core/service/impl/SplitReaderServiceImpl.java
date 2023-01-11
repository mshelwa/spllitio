package com.featureflags.splitio.core.service.impl;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.featureflags.splitio.core.service.SplitReaderService;
import io.split.client.SplitClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class SplitReaderServiceImpl implements SplitReaderService {

	public static final String RING_INTEGRATION_CUSTOM_ATTRIBUTE = "ring-integration";
	private final SplitClient splitClient;

	@Override
	public String getValue(final String splitName, final String userId, final String userAttribute) {

		Map<String, Object> userAttributes = Collections.singletonMap(RING_INTEGRATION_CUSTOM_ATTRIBUTE, userAttribute);

		final String treatment = splitClient.getTreatment(userId, splitName, userAttributes);

		log.info("Evaluation of Split {} is: {} ", splitName, treatment);

		return treatment;
	}
}
