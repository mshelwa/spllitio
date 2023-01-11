package com.featureflags.splitio.infrastructure.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.split.client.SplitClient;
import io.split.client.SplitClientConfig;
import io.split.client.SplitFactory;
import io.split.client.SplitFactoryBuilder;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SplitConfiguration {

	public static final String SPLIT_API_KEY = "k4hpmstgb2i8btarvnu8ktkn448oig3i3rhd";
	public static final int BLOCK_TIME_UNTIL_READY_TIMEOUT = 10000;

	@Bean
	public SplitClient createSplitClient() {

		final SplitFactory splitFactory = createSplitFactory();

		if (splitFactory == null) {
			return null;
		}

		final SplitClient client = splitFactory.client();
		try {

			client.blockUntilReady();

		} catch (final TimeoutException | InterruptedException e) {

			log.error("Failed to instantiate Split client due to {}", e.getMessage(), e);
			return null;
		}

		log.info("Split client is ready for use!!");
		return client;
	}

	private SplitFactory createSplitFactory() {

		final SplitClientConfig config = SplitClientConfig.builder()
				.setBlockUntilReadyTimeout(BLOCK_TIME_UNTIL_READY_TIMEOUT)
				.build();

		final SplitFactory splitFactory;
		try {

			splitFactory = SplitFactoryBuilder.build(SPLIT_API_KEY, config);

		} catch (final IOException | URISyntaxException e) {

			log.error("Failed to instantiate Split client due to {}", e.getMessage(), e);
			return null;
		}

		return splitFactory;
	}
}
