package com.featureflags.splitio.entrypoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.featureflags.splitio.core.service.SplitReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/split-client")
@RequiredArgsConstructor
@Log4j2
public class SplitReaderController {

	private final SplitReaderService splitReaderService;

	@GetMapping("/merchant/{merchant-id}/features/{feature-flag}")
	public ResponseEntity<String> readLastValue(@PathVariable("feature-flag") final String featureFlagName,
												@PathVariable("merchant-id") final String merchantUserId,
												@RequestParam(name = "userAttribute", required = false) final String userAttribute) {

		final String featureFlagValue = splitReaderService.getValue(featureFlagName, merchantUserId, userAttribute);

		return ResponseEntity.ok(featureFlagValue);
	}
}
