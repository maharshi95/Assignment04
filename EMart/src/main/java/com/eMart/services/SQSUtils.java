package com.eMart.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by maharshigor on 11/07/16.
 */

public class SQSUtils {

	private static final String QUEUE_NAME = "cnu2016_mgor";
	private static final Logger log = LoggerFactory.getLogger (SQSUtils.class);
	private static final AmazonSQS sqs;

	static {
		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider().getCredentials();
		} catch (Exception e) {
			//TODO: cannot find credentials, throw exception
			log.info ("Cannot create Credentials");
		}
		sqs = new AmazonSQSClient(credentials);
		Region usEast1 = Region.getRegion(Regions.US_EAST_1);
		sqs.setRegion(usEast1);
	}

	public static AmazonSQS getClient() {
		return sqs;
	}


	public static void sendMessage(String message) {
		try {
			String myQueueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
			sqs.sendMessage(new SendMessageRequest(myQueueUrl, message));

		} catch (AmazonServiceException ase) {
			//TODO: catch exception
		} catch (AmazonClientException ace) {
			//TODO: catch exception
		}
	}
}
